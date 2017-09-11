package com.xmxnkj.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.RemindDao;
import com.xmxnkj.voip.system.entity.Remind;
import com.xmxnkj.voip.system.entity.query.RemindQuery;
/**
 * RemindDaoImp
 * @author Administrator
 *
 */
@Repository
public class RemindDaoImp extends SimpleHibernate4Dao<Remind, RemindQuery> implements RemindDao{ 

}
