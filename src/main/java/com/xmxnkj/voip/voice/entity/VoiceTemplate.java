package com.xmszit.voip.voice.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.common.entity.VoipEntity;

/**
 * 语音模板
 *
 * @author chenxin
 * @Date 2017-05-22
 * @version 1.0.0
 */
public class VoiceTemplate extends VoipEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5255078565342740049L;

	public VoiceTemplate() {
		super();
	}

	public VoiceTemplate(String id) {
		this();
		this.setId(id);
	}

	/** 名称 */
	private String name;
	/** 文件路径 */
	private String path;
	/** 描述 */
	private String description;
	/** 上传人 */
	private ClientUser person;
	/** 上传时间 */
	private Date uploadDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ClientUser getPerson() {
		return person;
	}

	public void setPerson(ClientUser person) {
		this.person = person;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", locale = "zh" , timezone="GMT+8")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}