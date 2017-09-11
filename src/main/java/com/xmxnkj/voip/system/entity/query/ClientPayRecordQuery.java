package com.xmxnkj.voip.system.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;

/**
 * @ProjectName:voip
 * @ClassName: ClientPayRecordQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientPayRecordQuery extends VoipQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3576062728762518762L;
	private Boolean cancel;
	@EntityOrderAnnotation(fieldName="payDate",isDesc=true)
	@QueryParamAnnotation(propertyName="(obj.payDate like '%'||:payDate||'%')",assignName2="payDate",compareType=ParamCompareType.AssignExpression)
	private String payDate;
	@QueryParamAnnotation(propertyName="client.id")
	private String clientId;
	private String payCode;
	private Boolean isPay;
	
	public Boolean getIsPay() {
		return isPay;
	}
	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public Boolean getCancel() {
		return cancel;
	}
	public void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}
	
	
}
