package com.xmxnkj.voip.system.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.dao.TradProcessDao;
import com.xmxnkj.voip.system.entity.TradProcess;
import com.xmxnkj.voip.system.entity.query.TradProcessQuery;
import com.xmxnkj.voip.system.service.TradProcessService;

@Service
@Transactional
public class TradProcessServiceImpl extends AppBaseServiceImpl<TradProcess, TradProcessQuery> implements TradProcessService{
	
	@Autowired
	private TradProcessDao dao;

	@Override
	public TradProcessDao getDao() {
		return dao;
	}
	
}
