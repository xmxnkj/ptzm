package com.xmszit.voip.client.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.utils.DateUtil;
import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Dept;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.client.entity.query.ClientUserQuery;
import com.xmszit.voip.client.service.ClientAndRoleService;
import com.xmszit.voip.client.service.ClientService;
import com.xmszit.voip.client.service.ClientUserService;
import com.xmszit.voip.client.service.DeptService;
import com.xmszit.voip.client.service.OperateService;
import com.xmszit.voip.common.entity.Terminal;
import com.xmszit.voip.global.UserSessionService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;
import com.xmszit.voip.web.models.ResultJson;

/**
 * 
 * @author zjx
 *
 */
@Controller
@RequestMapping(value="/client/clientUser")
public class ClientUserController extends BaseController<ClientUser, ClientUserQuery, ClientUser>{
	@Autowired
	private ClientUserService service;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientAndRoleService clientAndRoleService;
	
	@Autowired
	private OperateService operateService;
	
	@Autowired
	private DeptService deptService;
	
	@Override
	public ClientUserService getService() {
		return service;
	}
	
	@Override
	public ListJson list(ClientUserQuery query, Integer rows, Integer page) {
		String showDept = getRequest().getParameter("showDept");
		if (!StringUtils.isEmpty(showDept)&&showDept.equals("1")) {
			if (getLoginClientUser().getDept()!=null) {
				query.setDeptId(getLoginClientUser().getDept().getId());
			}else {
				List<ClientUser> list = new ArrayList<>();
				list.add(getLoginClientUser());
				return new ListJson(list);
			}
		}
		return super.list(query, rows, page);
	}
	@RequestMapping("login")
	public ModelAndView login(){
		return new ModelAndView("/client/clientUser/login");
	}
	
	@ResponseBody
	@RequestMapping("loginJson")
	public ResultJson loginJson(String clientName, String account, String passwd, Terminal terminal, String remFlag,HttpServletRequest req){

		ClientUser clientUser = service.login(clientName, account, passwd);
		Client client = clientService.getById(clientUser.getClientId());
		
		//系统账户是否在有效期内
		if (client.getState()==null || !client.getState()) {
			throw new ApplicationException("您的账号处于停用状态!");
		}
		Date effectiveDate = client.getEffectiveDate();
		Date now = new Date();
		Long d = DateUtil.dateDiff(now, effectiveDate);
		//System.out.println("剩余："+d+"可延长："+(d.intValue()+7));
		if ((d.intValue()+7)<0) {
			throw new ApplicationException("您的账号已过期，请联系管理员进行缴费!");
		}
		//是否在限制时间内
		if ((clientUser.getNotLimit()==null || !clientUser.getNotLimit())
				&& (clientUser.getIsManager() == null || !clientUser.getIsManager())) {
			Boolean isLoginTime = isLoginTime(client);
			if (isLoginTime) {
				throw new ApplicationException("当前时间不允许登录！");
			}
		}
		ResultJson resultJson = new ResultJson();
		resultJson.setSuccess(clientUser!=null);
		if (clientUser!=null) {
			String sessionIdold = (String) getRequest().getSession().getAttribute("sessionId");
			if (!StringUtils.isEmpty(getLoginClientUserId())&&
					!StringUtils.isEmpty(sessionIdold)
					&&UserSessionService.sessions.containsKey(sessionIdold)) {
				//移除原本登录账号
				UserSessionService.users.remove(getLoginClientUserId());
			}
			setLoginClientUser(clientUser);
			//设置权限
			if (!StringUtils.isEmpty(clientUser.getClientId())) {
				setLoginClient(client);
				List<Operate> operates = operateService.queryUserOperate(getLoginClientUser());
				setLoginOperates(operates);
				
				if (terminal==null) {
					terminal=Terminal.Pc;
				}
				setTerminal(terminal);
			}
			String sessionId = UserSessionService.addSession(clientUser, getLoginClient());
			
			getSession().setAttribute("sessionId", sessionId);
			resultJson.setMessage(sessionId);
			if (!StringUtils.isEmpty(remFlag)&&"true".equals(remFlag)) {
				String loginInfo = clientName+"#"+account+"#"+passwd+"#true";
	             Cookie userCookie=new Cookie("loginInfo",loginInfo); 
	             userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
	             userCookie.setPath("/");
	             ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
	             attr.getResponse().addCookie(userCookie); 
			}else {
				Cookie delCookie = new Cookie("loginInfo", "false");
				delCookie.setMaxAge(0);
				delCookie.setPath("/");
				 ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
	             attr.getResponse().addCookie(delCookie);
			}
		}
		resultJson.setEntity(clientUser);
		return resultJson;
	}
	
	/**
	 * 保存
	 * @param clientUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveDate")
	public ListJson saveDate(ClientUser clientUser){
		ListJson json = new ListJson();
		try{
			//先判断账号是否重复
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setClientId(getLoginClientId());
			clientUserQuery.setAccount(clientUser.getAccount());
			clientUserQuery.setDeleted(false);
			ClientUser temp = service.getEntity(clientUserQuery);
			
			if(temp!=null){
				if(StringUtils.isEmpty(clientUser.getId()) || !clientUser.getId().equals(temp.getId())){
					json.setMessage("该账号已存在");
					json.setSuccess(false);
					return json;
				}
			}
			Dept dept = deptService.getById(clientUser.getDeptId());
			if(dept!=null){
				deptService.flushManager(deptService.getById(clientUser.getDeptId()));
				deptService.save(dept);
			}
			
			clientAndRoleService.updateClientAndRole(clientUser, getLoginClientId());
			json.setSuccess(true);
		}catch(Exception e){
			json.setSuccess(false);
		}
		return json;
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(){
		clearSession();
		return new ModelAndView("/client/clientUser/login");
	}
	@RequestMapping("changePasswd")
	@ResponseBody
	public ResultJson changePasswd(String oldPasswd, String newPasswd){
		service.changePasswd(getLoginClientUserId(), oldPasswd, newPasswd);
		return new ResultJson(true);
	}
	private void clearSession() {
		Enumeration<String> names = getSession().getAttributeNames();
		ClientUser clientUser = new ClientUser();
		
		if (clientUser!=null && !StringUtils.isEmpty(getLoginClientUserId())) {
			clientUser.setId(getLoginClientUserId());
			clientUser.setIsManager(getLoginClientUser().getIsManager());
			clientUser.setLastExitTime(new Date());
			getService().save(clientUser);
		}
		UserSessionService.users.remove(clientUser.getId());
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			getSession().removeAttribute(name);
		}
	}
	public Boolean isLoginTime(Client client){
		String loginTime = client.getLoginLimitTime();
		if (loginTime!=null) {
			String time_1 = loginTime.split("-")[0];
			String time_2 = loginTime.split("-")[1];
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			String nowTime = format.format(date);
			if (nowTime.compareTo(time_1) >= 0 && nowTime.compareTo(time_2) <= 0) {
				
			}else {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 删除
	 * @param clientUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteClientUser")
	public ListJson saveDate(String id){
		ListJson json = new ListJson();
		try{
			json = service.deleteClientUser(id);
		}catch(Exception e){
			json.setSuccess(false);
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("getObj")
	public Object getObj(){
		Map map = new HashMap();
		map.put("rows", service.getEntityCount(new ClientUserQuery()));
		return map;
	}
}