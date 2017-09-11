package com.xmxnkj.voip.client.dao;

import com.hsit.common.dao.Dao;
import com.xmxnkj.voip.client.entity.ClientAndRole;
import com.xmxnkj.voip.client.entity.query.ClientAndRoleQuery;

public interface ClientAndRoleDao  extends Dao<ClientAndRole, ClientAndRoleQuery>{
	public void deleteRole(String ClientUserId) ;
}
