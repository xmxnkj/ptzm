package com.xmszit.voip.system.entity;

import com.xmszit.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: BaseArea
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class BaseArea extends VoipEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1641120088838660599L;
	private String name;
	private String parentId;
	private String areaContentId;
	private String areaContent;
	
	public String getAreaContentId() {
		return areaContentId;
	}
	public void setAreaContentId(String areaContentId) {
		this.areaContentId = areaContentId;
	}
	public String getAreaContent() {
		return areaContent;
	}
	public void setAreaContent(String areaContent) {
		this.areaContent = areaContent;
	}
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
