package com.xmszit.voip.customer.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.customer.entity.CallPlan;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.query.CallPlanQuery;
import com.xmszit.voip.customer.entity.query.CustomerQuery;

public interface CallPlanService extends BusinessBaseService<CallPlan, CallPlanQuery> {
	public void delete(String id);
	
}
