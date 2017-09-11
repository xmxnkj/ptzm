package com.xmxnkj.voip.web.voip;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xmxnkj.voip.web.BaseAction;
import com.xmxnkj.voip.web.servletListener.ApplicationContextUtil;

import net.sf.json.JSONObject;

/**
 * @ProjectName:voip
 * @ClassName: SmartIvr
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/voip")
public class SmartIvr extends BaseAction{
	private static final Logger log = Logger.getLogger(SmartIvr.class);			

	//@Autowired
	private FinanceFlow financeFlow;
	
	private Map<String, FinanceFlow> callFlows = new HashMap<>();
	
	@RequestMapping("showTest")
	public ModelAndView showTest(){
		return new ModelAndView("test");
	}
	
	@ResponseBody
	@RequestMapping("test")
	public Map<String, Object> test(String message){
//		return noop("ssss");
		
		if (financeFlow==null) {
			financeFlow = (FinanceFlow)ApplicationContextUtil.getBean("financeFlow");
		}
		
		financeFlow.setIsDebug(true);
		String lastCode = (String)getSession().getAttribute("lastCode");
		if ("再见".equals(message)) {
			getSession().setAttribute("lastCode", null);
			financeFlow.clear();
			return null;
		}
		
		if("reload".equals(message)){
			getSession().setAttribute("lastCode", null);
			financeFlow.clear();
			financeFlow.loadData();
			return null;
		}
		
		if (StringUtils.isEmpty(lastCode)) {
			lastCode="hello";
		}
		Map<String, Object> result = voiceJudg(financeFlow, lastCode,message);
		lastCode = result.get("flowdata").toString();
		getSession().setAttribute("lastCode", lastCode);
		return result;
	}
	
	
	@RequestMapping("initData")
	public void initData(){
		//financeFlow.saveData();
	}
	
	
	@ResponseBody
	@RequestMapping("smartIvr")
	public Map<String,Object> smatrIvr(HttpServletRequest request,HttpServletResponse response) {
		String json = new String(getReqMsg(request));
        JSONObject jsonDate = JSONObject.fromObject(json);             
        String action = String.valueOf(jsonDate.get("notify"));       
        String flowdata = String.valueOf(jsonDate.get("flowdata"));    
        String message = jsonDate.optString("message");
        String callid = jsonDate.optString("callid");
		Map<String,Object> map = new HashMap<String, Object>();	
		
		//log.info(json);
		System.out.println("action:" + action + ",flowdata:" + flowdata + ",message:" + message);
		
		FinanceFlow flow = null;
		if (callFlows.containsKey(callid)) {
			flow = callFlows.get(callid);
			System.out.println("callid:" + callid);
		}else{
			//(FinanceFlow)ApplicationContextUtils.getBean("financeFlow"); //
			flow = (FinanceFlow)ApplicationContextUtil.getBean("financeFlow");
			flow.setCallId(callid);
			//financeFlow = new FinanceFlow();
			callFlows.put(callid, flow);
		}
		
		
		if("enter".equals(action)){
			flow.clear();
			
			map = flow.hello();
			
			flow.saveCallRecordDetailThread(jsonDate);
			
		}else if("asrprogress_notify".equals(action)){
			
			flow.saveCallRecordDetailThread(jsonDate);
			
			return noop(flowdata);
			//进度流程，按需填写
//			String message = jsonDate.optString("message"); //PinYin.getPingYin(jsonDate.getString("message"));//String.PyvalueOf(jsonDate.get("message")); //将识别结果转换为拼音
//			map = voiceJudg(flowdata,message);
		}else if("asrmessage_notify".equals(action)){
			 //PinYin.getPingYin(jsonDate.getString("message"));//String.PyvalueOf(jsonDate.get("message")); //将识别结果转换为拼音
			if (StringUtils.isEmpty(message)) {
				return noop(flowdata);
			}
			map = voiceJudg(flow, flowdata,message);
		}else if("playback_result".equals(action)){
			//String message = jsonDate.optString("message");
			
			map = flow.outTimeResponse(flowdata); //response(flowdata, "nihao.wav", null); //voiceJudg(flowdata, message);
		}else if ("leave".equals(action)) {
			//结束
			callFlows.remove(callid);
			
		}else{
			return response("", "信号不好，麻烦再说一遍", null);
		}

		//一段时间没响应
		//{"calleeid":"999","callerid":"+8613328795928","callid":"db38dfc5-d1b5-46e9-9002-2270061f1f34","duration":44,"errorcode":0,"flowdata":"您好，我是厦门西牛科技有限公司，我们楼盘很好！","flowid":"","hangup_disposition":"send_bye","notify":"leave"}

		
		return map;
	}
	
	
	
	
			
	/**
	 * 根据语音识别到的message选择放音
	 * @param flow
	 * @param message
	 * @return
	 */
	private Map<String, Object> voiceJudg(FinanceFlow fflow, String flow,String message){
		//动作1
		String action1 ="";
		//动作2 
		String action2 = "喂，您好！  ";
		//动作3
		String action3 = "";
		//动作4
		String action4 = "";
		
//		if("流程选择".equals(flow)){
//			action1 = "Java控制后台开发请联系 QQ: 243305203";
//			action3 = "体验及 定制测试流程请联系 微信: swcxy12315";
//			action4 = "拥有关键词智能识别算法,匹配多个关键词，可分配权重，可定制后台配置界面";
//		}
//		if("测试".equals(flow)){
//			action1 = "你正在进行顶顶通机器人测试";
//			action3 = "拥有关键词智能识别算法,匹配多个关键词，可分配权重，可定制后台配置界面";
//			action4 = "你不了解吗？你现正在进行测试";
//		}
//		if("介绍".equals(flow)){
//			action1 = "这里是顶顶通机器人，正在和你测试";
//			action3 = "定制配置管理后台及接口可联系：QQ: 243305203";
//			action4 = "机器人可以识别人的语音，和你对话";
//		}
//		if("再见".equals(flow)){
//			return hangUp("谢谢使用，再见");
//		}
//		
//		
//		log.debug("message:" + message);
//		if (!StringUtils.isEmpty(message)) {
//			System.out.println(HanLP.segment(message));
//		}
		
		
		
		if ("PLAYBACK ERROR".equals(message)) {
			return response(flow, "信号不好，麻烦再说一遍", null);
		}
		
		return fflow.executeFlow(flow, message);
//		return executeFlow(flow, message);
		
		//action1【权重高】
//		if(("联系").equals(message)||("qq").equals(message)||("说").equals(message)){		
//			return  fangyin(action1);
//		}
//		//action2【权重中】
//		else if(("微信").equals(message)|| ("了解").equals(message)||("好").equals(message)){
//			return fangyin(action3);
//		}
//		
//		//action2【权重低】
//		else if(("不懂").equals(message) ||("不知道").equals(message)||("再来").equals(message)){
//			return fangyin(action4);
//		}
//		//不理解重复action2
//		return fangyin("action2");
	}
	
	/**
	 * 根据流程，组装对应的放音json
	 * @param flow   放音音频
	 * @author cxy
     * @version 2017-04-15
	 */
	public Map<String,Object> response(String flow,String text, String sound){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("action", "playback");
        //缓存中取出这步的业务名称
		map.put("flowdata",flow);					
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("prompt",text);
		params.put("wait", 4000);
		params.put("retry", 2);
		
		map.put("params", params);
		return map;
	} 
	
	private Map<String, Object> noop(String flow){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("action", "noop");
        //缓存中取出这步的业务名称
		map.put("flowdata",flow);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("usermsg","aaa");
		map.put("params", params);
		System.out.println("noop:");
		System.out.println(map);
		return map;
	}
	
	/**
	 * 根据流程，组装对应的放音json
	 * @param flow   放音音频
	 * @author cxy
     * @version 2017-04-15
	 */
	public Map<String,Object> fangyin(String flow){
		Map<String,Object> map = new HashMap<String, Object>();
		if(flow!=null&&"".equals(flow)){
			map.put("action", "playback");
            //缓存中取出这步的业务名称
			map.put("flowdata","测试流程");					
			Map<String,Object> params = new HashMap<String, Object>();
			params.put("prompt",flow);
			params.put("wait", 4000);
			params.put("retry", 2);
			
			map.put("params", params);
			return map;
		}
		
		return null;
	}
	
	
	/**
	 * 放音后挂断
	 * @param voice  结束语
	 * @author cxy
     * @version 2017-04-15
	 * @return
	 */
	public Map<String,Object> hangUp(String voice){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("action", "playback");
		map.put("suspend_asr", true);
		map.put("flowdata", "");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("params", voice);
		map.put("params", params);
		map.put("after_ignore_error", true);
		Map<String,Object> after_params = new HashMap<String, Object>();
		after_params.put("cause", 0);
		after_params.put("usermsg", "");
		map.put("after_params", after_params);
		return map;
	}
//			
	/**
	 * @Description(解析request转为json) @param request
	 * @author cxy
     * @version 2017-04-19
	 */
	public static String getReqMsg(HttpServletRequest request) {
		InputStream in;
		StringBuffer json = null;
		try {
			in = request.getInputStream();
			json = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = in.read(b)) != -1;) {
				json.append(new String(b, 0, n));
			}
			String msg = json.toString();
			if (msg.indexOf("text=\"") > -1) {
				msg = msg.substring(0, msg.indexOf("text=\"") + 5) + "\\\""
						+ msg.substring(msg.indexOf("text=\"") + 6, msg.indexOf("\"\"")) + "\\\"\""
						+ msg.substring(msg.indexOf("\"\"") + 2, msg.length());
			}
			return msg;
		} catch (Exception e) {
			json.append("");
		}
		return json.toString();
	}
}
