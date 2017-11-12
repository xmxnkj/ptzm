package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.FutureDao;
import com.xmxnkj.voip.system.entity.Future;
import com.xmxnkj.voip.system.entity.query.FutureQuery;

@Repository
public class FutureDaoImp extends SimpleHibernate4Dao<Future, FutureQuery> implements FutureDao{

}
