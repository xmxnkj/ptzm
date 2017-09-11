package com.xmxnkj.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.xmxnkj.voip.system.entity.ModifyClientRecord;
import com.xmxnkj.voip.system.entity.query.ModifyClientRecordQuery;
import com.xmxnkj.voip.system.service.ModifyClientRecordService;
import com.xmxnkj.voip.web.SystemBaseController;

/**
 * @ProjectName:voip
 * @ClassName: ModifyClientRecordController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/modifyClientRecord")
public class ModifyClientRecordController extends SystemBaseController<ModifyClientRecord, ModifyClientRecordQuery, ModifyClientRecord>{
	@Autowired
	private ModifyClientRecordService service;

	@Override
	public ModifyClientRecordService getService() {
		return service;
	}
	
	
}
