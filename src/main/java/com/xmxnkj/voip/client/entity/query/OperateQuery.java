package com.xmszit.voip.client.entity.query;


import com.hsit.common.annotations.DistinctAnnotation;
import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.annotations.RelaParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.common.entity.query.VoipQuery;

@DistinctAnnotation
public class OperateQuery extends VoipQuery {

	/**
	 * 
	 */
	@EntityOrderAnnotation(isDesc=false)
	private Integer displayOrder;
	
	private static final long serialVersionUID = 2617322393091962037L;
	private Integer grade;
	private String pid;
	@QueryParamAnnotation(propertyName="(obj.id != :notId)",assignName2="notId", compareType=ParamCompareType.AssignExpression)
	private String notId;
	private String text;
	@QueryParamAnnotation(propertyName="(obj.text != :notText)",assignName2="notText",compareType=ParamCompareType.AssignExpression)
	private String notText;
	@QueryParamAnnotation(propertyName="(obj.id=OperateRole_p0.operate.id AND OperateRole_p0.userRole.id=UserRole_p0.id AND UserRole_p0.id=ClientAndRole_p0.userRole.id AND ClientAndRole_p0.clientUser.id=:clientUserId)"
			, assignName2="clientUserId",
			compareType=ParamCompareType.AssignExpression)
	@RelaParamAnnotation(relaNames="OperateRole;UserRole;ClientAndRole")
	private String clientUserId;
	
	@QueryParamAnnotation(propertyName="(obj.grade=1 OR obj.grade=2)",compareType=ParamCompareType.AssignExpression)
	private Boolean only;
	@QueryParamAnnotation(propertyName="(obj.grade!=1 and obj.grade!=2 and obj.id != '72df7b8b-0b81-419d-a350-664b9ed8202c' and obj.pid != '72df7b8b-0b81-419d-a350-664b9ed8202c')",compareType=ParamCompareType.AssignExpression)
	private Boolean onlyOther;
	private String code;
	@QueryParamAnnotation(propertyName="(obj.id in (SELECT p.operate.id AS id FROM OperateRole p WHERE p.clientMealId = :clientMealId))",assignName2="clientMealId",compareType=ParamCompareType.AssignExpression)
	private String clientMealId;
	
	


	public Boolean getOnly() {
		return only;
	}

	public void setOnly(Boolean only) {
		this.only = only;
	}

	public Boolean getOnlyOther() {
		return onlyOther;
	}

	public void setOnlyOther(Boolean onlyOther) {
		this.onlyOther = onlyOther;
	}

	public String getClientMealId() {
		return clientMealId;
	}

	public void setClientMealId(String clientMealId) {
		this.clientMealId = clientMealId;
	}


	public String getNotText() {
		return notText;
	}

	public void setNotText(String notText) {
		this.notText = notText;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getClientUserId() {
		return clientUserId;
	}

	public void setClientUserId(String clientUserId) {
		this.clientUserId = clientUserId;
	}
	
	public String getNotId() {
		return notId;
	}

	public void setNotId(String notId) {
		this.notId = notId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	
	
}
