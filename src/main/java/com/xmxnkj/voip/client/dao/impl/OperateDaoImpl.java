package com.xmxnkj.voip.client.dao.impl;


import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.client.dao.OperateDao;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.client.entity.query.OperateQuery;

@Repository
public class OperateDaoImpl  extends SimpleHibernate4Dao<Operate, OperateQuery> implements OperateDao {

}
