package com.xmszit.voip.common.entity;

import java.util.UUID;

import com.hsit.common.kfbase.entity.FileType;

/**
 * 
 * @author zjx
 *
 */
public class Images extends VoipEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3328850962203591017L;

	public Images() {
		super();
		setId(UUID.randomUUID().toString());
	}
	private String fileName;
	private Long fileSize;
	private String objectId;
	private String objectName;
	private FileType fileType;
	private String originalFileName;
	private String filePath;
	
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
