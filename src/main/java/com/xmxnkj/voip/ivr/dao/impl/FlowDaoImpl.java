package com.xmxnkj.voip.ivr.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.ivr.dao.FlowDao;
import com.xmxnkj.voip.ivr.entity.Flow;
import com.xmxnkj.voip.ivr.entity.query.FlowQuery;

@Repository
public class FlowDaoImpl extends SimpleHibernate4Dao<Flow, FlowQuery> implements FlowDao {

}
