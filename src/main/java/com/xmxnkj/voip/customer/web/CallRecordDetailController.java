package com.xmszit.voip.customer.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.voip.customer.entity.CallRecord;
import com.xmszit.voip.customer.entity.CallRecordDetail;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.query.CallRecordDetailQuery;
import com.xmszit.voip.customer.entity.query.CallRecordQuery;
import com.xmszit.voip.customer.entity.query.CustomerQuery;
import com.xmszit.voip.customer.service.CallPlanService;
import com.xmszit.voip.customer.service.CallRecordDetailService;
import com.xmszit.voip.customer.service.CallRecordService;
import com.xmszit.voip.customer.service.CustomerService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;

import net.sf.json.JSONObject;

/**
 * 
 * @author zjx
 *
 */
@Controller
@RequestMapping("/customer/callRecordDetail")
public class CallRecordDetailController extends BaseController<CallRecordDetail, CallRecordDetailQuery, CallRecordDetail> {
	@Autowired
	private CallRecordDetailService service;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CallPlanService callPlanService;
	
	@Autowired
	private CallRecordService callRecordService;
	
	@Override
	public CallRecordDetailService getService() {
		return service;
	}
	
	@Override
	protected void beforeQuery(CallRecordDetailQuery query) {
		query.setClientId(getLoginClientId());
		query.addOrder("displayOrder", false);
	}
	
	@RequestMapping("showDetails")
	@ResponseBody
	public ListJson showDetails(String number,String callPlanId){
		ListJson listJson = new ListJson();
		CustomerQuery customerQuery = new CustomerQuery();
		customerQuery.setMobile(number);
		customerQuery.setCallPlanId(callPlanId);
		customerQuery.setClientId(getLoginClientId());
		customerQuery.setDeleted(false);
		customerQuery.setClientUserId(getLoginClientUserId());
		Customer customer = customerService.getEntity(customerQuery);
		if(customer!=null){
			CallRecordQuery callRecordQuery = new CallRecordQuery();
			callRecordQuery.setCustomerId(customer.getId());
			callRecordQuery.setClientId(getLoginClientId());
			callRecordQuery.setDeleted(false);
			CallRecord callRecord = callRecordService.getEntity(callRecordQuery);
			if(callRecord!=null){
				CallRecordDetailQuery callRecordDetailQuery = new CallRecordDetailQuery();
				callRecordDetailQuery.setCallRecordId(callRecord.getId());
				callRecordDetailQuery.setClientId(getLoginClientId());
				callRecordDetailQuery.setDeleted(false);
				callRecordDetailQuery.addOrder("displayOrder", false);
				List<CallRecordDetail> list = service.getEntities(callRecordDetailQuery);
				listJson.setRows(list);
			}
		}
		return listJson;
	}
}
