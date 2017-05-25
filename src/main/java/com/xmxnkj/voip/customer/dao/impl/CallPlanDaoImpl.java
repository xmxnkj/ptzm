package com.xmszit.voip.customer.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.customer.dao.CallPlanDao;
import com.xmszit.voip.customer.dao.CustomerDao;
import com.xmszit.voip.customer.entity.CallPlan;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.query.CallPlanQuery;
import com.xmszit.voip.customer.entity.query.CustomerQuery;

@Repository
public class CallPlanDaoImpl extends SimpleHibernate4Dao<CallPlan, CallPlanQuery> implements CallPlanDao{
	
}
