package com.xmszit.voip.ivr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.ivr.dao.FlowDao;
import com.xmszit.voip.ivr.entity.Flow;
import com.xmszit.voip.ivr.entity.query.FlowQuery;
import com.xmszit.voip.ivr.service.FlowService;

@Service
@Transactional
public class FlowServiceImpl extends BusinessBaseServiceImpl<Flow, FlowQuery> implements FlowService{
	
	@Autowired
	private FlowDao dao;

	@Override
	public FlowDao getDao(){
		return dao;
	}
}
