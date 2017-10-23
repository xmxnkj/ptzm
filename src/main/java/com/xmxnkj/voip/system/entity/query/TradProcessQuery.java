package com.xmxnkj.voip.system.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.kfbase.entity.EntityQueryParam;

//交易过程
public class TradProcessQuery extends EntityQueryParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2086537584481080209L;
	
	@EntityOrderAnnotation
	private String orderNumber; 		//客户订单号
	
	private String localId;
	
	private String sysId;
	
	private String traderId;
	
	private String settlementId;
	
	private String exchangeId;
	
	@EntityOrderAnnotation
	private String tradeAmount;			//已交易数量
	
	@EntityOrderAnnotation
	private String leftAmount;			//未交易数量

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

	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(String leftAmount) {
		this.leftAmount = leftAmount;
	}	
}