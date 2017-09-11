package com.xmxnkj.voip.client.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.client.dao.SeatGroupDao;
import com.xmxnkj.voip.client.entity.SeatGroup;
import com.xmxnkj.voip.client.entity.query.SeatGroupQuery;
import com.xmxnkj.voip.client.service.SeatGroupService;

@Service
@Transactional
public class SeatGroupServiceImpl extends BusinessBaseServiceImpl<SeatGroup, SeatGroupQuery>  implements SeatGroupService{
	
	@Autowired 
	private SeatGroupDao dao;
    
	@Override
	public SeatGroupDao getDao(){
	 return dao;
    }
}
