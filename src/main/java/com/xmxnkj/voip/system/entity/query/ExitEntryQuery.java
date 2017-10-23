package com.xmxnkj.voip.system.entity.query;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.kfbase.entity.EntityQueryParam;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.emun.ExitOrEntryEnum;

//出入金操作 
public class ExitEntryQuery extends EntityQueryParam{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EntityOrderAnnotation
	private String phoneNumber;			//账号(手机)
	
	@EntityOrderAnnotation
	private String userId;				
	
	@EntityOrderAnnotation
	private Date entryTime;				//入金时间
	
	@EntityOrderAnnotation
	private BigDecimal entryMoney;		//入金金额
	
	@EntityOrderAnnotation
	private Date exitTime;				//出金时间
	
	@EntityOrderAnnotation
	private BigDecimal exitMoney;		//出金金额
	
	private ClientUser clientUser;		//当事人
	
	@EntityOrderAnnotation
	private ExitOrEntryEnum exitOrEntry;
	
	@EntityOrderAnnotation
	private String payOrderNumber;		//账号支付订单号
	
	@EntityOrderAnnotation
	private Integer result = 0;			//支付结果  0：未响应  1：成功 2：失败

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserId() {
		return userId;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getEntryTime() {
		return entryTime;
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