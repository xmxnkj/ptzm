package com.xmszit.voip.voice.dao.impl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.hsit.common.dao.EntityOrder;
import com.hsit.common.dao.QueryParam;
import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.hsit.common.kfbase.entity.Paging;
import com.hsit.common.kfbase.entity.RelaParam;
import com.xmszit.voip.voice.dao.ICallTimeSetDao;
import com.xmszit.voip.voice.dao.IVoiceTemplateDao;
import com.xmszit.voip.voice.entity.CallTimeSet;
import com.xmszit.voip.voice.entity.VoiceTemplate;
import com.xmszit.voip.voice.entity.query.CallTimeSetQuery;
import com.xmszit.voip.voice.entity.query.VoiceTemplateQuery;

@Repository
public class CallTimeSetDaoImpl extends SimpleHibernate4Dao<CallTimeSet, CallTimeSetQuery> implements ICallTimeSetDao {

	@Override
	public Boolean add(CallTimeSet voice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CallTimeSet> query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallTimeSet queryById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 判断该时间段是否合理
	 * 
	 * 返回True，表示该时间段没有与原有的时间段冲突；
	 * 返回False，表示有冲突
	 */
	@Override
	public Boolean between(Date startDate, Date endDate, String clientId,String clientUserId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(startDate);
		String end = sdf.format(endDate);

//		StringBuffer sql = new StringBuffer("SELECT count(1) AS CA FROM CS_CALL_TIME_SET c  where c.CLIENTID = '"
//				+ clientId + "' and c.DELETED = 0 and c.START_TIME > '" + end + "' or c.END_TIME > '" + start + "' ");
		

		StringBuffer hql = new StringBuffer("SELECT count(1) AS CA FROM CS_CALL_TIME_SET c where");
		hql.append("  c.DELETED = :deleted");
		hql.append(" AND c.CLIENTID = :clientId AND c.CLIENT_USER_ID=:clientUserId");
		hql.append(" AND (c.START_TIME > :end");
		hql.append(" OR c.END_TIME > :start)");
		
		Map<String, Object> map = new HashMap<>();
		map.put("deleted", false);
		map.put("clientId", clientId);
		map.put("clientUserId", clientUserId);
		map.put("end", end);
		map.put("start", start);
		BigInteger size = findSqlCounts(hql.toString(), map);
		BigInteger a1 = new BigInteger("0");
		if(size.compareTo(a1) == 0) {//等于0，返回true
			return true;
		}else {
			return false;
		}
	}

}
