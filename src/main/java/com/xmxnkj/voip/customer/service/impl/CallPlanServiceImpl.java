package com.xmszit.voip.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.customer.dao.CallPlanDao;
import com.xmszit.voip.customer.entity.CallPlan;
import com.xmszit.voip.customer.entity.query.CallPlanQuery;
import com.xmszit.voip.customer.service.CallPlanService;

@Service
public class CallPlanServiceImpl extends BusinessBaseServiceImpl<CallPlan, CallPlanQuery> implements CallPlanService {
	@Autowired
	private CallPlanDao dao;

	@Override
	public CallPlanDao getDao() {
		return dao;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		getDao().delete(id);
	}
	
}
