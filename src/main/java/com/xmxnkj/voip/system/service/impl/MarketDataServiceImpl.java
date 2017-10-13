package com.xmxnkj.voip.system.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.dao.MarketDataDao;
import com.xmxnkj.voip.system.entity.MarketData;
import com.xmxnkj.voip.system.entity.query.MarketDataQuery;
import com.xmxnkj.voip.system.service.MarketDataService;

@Service
@Transactional
public class MarketDataServiceImpl extends AppBaseServiceImpl<MarketData, MarketDataQuery> implements MarketDataService{
	
	@Autowired
	private MarketDataDao dao;

	@Override
	public MarketDataDao getDao() {
		return dao;
	}
	
}
