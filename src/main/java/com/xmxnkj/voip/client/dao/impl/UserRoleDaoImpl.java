package com.xmxnkj.voip.client.dao.impl;


import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.client.dao.UserRoleDao;
import com.xmxnkj.voip.client.entity.UserRole;
import com.xmxnkj.voip.client.entity.query.UserRoleQuery;

@Repository
public class UserRoleDaoImpl  extends SimpleHibernate4Dao<UserRole, UserRoleQuery> implements UserRoleDao {

}
