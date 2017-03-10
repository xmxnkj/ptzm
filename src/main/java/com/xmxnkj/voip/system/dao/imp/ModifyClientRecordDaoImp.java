package com.xmszit.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.system.dao.ModifyClientRecordDao;
import com.xmszit.voip.system.entity.ModifyClientRecord;
import com.xmszit.voip.system.entity.query.ModifyClientRecordQuery;
/**
 * ModifyClientRecordDaoImp
 * @author Administrator
 *
 */
@Repository
public class ModifyClientRecordDaoImp extends SimpleHibernate4Dao<ModifyClientRecord, ModifyClientRecordQuery> implements ModifyClientRecordDao{ 

}
