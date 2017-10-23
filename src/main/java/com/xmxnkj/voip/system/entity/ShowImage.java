package com.xmxnkj.voip.system.entity;

import com.hsit.common.kfbase.entity.DomainEntity;

public class ShowImage extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2122130779489904116L;
	
	private String url;		//路径
	
	private Integer sort_No;		//展示顺序

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort_No() {
		return sort_No;
	}

	public void setSort_No(Integer sort_No) {
		this.sort_No = sort_No;
	}
}