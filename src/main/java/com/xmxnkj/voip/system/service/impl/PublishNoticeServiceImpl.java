package com.xmszit.voip.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.system.dao.PublishNoticeDao;
import com.xmszit.voip.system.entity.PublishNotice;
import com.xmszit.voip.system.entity.query.PublishNoticeQuery;
import com.xmszit.voip.system.service.PublishNoticeService;

/**
 * @ProjectName:voip
 * @ClassName: NoticeServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class PublishNoticeServiceImpl extends BusinessBaseServiceImpl<PublishNotice, PublishNoticeQuery> implements PublishNoticeService{
	@Autowired
	private PublishNoticeDao dao;

	@Override
	public PublishNoticeDao getDao() {
		return dao;
	}
	
}
