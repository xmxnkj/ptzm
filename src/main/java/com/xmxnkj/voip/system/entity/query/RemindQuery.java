package com.xmxnkj.voip.system.entity.query;

import com.xmxnkj.voip.common.entity.query.VoipQuery;

/**
 * @ProjectName:voip
 * @ClassName: RemindQuery
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class RemindQuery extends VoipQuery{
	private Boolean isAdmin;

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
