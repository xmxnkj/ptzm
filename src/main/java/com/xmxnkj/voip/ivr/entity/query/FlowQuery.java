package com.xmxnkj.voip.ivr.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class FlowQuery extends VoipQuery{
	
	@QueryParamAnnotation(propertyName="flowTemplateId", 
			compareType=ParamCompareType.Equal)
	private String flowTemplateId;
	
	@EntityOrderAnnotation
	private String code;
	private String answer;
	private String sound;
	
	private Boolean isMainFlow;
	//在没有aiResponse满足条件的情况下，走原流程
	private Boolean remainOriginal;
	
	private String keywordsString;

	public String getFlowTemplateId() {
		return flowTemplateId;
	}

	public void setFlowTemplateId(String flowTemplateId) {
		this.flowTemplateId = flowTemplateId;
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

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public Boolean getIsMainFlow() {
		return isMainFlow;
	}

	public void setIsMainFlow(Boolean isMainFlow) {
		this.isMainFlow = isMainFlow;
	}

	public Boolean getRemainOriginal() {
		return remainOriginal;
	}

	public void setRemainOriginal(Boolean remainOriginal) {
		this.remainOriginal = remainOriginal;
	}

	public String getKeywordsString() {
		return keywordsString;
	}

	public void setKeywordsString(String keywordsString) {
		this.keywordsString = keywordsString;
	}
}