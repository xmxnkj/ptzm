package com.xmxnkj.voip.system.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.kfbase.entity.EntityQueryParam;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;

public class ClientUserQuery extends EntityQueryParam{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EntityOrderAnnotation
	private String cert;			//身份证
	
	@EntityOrderAnnotation
	private String phoneNumber;		//手机号
	
	@EntityOrderAnnotation
	private String name;			//真实姓名

	private String pwd;				//密码
	
	@EntityOrderAnnotation
	private String commendCode;		//推荐码
	
	private String weixinId;		//微信唯一标识ID
	
	@EntityOrderAnnotation
	private String checkCode;		//验证码
	
	@EntityOrderAnnotation
	private String leaderUserID;	//上级用户ID
	
	private String qrCode;			//二维码路径
	
	@EntityOrderAnnotation
	private String acount;			//账号
	
	@EntityOrderAnnotation
	private ClientTypeEnum clientType;	//用户类型	运营中心 会员单位 代理商  普通用户
	
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

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
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

	public ClientTypeEnum getClientType() {
		return clientType;
	}

	public void setClientType(ClientTypeEnum clientType) {
		this.clientType = clientType;
	}	
}