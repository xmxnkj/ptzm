package com.xmxnkj.voip.customer.dao;

import com.hsit.common.dao.Dao;
import com.xmxnkj.voip.customer.entity.CallRecord;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.query.CallRecordQuery;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;

public interface CallRecordDao  extends Dao<CallRecord, CallRecordQuery>{

	void delCallRecord(String[] customerIds);

}
