package com.xmszit.voip.ivr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.ivr.dao.AiResponseDao;
import com.xmszit.voip.ivr.entity.AiResponse;
import com.xmszit.voip.ivr.entity.query.AiResponseQuery;
import com.xmszit.voip.ivr.service.AiResponseService;

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
