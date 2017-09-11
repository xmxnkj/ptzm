package com.xmxnkj.voip.voice.dao;

import java.util.Date;
import java.util.List;

import com.hsit.common.dao.Dao;
import com.xmxnkj.voip.voice.entity.CallTimeSet;
import com.xmxnkj.voip.voice.entity.query.CallTimeSetQuery;

public interface ICallTimeSetDao extends Dao<CallTimeSet, CallTimeSetQuery> {

	/**
	 * 增加一条数据
	 * @param voice
	 * @return
	 */
	Boolean add(CallTimeSet time);
	/**
	 * 获取全部的数据
	 * @return
	 */
	List<CallTimeSet> query();
	/**
	 * 根据id获取一条具体的数据
	 * @param id
	 * @return
	 */
	CallTimeSet queryById(String id);
	
	/**
	 * 判断该时间是否处于某个时间段
	 * @param datetime
	 * @param clientUserId 坐席ID
	 * @return
	 */
	Boolean between(Date startDate,Date endDate,String clientId,String clientUserId);
}
