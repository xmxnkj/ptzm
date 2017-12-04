package com.xmxnkj.lightning.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.lightning.system.dao.ClientDao;
import com.xmxnkj.lightning.system.entity.Client;
import com.xmxnkj.lightning.system.entity.query.ClientQuery;
import com.xmxnkj.lightning.system.service.ClientService;

/**
 * @ProjectName:voip
 * @ClassName: ClientServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class ClientServiceImpl extends AppBaseServiceImpl<Client, ClientQuery> implements ClientService{
	@Autowired
	private ClientDao dao;

	@Override
	public ClientDao getDao() {
		return dao;
	}
}
