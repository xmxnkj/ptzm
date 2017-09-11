package com.xmxnkj.voip.customer.dao.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.customer.dao.CallPlanDao;
import com.xmxnkj.voip.customer.dao.CustomerDao;
import com.xmxnkj.voip.customer.entity.CallPlan;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.query.CallPlanQuery;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;

@Repository
public class CallPlanDaoImpl extends SimpleHibernate4Dao<CallPlan, CallPlanQuery> implements CallPlanDao{
	
}
