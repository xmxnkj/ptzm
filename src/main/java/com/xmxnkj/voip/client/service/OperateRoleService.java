package com.xmszit.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;

public interface OperateRoleService extends BusinessBaseService<OperateRole, OperateRoleQuery> {
	public void deleteOperateRole(String roleId);
	public void deleteOperateMeal(String mealId);
	public void deleteOtherOperate(String clientId,String clientMealId);
}
