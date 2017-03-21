package com.xmszit.voip.client.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.xmszit.voip.common.entity.query.VoipQuery;

public class ClientAndRoleQuery extends VoipQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7932406053949855718L;
	@QueryParamAnnotation(propertyName="clientUser.id")
	private String clientUserId;
	@QueryParamAnnotation(propertyName="userRole.id")
	private String userRoleId;
	
	@QueryParamAnnotation(propertyName="userRole.roleName")
	private String userRoleRoleName;	//权限名
	
	public String getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getUserRoleRoleName() {
		return userRoleRoleName;
	}

	public void setUserRoleRoleName(String userRoleRoleName) {
		this.userRoleRoleName = userRoleRoleName;
	}	
}