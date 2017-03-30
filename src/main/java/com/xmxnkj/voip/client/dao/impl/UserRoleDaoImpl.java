package com.xmszit.voip.client.dao.impl;


import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.client.dao.UserRoleDao;
import com.xmszit.voip.client.entity.UserRole;
import com.xmszit.voip.client.entity.query.UserRoleQuery;

@Repository
public class UserRoleDaoImpl  extends SimpleHibernate4Dao<UserRole, UserRoleQuery> implements UserRoleDao {

}
