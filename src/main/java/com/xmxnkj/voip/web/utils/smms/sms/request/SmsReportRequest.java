package com.xmxnkj.voip.web.utils.smms.sms.request;
/**
 * 
 * @author tianyh 
 * @date 2017年4月15日 下午3:26:51
 * @Title: SmsPullRequest
 * @ClassName: SmsPullRequest
 * @Description:查询状态报告
 */
public class SmsReportRequest {
	/**
	 * 用户账号，必填
	 */
	private String account;
	/**
	 * 用户密码，必填
	 */
	private String password;
	/**
	 * 拉取个数（最大100，默认20），选填
	 */
	private String count;
	
	public SmsReportRequest() {
		
	}
	public SmsReportRequest(String account, String password,String count) {
		super();
		this.account = account;
		this.password = password;
		this.count = count;
		
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}

	
	
}
