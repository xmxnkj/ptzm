package com.xmxnkj.voip.client.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class DeptQuery extends VoipQuery{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7378133038949917236L;

	@QueryParamAnnotation(propertyName="name", 
			compareType=ParamCompareType.Like)
	private String name;				//部门名
	
	@QueryParamAnnotation(propertyName="managerId", 
			compareType=ParamCompareType.Equal)
	private String managerId;			//经理ID
	
	@QueryParamAnnotation(propertyName="level", 
			compareType=ParamCompareType.Equal)
	private Integer level;
	
	
	@QueryParamAnnotation(propertyName="dpid", 
			compareType=ParamCompareType.Equal)
	private String dpid;
	
	@QueryParamAnnotation(propertyName="code", 
			compareType=ParamCompareType.Equal)
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDpid() {
		return dpid;
	}
	public void setDpid(String dpid) {
		this.dpid = dpid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
}