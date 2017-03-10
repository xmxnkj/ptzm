package com.xmszit.voip.system.dao.imp;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.system.dao.ClientMealDao;
import com.xmszit.voip.system.entity.ClientMeal;
import com.xmszit.voip.system.entity.query.ClientMealQuery;
/**
 * ClientMealDaoImp
 * @author Administrator
 *
 */
@Repository
public class ClientMealDaoImp extends SimpleHibernate4Dao<ClientMeal, ClientMealQuery> implements ClientMealDao{

	@Override
	public Integer countMealAmount(String mealId) {
		String sql = "select count(*) from ct_client where client_meal = :clientMeal";
		Map<String, Object> map = new HashMap<>();
		map.put("clientMeal", mealId);
		Integer count= findSqlCounts(sql, map).intValue();
		return count;
	} 
}
