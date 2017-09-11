package com.xmxnkj.voip.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.system.dao.ModifyClientRecordDao;
import com.xmxnkj.voip.system.entity.ModifyClientRecord;
import com.xmxnkj.voip.system.entity.query.ModifyClientRecordQuery;
import com.xmxnkj.voip.system.service.ModifyClientRecordService;

/**
 * @ProjectName:voip
 * @ClassName: ModifyClientRecordServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class ModifyClientRecordServiceImpl extends BusinessBaseServiceImpl<ModifyClientRecord, ModifyClientRecordQuery> implements ModifyClientRecordService{
	@Autowired
	private ModifyClientRecordDao dao;

	@Override
	public ModifyClientRecordDao getDao() {
		return dao;
	}
	
}
