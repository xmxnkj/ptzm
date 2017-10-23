package com.xmxnkj.voip.system.entity.query;

import java.math.BigDecimal;
import java.util.Date;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.kfbase.entity.EntityQueryParam;
import com.xmxnkj.voip.system.entity.emun.EveningUpEnum;
import com.xmxnkj.voip.system.entity.emun.OrderStatus;
import com.xmxnkj.voip.system.entity.emun.OrderWayEnum;

/**
 * 交易明细
 * @author Administrator
 *
 */
public class OrderItemQuery extends EntityQueryParam{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EntityOrderAnnotation
	private String account;			//交易账号（手机号？）
	
	@EntityOrderAnnotation
	private String clientOrderNo;	//客户单号
	
	@EntityOrderAnnotation
	private String closeOrderNo;	//平仓报单
	
	public String getCloseOrderNo() {
		return closeOrderNo;
	}

	public void setCloseOrderNo(String closeOrderNo) {
		this.closeOrderNo = closeOrderNo;
	}

	@EntityOrderAnnotation
	private String voipCode;		//期货编码

	private String voipName;		//期货编码
	
	private Date createOrderTime;	//下单时间
	
	@EntityOrderAnnotation
	private String cancelOrderNo;	//撤单报单
	
	@EntityOrderAnnotation
	private OrderWayEnum OrderWay;	//下单方向		买多  /买空

	private BigDecimal unitPrice;	//单价
	
	private Integer count;		// 数量/手
	
	private BigDecimal freezeDepositMoney;	//冻结保证金
	
	private BigDecimal exchangePoundageMoney;	//交易所手续费
	
	private BigDecimal exchangeInfomationMoney;	//交易所信息费

	private BigDecimal stopLoss;		//止损
	
	private BigDecimal targetProfit;	//止盈
	
	@EntityOrderAnnotation
	private OrderStatus status;				//订单状态
	
	@EntityOrderAnnotation
	private EveningUpEnum isEveningUp;		//是否平仓
	
	@EntityOrderAnnotation
	private String exchangeId;	//交易所编码
	
	@EntityOrderAnnotation
	private String orderSysId;	//报单生成报单在交易所的编号
	
	@EntityOrderAnnotation
	private String frontID;		//前置编号ID
	
	@EntityOrderAnnotation
	private Integer sessionID;	//会话ID
	
	public String getAccount() {
		return account;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Integer getSessionID() {
		return sessionID;
	}

	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}

	public String getOrderSysId() {
		return orderSysId;
	}

	public String getCancelOrderNo() {
		return cancelOrderNo;
	}

	public void setCancelOrderNo(String cancelOrderNo) {
		this.cancelOrderNo = cancelOrderNo;
	}

	public void setOrderSysId(String orderSysId) {
		this.orderSysId = orderSysId;
	}

	public String getFrontID() {
		return frontID;
	}

	public void setFrontID(String frontID) {
		this.frontID = frontID;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getClientOrderNo() {
		return clientOrderNo;
	}

	public void setClientOrderNo(String clientOrderNo) {
		this.clientOrderNo = clientOrderNo;
	}

	public BigDecimal getStopLoss() {
		return stopLoss;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setStopLoss(BigDecimal stopLoss) {
		this.stopLoss = stopLoss;
	}

	public BigDecimal getTargetProfit() {
		return targetProfit;
	}

	public void setTargetProfit(BigDecimal targetProfit) {
		this.targetProfit = targetProfit;
	}

	public String getvoipCode() {
		return voipCode;
	}

	public void setvoipCode(String voipCode) {
		this.voipCode = voipCode;
	}

	public Date getCreateOrderTime() {
		return createOrderTime;
	}

	public void setCreateOrderTime(Date createOrderTime) {
		this.createOrderTime = createOrderTime;
	}

	public OrderWayEnum getOrderWay() {
		return OrderWay;
	}

	public void setOrderWay(OrderWayEnum orderWay) {
		OrderWay = orderWay;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BigDecimal getFreezeDepositMoney() {
		return freezeDepositMoney;
	}

	public void setFreezeDepositMoney(BigDecimal freezeDepositMoney) {
		this.freezeDepositMoney = freezeDepositMoney;
	}

	public BigDecimal getExchangePoundageMoney() {
		return exchangePoundageMoney;
	}

	public void setExchangePoundageMoney(BigDecimal exchangePoundageMoney) {
		this.exchangePoundageMoney = exchangePoundageMoney;
	}

	public BigDecimal getExchangeInfomationMoney() {
		return exchangeInfomationMoney;
	}

	public String getvoipName() {
		return voipName;
	}

	public void setvoipName(String voipName) {
		this.voipName = voipName;
	}



	public EveningUpEnum getIsEveningUp() {
		return isEveningUp;
	}

	public void setIsEveningUp(EveningUpEnum isEveningUp) {
		this.isEveningUp = isEveningUp;
	}

	public void setExchangeInfomationMoney(BigDecimal exchangeInfomationMoney) {
		this.exchangeInfomationMoney = exchangeInfomationMoney;
	}
}