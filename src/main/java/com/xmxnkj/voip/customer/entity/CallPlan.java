package com.xmszit.voip.customer.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Line;
import com.xmszit.voip.common.entity.VoipEntity;
import com.xmszit.voip.voice.entity.CallTimeSet;
import com.xmszit.voip.voice.entity.VoiceTemplate;

/**
 * 拨打计划
 * @author Administrator
 *
 */
public class CallPlan extends VoipEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3908408827484725802L;
	private VoiceTemplate voiceTemplate;//关联模板
	private CallState callState;
	private Boolean inUse;
	private String clientUserId;
	private ClientUser clientUser;		//所属坐席
	private Line line;					//电话线路
	private String lineId;
	
	private Set<CallTimeSet> callTimeSet;	//所设置的时间段
	
	
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public Boolean getInUse() {
		return inUse;
	}
	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}
	public VoiceTemplate getVoiceTemplate() {
		return voiceTemplate;
	}
	public void setVoiceTemplate(VoiceTemplate voiceTemplate) {
		this.voiceTemplate = voiceTemplate;
	}
	public CallState getCallState() {
		return callState;
	}
	public void setCallState(CallState callState) {
		this.callState = callState;
	}
	public ClientUser getClientUser() {
		return clientUser;
	}
	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}
	public String getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}
	public Line getLine() {
		return line;
	}

	public Set<CallTimeSet> getCallTimeSet() {
		return callTimeSet;
	}
	public void setCallTimeSet(Set<CallTimeSet> callTimeSet) {
		this.callTimeSet = callTimeSet;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	
	private String callTimeSets;//不做映射


	public String getCallTimeSets() {
		return callTimeSets;
	}
	public void setCallTimeSets(String callTimeSets) {
		this.callTimeSets = callTimeSets;
	}
	
}