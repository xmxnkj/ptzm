package com.xmszit.voip.client.entity;

import java.util.List;

import com.xmszit.voip.common.entity.VoipEntity;

public class UserRole extends VoipEntity {
     
	 
	public UserRole() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1430107481782759571L;
	
	private String clientId;
	
	private String roleName; // 角色名

	private String powerContent;// 权限内容

	private String remark;// 备注

	private List<OperateRole> operateRoles;
	
	private String show;
	
	private Boolean adminRole;
	
	
	public Boolean getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(Boolean adminRole) {
		this.adminRole = adminRole;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public List<OperateRole> getOperateRoles() {
		return operateRoles;
	}

	public void setOperateRoles(List<OperateRole> operateRoles) {
		this.operateRoles = operateRoles;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPowerContent() {
		return powerContent;
	}

	public void setPowerContent(String powerContent) {
		this.powerContent = powerContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
