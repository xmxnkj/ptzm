package com.xmxnkj.voip.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ProjectName:lightning
 * @ClassName: IndexController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
public class IndexController extends BaseAction{
	@RequestMapping(value="/main")
	public ModelAndView main(String module, HttpServletRequest request){
		
		ModelAndView view = new ModelAndView("main", "module", module);
		
		view.addObject("user", request.getSession().getAttribute("user"));
		
		return view;
	}
}
