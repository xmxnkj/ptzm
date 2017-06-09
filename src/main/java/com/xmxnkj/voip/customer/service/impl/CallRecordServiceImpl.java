package com.xmszit.voip.customer.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.customer.dao.CallRecordDao;
import com.xmszit.voip.customer.entity.CallRecord;
import com.xmszit.voip.customer.entity.query.CallRecordQuery;
import com.xmszit.voip.customer.service.CallRecordService;

@Service
public class CallRecordServiceImpl extends BusinessBaseServiceImpl<CallRecord, CallRecordQuery> implements CallRecordService {
	@Autowired
	private CallRecordDao dao;

	@Override
	public CallRecordDao getDao() {
		return dao;
	}
	@Override
	public void delCallRecord(String[] customerIds){
		getDao().delCallRecord(customerIds);
	}
	/**
	 * 监控保存
	 */
	public void saveCallRecord(CallRecord callRecord) {
		if (callRecord!=null&&StringUtils.isEmpty(callRecord.getClientId())) {
			saveSimple(callRecord);
		}
	}
	
}
