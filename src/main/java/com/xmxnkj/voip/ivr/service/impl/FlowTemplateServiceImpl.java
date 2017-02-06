package com.xmszit.voip.ivr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.ivr.dao.FlowTemplateDao;
import com.xmszit.voip.ivr.entity.FlowTemplate;
import com.xmszit.voip.ivr.entity.query.FlowTemplateQuery;
import com.xmszit.voip.ivr.service.FlowTemplateService;

@Service
@Transactional
public class FlowTemplateServiceImpl extends BusinessBaseServiceImpl<FlowTemplate, FlowTemplateQuery> implements FlowTemplateService{
	
	@Autowired
	private FlowTemplateDao dao;

	@Override
	public FlowTemplateDao getDao(){
		return dao;
	}
	
}
