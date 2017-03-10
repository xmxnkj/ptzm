package com.xmszit.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.system.dao.ClientPayRecordDao;
import com.xmszit.voip.system.entity.ClientPayRecord;
import com.xmszit.voip.system.entity.query.ClientPayRecordQuery;
/**
 * ClientPayRecordDaoImp
 * @author Administrator
 *
 */
@Repository
public class ClientPayRecordDaoImp extends SimpleHibernate4Dao<ClientPayRecord, ClientPayRecordQuery> implements ClientPayRecordDao{ 

}
