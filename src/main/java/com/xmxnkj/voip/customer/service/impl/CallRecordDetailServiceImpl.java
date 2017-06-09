package com.xmszit.voip.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.customer.dao.CallRecordDetailDao;
import com.xmszit.voip.customer.entity.CallRecordDetail;
import com.xmszit.voip.customer.entity.query.CallRecordDetailQuery;
import com.xmszit.voip.customer.service.CallRecordDetailService;

@Service
public class CallRecordDetailServiceImpl extends BusinessBaseServiceImpl<CallRecordDetail, CallRecordDetailQuery> implements CallRecordDetailService {
	@Autowired
	private CallRecordDetailDao dao;

	@Override
	public CallRecordDetailDao getDao() {
		return dao;
	}
	
	
}
