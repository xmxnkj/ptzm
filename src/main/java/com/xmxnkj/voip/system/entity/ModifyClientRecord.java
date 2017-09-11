package com.xmxnkj.voip.system.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmxnkj.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: ModifyClientRecord
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ModifyClientRecord extends VoipEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6217660497179955515L;
	private String area;
	private String address;
	private String loginAccount;
	private String responsibleUser;
	private String mobile;
	private String name;
	private String clientMeal;
	private Boolean accountState;
	private Date effectiveDate;
	private String addUser;
	private String modifyUser;
	private String delUser;
	private Date modifyDate;
	private Date useDate;
	private String remark;
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getResponsibleUser() {
		return responsibleUser;
	}
	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClientMeal() {
		return clientMeal;
	}
	public void setClientMeal(String clientMeal) {
		this.clientMeal = clientMeal;
	}
	public Boolean getAccountState() {
		return accountState;
	}
	public void setAccountState(Boolean accountState) {
		this.accountState = accountState;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getDelUser() {
		return delUser;
	}
	public void setDelUser(String delUser) {
		this.delUser = delUser;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
