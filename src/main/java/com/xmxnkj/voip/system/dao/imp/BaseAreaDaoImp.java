package com.xmszit.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.system.dao.BaseAreaDao;
import com.xmszit.voip.system.entity.BaseArea;
import com.xmszit.voip.system.entity.query.BaseAreaQuery;
/**
 * BaseAreaDaoImp
 * @author Administrator
 *
 */
@Repository
public class BaseAreaDaoImp extends SimpleHibernate4Dao<BaseArea, BaseAreaQuery> implements BaseAreaDao{ 

}
