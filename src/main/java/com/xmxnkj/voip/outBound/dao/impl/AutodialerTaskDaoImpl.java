package com.xmxnkj.voip.outBound.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.outBound.dao.AutodialerTaskDao;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;
import com.xmxnkj.voip.outBound.entity.query.AutodialerTaskQuery;

@Repository
public class AutodialerTaskDaoImpl extends SimpleHibernate4Dao<AutodialerTask, AutodialerTaskQuery> implements AutodialerTaskDao{

}
