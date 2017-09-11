package com.xmxnkj.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.ClientAndRole;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.query.ClientAndRoleQuery;

public interface ClientAndRoleService extends BusinessBaseService<ClientAndRole, ClientAndRoleQuery> {
	public void deleteRole(String ClientUserId);
	
	//修改
	public void updateClientAndRole(ClientUser clientUser,String loginClientId);
}
