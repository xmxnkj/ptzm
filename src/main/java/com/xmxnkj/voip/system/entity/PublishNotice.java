package com.xmszit.voip.system.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsit.common.uac.entity.User;
import com.xmszit.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: PublishNotice
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class PublishNotice extends VoipEntity{
	
	public PublishNotice() {
		if (this.getId()==null) {
			this.noticeDate = new Date();
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4358241995473171264L;
	//账号到期提醒时间
	private Date noticeDate;
	//通知内容
	private String content;
	//提醒主题
	private String theme;
	//发布人
	private User user;
	//发布区域
	private BaseArea noticeArea;
	//发布对象
	private String clientId;
	//是否已读
	private Boolean isRead;
	private Integer showDays;
	
	
	public Integer getShowDays() {
		return showDays;
	}
	public void setShowDays(Integer showDays) {
		this.showDays = showDays;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BaseArea getNoticeArea() {
		return noticeArea;
	}
	public void setNoticeArea(BaseArea noticeArea) {
		this.noticeArea = noticeArea;
	}
	
	public PublishNotice(Date noticeDate, String content, String theme, User user, BaseArea noticeArea, String clientId,
			Boolean isRead) {
		super();
		this.noticeDate = noticeDate;
		this.content = content;
		this.theme = theme;
		this.user = user;
		this.noticeArea = noticeArea;
		this.clientId = clientId;
		this.isRead = isRead;
	}
	
}
