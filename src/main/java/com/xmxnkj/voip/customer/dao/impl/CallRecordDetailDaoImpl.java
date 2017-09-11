package com.xmxnkj.voip.customer.dao.impl;



import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.customer.dao.CallRecordDetailDao;
import com.xmxnkj.voip.customer.entity.CallRecordDetail;
import com.xmxnkj.voip.customer.entity.query.CallRecordDetailQuery;

@Repository
public class CallRecordDetailDaoImpl extends SimpleHibernate4Dao<CallRecordDetail, CallRecordDetailQuery> implements CallRecordDetailDao{
	
}
