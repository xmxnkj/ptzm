package com.xmszit.voip.client.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.common.entity.query.VoipQuery;

public class UserRoleQuery extends VoipQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8956652376386172651L;
	@QueryParamAnnotation(propertyName="(obj.id != :notId)",assignName2="notId", compareType=ParamCompareType.AssignExpression)
	private String notId;
	
	//@EntityOrderAnnotation(isDesc=false)
	@QueryParamAnnotation(propertyName="roleName", 
			compareType=ParamCompareType.Like)
	private String roleName;
	
	@QueryParamAnnotation(propertyName="(obj.adminRole IS NULL OR obj.adminRole = 0)",compareType=ParamCompareType.AssignExpression)
	private Boolean hideAdminRole=true;
	
	private Boolean adminRole;
	
	
	public Boolean getAdminRole() {
		return adminRole;
	}
	public void setAdminRole(Boolean adminRole) {
		this.adminRole = adminRole;
	}
	public void setHideAdminRole(Boolean hideAdminRole) {
		this.hideAdminRole = hideAdminRole;
	}
	public Boolean getHideAdminRole() {
		return hideAdminRole;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getNotId() {
		return notId;
	}
	public void setNotId(String notId) {
		this.notId = notId;
	}
	
}
