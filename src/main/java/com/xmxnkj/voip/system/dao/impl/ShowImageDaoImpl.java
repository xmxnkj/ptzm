package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.ShowImageDao;
import com.xmxnkj.voip.system.entity.ShowImage;
import com.xmxnkj.voip.system.entity.query.ShowImageQuery;

@Repository
public class ShowImageDaoImpl extends SimpleHibernate4Dao<ShowImage, ShowImageQuery> implements ShowImageDao{

}
