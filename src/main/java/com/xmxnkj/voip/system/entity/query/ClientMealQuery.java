package com.xmxnkj.voip.system.entity.query;

import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

/**
 * @ProjectName:voip
 * @ClassName: ClientMealQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientMealQuery extends VoipQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 430929359470842408L;
	private String name;
	@QueryParamAnnotation(propertyName="(obj.id != :notId)",assignName2="notId",compareType=ParamCompareType.AssignExpression)
	private String notId;
	private Boolean mealState;
	
	public Boolean getMealState() {
		return mealState;
	}
	public void setMealState(Boolean mealState) {
		this.mealState = mealState;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotId() {
		return notId;
	}
	public void setNotId(String notId) {
		this.notId = notId;
	}
	
}
