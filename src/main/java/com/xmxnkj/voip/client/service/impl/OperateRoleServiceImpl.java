package com.xmszit.voip.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.client.dao.OperateRoleDao;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;
import com.xmszit.voip.client.service.OperateRoleService;

@Service
@Transactional
public class OperateRoleServiceImpl extends BusinessBaseServiceImpl<OperateRole, OperateRoleQuery>  implements OperateRoleService{
   
	@Autowired 
	private OperateRoleDao dao;
    
	@Override
	public OperateRoleDao getDao(){
	 return dao;
      }

	@Override
	public void deleteOperateRole(String roleId) {
		// TODO Auto-generated method stub
		getDao().deleteOperateRole(roleId);
	}
	@Override
	public void deleteOperateMeal(String mealId) {
		// TODO Auto-generated method stub
		getDao().deleteOperateMeal(mealId);
	}
	@Override
	public void deleteOtherOperate(String clientId,String clientMealId) {
		// TODO Auto-generated method stub
		getDao().deleteOtherOperate(clientId,clientMealId);
	}
}
