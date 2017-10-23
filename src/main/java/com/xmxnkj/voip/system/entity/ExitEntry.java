package com.xmxnkj.voip.system.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsit.common.kfbase.entity.DomainEntity;
import com.xmxnkj.voip.system.entity.emun.EntryStatusEnum;
import com.xmxnkj.voip.system.entity.emun.ExitOrEntryEnum;
import com.xmxnkj.voip.web.utils.CustomDateSerializerYmdhms;

//出入金操作记录
public class ExitEntry extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -331254836654457161L;
	
	private String phoneNumber;			//账号(手机)
	
	private String payOrderNumber;		//账号支付订单号
	
	private Integer result = 0;			//支付结果  0：未响应  1：成功 2：失败
	
	private String userId;				
	
	private Date entryTime;				//入金时间
	
	private BigDecimal entryMoney;		//入金金额
	
	private Date exitTime;				//出金时间
	
	private BigDecimal exitMoney;		//出金金额
	
	private ClientUser clientUser;		//当事人
	
	private ExitOrEntryEnum exitOrEntry;	//出入金状态
	
	private EntryStatusEnum status;			//用户类型
	
	private String season;				//备注
	
	private String bankCardNo;				//银行卡号
	
	private String name;				//姓名
	
	private String bankAddress;			//银行
	
	private String serials;			//流水号
	
	public String getSerials() {
		return serials;
	}

	public void setSerials(String serials) {
		this.serials = serials;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public EntryStatusEnum getStatus() {
		return status;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(EntryStatusEnum status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Date getEntryTime() {
		return entryTime;
	}
	
	public String getPayOrderNumber() {
		return payOrderNumber;
	}

	public void setPayOrderNumber(String payOrderNumber) {
		this.payOrderNumber = payOrderNumber;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public ExitOrEntryEnum getExitOrEntry() {
		return exitOrEntry;
	}

	public void setExitOrEntry(ExitOrEntryEnum exitOrEntry) {
		this.exitOrEntry = exitOrEntry;
	}

	public BigDecimal getEntryMoney() {
		return entryMoney;
	}

	public void setEntryMoney(BigDecimal entryMoney) {
		this.entryMoney = entryMoney;
	}
	
	public Date getExitTime() {
		return exitTime;
	}
	
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public BigDecimal getExitMoney() {
		return exitMoney;
	}

	public void setExitMoney(BigDecimal exitMoney) {
		this.exitMoney = exitMoney;
	}
}