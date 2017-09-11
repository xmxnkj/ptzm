package com.xmxnkj.voip.customer.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.customer.entity.CallRecord;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.query.CallRecordQuery;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;

public interface CallRecordService extends BusinessBaseService<CallRecord, CallRecordQuery> {

	void delCallRecord(String[] customerIds);
}
