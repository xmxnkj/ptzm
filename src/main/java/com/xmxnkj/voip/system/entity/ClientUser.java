package com.xmxnkj.voip.system.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hsit.common.kfbase.entity.DomainEntity;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;

//用户	包括  运营中心 会员单位 代理商  普通用户
public class ClientUser extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7830431808267118799L;
	
	private String cert;			//身份证
	
	private String phoneNumber;		//手机号
	
	private String agentName;		//代理名称
	
	private String name;			//真实姓名
	
	private String acount;			//账号（后台）  会员单位为保证金账户
	
	private String pwd;				//密码
	
	private String commendCode;		//推荐码
	
	private String weixinId;		//微信唯一标识ID
	
	private String checkCode;		//验证码
	
	private String leaderUserID;	//上级用户ID
	
	private String qrCode;			//二维码路径
	
	private String bank;			//开户银行

	private String bankAddress;		//开户银行地址
	
	private String bankCardCode;	//银行卡号
	
	private String bankCardType;	//银行卡类型	00信用卡  01储蓄卡
	
	private ClientTypeEnum clientType;	//用户类型	运营中心 会员单位 代理商  普通用户
	
	private BigDecimal enableMoney =  new BigDecimal(0);		//可用余额
	
	private ClientUser leaderUser;		//上级用户
	
	private Date regTime;			//注册时间
	
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	//会员单位专用
	//保证金额度
	private BigDecimal bailCash = new BigDecimal(0);
	
	//会员单位专用
	//占用保证金
	private BigDecimal seatBailCash = new BigDecimal(0);
	
	//会员单位专用
	//可用保证金
	private BigDecimal enableBailCash = new BigDecimal(0);
	
	
	public ClientUser getLeaderUser() {
		return leaderUser;
	}

	public void setLeaderUser(ClientUser leaderUser) {
		this.leaderUser = leaderUser;
	}

	public ClientTypeEnum getClientType() {
		return clientType;
	}

	public void setClientType(ClientTypeEnum clientType) {
		this.clientType = clientType;
	}

	public String getBank() {
		return bank;
	}

	public BigDecimal getEnableMoney() {
		return enableMoney;
	}

	public void setEnableMoney(BigDecimal enableMoney) {
		this.enableMoney = enableMoney;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getBankCardCode() {
		return bankCardCode;
	}

	public BigDecimal getBailCash() {
		return bailCash;
	}

	public void setBailCash(BigDecimal bailCash) {
		this.bailCash = bailCash;
	}

	public BigDecimal getSeatBailCash() {
		return seatBailCash;
	}

	public void setSeatBailCash(BigDecimal seatBailCash) {
		this.seatBailCash = seatBailCash;
	}

	public BigDecimal getEnableBailCash() {
		return enableBailCash;
	}

	public void setEnableBailCash(BigDecimal enableBailCash) {
		this.enableBailCash = enableBailCash;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCommendCode() {
		return commendCode;
	}

	public void setCommendCode(String commendCode) {
		this.commendCode = commendCode;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getLeaderUserID() {
		return leaderUserID;
	}

	public void setLeaderUserID(String leaderUserID) {
		this.leaderUserID = leaderUserID;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	@Override
	public String toString() {
		return "ClientUser [cert=" + cert + ", phoneNumber=" + phoneNumber
				+ ", name=" + name + ", acount=" + acount + ", pwd=" + pwd
				+ ", commendCode=" + commendCode + ", weixinId=" + weixinId
				+ ", checkCode=" + checkCode + ", leaderUserID=" + leaderUserID
				+ ", qrCode=" + qrCode + ", bank=" + bank + ", bankAddress="
				+ bankAddress + ", bankCardCode=" + bankCardCode
				+ ", clientType=" + clientType + ", enableMoney=" + enableMoney
				+ ", leaderUser=" + leaderUser + "]";
	}	
	
}