package com.xmszit.voip.customer.entity;

import java.util.Date;

import com.xmszit.voip.common.entity.VoipEntity;

/**
 * 通话记录
 * @author Administrator
 *
 */
public class CallRecord extends VoipEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -908280505678111898L;
	//用户
	private Customer customer;
	//语音
	private String voiceRecord;
	//文字
	private String textRecord;
	//开始时间
	private Date callDate;
	//通话时长（秒）
	private Integer talkTime;
	//延迟
	private Integer delay;
	//是否有效
	private Boolean isVaild;
	
	private String callId;
	
	
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getVoiceRecord() {
		return voiceRecord;
	}
	public void setVoiceRecord(String voiceRecord) {
		this.voiceRecord = voiceRecord;
	}
	public String getTextRecord() {
		return textRecord;
	}
	public void setTextRecord(String textRecord) {
		this.textRecord = textRecord;
	}
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	public Integer getTalkTime() {
		return talkTime;
	}
	public void setTalkTime(Integer talkTime) {
		this.talkTime = talkTime;
	}
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public Boolean getIsVaild() {
		return isVaild;
	}
	public void setIsVaild(Boolean isVaild) {
		this.isVaild = isVaild;
	}
	
	
}
