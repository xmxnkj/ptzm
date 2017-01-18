package com.xmszit.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hsit.common.uac.entity.Operation;
import com.hsit.common.uac.entity.queryparam.OperationQuery;
import com.hsit.common.uac.service.OperationService;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;
import com.xmszit.voip.client.service.OperateRoleService;
import com.xmszit.voip.client.service.OperateService;
import com.xmszit.voip.system.entity.BaseArea;
import com.xmszit.voip.system.entity.query.BaseAreaQuery;
import com.xmszit.voip.system.service.BaseAreaService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.SystemBaseController;
import com.xmszit.voip.web.models.ListJson;

/**
 * @ProjectName:voip
 * @ClassName: operationController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/operation")
public class operationController extends SystemBaseController<Operation, OperationQuery, Operation>{
	@Autowired
	private OperationService service;

	@Override
	public OperationService getService() {
		return service;
	}
	@Autowired
	private OperateRoleService operateRoleService;
	@RequestMapping(value="/operateRoleList")
	@ResponseBody
	public ListJson operateRoleList(OperateRoleQuery query){
		try{
			if (operateRoleService==null) {
				return new ListJson(false);
			}
			List<OperateRole> entities = operateRoleService.getEntities(query);
			ListJson listJson = null;
			listJson = new ListJson(entities);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
}
