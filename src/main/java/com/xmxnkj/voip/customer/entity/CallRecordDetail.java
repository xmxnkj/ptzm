package com.xmxnkj.voip.customer.entity;

import java.util.Date;

import com.xmxnkj.voip.common.entity.VoipEntity;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;

/**
 * 通话记录
 * @author Administrator
 *
 */
public class CallRecordDetail extends VoipEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2945826238891195883L;
	//用户/AI?
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
	//录音记录
	private String callRecordId;
	
	//private CallRecord callRecord;	//外键
	
	
	/*public CallRecord getCallRecord() {
		return callRecord;
	}
	public void setCallRecord(CallRecord callRecord) {
		this.callRecord = callRecord;
	}*/
	private String asrElapse;
	private String asrTextAll;
	private String asrType;
	private String calleeId;
	private String callerId;
	private String callId;
	private Integer duration;
	private Integer errorCode;
	private String flowData;
	private String flowId;
	private AutodialerTask autodialerTask;
	public AutodialerTask getAutodialerTask() {
		return autodialerTask;
	}
	public void setAutodialerTask(AutodialerTask autodialerTask) {
		this.autodialerTask = autodialerTask;
	}
	private Integer gender;
	private String message;
	private String notify;
	private String origCallerId;
	private Integer playms;
	private String recordFile;
	private Integer recordIndex;
	private Integer recordms;
	private Double volumeGain;
	
	
	
	
	public String getAsrElapse() {
		return asrElapse;
	}
	public void setAsrElapse(String asrElapse) {
		this.asrElapse = asrElapse;
	}
	public String getAsrTextAll() {
		return asrTextAll;
	}
	public void setAsrTextAll(String asrTextAll) {
		this.asrTextAll = asrTextAll;
	}
	public String getAsrType() {
		return asrType;
	}
	public void setAsrType(String asrType) {
		this.asrType = asrType;
	}
	public String getCalleeId() {
		return calleeId;
	}
	public void setCalleeId(String calleeId) {
		this.calleeId = calleeId;
	}
	public String getCallerId() {
		return callerId;
	}
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getFlowData() {
		return flowData;
	}
	public void setFlowData(String flowData) {
		this.flowData = flowData;
	}
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public String getOrigCallerId() {
		return origCallerId;
	}
	public void setOrigCallerId(String origCallerId) {
		this.origCallerId = origCallerId;
	}
	public Integer getPlayms() {
		return playms;
	}
	public void setPlayms(Integer playms) {
		this.playms = playms;
	}
	public String getRecordFile() {
		return recordFile;
	}
	public void setRecordFile(String recordFile) {
		this.recordFile = recordFile;
	}
	public Integer getRecordIndex() {
		return recordIndex;
	}
	public void setRecordIndex(Integer recordIndex) {
		this.recordIndex = recordIndex;
	}
	public Integer getRecordms() {
		return recordms;
	}
	public void setRecordms(Integer recordms) {
		this.recordms = recordms;
	}
	public Double getVolumeGain() {
		return volumeGain;
	}
	public void setVolumeGain(Double volumeGain) {
		this.volumeGain = volumeGain;
	}
	public String getCallRecordId() {
		return callRecordId;
	}
	public void setCallRecordId(String callRecordId) {
		this.callRecordId = callRecordId;
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
