package com.xmxnkj.lightning.system.entity.query;

import com.hsit.common.annotations.EntityOrderAnnotation;
import com.hsit.common.annotations.QueryParamAnnotation;
import com.hsit.common.kfbase.entity.BusinessObjectQueryParam;
import com.hsit.common.kfbase.entity.EntityQueryParam;

/**
 * @ProjectName:voip
 * @ClassName: ClientQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientQuery extends EntityQueryParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EntityOrderAnnotation
	private String wechatId;

	@QueryParamAnnotation(propertyName="wechatId")
	private String wchat;
	
	
	
	public String getWchat() {
		return wchat;
	}

	public void setWchat(String wchat) {
		this.wchat = wchat;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	
}
