package com.xmszit.voip.ivr.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.ivr.dao.FlowDao;
import com.xmszit.voip.ivr.entity.Flow;
import com.xmszit.voip.ivr.entity.query.FlowQuery;

@Repository
public class FlowDaoImpl extends SimpleHibernate4Dao<Flow, FlowQuery> implements FlowDao {

}
