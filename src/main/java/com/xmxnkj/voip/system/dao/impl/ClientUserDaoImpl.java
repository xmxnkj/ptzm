package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.ClientUserDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;

@Repository
public class ClientUserDaoImpl extends SimpleHibernate4Dao<ClientUser, ClientUserQuery> implements ClientUserDao{

}
