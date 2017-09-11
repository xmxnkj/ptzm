package com.xmxnkj.voip.client.entity;

import com.xmxnkj.voip.common.entity.VoipEntity;

public class SeatGroup extends VoipEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8121207135894051730L;

	public SeatGroup() {
		super();
	}
	
	private String deptId;	//所属部门
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}
