package com.xmszit.voip.web.models;

import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.kfbase.entity.Paging;

/**
 * @ProjectName:voip
 * @ClassName: JsonObject
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ListJson extends ResultJson{
	
	/**
	 * 
	 */
	public ListJson() {
		// TODO Auto-generated constructor stub
	}
	
	public ListJson(Object rows){
		this.rows = rows;
		setSuccess(true);
		setMessage("");
	}
	
	public ListJson(Exception e){
		if (e!=null && e instanceof ApplicationException) {
			setMessage(e.getMessage());
		}
	}
	
	public ListJson(boolean success){
		setSuccess(success);
		setMessage("");
	}
	
	public ListJson(boolean success, String message){
		setSuccess(success);
		setMessage(message);
	}
	
	
	private Object rows;
	private Object footer;

	
	public Object getFooter() {
		return footer;
	}

	public void setFooter(Object footer) {
		this.footer = footer;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
	
	public void setPaging(Paging paging) {
		if (paging!=null) {
			this.page = paging.getPage();
			this.pageCount=paging.getPageCount();
			this.pageSize=paging.getPageSize();
			this.recordCount=paging.getRecordCount();
			this.total = paging.getRecordCount();
		}
		
	}
	
	private Integer page;
	private Integer pageCount;
	private Integer pageSize;
	private Long recordCount;
	private Long total;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	
}
