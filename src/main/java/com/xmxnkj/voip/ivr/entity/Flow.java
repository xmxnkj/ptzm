package com.xmxnkj.voip.ivr.entity;

import java.util.ArrayList;
import java.util.List;

import com.xmxnkj.voip.common.entity.VoipEntity;

/**	流程
 * @ProjectName:voip
 * @ClassName: Flow
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class Flow extends VoipEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8016733729377610115L;



	/**
	 * 
	 */
	public Flow() {
		aiResponses = new ArrayList<>();
	}
	
	private String flowTemplateId;		//所属模板ID
	
	private String code;						//流程代码
	private String answer;					//应答
	private String sound;						//语音
	
	private Boolean isMainFlow;			//是否为主流程
	//在没有aiResponse满足条件的情况下，走原流程
	private Boolean remainOriginal;	//是否为原流程
	
	private String keywordsString;		//关键字
	
	private FlowTemplate flowTemplate;
	
	//以下属性不做存储
	
	public FlowTemplate getFlowTemplate() {
		return flowTemplate;
	}
	public void setFlowTemplate(FlowTemplate flowTemplate) {
		this.flowTemplate = flowTemplate;
	}

	private List<String> keywords;
	private List<AiResponse> aiResponses;
	
	
	
	private List<String> specialKeys;
	private List<String> refuseKeys;
	private List<String> definiteKeys;
	private List<String> otherKeys;
	
	
	private Boolean specialCommonRefuse;
	private Boolean specialCommonNone;
	
	//拒绝-》通用拒绝关键字
	private Boolean refuseCommonRefuse;
	//拒绝->通用没有关键字
	private Boolean refuseCommonNone;
	
	private Boolean definiteCommonDefinite;
	private Boolean definiteCommonNono;
	
	
	
	
	private String specialStep;
	private String refuseStep;
	private String definiteStep;
	private String otherStep;
	
	private Boolean specialRemainOld;
	private Boolean refuseRemainOld;
	private Boolean definiteRemainOld;
	private Boolean definiteOther;
	
	
	//是否
	private Boolean isHangup;
	//是否有全局语境转向相应流程
	private Boolean toGlobalFlow;
	
	//任意结束
	private Boolean toEnd;
	//结束的步骤
	private String endStep;
	//针对全局语境，要转入该步骤的关键字
	private List<String> globalKeywords;
	private Boolean blockAsr;
	
	
	public Boolean getBlockAsr() {
		return blockAsr;
	}
	public void setBlockAsr(Boolean blockAsr) {
		this.blockAsr = blockAsr;
	}
	public String getKeywordsString() {
		return keywordsString;
	}
	public void setKeywordsString(String keywordsString) {
		this.keywordsString = keywordsString;
	}
	public String getFlowTemplateId() {
		return flowTemplateId;
	}
	public void setFlowTemplateId(String flowTemplateId) {
		this.flowTemplateId = flowTemplateId;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public Boolean getIsMainFlow() {
		return isMainFlow!=null?isMainFlow:false;
	}
	public void setIsMainFlow(Boolean isMainFlow) {
		this.isMainFlow = isMainFlow;
	}
	public Boolean getRemainOriginal() {
		return remainOriginal!=null?remainOriginal:false;
	}
	public void setRemainOriginal(Boolean remainOriginal) {
		this.remainOriginal = remainOriginal;
	}
	public List<AiResponse> getAiResponses() {
		return aiResponses;
	}
	public void setAiResponses(List<AiResponse> aiResponses) {
		this.aiResponses = aiResponses;
	}

	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public Boolean getSpecialRemainOld() {
		return specialRemainOld;
	}
	public void setSpecialRemainOld(Boolean specialRemainOld) {
		this.specialRemainOld = specialRemainOld;
	}
	public Boolean getRefuseRemainOld() {
		return refuseRemainOld;
	}
	public void setRefuseRemainOld(Boolean refuseRemainOld) {
		this.refuseRemainOld = refuseRemainOld;
	}
	public Boolean getDefiniteRemainOld() {
		return definiteRemainOld;
	}
	public void setDefiniteRemainOld(Boolean definiteRemainOld) {
		this.definiteRemainOld = definiteRemainOld;
	}
	public Boolean getDefiniteOther() {
		return definiteOther;
	}
	public void setDefiniteOther(Boolean definiteOther) {
		this.definiteOther = definiteOther;
	}
	public List<String> getGlobalKeywords() {
		return globalKeywords;
	}
	public void setGlobalKeywords(List<String> globalKeywords) {
		this.globalKeywords = globalKeywords;
	}
	public Boolean getToEnd() {
		return toEnd!=null?toEnd:false;
	}
	public void setToEnd(Boolean toEnd) {
		this.toEnd = toEnd;
	}
	public String getEndStep() {
		return endStep;
	}
	public void setEndStep(String endStep) {
		this.endStep = endStep;
	}
	public Boolean getToGlobalFlow() {
		return toGlobalFlow;
	}
	public void setToGlobalFlow(Boolean toGlobalFlow) {
		this.toGlobalFlow = toGlobalFlow;
	}
	
	public Boolean getIsHangup() {
		return isHangup;
	}
	public void setIsHangup(Boolean isHangup) {
		this.isHangup = isHangup;
	}
	public Boolean getSpecialCommonRefuse() {
		return specialCommonRefuse!=null?specialCommonRefuse:false;
	}
	public void setSpecialCommonRefuse(Boolean specialCommonRefuse) {
		this.specialCommonRefuse = specialCommonRefuse;
	}
	public Boolean getSpecialCommonNone() {
		return specialCommonNone!=null?specialCommonNone:false;
	}
	public void setSpecialCommonNone(Boolean specialCommonNone) {
		this.specialCommonNone = specialCommonNone;
	}
	public Boolean getDefiniteCommonNono() {
		return definiteCommonNono!=null?definiteCommonNono:false;
	}
	public void setDefiniteCommonNono(Boolean definiteCommonNono) {
		this.definiteCommonNono = definiteCommonNono;
	}
	public Boolean getDefiniteCommonDefinite() {
		return definiteCommonDefinite!=null?definiteCommonDefinite:false;
	}
	public void setDefiniteCommonDefinite(Boolean definiteCommonDefinite) {
		this.definiteCommonDefinite = definiteCommonDefinite;
	}
	public Boolean getRefuseCommonRefuse() {
		return refuseCommonRefuse!=null?refuseCommonRefuse:false;
	}
	public void setRefuseCommonRefuse(Boolean refuseCommonRefuse) {
		this.refuseCommonRefuse = refuseCommonRefuse;
	}
	public Boolean getRefuseCommonNone() {
		return refuseCommonNone!=null?refuseCommonNone:false;
	}
	public void setRefuseCommonNone(Boolean refuseCommonNone) {
		this.refuseCommonNone = refuseCommonNone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public List<String> getSpecialKeys() {
		return specialKeys;
	}
	public void setSpecialKeys(List<String> specialKeys) {
		this.specialKeys = specialKeys;
	}
	public List<String> getRefuseKeys() {
		return refuseKeys;
	}
	public void setRefuseKeys(List<String> refuseKeys) {
		this.refuseKeys = refuseKeys;
	}
	public List<String> getDefiniteKeys() {
		return definiteKeys;
	}
	public void setDefiniteKeys(List<String> definiteKeys) {
		this.definiteKeys = definiteKeys;
	}
	public List<String> getOtherKeys() {
		return otherKeys;
	}
	public void setOtherKeys(List<String> otherKeys) {
		this.otherKeys = otherKeys;
	}
	public String getSpecialStep() {
		return specialStep;
	}
	public void setSpecialStep(String specialStep) {
		this.specialStep = specialStep;
	}
	public String getRefuseStep() {
		return refuseStep;
	}
	public void setRefuseStep(String refuseStep) {
		this.refuseStep = refuseStep;
	}
	public String getDefiniteStep() {
		return definiteStep;
	}
	public void setDefiniteStep(String definiteStep) {
		this.definiteStep = definiteStep;
	}
	public String getOtherStep() {
		return otherStep;
	}
	public void setOtherStep(String otherStep) {
		this.otherStep = otherStep;
	}
	
	
}
