package com.xmxnkj.voip.system.entity.query;

import java.util.Date;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.dao.QueryParam.ParamCompareType;
import com.xmxnkj.voip.common.entity.query.VoipQuery;
import com.xmxnkj.voip.system.entity.BaseArea;

/**
 * @ProjectName:voip
 * @ClassName: NoticeQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class PublishNoticeQuery extends VoipQuery{
	@EntityOrderAnnotation(fieldName="noticeDate",isDesc=true)
	private Date noticeDate;
	@QueryParamAnnotation(propertyName="noticeArea.id")
	private String noticeAreaId;
	private String clientId;
	private Boolean isRead;
	@QueryParamAnnotation(propertyName="(obj.isRead IS NULL OR obj.isRead = 0)",compareType=ParamCompareType.AssignExpression)
	private Boolean notRead;
	@QueryParamAnnotation(propertyName="(obj.clientId IS NULL)",compareType=ParamCompareType.AssignExpression)
	private Boolean notClientId;
	@QueryParamAnnotation(propertyName="DATEDIFF (CONVERT(CURRENT_DATE(),DATETIME),CONVERT(obj.noticeDate,DATETIME)) <= obj.showDays",compareType=ParamCompareType.AssignExpression)
	private Boolean showDay;
	
	public Boolean getShowDay() {
		return showDay;
	}
	public void setShowDay(Boolean showDay) {
		this.showDay = showDay;
	}
	public Boolean getNotClientId() {
		return notClientId;
	}
	public void setNotClientId(Boolean notClientId) {
		this.notClientId = notClientId;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public Boolean getNotRead() {
		return notRead;
	}
	public void setNotRead(Boolean notRead) {
		this.notRead = notRead;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getNoticeAreaId() {
		return noticeAreaId;
	}
	public void setNoticeAreaId(String noticeAreaId) {
		this.noticeAreaId = noticeAreaId;
	}
}
