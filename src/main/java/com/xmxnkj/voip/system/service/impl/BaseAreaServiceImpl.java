package com.xmxnkj.voip.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.system.dao.BaseAreaDao;
import com.xmxnkj.voip.system.entity.BaseArea;
import com.xmxnkj.voip.system.entity.query.BaseAreaQuery;
import com.xmxnkj.voip.system.service.BaseAreaService;

/**
 * @ProjectName:voip
 * @ClassName: BaseAreaServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class BaseAreaServiceImpl extends BusinessBaseServiceImpl<BaseArea, BaseAreaQuery> implements BaseAreaService{
	@Autowired
	private BaseAreaDao dao;

	@Override
	public BaseAreaDao getDao() {
		return dao;
	}
	
}
