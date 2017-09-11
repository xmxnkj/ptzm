package com.xmxnkj.voip.customer.service;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.customer.entity.CallPlan;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.query.CallPlanQuery;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;

public interface CallPlanService extends BusinessBaseService<CallPlan, CallPlanQuery> {
	public void delete(String id);
	
}
