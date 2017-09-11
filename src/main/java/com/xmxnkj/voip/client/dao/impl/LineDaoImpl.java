package com.xmxnkj.voip.client.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.client.dao.LineDao;
import com.xmxnkj.voip.client.entity.Line;
import com.xmxnkj.voip.client.entity.query.LineQuery;

@Repository
public class LineDaoImpl extends SimpleHibernate4Dao<Line, LineQuery> implements LineDao{

}
