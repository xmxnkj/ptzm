package com.xmszit.voip.client.dao;

import com.hsit.common.dao.Dao;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;

public interface OperateRoleDao  extends Dao<OperateRole, OperateRoleQuery>{
	public void deleteOperateRole(String roleId);
	public void deleteOperateMeal(String mealId);
	public void deleteOtherOperate(String clientId,String clientMealId);
}
