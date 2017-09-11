package com.xmxnkj.voip.customer.service;

import java.math.BigInteger;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.customer.entity.ContactState;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.CustomerType;
import com.xmxnkj.voip.customer.entity.ReceivingState;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;
import com.xmxnkj.voip.customer.web.models.CountCustomerType;

public interface CustomerService extends BusinessBaseService<Customer, CustomerQuery> {

	public String importCustomer(XSSFSheet sheet, String type,String clientId, ClientUser clientUser);
	public void deleteData(String id);
	public void open(String customerId);
	public Boolean isExistCustomer(String mobile, String clientId,String id);
	public CountCustomerType countCustomerType(String clientId, String clientUserId,String type,String jDate,String deptId);
	public CountCustomerType countCustState(String clientId, String clientUserId,String deptId);
	public BigInteger privateSeaCount(String clientId, String clientUserId,String deptId);
	public void callBack(String mobile, 
			ContactState contactState, 
			ReceivingState receivingState, 
			CustomerType customerType,
			String clientId);
	
	public Customer queryCustomer(String mobile, String clientId);
}
