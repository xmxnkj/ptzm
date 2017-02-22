package com.xmszit.voip.customer.entity;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsit.common.annotations.CopyNullProperty;
import com.hsit.common.utils.DateDiff;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.common.entity.VoipEntity;
import com.xmszit.voip.voice.entity.VoiceTemplate;

/**
 * 公海/私海客户
 * @author zjx
 *
 */
public class Customer extends VoipEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5247471427399759531L;
	public Customer() {
		super();
	}
	
	public Customer(String id){
		this();
		this.setId(id);
	}
	//公司名
	private String companyName;
	//联系人
	private String contactUser;
	//电话号码
	private String mobile;
	//上次拨打
	private Date lastCallDate;
	//通话时长
	private String talkTime;
	//客户分类A/B/C/D/E/F
	private CustomerType customerType;
	//客户联系状态（未联系、已联系、联系中）
	private ContactState contactState;
	//计划状态
	private PlanState planState;
	//接听状态
	private ReceivingState receivingState;
	//公海：1，私海：0
	private Integer type;
	/*//通话录音(关联通话记录)
	private CallRecord callRecord;*/
	/*//跟进记录（关联跟进记录，待完善）
	private String followRecord;*/
	/*//语音模板(关联语音模板)
	private VoiceTemplate voiceTemplate;*/
	//拨打计划(关联拨打计划)
	private CallPlan callPlan;
	//所属坐席
	private ClientUser clientUser; 
	//用户导入/创建时间
	private Date createDate;
	//用户确认类型时间
	private Date comCustTypeDate;
	//是否属于导入客户
	private Boolean isImport;
	//是否私人客户	1 是 0 不是
	private Integer isPrivate;	
	
	public Date getComCustTypeDate() {
		return comCustTypeDate;
	}

	public void setComCustTypeDate(Date comCustTypeDate) {
		this.comCustTypeDate = comCustTypeDate;
	}

	public Boolean getIsImport() {
		return isImport;
	}

	public void setIsImport(Boolean isImport) {
		this.isImport = isImport;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public CallPlan getCallPlan() {
		return callPlan;
	}
	public void setCallPlan(CallPlan callPlan) {
		this.callPlan = callPlan;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public PlanState getPlanState() {
		return planState;
	}

	public void setPlanState(PlanState planState) {
		this.planState = planState;
	}

	public ReceivingState getReceivingState() {
		return receivingState;
	}

	public Integer getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}

	public void setReceivingState(ReceivingState receivingState) {
		this.receivingState = receivingState;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getLastCallDate() {
		return lastCallDate;
	}

	public void setLastCallDate(Date lastCallDate) {
		this.lastCallDate = lastCallDate;
	}

	public String getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}

	public ContactState getContactState() {
		return contactState;
	}

	public void setContactState(ContactState contactState) {
		this.contactState = contactState;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
}
