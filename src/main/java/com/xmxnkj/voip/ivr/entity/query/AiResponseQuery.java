package com.xmxnkj.voip.ivr.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class AiResponseQuery extends VoipQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6969711810719315011L;
	
	private String nextStepCode;
	private String answer;
	private String sound;
	private Boolean remainOriginal;
	private String remainOriginalAnswer;
	private String remainOriginalSound;
	@EntityOrderAnnotation
	private String code;
	private Boolean repeat;
	
	@QueryParamAnnotation(propertyName="flowId", 
			compareType=ParamCompareType.Equal)
	private String flowId;
	
	@QueryParamAnnotation(propertyName="keywordsString", 
			compareType=ParamCompareType.Like)
	private String keywordsString;
	private String templateId;
	
	private String flowCode;
	
	
	public String getFlowCode() {
		return flowCode;
	}
	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getNextStepCode() {
		return nextStepCode;
	}
	public void setNextStepCode(String nextStepCode) {
		this.nextStepCode = nextStepCode;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public Boolean getRemainOriginal() {
		return remainOriginal;
	}
	public void setRemainOriginal(Boolean remainOriginal) {
		this.remainOriginal = remainOriginal;
	}
	public String getRemainOriginalAnswer() {
		return remainOriginalAnswer;
	}
	public void setRemainOriginalAnswer(String remainOriginalAnswer) {
		this.remainOriginalAnswer = remainOriginalAnswer;
	}
	public String getRemainOriginalSound() {
		return remainOriginalSound;
	}
	public void setRemainOriginalSound(String remainOriginalSound) {
		this.remainOriginalSound = remainOriginalSound;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getRepeat() {
		return repeat;
	}
	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getKeywordsString() {
		return keywordsString;
	}
	public void setKeywordsString(String keywordsString) {
		this.keywordsString = keywordsString;
	}
}