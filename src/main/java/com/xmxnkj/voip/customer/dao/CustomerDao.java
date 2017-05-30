package com.xmszit.voip.customer.dao;

import java.math.BigInteger;

import com.hsit.common.dao.Dao;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.query.CustomerQuery;
import com.xmszit.voip.customer.web.models.CountCustomerType;

public interface CustomerDao  extends Dao<Customer, CustomerQuery>{

	public void openDao(String customerId);
	public CountCustomerType countCustomerType(String clientId,String clientUserId,String type,String jDate,String deptId);
	public CountCustomerType countCustState(String clientId, String clientUserId,String deptId);
	BigInteger privateSeaCount(String clientId, String clientUserId,String deptId);
}
