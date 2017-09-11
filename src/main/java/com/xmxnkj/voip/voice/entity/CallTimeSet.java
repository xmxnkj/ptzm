package com.xmxnkj.voip.voice.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.common.entity.VoipEntity;
import com.xmxnkj.voip.voice.entity.emun.CallTimeState;
import com.xmxnkj.voip.voice.entity.emun.OperationState;

/**
 * 拨打时间设置
 *
 * @author chenxin
 * @Date 2017-05-22
 * @version 1.00
 */
public class CallTimeSet extends VoipEntity {
	
	public CallTimeSet() {
		super();
	}
	
	public CallTimeSet(String id){
		this();
		this.setId(id);
	}
	
	/** 名称 */
	private String name;
	/** 开始的时间 */
	private Date startTime;
	/** 结束的时间 */
	private Date endTime;
	/** 类型 */
	private CallTimeState state;
	/** 创建时间 */
	private Date createTime;
	/** 是否启用 */
	private OperationState operation;
	/**
	 * 所属坐席
	 */
	private String clientUserId;
	
	private ClientUser clientUser;		//所属坐席
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@JsonFormat(pattern = "HH:mm", locale = "zh" , timezone="GMT+8")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(pattern = "HH:mm", locale = "zh" , timezone="GMT+8")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public CallTimeState getState() {
		return state;
	}

	public void setState(CallTimeState state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public OperationState getOperation() {
		return operation;
	}

	public void setOperation(OperationState operation) {
		this.operation = operation;
	}

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