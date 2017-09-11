package com.xmxnkj.voip.system.entity;

import com.xmxnkj.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: Remind
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class Remind extends VoipEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1770122887285586020L;
	//账号到期提醒时间
	private Integer date;
	//账号到期提醒次数
	private Integer amount;
	//内容
	private String content;
	//支付商户ID
	private String merchantId;
	//支付key
	private String payKey;
	//公众号AppId
	private String appId;
	//公众号Secert
	private String secert;
	//服务器Token
	private String token;
	//是否为付款主账号设置
	private Boolean isAdmin;
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPayKey() {
		return payKey;
	}
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSecert() {
		return secert;
	}
	public void setSecert(String secert) {
		this.secert = secert;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Remind() {
		super();
	}
	public Remind(Integer date, Boolean isAdmin) {
		super();
		this.date = date;
		this.isAdmin = isAdmin;
	}
}
