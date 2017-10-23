package com.xmxnkj.voip.system.entity;

import com.hsit.common.kfbase.entity.EntityQueryParam;

//交易过程
public class TradProcess extends EntityQueryParam{

	
	private String orderNumber; 		//客户订单号
	
	private String localId;
	
	private String sysId;
	
	private String traderId;
	
	private Integer settlementId;
	
	private Integer notifySequence;
	
	private String exchangeId;
	
	private Integer tradeAmount;			//已交易数量
	
	private Integer leftAmount;			//未交易数量

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getTraderId() {
		return traderId;
	}

	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}

	public Integer getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(Integer settlementId) {
		this.settlementId = settlementId;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Integer getNotifySequence() {
		return notifySequence;
	}

	public void setNotifySequence(Integer notifySequence) {
		this.notifySequence = notifySequence;
	}

	public Integer getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(Integer tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public Integer getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Integer leftAmount) {
		this.leftAmount = leftAmount;
	}

}