package com.xmszit.voip.customer.dao.impl;



import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.customer.dao.CallRecordDetailDao;
import com.xmszit.voip.customer.entity.CallRecordDetail;
import com.xmszit.voip.customer.entity.query.CallRecordDetailQuery;

@Repository
public class CallRecordDetailDaoImpl extends SimpleHibernate4Dao<CallRecordDetail, CallRecordDetailQuery> implements CallRecordDetailDao{
	
}
