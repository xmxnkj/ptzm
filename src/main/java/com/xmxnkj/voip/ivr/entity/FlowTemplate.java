package com.xmxnkj.voip.ivr.entity;

import com.xmxnkj.voip.common.entity.VoipEntity;

/**	流程模板
 * @ProjectName:voip
 * @ClassName: FlowTemplate
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class FlowTemplate extends VoipEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4047085340353158990L;
	
	private String remarks;			//模板备注

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}