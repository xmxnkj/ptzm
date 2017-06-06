package com.xmszit.voip.customer.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Line;
import com.xmszit.voip.common.entity.query.VoipQuery;
import com.xmszit.voip.customer.entity.CallState;

public class CallPlanQuery extends VoipQuery {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5801958249273735864L;
	
	private Boolean inUse;
	private CallState callState;
	
	@QueryParamAnnotation(propertyName="clientUser.id")
	private String clientUserId;
	private ClientUser clientUser;		//所属坐席
	
	private Line line;					//线路
	@QueryParamAnnotation(propertyName="line.id")
	private String lineId;
	
	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public CallState getCallState() {
		return callState;
	}

	public void setCallState(CallState callState) {
		this.callState = callState;
	}

	public Boolean getInUse() {
		return inUse;
	}

	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
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