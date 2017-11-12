package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.ExitEntryDao;
import com.xmxnkj.voip.system.entity.ExitEntry;
import com.xmxnkj.voip.system.entity.query.ExitEntryQuery;

@Repository
public class ExitEntryDaoImpl extends SimpleHibernate4Dao<ExitEntry, ExitEntryQuery> implements ExitEntryDao{

}
