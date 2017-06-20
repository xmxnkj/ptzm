package com.xmszit.voip.voice.service;

import java.util.Date;
import java.util.List;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.voice.entity.CallTimeSet;
import com.xmszit.voip.voice.entity.VoiceTemplate;
import com.xmszit.voip.voice.entity.query.CallTimeSetQuery;

public interface ICallTimeSetService extends BusinessBaseService<CallTimeSet, CallTimeSetQuery>{
	/** 根据id获取结果 */
	CallTimeSet findById(String id);
	/** 查询 */
	List<CallTimeSet> find();
	/** 添加|更新 */
	void add(CallTimeSet time);
	/** 完全删除（删除数据库的数据）*/
	void delById(String id);
	
	/** 判断时间是否处于某个时间段*/
	boolean between(Date startDate,Date endDate,String clientId,String clientUserId);
}
