package com.xmxnkj.voip.system.entity.query;

import java.math.BigDecimal;
import java.util.Date;

import com.hsit.common.kfbase.entity.EntityQueryParam;

//行情数据表
public class MarketDataQuery extends EntityQueryParam{
private String code;			//代码
	
	
	private Date time;				//时间
	
	private BigDecimal openPrice;	//开盘价
	
	private BigDecimal maxPrice;	//最高价
	
	private BigDecimal minPrice;	//最低价
	
	private BigDecimal yesterdayClosePrice;	//昨日收盘价
	
	private BigDecimal buyPrice;		//卖价
	
	private BigDecimal sellPrice;		//卖价
	
	private BigDecimal newPrice;	//最新价格
	
	private BigDecimal balancePrice;	//结算价
	
	private BigDecimal yesterdayBalancePrice;	//昨结算价
	
	private BigDecimal buy;				//买量
	
	private BigDecimal sell;				//卖量
	
	private String position;				//持仓量
	
	private String negotiation;		//成交量

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getYesterdayClosePrice() {
		return yesterdayClosePrice;
	}

	public void setYesterdayClosePrice(BigDecimal yesterdayClosePrice) {
		this.yesterdayClosePrice = yesterdayClosePrice;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public BigDecimal getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(BigDecimal newPrice) {
		this.newPrice = newPrice;
	}


	public BigDecimal getBalancePrice() {
		return balancePrice;
	}

	public void setBalancePrice(BigDecimal balancePrice) {
		this.balancePrice = balancePrice;
	}

	public BigDecimal getYesterdayBalancePrice() {
		return yesterdayBalancePrice;
	}

	public void setYesterdayBalancePrice(BigDecimal yesterdayBalancePrice) {
		this.yesterdayBalancePrice = yesterdayBalancePrice;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNegotiation() {
		return negotiation;
	}

	public void setNegotiation(String negotiation) {
		this.negotiation = negotiation;
	}

}