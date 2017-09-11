package com.xmxnkj.voip.ivr.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

public class FlowTemplateQuery extends VoipQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4785264069340002146L;
	
	
	private String remarks;			//模板备注
	
	@QueryParamAnnotation(propertyName="name", 
			compareType=ParamCompareType.Equal)
	private String name;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}