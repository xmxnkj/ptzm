package com.xmxnkj.voip.client.entity.query;

import java.util.List;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.client.entity.en.IsOnSeatGroup;
import com.xmxnkj.voip.common.entity.query.VoipQuery;
/**
 * 
 * @author zjx
 *
 */
public class ClientUserQuery extends VoipQuery {

	private String account;
	private String passwd;
	private String md5Passwd;
	
	@QueryParamAnnotation(propertyName="name", 
	compareType=ParamCompareType.Equal)
	private String name;	//姓名
	
	@QueryParamAnnotation(propertyName="IDcard", 
			compareType=ParamCompareType.Equal)
	private String IDcard;//身份证
	
	@QueryParamAnnotation(propertyName="email", 
			compareType=ParamCompareType.Equal)
	private String email;	//邮箱
	
	private Integer privateSea;	//私海容量
	
	private Integer aiCount;//AI数
	
	@QueryParamAnnotation(propertyName="seatId", 
			compareType=ParamCompareType.Equal)
	private String seatId;	//座机id
	
	@QueryParamAnnotation(propertyName="dept.id", 
			compareType=ParamCompareType.Equal)
	private String deptId;
	
	@QueryParamAnnotation(propertyName="(obj.deptId='' OR obj.deptId IS NULL)",compareType=ParamCompareType.AssignExpression)
	private Boolean notDept;			//无所属部门

	@QueryParamAnnotation(propertyName="roleIds", 
			compareType=ParamCompareType.Like)
	private String roleIds;						//职位

	@QueryParamAnnotation(propertyName="(obj.notLimit=0 OR obj.notLimit IS NULL)",compareType=ParamCompareType.AssignExpression)
	private Boolean isNotLimit;
	
	@QueryParamAnnotation(propertyName="isOnSeatGroup", 
			compareType=ParamCompareType.Equal)
	private IsOnSeatGroup isOnSeatGroup;	//是否为坐席组	YES NO
	
	public Boolean getIsNotLimit() {
		return isNotLimit;
	}
	public void setIsNotLimit(Boolean isNotLimit) {
		this.isNotLimit = isNotLimit;
	}
	public String getMd5Passwd() {
		return md5Passwd;
	}
	public void setMd5Passwd(String md5Passwd) {
		this.md5Passwd = md5Passwd;
	}

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Boolean getNotDept() {
		return notDept;
	}
	public void setNotDept(Boolean notDept) {
		this.notDept = notDept;
	}




	@QueryParamAnnotation(propertyName="id", 
			compareType=ParamCompareType.AssignExpression)
	private List<String> ids;
	
	public String getIDcard() {
		return IDcard;
	}
	public void setIDcard(String iDcard) {
		IDcard = iDcard;
	}

	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public IsOnSeatGroup getIsOnSeatGroup() {
		return isOnSeatGroup;
	}
	public void setIsOnSeatGroup(IsOnSeatGroup isOnSeatGroup) {
		this.isOnSeatGroup = isOnSeatGroup;
	}
	public String getSeatId() {
		return seatId;
	}
	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}



	@QueryParamAnnotation(propertyName="id", 
			compareType=ParamCompareType.AssignExpression,
			isNotIn=true)
	private List<String> notEqualIds;
	
	@QueryParamAnnotation(propertyName="(obj.isUser != :aisUser or obj.isUser is null)", assignName2="aisUser", compareType=ParamCompareType.AssignExpression)
	private Boolean aisUser;
	
	@QueryParamAnnotation(propertyName="(obj.name like '%'||:key||'%' or obj.phone like '%'||:key||'%')", 
			assignName2="key", compareType=ParamCompareType.AssignExpression)
	private String key;
	
	private Boolean statu;
	
	private Boolean isUser;
	
	private Boolean isManager;
	
	@QueryParamAnnotation(propertyName="(obj.id in (SELECT cr.clientUser.id as id FROM ClientUserRole cr WHERE cr.role.id = :clientRoleId ))",assignName2="clientRoleId",compareType=ParamCompareType.AssignExpression)
	private String clientRoleId;
	
	public String getClientRoleId() {
		return clientRoleId;
	}
	public void setClientRoleId(String clientRoleId) {
		this.clientRoleId = clientRoleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public Boolean getStatu() {
		return statu;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPrivateSea() {
		return privateSea;
	}
	public void setPrivateSea(Integer privateSea) {
		this.privateSea = privateSea;
	}
	public Integer getAiCount() {
		return aiCount;
	}
	public void setAiCount(Integer aiCount) {
		this.aiCount = aiCount;
	}
	public void setStatu(Boolean statu) {
		this.statu = statu;
	}
	public Boolean getAisUser() {
		return aisUser;
	}
	public void setAisUser(Boolean aisUser) {
		this.aisUser = aisUser;
	}
	public Boolean getIsManager() {
		return isManager;
	}
	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}
	public Boolean getIsUser() {
		return isUser;
	}
	public void setIsUser(Boolean isUser) {
		this.isUser = isUser;
	}
	public List<String> getNotEqualIds() {
		return notEqualIds;
	}
	public void setNotEqualIds(List<String> notEqualIds) {
		this.notEqualIds = notEqualIds;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
