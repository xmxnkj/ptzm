package com.xmszit.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hsit.common.exceptions.ApplicationException;
import com.xmszit.voip.system.entity.BaseArea;
import com.xmszit.voip.system.entity.query.BaseAreaQuery;
import com.xmszit.voip.system.service.BaseAreaService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.SystemBaseController;
import com.xmszit.voip.web.models.ResultJson;

/**
 * @ProjectName:voip
 * @ClassName: BaseAreaController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/baseArea")
public class BaseAreaController extends SystemBaseController<BaseArea, BaseAreaQuery, BaseArea>{
	@Autowired
	private BaseAreaService service;

	@Override
	public BaseAreaService getService() {
		return service;
	}

	@Override
	public ResultJson saveJson(BaseArea entity, String objName) {
		// TODO Auto-generated method stub
		BaseAreaQuery areaQuery = new BaseAreaQuery();
		areaQuery.setName(entity.getName().trim());
		if (getService().getEntity(areaQuery)!=null) {
			throw new ApplicationException("区域名称重复，请重新填写！");
		};
		return super.saveJson(entity, objName);
	}
}
