package com.xmszit.voip.client.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.client.dao.UserRoleDao;
import com.xmszit.voip.client.entity.UserRole;
import com.xmszit.voip.client.entity.query.UserRoleQuery;
import com.xmszit.voip.client.service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl extends BusinessBaseServiceImpl<UserRole, UserRoleQuery>  implements UserRoleService{
   
	@Autowired 
	private UserRoleDao dao;
    
	@Override
	public UserRoleDao getDao(){
	 return dao;
      }

	@Override
	public void deleteRole(String id) {
		// TODO Auto-generated method stub
		getDao().delete(id);
	}
	
}
