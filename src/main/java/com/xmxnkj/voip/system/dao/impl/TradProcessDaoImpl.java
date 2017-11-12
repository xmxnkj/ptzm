package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.TradProcessDao;
import com.xmxnkj.voip.system.entity.TradProcess;
import com.xmxnkj.voip.system.entity.query.TradProcessQuery;

@Repository
public class TradProcessDaoImpl extends SimpleHibernate4Dao<TradProcess, TradProcessQuery> implements TradProcessDao{

}
