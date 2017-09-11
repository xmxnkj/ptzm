package com.xmxnkj.voip.customer.web.models;

import java.math.BigInteger;

public class CountCustomerType {
	//客户类别
	private BigInteger countA;
	private BigInteger countB;
	private BigInteger countC;
	private BigInteger countD;
	private BigInteger countE;
	private BigInteger countF;
	//客户联系状态
	private BigInteger countUnCon;//未联系
	private BigInteger countConed;//已联系
	private BigInteger countConing;//联系中……
	private BigInteger countAll;//所有客户
	//当前用户拥有容量
	private BigInteger havaPriSea;
	//当前用户最大限制私海容量
	private Integer priSeaAll;
	
	
	public BigInteger getHavaPriSea() {
		return havaPriSea;
	}
	public void setHavaPriSea(BigInteger havaPriSea) {
		this.havaPriSea = havaPriSea;
	}
	public Integer getPriSeaAll() {
		return priSeaAll;
	}
	public void setPriSeaAll(Integer priSeaAll) {
		this.priSeaAll = priSeaAll;
	}
	public BigInteger getCountAll() {
		return countAll;
	}
	public void setCountAll(BigInteger countAll) {
		this.countAll = countAll;
	}
	public BigInteger getCountUnCon() {
		return countUnCon;
	}
	public void setCountUnCon(BigInteger countUnCon) {
		this.countUnCon = countUnCon;
	}
	public BigInteger getCountConed() {
		return countConed;
	}
	public void setCountConed(BigInteger countConed) {
		this.countConed = countConed;
	}
	public BigInteger getCountConing() {
		return countConing;
	}
	public void setCountConing(BigInteger countConing) {
		this.countConing = countConing;
	}
	public BigInteger getCountA() {
		return countA;
	}
	public void setCountA(BigInteger countA) {
		this.countA = countA;
	}
	public BigInteger getCountB() {
		return countB;
	}
	public void setCountB(BigInteger countB) {
		this.countB = countB;
	}
	public BigInteger getCountC() {
		return countC;
	}
	public void setCountC(BigInteger countC) {
		this.countC = countC;
	}
	public BigInteger getCountD() {
		return countD;
	}
	public void setCountD(BigInteger countD) {
		this.countD = countD;
	}
	public BigInteger getCountE() {
		return countE;
	}
	public void setCountE(BigInteger countE) {
		this.countE = countE;
	}
	public BigInteger getCountF() {
		return countF;
	}
	public void setCountF(BigInteger countF) {
		this.countF = countF;
	}
	
	
}
