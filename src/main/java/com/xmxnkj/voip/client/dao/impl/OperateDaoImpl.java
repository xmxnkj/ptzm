package com.xmszit.voip.client.dao.impl;


import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.client.dao.OperateDao;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.client.entity.query.OperateQuery;

@Repository
public class OperateDaoImpl  extends SimpleHibernate4Dao<Operate, OperateQuery> implements OperateDao {

}
