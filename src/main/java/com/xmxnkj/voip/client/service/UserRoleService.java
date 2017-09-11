package com.xmxnkj.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.UserRole;
import com.xmxnkj.voip.client.entity.query.UserRoleQuery;

public interface UserRoleService extends BusinessBaseService<UserRole, UserRoleQuery> {
	public void deleteRole(String id);
}
