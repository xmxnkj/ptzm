package com.xmszit.voip.customer.dao;

import com.hsit.common.dao.Dao;
import com.xmszit.voip.customer.entity.CallRecord;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.query.CallRecordQuery;
import com.xmszit.voip.customer.entity.query.CustomerQuery;

public interface CallRecordDao  extends Dao<CallRecord, CallRecordQuery>{

	void delCallRecord(String[] customerIds);

}
