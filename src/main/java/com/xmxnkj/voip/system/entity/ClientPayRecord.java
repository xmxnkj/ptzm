package com.xmxnkj.voip.system.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.client.entity.OperateRole;
import com.xmxnkj.voip.common.entity.VoipEntity;
import com.xmxnkj.voip.system.entity.PayType;

/**
 * @ProjectName:voip
 * @ClassName: ClientPayRecord
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientPayRecord extends VoipEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1672143556034502224L;
	//缴费时间
	private Date payDate;
	//客户
	private Client client;
	//下次到期时间
	private Date nextExpireDate;
	//上次到期时间
	private Date lastExpireDate;
	//缴费数量
	private Integer payAmount; 
	//实收金额
	private Double money;
	//优惠金额
	private Double giftMoney;
	//缴费方式
	private PayType payType;
	//备注
	private String description;
	//是否作废
	private Boolean cancel;
	//作废时间
	private Date cancelDate;
	//作废人员
	private User cancelUser;
	//收款员
	private User user;
	//缴费单号
	private String payCode;
	//是否付款
	private Boolean isPay;
	//缴费单号
	private String sid;
	
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Boolean getIsPay() {
		return isPay;
	}
	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public User getCancelUser() {
		return cancelUser;
	}
	public void setCancelUser(User cancelUser) {
		this.cancelUser = cancelUser;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getLastExpireDate() {
		return lastExpireDate;
	}
	public void setLastExpireDate(Date lastExpireDate) {
		this.lastExpireDate = lastExpireDate;
	}
	public Boolean getCancel() {
		return cancel;
	}
	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getNextExpireDate() {
		return nextExpireDate;
	}
	public void setNextExpireDate(Date nextExpireDate) {
		this.nextExpireDate = nextExpireDate;
	}
	public Integer getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getGiftMoney() {
		return giftMoney;
	}
	public void setGiftMoney(Double giftMoney) {
		this.giftMoney = giftMoney;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
