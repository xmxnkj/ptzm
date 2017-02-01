package com.xmszit.voip.system.entity.query;

import com.xmszit.voip.common.entity.query.VoipQuery;

/**
 * @ProjectName:voip
 * @ClassName: SupplierQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class BaseAreaQuery extends VoipQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4668005492041562305L;
	private String parentId;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
