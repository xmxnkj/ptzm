package com.xmszit.voip.global;

import java.util.Date;

import com.xmszit.voip.client.entity.ClientUser;

/**
 * @ProjectName:voip
 * @ClassName: ClientSession
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientSession {
	private String sessionId;
	private ClientUser clientUser;
	private Date loginTime;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public ClientUser getClientUser() {
		return clientUser;
	}
	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	
}
