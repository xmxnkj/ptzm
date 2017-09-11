package com.xmxnkj.voip.client.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class OperateRoleQuery extends VoipQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7868420704386075631L;
	@QueryParamAnnotation(propertyName="userRole.id")
	private String userRoleId;
	@QueryParamAnnotation(propertyName="operate.id")
	private String operateId;
	private String clientMealId;
	
	public String getClientMealId() {
		return clientMealId;
	}
	public void setClientMealId(String clientMealId) {
		this.clientMealId = clientMealId;
	}
	public String getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

}
