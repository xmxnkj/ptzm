package com.xmxnkj.voip.client.entity;


import com.xmxnkj.voip.common.entity.VoipEntity;

public class ClientAndRole extends VoipEntity {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 409613881423765469L;

	private ClientUser clientUser; // 客户

	private UserRole userRole;// 角色
	

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}


}
