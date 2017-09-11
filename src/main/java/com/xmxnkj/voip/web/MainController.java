package com.xmxnkj.voip.web;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.utils.DateUtil;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.client.service.ClientService;
import com.xmxnkj.voip.system.entity.PublishNotice;
import com.xmxnkj.voip.system.service.RemindService;
import com.xmxnkj.voip.web.utils.ExportExcel;


/**
 * @ProjectName:voip
 * @ClassName: MainController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */

@Controller
public class MainController extends BaseAction {
	
	@Autowired
	private RemindService remindService;
	@Autowired
	private ClientService clientService;
	@RequestMapping("/importCustomerTemplate")
	@ResponseBody
	public void importCustomerTemplate(){
		try {
			ExportExcel.loadExcelTemplate("importCustomer");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/main")
	public ModelAndView main(){
		ModelAndView model = new ModelAndView("main");
		
		model.addObject("clientUser", getLoginClientUser());
		model.addObject("client", getLoginClient());
		
		model.addObject("params", getRequestParams());
		
		
		List<Operate> topMenu = getLoginOperates(1);
//		Map<String, List<Operate>> menus = new HashMap<>();
//		for (Operate operate : topMenu) {
//			menus.put(operate.getId(), getLoginOperates(2, operate.getId()));
//		}
		
		model.addObject("topMenus", topMenu);
		model.addObject("topMenusLength", topMenu.size());
//		model.addObject("menus", menus);
		
		System.out.println(getRequest().getContextPath());
		//获取通知地区通知
		List<PublishNotice> notices = remindService.updateNotice(clientService.getById(getLoginClientId()));
		getSession().setAttribute("notices", notices);
		return model;
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index");
		return model;
	}
	public List<Operate> getLoginOperates(int grade){
		List<Operate> operates = getLoginOperates();
		List<Operate> gradeOperates = new ArrayList<>();
		Integer i = 0;
		if (operates!=null) {
			for (Operate operate : operates) {
				if (operate.getGrade()==grade) {
					if (grade==1&&operate.getIsShow()) {
						operate.setDisplayOrder(i);
						i++;
					}
					gradeOperates.add(operate);
				}
			}
		}
		return gradeOperates;
	}
	
}
