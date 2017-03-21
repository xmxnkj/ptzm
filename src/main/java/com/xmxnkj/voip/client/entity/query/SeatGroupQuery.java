package com.xmszit.voip.client.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.common.entity.query.VoipQuery;

public class SeatGroupQuery extends VoipQuery{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3026960616101785433L;
	@QueryParamAnnotation(propertyName="deptId", 
			compareType=ParamCompareType.Equal)
	private String deptId;	//所属部门

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}
