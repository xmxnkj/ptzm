package com.xmszit.voip.customer.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.common.entity.query.VoipQuery;
import com.xmszit.voip.customer.entity.CallRecord;

public class CallRecordDetailQuery extends VoipQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7144632980254220589L;
	
	@QueryParamAnnotation(propertyName="callRecordId", compareType=ParamCompareType.Equal)
	private String callRecordId;
	
	@QueryParamAnnotation(propertyName="callRecord.customer.id")
	private String customerId;
		
	public String getCallRecordId() {
		return callRecordId;
	}
	public void setCallRecordId(String callRecordId) {
		this.callRecordId = callRecordId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}