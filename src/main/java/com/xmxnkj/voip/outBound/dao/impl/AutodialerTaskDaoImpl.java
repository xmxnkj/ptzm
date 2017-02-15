package com.xmszit.voip.outBound.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.outBound.dao.AutodialerTaskDao;
import com.xmszit.voip.outBound.entity.AutodialerTask;
import com.xmszit.voip.outBound.entity.query.AutodialerTaskQuery;

@Repository
public class AutodialerTaskDaoImpl extends SimpleHibernate4Dao<AutodialerTask, AutodialerTaskQuery> implements AutodialerTaskDao{

}
