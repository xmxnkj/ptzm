package com.xmszit.voip.customer.entity.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.common.entity.query.VoipQuery;
import com.xmszit.voip.customer.entity.ContactState;
import com.xmszit.voip.customer.entity.CustomerType;
import com.xmszit.voip.customer.entity.PlanState;
import com.xmszit.voip.customer.entity.ReceivingState;

public class CustomerQuery extends VoipQuery {
	private Integer type;
	@EntityOrderAnnotation(fieldName="mobile",isDesc=false)
	private String mobile;
	@QueryParamAnnotation(propertyName="(obj.name LIKE '%'||:key||'%' "
									+ "OR obj.companyName LIKE '%'||:key||'%' "
									+ "OR obj.contactUser LIKE '%'||:key||'%' "
									+ "OR obj.mobile LIKE '%'||:key||'%' )",
									assignName2="key",compareType=ParamCompareType.AssignExpression)
	private String key;
	
	//客户分类A/B/C/D/E/F
	private CustomerType customerType;
	//客户联系状态（未联系、已联系、联系中）
	private ContactState contactState;
	//计划状态
	private PlanState planState;
	//接听状态
	private ReceivingState receivingState;
	@QueryParamAnnotation(propertyName="(obj.id != :notId)",assignName2="notId",compareType=ParamCompareType.AssignExpression)
	private String notId;
	
	@QueryParamAnnotation(propertyName="callPlan.id")
	private String callPlanId;
	
	@QueryParamAnnotation(propertyName="isPrivate")
	private Integer isPrivate;
	
	//客户联系状态（未联系、已联系、联系中）
	@QueryParamAnnotation(propertyName="contactState",compareType=ParamCompareType.AssignExpression)
	private List<String> contactState_s;
	
	//客户分类A/B/C/D/E/F
	@QueryParamAnnotation(propertyName="customerType",compareType=ParamCompareType.AssignExpression)
	private List<String> customerType_s;
	
	//创建或导入时间
	@QueryParamAnnotation(propertyName="createDate", compareType=ParamCompareType.LargeEqual)
	private Date createDateLower;
	@QueryParamAnnotation(propertyName="( CONVERT(obj.createDate,DATE) <= :createDateUpper )", 
			assignName2="createDateUpper", compareType=ParamCompareType.AssignExpression)
	private Date createDateUpper;
	
	@QueryParamAnnotation(propertyName="clientUser.id")
	private String clientUserId;
	
	@QueryParamAnnotation(propertyName="(obj.clientUser.id IN (select c.id as id FROM ClientUser c WHERE c.dept.id = :deptId))",assignName2="deptId",compareType=ParamCompareType.AssignExpression)
	private String deptId;

	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}

	public Date getCreateDateLower() {
		return createDateLower;
	}

	public void setCreateDateLower(Date createDateLower) {
		this.createDateLower = createDateLower;
	}

	public Date getCreateDateUpper() {
		return createDateUpper;
	}

	public void setCreateDateUpper(Date createDateUpper) {
		this.createDateUpper = createDateUpper;
	}




	public List<String> getCustomerType_s() {
		return customerType_s;
	}

	
	
	
	public String getCallPlanId() {
		return callPlanId;
	}


	public void setCallPlanId(String callPlanId) {
		this.callPlanId = callPlanId;
	}

	public void setCustomerType_s(String customerType_s) {
		if (!StringUtils.isEmpty(customerType_s)) {
			String [] s = customerType_s.split(",");
			List<String> strs = new ArrayList<>(); 
			for (String ss : s) {
				strs.add(ss);
			}
			this.customerType_s = strs;
		}
	}

	public List<String> getContactState_s() {
		return contactState_s;
	}

	public void setContactState_s(String contactState_s) {
		if (!StringUtils.isEmpty(contactState_s)) {
			String [] s = contactState_s.split(",");
			List<String> strss = new ArrayList<>(); 
			for (String ss : s) {
				strss.add(ss);
			}
			this.contactState_s = strss;
		}
	}

	public String getNotId() {
		return notId;
	}

	public void setNotId(String notId) {
		this.notId = notId;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Integer getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Integer isPrivate) {
		this.isPrivate = isPrivate;
	}

	public ContactState getContactState() {
		return contactState;
	}

	public void setContactState(ContactState contactState) {
		this.contactState = contactState;
	}

	public PlanState getPlanState() {
		return planState;
	}

	public void setPlanState(PlanState planState) {
		this.planState = planState;
	}

	public ReceivingState getReceivingState() {
		return receivingState;
	}

	public void setReceivingState(ReceivingState receivingState) {
		this.receivingState = receivingState;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}


