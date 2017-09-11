package com.xmxnkj.voip.common.entity.query;

import com.hsit.common.kfbase.entity.FileType;

/**
 * 
 * @author zjx
 *
 */
public class ImagesQuery extends VoipQuery {
	private String objectId;
	private String objectName;
	private FileType fileType;
	private String filePath;
	
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
	
	
}
