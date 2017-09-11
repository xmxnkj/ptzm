package com.xmxnkj.voip.web.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.query.ClientQuery;
import com.xmxnkj.voip.client.service.ClientService;
import com.xmxnkj.voip.web.SystemBaseController;
import com.xmxnkj.voip.web.models.ResultJson;

/**
 * @ProjectName:voip
 * @ClassName: ClientManageController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/client")
public class SystemClientController extends SystemBaseController<Client, ClientQuery, Client>{
	@Autowired
	private ClientService service;

	@Override
	public ClientService getService() {
		return service;
	}
	
	@RequestMapping("/createClient")
	@ResponseBody
	public ResultJson createClient(Client client){
		client.setAddUser(getLoginUser());
		service.createClient(client);
		return new ResultJson(true);
	}
	@RequestMapping("/modifyClient")
	@ResponseBody
	public ResultJson modifyClient(Client client,String managerPw,String modifyRemark){
		service.modifyClient(client, managerPw, modifyRemark, getLoginUser());
		return new ResultJson(true);
	}
	@RequestMapping("/delClient")
	@ResponseBody
	public ResultJson delClient(Client client,String managerPw,String modifyRemark){
		service.delClient(client, managerPw, modifyRemark, getLoginUser());
		return new ResultJson(true);
	}
}
