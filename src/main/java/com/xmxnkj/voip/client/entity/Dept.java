package com.xmszit.voip.client.entity;

import java.util.List;

import com.xmszit.voip.common.entity.VoipEntity;

public class Dept extends VoipEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1242863456364792135L;
	public Dept() {
		super();
	}

	public Dept(String id){
		this();
		this.setId(id);
	}
	
	private String name;				//部门名
	private String clientUserId;		//经理ID  多个，逗号隔开
	private String clientUserNames;		//经理名称  多个，逗号隔开
	
	private ClientUser clientUser;		
	
	private String code;				//部门编码
	
	private String dpid;				//上级部门
	
	private Integer aiCount;			//AI数
	
	private Integer privateSea;			//私海容量
	
	private Integer level;				//等级
	
	private String remarks;				//备注
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private	List<Dept> children;		
	
	private	String state;				//节点的状态
	
	private	List<ClientUser> managers;	//多个管理员	不参与字段
	
	private	List<ClientUser> seats;		//多个坐席		不参与字段

	public List<ClientUser> getManagers() {
		return managers;
	}
	
	public void setManagers(List<ClientUser> managers) {
		this.managers = managers;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getClientUserId() {
		return clientUserId;
	}

	public String getClientUserNames() {
		return clientUserNames;
	}

	public void setClientUserNames(String clientUserNames) {
		this.clientUserNames = clientUserNames;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public ClientUser getClientUser() {
		return clientUser;
	}

	public void setClientUser(ClientUser clientUser) {
		this.clientUser = clientUser;
	}

	public String getDpid() {
		return dpid;
	}

	public void setDpid(String dpid) {
		this.dpid = dpid;
	}

	public Integer getAiCount() {
		return aiCount;
	}

	public void setAiCount(Integer aiCount) {
		this.aiCount = aiCount;
	}

	public Integer getPrivateSea() {
		return privateSea;
	}

	public void setPrivateSea(Integer privateSea) {
		this.privateSea = privateSea;
	}

	public Integer getLevel() {
		return level;
	}

	public List<Dept> getChildren() {
		return children;
	}

	public void setChildren(List<Dept> children) {
		this.children = children;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<ClientUser> getSeats() {
		return seats;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setSeats(List<ClientUser> seats) {
		this.seats = seats;
	}
}