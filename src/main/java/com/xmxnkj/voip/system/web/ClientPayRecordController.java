package com.xmszit.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.service.ClientService;
import com.xmszit.voip.system.entity.PayType;
import com.xmszit.voip.system.entity.ClientPayRecord;
import com.xmszit.voip.system.entity.query.ClientPayRecordQuery;
import com.xmszit.voip.system.service.ClientPayRecordService;
import com.xmszit.voip.web.SystemBaseController;
import com.xmszit.voip.web.models.ResultJson;

/**
 * @ProjectName:voip
 * @ClassName: ClientPayRecordController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/clientPayRecord")
public class ClientPayRecordController extends SystemBaseController<ClientPayRecord, ClientPayRecordQuery, ClientPayRecord>{
	@Autowired
	private ClientPayRecordService service;

	@Override
	public ClientPayRecordService getService() {
		return service;
	}
	
	@RequestMapping(value="/savePay")
	@ResponseBody
	public ResultJson savePay(ClientPayRecord clientPayRecord,String managerPw) {
		ResultJson json = new ResultJson();
		if (clientPayRecord==null || clientPayRecord.getClient()==null) {
			json.setMessage("请填写相关数据");
			json.setSuccess(false);
			return json;
		}
		try {
			getService().savePay(clientPayRecord, getLoginUser(),managerPw);
			json.setSuccess(true);
			json.setEntity(clientPayRecord.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultJson(e);
		}
		return json;
	}
	
}
