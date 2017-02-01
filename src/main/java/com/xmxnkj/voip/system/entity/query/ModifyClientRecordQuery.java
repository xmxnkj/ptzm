package com.xmszit.voip.system.entity.query;

import java.util.Date;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmszit.voip.common.entity.query.VoipQuery;

/**
 * @ProjectName:voip
 * @ClassName: ModifyClientRecordQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ModifyClientRecordQuery extends VoipQuery{
	
	@QueryParamAnnotation(propertyName="(obj.delUser IS NOT NULL)",compareType=ParamCompareType.AssignExpression)
	private Boolean delRecord;
	@QueryParamAnnotation(propertyName="(obj.modifyUser IS NOT NULL)",compareType=ParamCompareType.AssignExpression)
	private Boolean ModifyRecord;
	@EntityOrderAnnotation(fieldName="modifyDate",isDesc=true)
	private Date modifyDate;
	
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Boolean getDelRecord() {
		return delRecord;
	}
	public void setDelRecord(Boolean delRecord) {
		this.delRecord = delRecord;
	}
	public Boolean getModifyRecord() {
		return ModifyRecord;
	}
	public void setModifyRecord(Boolean modifyRecord) {
		ModifyRecord = modifyRecord;
	}
	
}
