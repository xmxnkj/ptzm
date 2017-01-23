package com.xmszit.voip.client.dao;

import com.hsit.common.dao.Dao;
import com.xmszit.voip.client.entity.ClientAndRole;
import com.xmszit.voip.client.entity.query.ClientAndRoleQuery;

public interface ClientAndRoleDao  extends Dao<ClientAndRole, ClientAndRoleQuery>{
	public void deleteRole(String ClientUserId) ;
}
