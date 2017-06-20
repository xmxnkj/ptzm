package com.xmszit.voip.voice.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.voice.dao.ICallTimeSetDao;
import com.xmszit.voip.voice.entity.CallTimeSet;
import com.xmszit.voip.voice.entity.query.CallTimeSetQuery;
import com.xmszit.voip.voice.service.ICallTimeSetService;

@Service
public class CallTimeSetServiceImpl extends BusinessBaseServiceImpl<CallTimeSet, CallTimeSetQuery> implements ICallTimeSetService {

	@Autowired
	private ICallTimeSetDao dao;

	@Override
	protected ICallTimeSetDao getDao() {
		return dao;
	}

	@Override
	public CallTimeSet findById(String id) {
		CallTimeSet time = getEntityById(id);
		return time;
	}

	@Override
	public List<CallTimeSet> find() {
		List<CallTimeSet> list = getEntities(null);
		return list;
	}

	@Override
	public void add(CallTimeSet time) {
		save(time);
	}

	@Override
	public void delById(String id) {
		dao.delete(id);
	}

	@Override
	public boolean between(Date startDate, Date endDate,String clientId,String clientUserId) {
		return dao.between(startDate,endDate,clientId,clientUserId);
	}

}
