package com.xmxnkj.voip.voice.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class CallTimeSetQuery extends VoipQuery {
	
	@QueryParamAnnotation(propertyName="clientUser.id")
	private String clientUserId;
	
	private ClientUser clientUser;		//所属坐席

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}
}