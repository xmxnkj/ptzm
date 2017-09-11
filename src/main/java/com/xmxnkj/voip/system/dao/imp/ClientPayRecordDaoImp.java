package com.xmxnkj.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.ClientPayRecordDao;
import com.xmxnkj.voip.system.entity.ClientPayRecord;
import com.xmxnkj.voip.system.entity.query.ClientPayRecordQuery;
/**
 * ClientPayRecordDaoImp
 * @author Administrator
 *
 */
@Repository
public class ClientPayRecordDaoImp extends SimpleHibernate4Dao<ClientPayRecord, ClientPayRecordQuery> implements ClientPayRecordDao{ 

}
