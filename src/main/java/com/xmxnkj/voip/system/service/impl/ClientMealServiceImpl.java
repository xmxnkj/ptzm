package com.xmszit.voip.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.system.dao.ClientMealDao;
import com.xmszit.voip.system.entity.ClientMeal;
import com.xmszit.voip.system.entity.query.ClientMealQuery;
import com.xmszit.voip.system.service.ClientMealService;

/**
 * @ProjectName:voip
 * @ClassName: ClientMealServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class ClientMealServiceImpl extends BusinessBaseServiceImpl<ClientMeal, ClientMealQuery> implements ClientMealService{
	@Autowired
	private ClientMealDao dao;

	@Override
	public ClientMealDao getDao() {
		return dao;
	}

	@Override
	public Integer countMealAmount(String mealId) {
		// TODO Auto-generated method stub
		return getDao().countMealAmount(mealId);
	}
	
}
