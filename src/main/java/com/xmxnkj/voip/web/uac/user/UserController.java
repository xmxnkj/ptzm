package com.xmszit.futures.web.uac.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.hsit.common.uac.dao.UserDao;
import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.entity.queryparam.UserQuery;
import com.hsit.common.uac.service.UserService;
import com.xmszit.futures.web.BaseController;
import com.xmszit.futures.web.models.ResultJson;

/**
 * @ProjectName:lightning
 * @ClassName: UserController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门晟中信息技术有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController<User, UserQuery>{
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserDao dao;
	
	@Override
	public UserService getService() {
		return service;
	}

	@RequestMapping(value="/showLogin")
	public ModelAndView showLogin(){
		return new ModelAndView("uac/user/login");
	}
	
	
	@RequestMapping("/login")
	public String login(String account, String passwd, HttpServletRequest request){
		try{
			
			//User user = service.login(account, passwd);
			
			User user = new User();
			if(account.equals("admin")){
				user.setLoginAccount(account);
				user.setName("admin");
			}
			
			if(user!=null){
				request.getSession().setAttribute("user", user);
				
				return "main";
			}
		}catch(Exception e){
			
		}
		return "showLogin";
	}
	

	
	@RequestMapping("/logout")
	public RedirectView logout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return new RedirectView("showLogin");
	}
	
	@RequestMapping("/loginjson")
	@ResponseBody
	public ResultJson loginJson(String account, String passwd){
		try{
			User user = service.login(account, passwd);
			if(user!=null){
				getSession().setAttribute("user", user);
				
				
				return new ResultJson(true);
			}
		}catch(Exception e){
			
		}
		return new ResultJson(false);
	}
	
	@RequestMapping("/showSetLogo")
	public ModelAndView showSetLogo(){
		return new ModelAndView("uac/user/logo");
	}
}