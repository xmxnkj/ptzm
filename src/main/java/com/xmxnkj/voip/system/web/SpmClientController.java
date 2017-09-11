package com.xmxnkj.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import com.xmxnkj.voip.web.BaseAction;

/**
 * @ProjectName:voip
 * @ClassName: SpmClientController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/spmClient")
public class SpmClientController extends BaseAction{
	@RequestMapping("/spmClientList")
	public ModelAndView showPage(){
		ModelAndView modelAndView = new ModelAndView("system/spmClient/list","params", getRequestParams());
		return modelAndView;
	}
	@RequestMapping("/spmNewsList")
	public ModelAndView spmNewsList(){
		ModelAndView modelAndView = new ModelAndView("system/spmNews/list","params", getRequestParams());
		return modelAndView;
	}
	@RequestMapping("/spmNewsEdit")
	public ModelAndView spmNewsEdit(String id) {
		ModelAndView modelAndView = new ModelAndView("system/spmNews/form", "id", id);
		modelAndView.getModel().put("params", getRequestParams());
		return modelAndView;
	}
	
	protected Map<String, String> getRequestParams(){
	    HttpServletRequest request = getRequest();
	    Map<String, String> params = new HashMap();
	    Enumeration<String> names = request.getParameterNames();
	    while (names.hasMoreElements())
	    {
	      String name = (String)names.nextElement();
	      params.put(name, request.getParameter(name));
	    }
	    return params;
	}
}
