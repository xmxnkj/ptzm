package com.xmxnkj.voip.system.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.dao.ShowImageDao;
import com.xmxnkj.voip.system.entity.ShowImage;
import com.xmxnkj.voip.system.entity.query.ShowImageQuery;
import com.xmxnkj.voip.system.service.ShowImageService;

@Service
@Transactional
public class ShowImageServiceImpl extends AppBaseServiceImpl<ShowImage, ShowImageQuery> implements ShowImageService{
	
	@Autowired
	private ShowImageDao dao;

	@Override
	public ShowImageDao getDao() {
		return dao;
	}
	
}
