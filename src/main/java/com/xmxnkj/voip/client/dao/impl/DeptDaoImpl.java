package com.xmxnkj.voip.client.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.client.dao.DeptDao;
import com.xmxnkj.voip.client.entity.Dept;
import com.xmxnkj.voip.client.entity.query.DeptQuery;

@Repository
public class DeptDaoImpl extends SimpleHibernate4Dao<Dept, DeptQuery> implements DeptDao{

}
