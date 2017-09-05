package com.xmxnkj.voip.web.models;

import com.hsit.common.exceptions.ApplicationException;

/**
 * @ProjectName:lightning
 * @ClassName: ErrorJson
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ResultJson {
	
	/**
	 * 
	 */
	public ResultJson() {
		// TODO Auto-generated constructor stub
	}
	
	public ResultJson(Exception e){
		if (e!=null && e instanceof ApplicationException) {
			this.message=e.getMessage();
			setSuccess(false);
		}
	}
	
	public ResultJson(boolean success){
		this.success = success;
		message="";
	}
	
	public ResultJson(boolean success, String message){
		this.success = success;
		this.message = message;
	}
	
	
	
	private String message="系统内部错误，请联系管理员";
	private Boolean success;
	private Object entity;
	private ResultType resultType;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
	
	
}
