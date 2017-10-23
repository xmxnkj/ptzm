package com.xmxnkj.voip.system.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hsit.common.kfbase.entity.DomainEntity;
import com.xmxnkj.voip.system.entity.emun.EveningUpEnum;
import com.xmxnkj.voip.system.entity.emun.OrderStatus;
import com.xmxnkj.voip.system.entity.emun.OrderWayEnum;

/**
 * 交易明细
 * @author Administrator
 *
 */
public class OrderItem extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7782080996736009696L;
	
	private String account;			//交易账号（手机号？）
	
	private String clientOrderNo;	//客户订单号（开仓报单）
	
	private String closeOrderNo;	//平仓报单
	
	private String cancelOrderNo;	//撤单报单
	
	private String voipCode;		//期货编码
	
	private String voipName;		//商品名称
	
	private Date createOrderTime;	//下单时间
	
	private OrderWayEnum OrderWay;	//下单方向		买多  /买空
	
	private BigDecimal unitPrice = new BigDecimal(0);	//下单价格
	
	private BigDecimal eveningUpPrice = new BigDecimal(0);	//平仓价格
	
	private Date closeTime;			//平仓时间

	private Integer count;			// 数量/手
	
	private BigDecimal payMoney = new BigDecimal(0);	//用户支付金额(冻结保证金)
	
	private BigDecimal freezeDepositMoney = new BigDecimal(0);		//止损保证金
	
	private BigDecimal exchangePoundageMoney = new BigDecimal(0);	//交易手续费
	
	private BigDecimal exchangeInfomationMoney = new BigDecimal(0);	//信息费

	private BigDecimal stopLoss = new BigDecimal(0);			//止损
	
	private BigDecimal targetProfit = new BigDecimal(0);		//止盈
	
	private EveningUpEnum isEveningUp;		//是否平仓
	
	private BigDecimal floatingPL = new BigDecimal(0);			//浮动盈亏
	
	private BigDecimal offsetGainAndLoss = new BigDecimal(0);	//平仓盈亏
	
	private BigDecimal realOffsetGainAndLoss = new BigDecimal(0);	//实际平仓盈亏
	
	private OrderStatus status;				//订单状态
	
	private BigDecimal stopLossPrice = new BigDecimal(0);		//止损平仓价
	
	private BigDecimal targetProfitPrice = new BigDecimal(0);	//止盈平仓价
	
	private Integer warn = 0;	//是否警告	0不警告  1警告
	
	private String exchangeId;	//交易所编码
	
	private String orderSysOpenId;	//报单生成报单在交易所的编号(开仓)
	
	private String orderSysId;	//报单生成报单在交易所的编号(平仓)
	
	private String frontID;		//前置编号ID
	
	private Integer sessionID;	//会话ID
	
	private String orderLocalID;	//OrderLocalID
	
	private String traderID;   //TraderID
	
	private Integer openDeal;	//开仓已成交手数
	
	private Integer notOpenDeal;	//开仓未成交手数
	
	private Integer closeDeal;	//平仓已成交手数
	
	private Integer notCloseDeal;	//平仓未成交手数
	
	private String failReason;	//失败原因 
		
	private String closeType = "T";	//平仓类型
	
	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getCancelOrderNo() {
		return cancelOrderNo;
	}

	public void setCancelOrderNo(String cancelOrderNo) {
		this.cancelOrderNo = cancelOrderNo;
	}

	public Integer getOpenDeal() {
		return openDeal;
	}

	public void setOpenDeal(Integer openDeal) {
		this.openDeal = openDeal;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getOrderSysOpenId() {
		return orderSysOpenId;
	}

	public void setOrderSysOpenId(String orderSysOpenId) {
		this.orderSysOpenId = orderSysOpenId;
	}

	public Integer getSessionID() {
		return sessionID;
	}

	public BigDecimal getRealOffsetGainAndLoss() {
		return realOffsetGainAndLoss;
	}

	public void setRealOffsetGainAndLoss(BigDecimal realOffsetGainAndLoss) {
		this.realOffsetGainAndLoss = realOffsetGainAndLoss;
	}

	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}

	public Integer getNotOpenDeal() {
		return notOpenDeal;
	}

	public void setNotOpenDeal(Integer notOpenDeal) {
		this.notOpenDeal = notOpenDeal;
	}

	public Integer getCloseDeal() {
		return closeDeal;
	}

	public String getFrontID() {
		return frontID;
	}


	public String getOrderLocalID() {
		return orderLocalID;
	}

	public void setOrderLocalID(String orderLocalID) {
		this.orderLocalID = orderLocalID;
	}

	public String getTraderID() {
		return traderID;
	}

	public void setTraderID(String traderID) {
		this.traderID = traderID;
	}

	public void setFrontID(String frontID) {
		this.frontID = frontID;
	}

	public void setCloseDeal(Integer closeDeal) {
		this.closeDeal = closeDeal;
	}

	public Integer getNotCloseDeal() {
		return notCloseDeal;
	}

	public void setNotCloseDeal(Integer notCloseDeal) {
		this.notCloseDeal = notCloseDeal;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getAccount() {
		return account;
	}

	public BigDecimal getStopLossPrice() {
		return stopLossPrice;
	}

	public BigDecimal getEveningUpPrice() {
		return eveningUpPrice;
	}

	public void setEveningUpPrice(BigDecimal eveningUpPrice) {
		this.eveningUpPrice = eveningUpPrice;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public void setStopLossPrice(BigDecimal stopLossPrice) {
		this.stopLossPrice = stopLossPrice;
	}

	public BigDecimal getTargetProfitPrice() {
		return targetProfitPrice;
	}

	public void setTargetProfitPrice(BigDecimal targetProfitPrice) {
		this.targetProfitPrice = targetProfitPrice;
	}

	public String getCloseOrderNo() {
		return closeOrderNo;
	}

	public void setCloseOrderNo(String closeOrderNo) {
		this.closeOrderNo = closeOrderNo;
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

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}

	public String getvoipCode() {
		return voipCode;
	}

	public void setvoipCode(String voipCode) {
		this.voipCode = voipCode;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getvoipName() {
		return voipName;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getOrderSysId() {
		return orderSysId;
	}

	public void setOrderSysId(String orderSysId) {
		this.orderSysId = orderSysId;
	}

	public void setvoipName(String voipName) {
		this.voipName = voipName;
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

	public void setExchangeInfomationMoney(BigDecimal exchangeInfomationMoney) {
		this.exchangeInfomationMoney = exchangeInfomationMoney;
	}

	public BigDecimal getStopLoss() {
		return stopLoss;
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

	public EveningUpEnum getIsEveningUp() {
		return isEveningUp;
	}

	public void setIsEveningUp(EveningUpEnum isEveningUp) {
		this.isEveningUp = isEveningUp;
	}

	public BigDecimal getFloatingPL() {
		return floatingPL;
	}

	public void setFloatingPL(BigDecimal floatingPL) {
		this.floatingPL = floatingPL;
	}

	public BigDecimal getOffsetGainAndLoss() {
		return offsetGainAndLoss;
	}

	public void setOffsetGainAndLoss(BigDecimal offsetGainAndLoss) {
		this.offsetGainAndLoss = offsetGainAndLoss;
	}
}