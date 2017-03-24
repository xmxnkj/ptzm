package com.xmszit.voip.client.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.client.dao.LineDao;
import com.xmszit.voip.client.entity.Line;
import com.xmszit.voip.client.entity.query.LineQuery;

@Repository
public class LineDaoImpl extends SimpleHibernate4Dao<Line, LineQuery> implements LineDao{

}
