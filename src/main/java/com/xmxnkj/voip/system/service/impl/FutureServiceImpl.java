package com.xmxnkj.voip.system.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.dao.FutureDao;
import com.xmxnkj.voip.system.entity.Future;
import com.xmxnkj.voip.system.entity.query.FutureQuery;
import com.xmxnkj.voip.system.service.voipervice;

@Service
@Transactional
public class voiperviceImpl extends AppBaseServiceImpl<Future, FutureQuery> implements voipervice{
	

	@Autowired
	private FutureDao dao;

	@Override
	public FutureDao getDao() {
		return dao;
	}
}
