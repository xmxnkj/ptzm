package com.xmszit.voip.client.service.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.MD5Util;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.client.dao.ClientAndRoleDao;
import com.xmszit.voip.client.entity.ClientAndRole;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.UserRole;
import com.xmszit.voip.client.entity.query.ClientAndRoleQuery;
import com.xmszit.voip.client.service.ClientAndRoleService;
import com.xmszit.voip.client.service.ClientUserService;

@Service
public class ClientAndRoleServiceImpl extends BusinessBaseServiceImpl<ClientAndRole, ClientAndRoleQuery>  implements ClientAndRoleService{
   
	@Autowired 
	private ClientAndRoleDao dao;
    
	@Autowired
	private ClientUserService ClientUserService;
	
	@Override
	public ClientAndRoleDao getDao(){
	 return dao;
      }

	@Override
	public void deleteRole(String ClientUserId) {
		getDao().deleteRole(ClientUserId);
	}

	@Override
	public void updateClientAndRole(ClientUser clientUser,String loginClientId) {
		clientUser.setClientId(loginClientId);
		if(StringUtils.isNotEmpty(clientUser.getPasswd())){
			clientUser.setMd5Passwd(MD5Util.MD5(clientUser.getPasswd()));
		}
		if(StringUtils.isEmpty(clientUser.getDeptId())){
			clientUser.setDeptId(null);
		}
		ClientUserService.save(clientUser);
		//加入
		ClientAndRoleQuery clientAndRoleQuery = new ClientAndRoleQuery();
		clientAndRoleQuery.setClientId(loginClientId);
		clientAndRoleQuery.setClientUserId(clientUser.getId());
		List<ClientAndRole> list = getEntities(clientAndRoleQuery);
		//已有权限
		for(ClientAndRole clientAndRole:list){
			deleteById(clientAndRole.getId());
		}
		
		String[] ids = clientUser.getRoleIds().split(",");
		for(String id:ids){
			if(StringUtils.isNotEmpty(id)){
				ClientAndRole clientAndRole = new ClientAndRole();
				UserRole userRole = new UserRole();
				userRole.setId(id);
				clientAndRole.setUserRole(userRole);
				clientAndRole.setClientUser(clientUser);
				clientAndRole.setClientId(loginClientId);
				save(clientAndRole);
			}
		}
	}

}
