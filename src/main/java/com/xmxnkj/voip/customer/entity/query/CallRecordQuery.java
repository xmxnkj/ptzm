package com.xmxnkj.voip.customer.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class CallRecordQuery extends VoipQuery {
	//当前坐席的客户通话记录
	@QueryParamAnnotation(propertyName="customer.id")
	private String customerId;
	@QueryParamAnnotation(propertyName="(obj.customer.id IN (SELECT c.id as id FROM Customer c WHERE c.clientUser.id =:clientUserId))",assignName2="clientUserId",compareType=ParamCompareType.AssignExpression)
	private String clientUserId;
	

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}
	
}


