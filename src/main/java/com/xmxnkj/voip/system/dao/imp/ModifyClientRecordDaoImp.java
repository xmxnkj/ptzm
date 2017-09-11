package com.xmxnkj.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.ModifyClientRecordDao;
import com.xmxnkj.voip.system.entity.ModifyClientRecord;
import com.xmxnkj.voip.system.entity.query.ModifyClientRecordQuery;
/**
 * ModifyClientRecordDaoImp
 * @author Administrator
 *
 */
@Repository
public class ModifyClientRecordDaoImp extends SimpleHibernate4Dao<ModifyClientRecord, ModifyClientRecordQuery> implements ModifyClientRecordDao{ 

}
