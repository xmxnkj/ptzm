package com.xmxnkj.voip.system.dao;

import com.hsit.common.dao.Dao;
import com.xmxnkj.voip.system.entity.ClientMeal;
import com.xmxnkj.voip.system.entity.query.ClientMealQuery;
/**
 * ClientMeal
 * @author Administrator
 *
 */
public interface ClientMealDao extends Dao<ClientMeal, ClientMealQuery>{
	public Integer countMealAmount(String mealId);
}
