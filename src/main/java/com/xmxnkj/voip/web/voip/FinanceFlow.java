package com.xmszit.voip.web.voip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.xmszit.voip.customer.entity.CallRecord;
import com.xmszit.voip.customer.entity.CallRecordDetail;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.service.CallRecordDetailService;
import com.xmszit.voip.customer.service.CallRecordService;
import com.xmszit.voip.customer.service.CustomerService;
import com.xmszit.voip.ivr.entity.AiResponse;
import com.xmszit.voip.ivr.entity.Flow;
import com.xmszit.voip.ivr.entity.query.AiResponseQuery;
import com.xmszit.voip.ivr.entity.query.FlowQuery;
import com.xmszit.voip.ivr.service.AiResponseService;
import com.xmszit.voip.ivr.service.FlowService;
import com.xmszit.voip.outBound.entity.AutodialerTask;
import com.xmszit.voip.outBound.service.AutodialerTaskService;

import net.sf.json.JSONObject;

/**
 * @ProjectName:voip
 * @ClassName: FinanceFlow
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service("financeFlow")
@Scope("prototype")
public class FinanceFlow {
	private Flow start;
	private Map<String, Flow> flows;
	private List<AiResponse> globalAiResponse;
	private List<Flow> globalFlows;
	private List<String> answeredMainFlow;
	//已回答全局问题次数
	private int globalResponseCount=0;
	private int needGlobalResponseCount=5;
	
	
	private String mainStartCode="2.1";
	private String goBackHeader = "back_";
	private String globalHeader = "g";
	
	private int repeatCount=0;
	private String lastResponse;
	private String lastSound;
	private String callId;
	
	private List<String> globalRefuseResponse;
	//全局问题拒绝后转向的步骤
	private String globalRefuseNextCode="5.1";
			
	
	private List<String> mainFlows = new ArrayList<>(Arrays.asList("2.1","2.2","2.3","2.4","2.5"));
	
	@Autowired
	private FlowService flowService;
	@Autowired
	private AiResponseService aiResponseService;
	
	private Date startTime;
	private int weiSecond=5*1000;
	
	public void loadData(){
		System.out.println("start load data");
		
		String templateId = "01f72f54-625b-46af-8836-a4ef5c4ba050";
		String clientId = "d70a3c49-c34a-4ecc-a808-f1cf04cb6278";
		
		answeredMainFlow = new ArrayList<>();
		
		mainStartCode=null;
		
		globalRefuseResponse = new ArrayList<>(Arrays.asList("不需要","不用","没兴趣","没钱","不考虑","没考虑","打错了","不想了解","别打了","不要再打了","没有需要","不用介绍","已经买了","已经贷了","没有需求","别给我打电话了","不想要","有空再说","不需要","不要","不用","不用不用","不需要不需要", "我闭了","不必了","没兴趣","用不上","用不到"));
		
		FlowQuery flowQuery = new FlowQuery();
		flowQuery.setFlowTemplateId(templateId);
		flowQuery.setClientId(clientId);
		List<Flow> flowLists = flowService.getEntities(flowQuery);
		if (flows==null){
			flows = new HashMap<>();
		}
		flows.clear();
		mainFlows.clear();
		for (Flow flow : flowLists) {
			
			if (!StringUtils.isEmpty(flow.getKeywordsString())) {
				flow.setKeywordsString(flow.getKeywordsString().replace(" ", "").replace("，", ",").replace("、", ","));
				flow.setKeywords(new ArrayList<String>(Arrays.asList(flow.getKeywordsString().split(","))));
			}
			
			flows.put(flow.getCode(), flow);
			if ("hello".equals(flow.getCode())) {
				start = flow;
				System.out.println("start flow code:" + flow.getCode());
			}
			if (flow.getIsMainFlow()) {
				mainFlows.add(flow.getCode());
				if (StringUtils.isEmpty(mainStartCode)) {
					mainStartCode=flow.getCode();
				}
			}
		}
		
		if (globalAiResponse==null) {
			globalAiResponse = new ArrayList<>();
		}
		globalAiResponse.clear();
		
		AiResponseQuery aiResponseQuery = new AiResponseQuery();
		aiResponseQuery.setTemplateId(templateId);
		aiResponseQuery.setClientId(clientId);
		
		List<AiResponse> responses = aiResponseService.getEntities(aiResponseQuery);
		if (responses != null && responses.size()>0) {
			for (AiResponse aiResponse : responses) {
				if (!StringUtils.isEmpty(aiResponse.getKeywordsString())) {
					aiResponse.setKeywordsString(aiResponse.getKeywordsString().replace(" ", "").replace("，", ",").replace("、", ","));
					aiResponse.setKeywords(new ArrayList<String>(Arrays.asList(aiResponse.getKeywordsString().split(","))));
				}
				if (StringUtils.isEmpty(aiResponse.getFlowId())) {
					globalAiResponse.add(aiResponse);
				}else{
					for (String key : flows.keySet()) {
						if (aiResponse.getFlowId().equalsIgnoreCase(flows.get(key).getId())) {
							if (flows.get(key).getAiResponses()==null) {
								flows.get(key).setAiResponses(new ArrayList<AiResponse>());
							}
							flows.get(key).getAiResponses().add(aiResponse);
							break;
						}
					}
				}
			}
		}
		
		
		startTime = Calendar.getInstance().getTime();
	}
	
	
	public void init(){
		if (flows==null) {
			loadData();
		}
	}

	
	public void clear(){
		if(answeredMainFlow!=null){
			answeredMainFlow.clear();
		}
	}
	
	public Map<String, Object> executeFlow(String lastFlowCode, String message){
		
		init();
		
		if (!StringUtils.isEmpty(lastFlowCode) && !StringUtils.isEmpty(message)) {
			
			//处理"喂"
			Map<String, Object> wei = dealWei(lastFlowCode, message);
			if (wei!=null) {
				return wei;
			}
			
			//是否为用户问全局问题
			AiResponse response = findGlobalFlow(message);
			if (response!=null) {
				return makeGlobalFlowResponse(response, lastFlowCode);
			}
			
			//全局拒绝转挽回
			if (isGlobalResponseRefuse(lastFlowCode, message)) {
				Flow flow = flows.get(globalRefuseNextCode);
				return response(flow.getCode(), flow.getAnswer(), flow.getSound(), flow.getBlockAsr());
			}
			
			
			//返回的流程gb开头，即全局问题返回主流程继续执行
			if (lastFlowCode.startsWith(goBackHeader)) {
				Map<String, Object> res = makeOriginalResponse();
				if (res!=null) {
					return res;
				}
			}
			
			
			//全局问题的回答
			if (lastFlowCode.startsWith(globalHeader)) {
				response = findGlobalFlowByCode(lastFlowCode);
				if (response!=null 
						&& !StringUtils.isEmpty(response.getNextStepCode()) 
						&& flows.containsKey(response.getNextStepCode())) {
					Flow flow = flows.get(getSplitCode(response.getNextStepCode()));
					if (!StringUtils.isEmpty(flow.getAnswer())) {
						return response(flow.getCode(), flow.getAnswer(), flow.getSound(), flow.getBlockAsr());
					}
				}
			}
			
			if (flows.containsKey(lastFlowCode)) {
				Flow flow = flows.get(lastFlowCode);
				if (flow.getAiResponses()!=null && flow.getAiResponses().size()>0) {
					AiResponse aiResponse = findAiResponse(message, flow.getAiResponses());
					if (aiResponse!=null) {
						Map<String, Object> res = makeResponse(aiResponse, flow);
						if (res!=null) {
							return res;
						}
					}else if(flow.getRemainOriginal()){
						Map<String, Object> res = makeOriginalResponse();
						if (res!=null) {
							return res;
						}
					}
				}else if (!StringUtils.isEmpty(flow.getAnswer())) {
					//直接回答的，没有后续步骤的
					return response(flow.getCode(), flow.getAnswer(), flow.getSound(), flow.getBlockAsr());
				}else if(flow.getRemainOriginal()){
					Map<String, Object> res = makeOriginalResponse();
					if (res!=null) {
						return res;
					}
				}
			}
			
		}
		
		return response(lastFlowCode, "信号不好，麻烦再说一遍", null, false);
		
	}
	
	private Map<String, Object> dealWei(String lastFlowCode, String message){
		message = message.replace(";", "");
		String[] arr = message.split("\\.");
		if (arr.length==2
				&& (arr[1].equals("喂") || arr[1].equals("喂。"))
				&& Calendar.getInstance().getTime().getTime()-startTime.getTime()>weiSecond) {
			//是否为用户问全局问题
			AiResponse response = findGlobalFlowByCode("g23");
			if (response!=null) {
				return makeGlobalFlowResponse(response, lastFlowCode);
			}
		}
		return null;
	}
	
	/**
	 * 判断是否为全局问题的拒绝回答
	 * @param lastFlowCode
	 * @param message
	 * @return
	 */
	private boolean isGlobalResponseRefuse(String lastFlowCode, String message){
		if (lastFlowCode.startsWith(goBackHeader)
				|| lastFlowCode.startsWith(globalHeader)) {
			for (String str : globalRefuseResponse) {
				if (message.contains(str)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Map<String, Object> makeOriginalResponse(){
		if (answeredMainFlow.size()>0) {
			String code = answeredMainFlow.get(answeredMainFlow.size()-1);
			Flow originalFlow = flows.get(code);
			return response(originalFlow.getCode(), originalFlow.getAnswer(), originalFlow.getSound(), originalFlow.getBlockAsr());
		}else{
			Flow originalFlow = flows.get(mainStartCode);
			return response(originalFlow.getCode(), originalFlow.getAnswer(), originalFlow.getSound(), originalFlow.getBlockAsr());
		}
	}
	
	private Map<String, Object> makeGlobalFlowResponse(AiResponse aiResponse, String lastFlowCode){
		if (aiResponse.getRepeat()
				&& !StringUtils.isEmpty(lastResponse)) {
			repeatCount++;
			return response(lastFlowCode, lastResponse, lastSound, true);
		}
		
		globalResponseCount++;
		
		//answeredMainFlow.add(aiResponse.getCode());
		if (answeredMainFlow.size()<needGlobalResponseCount ) {
			//&& !StringUtils.isEmpty(aiResponse.getRemainOriginalAnswer())
			String code = lastFlowCode.startsWith(goBackHeader)?lastFlowCode:(goBackHeader+lastFlowCode);
			return response(code, aiResponse.getAnswer(), aiResponse.getSound(), aiResponse.getBlockAsr());
//			return response(goBackHeader+lastFlowCode, aiResponse.getRemainOriginalAnswer(), aiResponse.getSound());
		}else{
			return response(aiResponse.getCode(), aiResponse.getAnswer(), aiResponse.getSound(), aiResponse.getBlockAsr());
		}
		
	}
	
	private void addAnsweredMainFlow(String code){
		if (answeredMainFlow==null) {
			answeredMainFlow = new ArrayList<>();
		}
		if (!answeredMainFlow.contains(code)) {
			answeredMainFlow.add(code);
		}
	}
	
	private Map<String, Object> makeResponse(AiResponse aiResponse, Flow flow){
		if (aiResponse!=null) {
			if (!StringUtils.isEmpty(aiResponse.getNextStepCode())) {
				String nextStepCode = null;
				nextStepCode = getSplitCode(aiResponse.getNextStepCode());
				if (flows.containsKey(nextStepCode)) {
					Flow nextFlow = flows.get(nextStepCode);
					if (nextFlow.getIsMainFlow()) {
						addAnsweredMainFlow(nextFlow.getCode());
					}
					return response(nextFlow.getCode(), nextFlow.getAnswer(), nextFlow.getSound(), nextFlow.getBlockAsr());
				}
			}else if (!StringUtils.isEmpty(aiResponse.getAnswer())) {
				return response(flow.getCode(), aiResponse.getAnswer(), aiResponse.getSound(), aiResponse.getBlockAsr());
			}
		}
		return null;
	}
	
	private String getSplitCode(String code){
		String rst = null;
		if (code.contains(",")) {
			String[] arrCode = code.split(",");
			int index = new Random().nextInt(arrCode.length);
			rst = arrCode[index];
		}else{
			rst = code;
		}
		return rst;
	}
	
	private AiResponse findGlobalFlowByCode(String code){
		for (AiResponse aiResponse : globalAiResponse) {
			if (aiResponse.getCode().equals(code)) {
				return aiResponse;
			}
		}
		return null;
	}
	
	private AiResponse findGlobalFlow(String message){
		for (AiResponse aiResponse : globalAiResponse) {
			if (aiResponse.getKeywords()!=null) {
				for (String key : aiResponse.getKeywords()) {
					if (message.contains(key)) {
						return aiResponse;
					}
				}
			}
		}
		return null;
	}
	
	private AiResponse findAiResponse(String message, List<AiResponse> aiResponses){
		AiResponse defaultResponse = null;
		for (AiResponse aiResponse : aiResponses) {
			if (aiResponse.getKeywords()!=null && aiResponse.getKeywords().size()>0){
				for (String key : aiResponse.getKeywords()) {
					if (message.contains(key)) {
						return aiResponse;
					}
				}
			}else{
				defaultResponse = aiResponse;
			}
		}
		return defaultResponse;
	}
	
	
	
	
	public Flow getStart() {
		return start;
	}
	public void setStart(Flow start) {
		this.start = start;
	}
	public Map<String, Flow> getFlows() {
		return flows;
	}
	public void setFlows(Map<String, Flow> flows) {
		this.flows = flows;
	}
	public List<AiResponse> getGlobalAiResponse() {
		return globalAiResponse;
	}
	public void setGlobalAiResponse(List<AiResponse> globalAiResponse) {
		this.globalAiResponse = globalAiResponse;
	}

	private Map<String,Object> response(String flow,String text, String sound, Boolean blockAsr){
		return response(flow, text, sound, false, blockAsr);
	}
	
	
	public Map<String, Object> hello(){
		init();
		
		saveResponseRecordThread(start.getAnswer());
		
		Map<String,Object> map = new HashMap<String, Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		Map<String,Object> after_params = new HashMap<String, Object>();
		map.put("action", "start_asr");
		map.put("after_action", "playback");
		map.put("flowdata", start.getCode());
		map.put("after_ignore_error", false);					
		params.put("min_speak_ms", 100);
		params.put("max_speak_ms", 10000);
		params.put("min_pause_ms", 300);
		params.put("max_pause_ms", 600);
		params.put("pause_play_ms", 600);//暂停播放毫秒
		params.put("threshold", 0);//VAD阈值，默认0，建议不要设置，如果一定要设置，建议 2000以下的值。
		params.put("volume", 50);
		params.put("recordpath", "");
		params.put("filter_level", 0);
		map.put("params", params);
		if (!StringUtils.isEmpty(start.getSound()) && !getIsDebug()) {
			after_params.put("prompt", start.getSound());
		}else{
			after_params.put("prompt", start.getAnswer());
		}
		after_params.put("wait", 5000);
		after_params.put("retry", 0);
		after_params.put("block_asr", -1);
		map.put("after_params", after_params);
		return map;
	}
	
	public Map<String, Object> outTimeResponse(String flow){
		if (flow.equals("hello")) {
			//是否为用户问全局问题
			AiResponse response = findGlobalFlowByCode("g23");
			if (response!=null) {
				return makeGlobalFlowResponse(response, "hello");
			}
		}
		return response(flow, "你好", "nihao.wav", true);
	}
	
	
	private Map<String,Object> response(String flow,String text, String sound, boolean repeat, Boolean blockAsr){
		
		saveResponseRecordThread(text);
		
		if (mainFlows.contains(flow)) {
			addAnsweredMainFlow(flow);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("action", "playback");
        //缓存中取出这步的业务名称
		map.put("flowdata",flow);					
		Map<String,Object> params = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(sound) && !getIsDebug()) {
			params.put("prompt",sound);
			System.out.println("sound:" + sound);
		}else{
			params.put("prompt",text);
			System.out.println("text:" + text);
		}
		
		params.put("wait", 4000);
		params.put("retry", 0);
		if (blockAsr!=null && blockAsr) {
			params.put("block_asr", -1);
		}
		
		map.put("params", params);
		
		
		lastResponse = text;
		lastSound=sound;
		if (!repeat) {
			repeatCount=0;
		}
		
		
		return map;
	}
	

	@Autowired
	private CallRecordDetailService callRecordDetailService;
	
	public void saveCallRecordDetailThread(final JSONObject jsonObject){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try{
					saveCallRecordDetail(jsonObject);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CallRecordService callRecordService;
	@Autowired
	private AutodialerTaskService autodialerTaskService;
	private String callRecordId;
	private void saveCallRecordDetail(JSONObject jsonObject){
		CallRecordDetail callRecordDetail = convertToCallRecordDetail(jsonObject);
		if (callRecordDetail.getNotify().equals("enter")) {
			saveCallRecord(callRecordDetail);
		}else{
			AutodialerTask task = autodialerTaskService.getById(callRecordDetail.getFlowId());
			if(task!=null){
				Customer customer = customerService.queryCustomer(callRecordDetail.getCallerId(), task.getClientId());
				if(customer!=null){
					callRecordDetail.setClientId(task.getClientId());
					callRecordDetail.setCallRecordId(callRecordId);
					callRecordDetailService.save(callRecordDetail);
				}
			}
		}
	}
	
	private void saveCallRecord(CallRecordDetail callRecordDetail){
		AutodialerTask task = autodialerTaskService.getById(callRecordDetail.getFlowId());
		if(task!=null){
			CallRecord callRecord = new CallRecord();
			callRecord.setCustomer(customerService.queryCustomer(callRecordDetail.getCallerId(), task.getClientId()));
			if (callRecord.getCustomer()!=null) {
				callRecord.setClientId(task.getClientId());
				callRecord.setCallDate(new Date());
				callRecord.setCallId(callId);
				callRecordService.save(callRecord);
				callRecordId = callRecord.getId();
			}
		}
	}
	
	private int displayOrder=0;
	
	private void saveResponseRecordThread(final String message){
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					saveResponseRecord(message);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}
	
	private void saveResponseRecord(String message){
		CallRecordDetail detail = new CallRecordDetail();
		detail.setMessage(message);
		detail.setCallRecordId(callRecordId);
		detail.setDisplayOrder(displayOrder);
		callRecordDetailService.save(detail);
		
		displayOrder++;
	}
	
	private CallRecordDetail convertToCallRecordDetail(JSONObject jsonObject){
		CallRecordDetail detail = new CallRecordDetail();
		
		detail.setAsrElapse(jsonObject.optString("asrelapse"));
		detail.setAsrTextAll(jsonObject.optString("asrtextall"));
		detail.setAsrType(jsonObject.optString("asrtype"));
		detail.setCallerId(jsonObject.optString("callerid"));
		detail.setCalleeId(jsonObject.optString("calleeid"));
		detail.setDuration(jsonObject.optInt("duration"));
		detail.setErrorCode(jsonObject.optInt("errorcode"));
		detail.setFlowData(jsonObject.optString("flowdata"));
		detail.setFlowId(jsonObject.optString("flowid"));
		detail.setGender(jsonObject.optInt("gender"));
		detail.setMessage(jsonObject.optString("message"));
		detail.setNotify(jsonObject.optString("notify"));
		detail.setOrigCallerId(jsonObject.optString("origcallerid"));
		detail.setPlayms(jsonObject.optInt("playms"));
		detail.setRecordFile(jsonObject.optString("recordfile"));
		if (!StringUtils.isEmpty(detail.getRecordFile())) {
			detail.setRecordFile(detail.getRecordFile().replace("/root/voip/voip", ""));
		}
		detail.setRecordIndex(jsonObject.optInt("recordindex"));
		detail.setRecordms(jsonObject.optInt("recordms"));
		detail.setVolumeGain(jsonObject.optDouble("volumegain"));
		detail.setDisplayOrder(displayOrder);
		
		displayOrder++;
		
		return detail;
	}
	
	
	private Boolean isDebug;
	public Boolean getIsDebug() {
		return false;
		//return isDebug!=null?isDebug:false;
	}
	public void setIsDebug(Boolean isDebug) {
		this.isDebug = isDebug;
	}


	public void setCallId(String callId) {
		this.callId = callId;
	}
	
	
}
