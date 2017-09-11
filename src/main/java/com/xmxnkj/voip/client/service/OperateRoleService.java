package com.xmxnkj.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.OperateRole;
import com.xmxnkj.voip.client.entity.query.OperateRoleQuery;

public interface OperateRoleService extends BusinessBaseService<OperateRole, OperateRoleQuery> {
	public void deleteOperateRole(String roleId);
	public void deleteOperateMeal(String mealId);
	public void deleteOtherOperate(String clientId,String clientMealId);
}
