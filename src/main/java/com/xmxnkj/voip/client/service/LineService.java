package com.xmxnkj.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.Line;
import com.xmxnkj.voip.client.entity.query.LineQuery;

public interface LineService extends BusinessBaseService<Line, LineQuery>{
	
	/**
	 * 获取编号最大值
	 * @param deptId
	 * @return
	 */
	public Integer getMaxNumberLine(String deptId);
	
	/**
	 * 是否有线路	true 包含 
	 * @param clientUserId
	 * @return
	 */
	public boolean hasLineOnCurrent(String clientUserId);
}
