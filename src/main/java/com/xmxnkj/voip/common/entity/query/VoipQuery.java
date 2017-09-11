package com.xmxnkj.voip.common.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.kfbase.entity.BusinessObjectQueryParam;

/**
 * 
 * @author zjx
 *
 */
public class VoipQuery extends BusinessObjectQueryParam{
	
	@QueryParamAnnotation(propertyName="clientId")
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
