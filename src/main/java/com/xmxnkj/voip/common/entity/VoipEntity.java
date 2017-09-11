package com.xmxnkj.voip.common.entity;

import com.hsit.common.kfbase.entity.BusinessObject;

/**
 * 
 * @author zjx
 *
 */
public class VoipEntity extends BusinessObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5154726963633307997L;
	private String clientId;
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
