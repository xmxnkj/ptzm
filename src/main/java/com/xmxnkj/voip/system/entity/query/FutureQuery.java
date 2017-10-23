package com.xmxnkj.voip.system.entity.query;

import java.util.Date;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.kfbase.entity.EntityQueryParam;

/**
 * 期货商品
 * @author Administrator
 *
 */
public class FutureQuery extends EntityQueryParam{
	
	@EntityOrderAnnotation
	private String futureCode;		//期货商品代码
	
	@EntityOrderAnnotation
	private String name;			//名称
	
	private String businessCount;	//交易数量
	
	private String tiggerStop;		//出发止损。
	
	private String tiggerOffset;		//出发止盈
	
	private String stopOutTimeOne;		//强制平仓时间1
	
	private String stopOutTimeTwo;		//强制平仓时间2

	public String getStopOutTimeOne() {
		return stopOutTimeOne;
	}

	public String getStopOutTimeTwo() {
		return stopOutTimeTwo;
	}

	public void setStopOutTimeTwo(String stopOutTimeTwo) {
		this.stopOutTimeTwo = stopOutTimeTwo;
	}

	public void setStopOutTimeOne(String stopOutTimeOne) {
		this.stopOutTimeOne = stopOutTimeOne;
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

	public void setName(String name) {
		this.name = name;
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
}