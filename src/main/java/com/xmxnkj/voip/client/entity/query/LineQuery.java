package com.xmxnkj.voip.client.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class LineQuery extends VoipQuery{
	
	@QueryParamAnnotation(propertyName="lingTel", 
			compareType=ParamCompareType.Equal)
	private String name;
	private String lingTel;	//线路号码
	
	@QueryParamAnnotation(propertyName="serialNumber", 
			compareType=ParamCompareType.Equal)
	private String serialNumber;	//线路编号
	
	@QueryParamAnnotation(propertyName="dept.id", 
			compareType=ParamCompareType.Equal)
	private String deptId;
	
	@QueryParamAnnotation(propertyName="clientUser.id", 
			compareType=ParamCompareType.Equal)
	private String clientUserId;
	
	private Long number;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public String getLingTel() {
		return lingTel;
	}

	public void setLingTel(String lingTel) {
		this.lingTel = lingTel;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}	
}