package com.xmszit.voip.outBound.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.xmszit.voip.common.entity.VoipEntity;
import com.xmszit.voip.customer.entity.CallPlan;

/**
 * 任务表
 * @author Administrator
 *
 */
public class AutodialerTask extends VoipEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5172307693726117280L;
	
	private String uuid;			//主键
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	private CallPlan callPlan;		//拨打计划
	
	private String callPlanId;		//拨打计划ID
	
	private String name;			//任务名
	
	private Date createDatetime;	//创建时间
	
	private Date alterDatetime;	//更改时间
	
	private Integer start;		
	
	private Integer callPerSecond;
	
	private Integer maximumcall;	//并发数
	
	private Integer recycleLimit;	//
	
	private Integer cacheNumberCount;	//
	private Integer callLimit;	
	private Integer callLimitCycle;
	
	public Integer getCallLimit() {
		return callLimit;
	}
	public void setCallLimit(Integer callLimit) {
		this.callLimit = callLimit;
	}
	private Integer callPauseSecond;
	
	private Integer randomAssignmentNumber;
	
	private String disableDialTimegroup;
	private String destinationExtension;
	private String destinationDialplan;
	private String destinationContext;
	private Float schedulingPolicyRatio;
	private String schedulingQueue;
	private String dialFormat;
	private String domain;
	private String remark;
	private String callNotifyUrl;
	private Integer callNotifyType;
	private String originateVariables;
	private Integer originateTimeout;
	
	private String originationCallerIdNumber;
	private String ignoreEarlyMedia;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}
	public Date getAlterDatetime() {
		return alterDatetime;
	}
	public void setAlterDatetime(Date alterDatetime) {
		this.alterDatetime = alterDatetime;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getCallPerSecond() {
		return callPerSecond;
	}
	public void setCallPerSecond(Integer callPerSecond) {
		this.callPerSecond = callPerSecond;
	}
	public Integer getMaximumcall() {
		return maximumcall;
	}
	public void setMaximumcall(Integer maximumcall) {
		this.maximumcall = maximumcall;
	}
	public Integer getRecycleLimit() {
		return recycleLimit;
	}
	public void setRecycleLimit(Integer recycleLimit) {
		this.recycleLimit = recycleLimit;
	}
	public Integer getCacheNumberCount() {
		return cacheNumberCount;
	}
	public void setCacheNumberCount(Integer cacheNumberCount) {
		this.cacheNumberCount = cacheNumberCount;
	}
	public Integer getCallLimitCycle() {
		return callLimitCycle;
	}
	public void setCallLimitCycle(Integer callLimitCycle) {
		this.callLimitCycle = callLimitCycle;
	}
	public Integer getCallPauseSecond() {
		return callPauseSecond;
	}
	public void setCallPauseSecond(Integer callPauseSecond) {
		this.callPauseSecond = callPauseSecond;
	}
	public Integer getRandomAssignmentNumber() {
		return randomAssignmentNumber;
	}
	public void setRandomAssignmentNumber(Integer randomAssignmentNumber) {
		this.randomAssignmentNumber = randomAssignmentNumber;
	}
	public String getDisableDialTimegroup() {
		return disableDialTimegroup;
	}
	public void setDisableDialTimegroup(String disableDialTimegroup) {
		this.disableDialTimegroup = disableDialTimegroup;
	}
	public String getDestinationExtension() {
		return destinationExtension;
	}
	public void setDestinationExtension(String destinationExtension) {
		this.destinationExtension = destinationExtension;
	}
	public String getDestinationDialplan() {
		return destinationDialplan;
	}
	public void setDestinationDialplan(String destinationDialplan) {
		this.destinationDialplan = destinationDialplan;
	}
	public String getDestinationContext() {
		return destinationContext;
	}
	public void setDestinationContext(String destinationContext) {
		this.destinationContext = destinationContext;
	}
	public Float getSchedulingPolicyRatio() {
		return schedulingPolicyRatio;
	}
	public void setSchedulingPolicyRatio(Float schedulingPolicyRatio) {
		this.schedulingPolicyRatio = schedulingPolicyRatio;
	}
	public String getSchedulingQueue() {
		return schedulingQueue;
	}
	public void setSchedulingQueue(String schedulingQueue) {
		this.schedulingQueue = schedulingQueue;
	}
	public String getDialFormat() {
		return dialFormat;
	}
	public void setDialFormat(String dialFormat) {
		this.dialFormat = dialFormat;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCallNotifyUrl() {
		return callNotifyUrl;
	}
	public void setCallNotifyUrl(String callNotifyUrl) {
		this.callNotifyUrl = callNotifyUrl;
	}
	public Integer getCallNotifyType() {
		return callNotifyType;
	}
	public void setCallNotifyType(Integer callNotifyType) {
		this.callNotifyType = callNotifyType;
	}
	public String getOriginateVariables() {
		return originateVariables;
	}
	public void setOriginateVariables(String originateVariables) {
		this.originateVariables = originateVariables;
	}
	public Integer getOriginateTimeout() {
		return originateTimeout;
	}
	public void setOriginateTimeout(Integer originateTimeout) {
		this.originateTimeout = originateTimeout;
	}
	public String getOriginationCallerIdNumber() {
		return originationCallerIdNumber;
	}
	public void setOriginationCallerIdNumber(String originationCallerIdNumber) {
		this.originationCallerIdNumber = originationCallerIdNumber;
	}
	public String getIgnoreEarlyMedia() {
		return ignoreEarlyMedia;
	}
	public void setIgnoreEarlyMedia(String ignoreEarlyMedia) {
		this.ignoreEarlyMedia = ignoreEarlyMedia;
	}
	public CallPlan getCallPlan() {
		return callPlan;
	}
	public void setCallPlan(CallPlan callPlan) {
		this.callPlan = callPlan;
	}
	public String getCallPlanId() {
		return callPlanId;
	}
	public void setCallPlanId(String callPlanId) {
		this.callPlanId = callPlanId;
	}
}