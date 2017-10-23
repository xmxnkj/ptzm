package com.xmxnkj.voip.system.entity;

import com.hsit.common.kfbase.entity.DomainEntity;

/**
 * 期货商品
 * @author Administrator
 *
 */
public class Future extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6411884088909224087L;
	
	private String futureCode;			//期货商品代码
	
	private String name;				//名称
	
	private String businessCount;		//交易数量
	
	private String tiggerStop;			//触发止损
	
	private String tiggerOffset;		//触发止盈
	
	private String stopOutTimeOne;		//强制平仓时间1
	
	private String stopOutTimeTwo;		//强制平仓时间2
	
	private Integer unit;				//交易单位

	private Integer type = 1;					//类型	1 为手续费百分比类型  2手续费固定值
	
	private Double exchangeCommission;			//交易所手续费
	
	private Double minSandards;					//最低标准
	
	private Double exchangeMarginRatio;			//交易所保证金比率
	
	private Double rateOfInformationService;	//信息服务费率
	
	private String startTime1;				//时间段1
	
	private String endTime1;				
	
	private String startTime2;				//时间段2
	
	private String endTime2;				
	
	private String startTime3;				//时间段3
	
	private String endTime3;
	
	private String startTime4;				//时间段4
	
	private String endTime4;
	
	private Double minPriceChange;			//最小变动价位

	public String getStartTime3() {
		return startTime3;
	}

	public void setStartTime3(String startTime3) {
		this.startTime3 = startTime3;
	}

	public String getEndTime3() {
		return endTime3;
	}

	public void setEndTime3(String endTime3) {
		this.endTime3 = endTime3;
	}

	public String getStartTime1() {
		return startTime1;
	}

	public void setStartTime1(String startTime1) {
		this.startTime1 = startTime1;
	}

	public Double getMinPriceChange() {
		return minPriceChange;
	}

	public void setMinPriceChange(Double minPriceChange) {
		this.minPriceChange = minPriceChange;
	}

	public String getEndTime1() {
		return endTime1;
	}

	public String getStartTime4() {
		return startTime4;
	}


	public void setStartTime4(String startTime4) {
		this.startTime4 = startTime4;
	}

	public String getEndTime4() {
		return endTime4;
	}

	public void setEndTime4(String endTime4) {
		this.endTime4 = endTime4;
	}

	public void setEndTime1(String endTime1) {
		this.endTime1 = endTime1;
	}

	public String getStartTime2() {
		return startTime2;
	}

	public void setStartTime2(String startTime2) {
		this.startTime2 = startTime2;
	}

	public String getEndTime2() {
		return endTime2;
	}

	public void setEndTime2(String endTime2) {
		this.endTime2 = endTime2;
	}

	public void setExchangeCommission(Double exchangeCommission) {
		this.exchangeCommission = exchangeCommission;
	}

	public void setMinSandards(Double minSandards) {
		this.minSandards = minSandards;
	}

	public void setExchangeMarginRatio(Double exchangeMarginRatio) {
		this.exchangeMarginRatio = exchangeMarginRatio;
	}

	public void setRateOfInformationService(Double rateOfInformationService) {
		this.rateOfInformationService = rateOfInformationService;
	}

	public String getFutureCode() {
		return futureCode;
	}

	public void setFutureCode(String futureCode) {
		this.futureCode = futureCode;
	}

	public String getName() {
		return name;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public String getBusinessCount() {
		return businessCount;
	}

	public void setBusinessCount(String businessCount) {
		this.businessCount = businessCount;
	}

	public String getTiggerStop() {
		return tiggerStop;
	}

	public void setTiggerStop(String tiggerStop) {
		this.tiggerStop = tiggerStop;
	}

	public String getTiggerOffset() {
		return tiggerOffset;
	}

	public void setTiggerOffset(String tiggerOffset) {
		this.tiggerOffset = tiggerOffset;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStopOutTimeOne() {
		return stopOutTimeOne;
	}

	public void setStopOutTimeOne(String stopOutTimeOne) {
		this.stopOutTimeOne = stopOutTimeOne;
	}

	public String getStopOutTimeTwo() {
		return stopOutTimeTwo;
	}

	public void setStopOutTimeTwo(String stopOutTimeTwo) {
		this.stopOutTimeTwo = stopOutTimeTwo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getExchangeCommission() {
		return exchangeCommission==null?0:exchangeCommission;
	}

	public void setExchangeCommission(double exchangeCommission) {
		this.exchangeCommission = exchangeCommission;
	}

	public double getMinSandards() {
		return minSandards==null?0:minSandards;
	}

	public void setMinSandards(double minSandards) {
		this.minSandards = minSandards;
	}

	public double getExchangeMarginRatio() {
		return exchangeMarginRatio==null?0:exchangeMarginRatio;
	}

	public void setExchangeMarginRatio(double exchangeMarginRatio) {
		this.exchangeMarginRatio = exchangeMarginRatio;
	}

	public double getRateOfInformationService() {
		return rateOfInformationService==null?0:rateOfInformationService;
	}

	public void setRateOfInformationService(double rateOfInformationService) {
		this.rateOfInformationService = rateOfInformationService;
	}
}