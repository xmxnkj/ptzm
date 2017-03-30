package com.xmszit.voip.client.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.client.dao.OperateRoleDao;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;

@Repository
public class OperateRoleDaoImpl  extends SimpleHibernate4Dao<OperateRole, OperateRoleQuery> implements OperateRoleDao {
	@Override
	public void deleteOperateRole(String roleId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("roleId", roleId);
		String hql = "delete from OperateRole u where u.userRole.id = :roleId";
		executeUpdateHql(hql, paramMap);
	}

	@Override
	public void deleteOperateMeal(String mealId) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("mealId", mealId);
		String hql = "delete from OperateRole u where u.clientMealId = :mealId";
		executeUpdateHql(hql, paramMap);
	}
	@Override
	public void deleteOtherOperate(String clientId,String clientMealId) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientMealId", clientMealId);
		String sqlId = "SELECT id AS id FROM ct_operate where (grade=1 or grade=2) and id not in (SELECT p.operate AS id FROM ct_Operate_Role p WHERE p.client_Meal_Id = :clientMealId)";
		List<Operate> list = findSqlBeanToList(sqlId, Operate.class, paramMap);
		paramMap.put("clientMealId", clientId);
		StringBuffer sql = new StringBuffer("delete from ct_Operate_Role  where client_id = :clientMealId AND operate in (");
		if (list!=null&&list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				if (i==list.size()-1) {
					sql.append("'"+list.get(i).getId()+"'");
				}else {
					sql.append("'"+list.get(i).getId()+"',");
				}
			}
			sql.append(")");
			executeUpdateSql(sql.toString(), paramMap);
		}
	}
}
