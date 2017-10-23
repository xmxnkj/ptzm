package com.xmxnkj.voip.system.entity;

import com.hsit.common.kfbase.entity.DomainEntity;

//行情数据表
public class MarketData extends DomainEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6020905428847873311L;

	private String code;						//代码
	
	private String time;							//时间
	
/*	private BigDecimal openPrice;				//开盘价
	
	private BigDecimal maxPrice;				//最高价
	
	private BigDecimal minPrice;				//最低价
	
	private BigDecimal yesterdayClosePrice;		//昨日收盘价
	
	private BigDecimal buyPrice;				//买价
	
	private BigDecimal sellPrice;				//卖价
*/	
	private double newPrice;				//最新价格

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}
	
/*	private BigDecimal balancePrice;			//结算价
	
	private BigDecimal yesterdayBalancePrice;	//昨结算价
	
	private BigDecimal buy;						//买量
	
	private BigDecimal sell;					//卖量
	
	private String position;					//持仓量
	
	private String negotiation;					//成交量
*/
	
}