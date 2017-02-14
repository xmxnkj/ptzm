package com.xmszit.voip.outBound.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.common.entity.query.VoipQuery;
import com.xmszit.voip.customer.entity.CallPlan;

public class AutodialerTaskQuery extends VoipQuery{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3516508871578839633L;
	
	@QueryParamAnnotation(propertyName="uuid", 
			compareType=ParamCompareType.Equal)
	private String uuid;			//主键
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	private CallPlan callPlan;	//任务
	
	@QueryParamAnnotation(propertyName="callPlanId", 
	compareType=ParamCompareType.Equal)
	private String callPlanId;		//任务ID

	public CallPlan getCallPlan() {
		return callPlan;
	}

	public void setCallPlan(CallPlan callPlan) {
		this.callPlan = callPlan;
	}

	public String getCallPlanId() {
		return callPlanId;
	}

	public void setCallPlanId(String callPlanId) {
		this.callPlanId = callPlanId;
	}
}