package com.xmxnkj.voip.ivr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.ivr.dao.AiResponseDao;
import com.xmxnkj.voip.ivr.entity.AiResponse;
import com.xmxnkj.voip.ivr.entity.query.AiResponseQuery;
import com.xmxnkj.voip.ivr.service.AiResponseService;

@Service
@Transactional
public class AiResponseServiceImpl extends BusinessBaseServiceImpl< AiResponse,  AiResponseQuery> implements AiResponseService{
	
	@Autowired
	private AiResponseDao dao;

	@Override
	public AiResponseDao getDao(){
		return dao;
	}
}
