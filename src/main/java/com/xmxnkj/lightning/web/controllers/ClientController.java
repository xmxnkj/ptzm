package com.xmxnkj.lightning.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.lightning.system.entity.Client;
import com.xmxnkj.lightning.system.entity.query.ClientQuery;
import com.xmxnkj.lightning.system.service.ClientService;

/**
 * @ProjectName:voip
 * @ClassName: ClientController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/client")
public class ClientController extends BaseController<Client, ClientQuery> {
	@Autowired
	private ClientService service;

	@Override
	public ClientService getService() {
		return service;
	}
}
