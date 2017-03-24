package com.xmszit.voip.client.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.client.dao.ClientAndRoleDao;
import com.xmszit.voip.client.entity.ClientAndRole;
import com.xmszit.voip.client.entity.query.ClientAndRoleQuery;

@Repository
public class ClientAndRoleDaoImpl  extends SimpleHibernate4Dao<ClientAndRole, ClientAndRoleQuery> implements ClientAndRoleDao {

	@Override
	public void deleteRole(String ClientUserId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("clientUserId", ClientUserId);
		String hql = "delete from ClientAndRole u where u.clientUser.id = :clientUserId";
		executeUpdateHql(hql, paramMap);
	}

}
