package com.xmszit.voip.customer.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.customer.entity.CallRecord;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.query.CallRecordQuery;
import com.xmszit.voip.customer.entity.query.CustomerQuery;

public interface CallRecordService extends BusinessBaseService<CallRecord, CallRecordQuery> {

	void delCallRecord(String[] customerIds);
}
