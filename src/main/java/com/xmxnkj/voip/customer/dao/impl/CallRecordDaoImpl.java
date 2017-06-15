package com.xmszit.voip.customer.dao.impl;



import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.customer.dao.CallRecordDao;
import com.xmszit.voip.customer.entity.CallRecord;
import com.xmszit.voip.customer.entity.query.CallRecordQuery;

@Repository
public class CallRecordDaoImpl extends SimpleHibernate4Dao<CallRecord, CallRecordQuery> implements CallRecordDao{
	/**
	 * 删除所有关联的记录
	 */
	@Override
	public void delCallRecord(String [] customerIds){
	  StringBuffer sql = new StringBuffer("DELETE FROM CUT_CALL_RECORD WHERE 1=1 and CUSTOMER IN ( ");
	  if (customerIds!=null&&customerIds.length>0) {
		for (int i = 0; i < customerIds.length; i++) {
			if (i==customerIds.length-1) {
				sql.append("'"+customerIds[i]+"' )");
			}else {
				sql.append("'"+customerIds[i]+"',");
			}
		}
		executeSql(sql.toString());
	  }
	}
}
