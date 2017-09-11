package com.xmxnkj.voip.client.entity;

import com.xmxnkj.voip.common.entity.VoipEntity;

public class Line extends VoipEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 111973439173677933L;

	public Line() {
		super();
	}

	public Line(String id){
		this();
		this.setId(id);
	}
	
	//名称、所属部门
	
	private String lingTel;	//线路号码
	
	private String serialNumber;	//线路编码
	
	private Integer number;	//编号
	
	private String deptId;			//部门
	
	private Dept dept;				
	
	private String clientUserId;
	
	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	private ClientUser clientUser;	//所属坐席
	
	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
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

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}