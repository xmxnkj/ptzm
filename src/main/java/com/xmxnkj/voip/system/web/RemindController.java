package com.xmxnkj.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.xmxnkj.voip.system.entity.Remind;
import com.xmxnkj.voip.system.entity.query.RemindQuery;
import com.xmxnkj.voip.system.service.RemindService;
import com.xmxnkj.voip.web.SystemBaseController;
import com.xmxnkj.voip.web.models.ListJson;

/**
 * @ProjectName:voip
 * @ClassName: RemindController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/remind")
public class RemindController extends SystemBaseController<Remind, RemindQuery, Remind>{
	@Autowired
	private RemindService service;

	@Override
	public RemindService getService() {
		return service;
	}
	@Override
	public ListJson list(RemindQuery query, Integer rows, Integer page) {
		// TODO Auto-generated method stub
		List<Remind> list = new ArrayList<Remind>();
		List<Remind> reminds = getService().getEntities(query);
		if (reminds!=null&&reminds.size()>0) {
			if (reminds.size()>1) {
				for (Remind remind : reminds) {
					getService().deleteById(remind.getId());
				}
				Remind remind = new Remind(15,true);
				getService().save(remind);
				list.add(remind);
			}else {
				list.addAll(reminds);
			}
		}else {
			Remind remind = new Remind(15,true);
			getService().save(remind);
			list.add(remind);
		}
		ListJson listJson = new ListJson(true);
		listJson.setRows(list);
		return listJson;
	}
	
}
