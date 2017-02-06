package com.xmszit.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.client.entity.UserRole;
import com.xmszit.voip.client.entity.query.UserRoleQuery;

public interface UserRoleService extends BusinessBaseService<UserRole, UserRoleQuery> {
	public void deleteRole(String id);
}
