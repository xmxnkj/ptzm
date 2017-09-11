package com.xmxnkj.voip.client.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.hsit.common.kfbase.entity.BusinessObjectQueryParam;

public class ClientQuery extends BusinessObjectQueryParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4238760171675927814L;
	@QueryParamAnnotation(propertyName="(obj.name like '%'||:key||'%' or obj.loginName like '%'||:key||'%')"
			,assignName2="key",compareType=ParamCompareType.AssignExpression)
	private String key;

	@QueryParamAnnotation(propertyName="area.id")
	private String areaId;
	
	@QueryParamAnnotation(propertyName="clientMeal.id")
	private String clientMealId;
	
	
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getClientMealId() {
		return clientMealId;
	}
	public void setClientMealId(String clientMealId) {
		this.clientMealId = clientMealId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
}
