package com.xmszit.voip.system.dao;

import com.hsit.common.dao.Dao;
import com.xmszit.voip.system.entity.ClientMeal;
import com.xmszit.voip.system.entity.query.ClientMealQuery;
/**
 * ClientMeal
 * @author Administrator
 *
 */
public interface ClientMealDao extends Dao<ClientMeal, ClientMealQuery>{
	public Integer countMealAmount(String mealId);
}
