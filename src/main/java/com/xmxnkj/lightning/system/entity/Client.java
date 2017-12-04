package com.xmxnkj.lightning.system.entity;

import com.hsit.common.kfbase.entity.BusinessObject;
import com.hsit.common.kfbase.entity.DomainEntity;

/**
 * @ProjectName:voip
 * @ClassName: Client
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class Client extends DomainEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5224133866509064130L;

	private String wechatId;

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	
}
