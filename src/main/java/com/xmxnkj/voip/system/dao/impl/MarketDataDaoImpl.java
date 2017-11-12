package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.MarketDataDao;
import com.xmxnkj.voip.system.entity.MarketData;
import com.xmxnkj.voip.system.entity.query.MarketDataQuery;

@Repository
public class MarketDataDaoImpl extends SimpleHibernate4Dao<MarketData, MarketDataQuery> implements MarketDataDao{

}
