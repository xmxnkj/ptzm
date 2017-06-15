package com.xmszit.voip.customer.dao.impl;


import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.hsit.common.utils.DateUtil;
import com.xmszit.voip.customer.dao.CustomerDao;
import com.xmszit.voip.customer.entity.ContactState;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.CustomerType;
import com.xmszit.voip.customer.entity.query.CustomerQuery;
import com.xmszit.voip.customer.web.models.CountCustomerType;

@Repository
public class CustomerDaoImpl extends SimpleHibernate4Dao<Customer, CustomerQuery> implements CustomerDao{
	@Override
	public void openDao(String customerId) {
		String sql = "UPDATE CUT_CUSTOMER c SET c.TYPE = 1,"
				+ " c.CLIENT_USER = null,"
				+ " c.CUSTOMER_TYPE = null,"
				+ " c.CALL_PLAN = null,"
				+ " c.CONTACT_STATE = null,"
				+ " c.RECEIVING_STATE = null,"
				+ " c.TALK_TIME = null,"
				+ " c.LAST_CALL_DATE = null "
				+ " WHERE c.ID = :id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", customerId);
		executeUpdateSql(sql, map);
	}
	@Override
	public CountCustomerType countCustomerType(String clientId,String clientUserId,String type,String jDate,String deptId) {
		StringBuffer sql = new StringBuffer("SELECT count(1) AS CA FROM CUT_CUSTOMER c  where c.CUSTOMER_TYPE = :customerType ");
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(clientId)) {
			sql.append(" AND c.CLIENT_ID = :clientId");
			map.put("clientId", clientId);
		}
		if (!StringUtils.isEmpty(clientUserId)) {
			sql.append(" AND c.CLIENT_USER = :clientUserId");
			map.put("clientUserId", clientUserId);
		}
		if (!StringUtils.isEmpty(deptId)) {
			sql.append(" AND c.CLIENT_USER IN (SELECT cu.ID FROM CT_CLIENT_USER cu WHERE cu.DEPT_ID = :deptId)");
			map.put("deptId", deptId);
		}
		if (!StringUtils.isEmpty(type)) {
			if (type.equals("Contacted")) {
				sql.append(" AND c.CONTACT_STATE = :contactState");
				map.put("contactState", ContactState.Contacted.ordinal());
				sql.append(" AND c.TYPE = :type");
				map.put("type", 0);
			}else {
				sql.append(" AND c.TYPE = :type");
				map.put("type", type);
			}
		}
		Date nowDate = new Date();
		String dayDay = DateUtil.formatDate(nowDate);
		if (jDate.equals("w")) {
			sql.append(" AND CONVERT(c.COM_CUST_TYPE_DATE,DATE) <= date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 5 DAY) ")
			.append(" AND CONVERT(c.COM_CUST_TYPE_DATE,DATE) >= date_sub(curdate(),INTERVAL WEEKDAY(curdate()) + 1 DAY);");
		}else if (jDate.equals("m")) {
			//本月
			String monthDay = dayDay.substring(0,7);
			sql.append(" AND c.COM_CUST_TYPE_DATE LIKE :day ");
			map.put("day", monthDay+"%");
			System.out.println("本月："+monthDay);
		}else if (jDate.equals("d")) {
			//今日
			sql.append(" AND c.COM_CUST_TYPE_DATE LIKE :day ");
			map.put("day", dayDay+"%");
			System.out.println("本日："+dayDay);
		}
		//A/B/C/D/E/F
		CountCustomerType countCustomerType = new CountCustomerType();
		Integer customerType = CustomerType.A.ordinal();
		map.put("customerType", customerType);
		BigInteger ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountA(ca);
		customerType = CustomerType.B.ordinal();
		map.put("customerType", customerType);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountB(ca);
		customerType = CustomerType.C.ordinal();
		map.put("customerType", customerType);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountC(ca);
		customerType = CustomerType.D.ordinal();
		map.put("customerType", customerType);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountD(ca);
		customerType = CustomerType.E.ordinal();
		map.put("customerType", customerType);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountE(ca);
		customerType = CustomerType.F.ordinal();
		map.put("customerType", customerType);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountF(ca);
		return countCustomerType;
	}
	@Override
	public CountCustomerType countCustState(String clientId,String clientUserId,String deptId) {
		StringBuffer sql = new StringBuffer("SELECT count(1) AS CA FROM CUT_CUSTOMER c  where  1=1 ");
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(clientId)) {
			sql.append(" AND c.CLIENT_ID = :clientId");
			map.put("clientId", clientId);
		}
		if (!StringUtils.isEmpty(clientUserId)) {
			sql.append(" AND c.CLIENT_USER = :clientUserId");
			map.put("clientUserId", clientUserId);
		}
		if (!StringUtils.isEmpty(deptId)) {
			sql.append(" AND c.CLIENT_USER IN (SELECT cu.ID FROM CT_CLIENT_USER cu WHERE cu.DEPT_ID = :deptId)");
			map.put("deptId", deptId);
		}
		CountCustomerType countCustomerType = new CountCustomerType();
		//所有客户
		BigInteger ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountAll(ca);
		sql.append(" AND c.CONTACT_STATE = :state");
		//未联系
		Integer state = ContactState.UnContact.ordinal();
		map.put("state", state);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountUnCon(ca);
		//已联系
		state = ContactState.Contacted.ordinal();
		map.put("state", state);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountConed(ca);
		//联系中
		state = ContactState.Contacting.ordinal();
		map.put("state", state);
		ca= findSqlCounts(sql.toString(), map);
		countCustomerType.setCountConing(ca);
		return countCustomerType;
	}
	/**
	 * 计算坐席拥有的私海客户数量
	 */
	@Override
	public BigInteger privateSeaCount(String clientId,String clientUserId,String deptId) {
		StringBuffer sql = new StringBuffer("SELECT count(1) AS CA FROM CUT_CUSTOMER c  where c.TYPE = 0 ");
		Map<String, Object> map = new HashMap<>();
		if (!StringUtils.isEmpty(clientId)) {
			sql.append(" AND c.CLIENT_ID = :clientId");
			map.put("clientId", clientId);
		}
		if (!StringUtils.isEmpty(clientUserId)) {
			sql.append(" AND c.CLIENT_USER = :clientUserId");
			map.put("clientUserId", clientUserId);
		}
		/*if (!StringUtils.isEmpty(deptId)) {
			sql.append(" AND c.CLIENT_USER IN (SELECT cu.ID FROM CT_CLIENT_USER cu WHERE cu.DEPT_ID = :deptId)");
			map.put("deptId", deptId);
		}*/
		BigInteger b = findSqlCounts(sql.toString(), map);
		return b;
	}
    
}
