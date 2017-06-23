package com.xmszit.voip.web.system.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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

import com.hsit.common.MD5Util;
import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.uac.entity.Ace;
import com.hsit.common.uac.entity.Operation;
import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.entity.UserRoleType;
import com.hsit.common.uac.entity.UserState;
import com.hsit.common.uac.entity.queryparam.AceQuery;
import com.hsit.common.uac.entity.queryparam.OperationQuery;
import com.hsit.common.uac.entity.queryparam.UserQuery;
import com.hsit.common.uac.service.AceService;
import com.hsit.common.uac.service.OperationService;
import com.hsit.common.uac.service.UserService;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.system.web.model.AceList;
import com.xmszit.voip.web.SystemBaseController;
import com.xmszit.voip.web.models.ListJson;
import com.xmszit.voip.web.models.ResultJson;


/**
 * @ProjectName:voip
 * @ClassName: UserController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends SystemBaseController<User, UserQuery, User> {
	@Autowired
	private UserService service;

	@Override
	public UserService getService() {
		return service;
	}
	
	@Autowired
	private OperationService operationService;
	@Autowired
	private AceService aceService;
	
	@RequestMapping("/showLogin")
	public ModelAndView showLogin(){
		return new ModelAndView("system/user/login");
	}
	
	@RequestMapping("/loginjson")
	@ResponseBody
	public ResultJson login(String account, String passwd,String remFlag){
		/*System.out.println(MD5Util.MD5(passwd));*/
		User user = service.login(account, passwd);
		user.setDisplayOrder(1+(user.getDisplayOrder()==null?0:user.getDisplayOrder()));
		user.setLoginPasswd(null);
		String ip = getIpAddr(getRequest());
		user.setDefaultLoginIp(ip);
		user.setBornDate(new Date());
		getService().save(user);
		setLoginUser(user);
		if (user.getUserRoleType()!=null&&user.getUserRoleType()==UserRoleType.SysAdmin) {
			OperationQuery query = new OperationQuery();
			List<Operation> operations = operationService.getEntities(query);
			for (Operation operation : operations) {
				if (operation.getDescription().equals("1")) {
					operation.setUrlPath(getOperationUrl(operations, operation,1));
				}
			}
			setLoginOperations(operations);
		}else {
			AceQuery aceQuery = new AceQuery();
	    	aceQuery.setObjectId(user.getId());
	    	List<Ace> list = aceService.getEntities(aceQuery);
	    	List<Operation> operations = new ArrayList<Operation>();
	    	for (Ace ace : list) {
	    		operations.add(operationService.getById(ace.getOperationId()));
			}
	    	if (operations!=null&&operations.size()>0) {
	    		Collections.sort(operations, new Comparator<Operation>() {
	    			@Override
	    			public int compare(Operation o1, Operation o2) {
	    				if (o1.getDisplayOrder() > o2.getDisplayOrder()) {  
	                        return 1;  
	                    }  
	                    if (o1.getDisplayOrder() == o2.getDisplayOrder()) {  
	                        return 0;  
	                    }  
	                    return -1; 
	    			}
	    		});
			}
	    	for (Operation operation : operations) {
				if (operation.getDescription().equals("1")) {
					operation.setUrlPath(getOperationUrl(operations, operation,1));
				}
			}
	    	setLoginOperations(operations);
		}
		if (remFlag.equals("true")) {
			String loginInfo = account+"#"+passwd+"#true";
             Cookie userCookie=new Cookie("systemLogInfo",loginInfo); 
             userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
             userCookie.setPath("/");
             ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
             attr.getResponse().addCookie(userCookie); 
		}else {
			Cookie delCookie = new Cookie("systemLogInfo", "false");
			delCookie.setMaxAge(0);
			delCookie.setPath("/");
			 ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
             attr.getResponse().addCookie(delCookie);
		}
    	//getSession().setAttribute("operations", operations);
		return new ResultJson(true);
	}
	
	public String getOperationUrl(List<Operation> list,Operation operation_s,Integer grade_s){
		List<Operation> operates = list;
		if (operates!=null) {
			for (Operation operation : operates) {
				Integer grade = Integer.valueOf(operation.getDescription());
				if ((grade_s+1==grade)&&(operation_s.getId().equals(operation.getCategoryId()))) {
					if (operation.getActionName().equals("1")) {
						return operation_s.getUrlPath();
					}else {
						return getOperationUrl(list,operation,grade);
					}
				}
			}
		}
		return operation_s.getUrlPath();
	}
	
	
	@RequestMapping(value="/saveUser")
	@ResponseBody
	public ResultJson saveUser(User entity,AceList aces){
		User user = null;
		if (entity==null) {
			return new ResultJson(false);
		}
		if (getService()==null) {
			return new ResultJson(false);
		}
		if (!StringUtils.isEmpty(entity.getId())) {
			user = getService().getById(entity.getId());
			user.setDepartment(entity.getDepartment());
			user.setContact(entity.getContact());
			user.setDescription(entity.getDescription());
			user.setLoginPasswd(entity.getLoginPasswd());
			if (!user.getUserState().toString().equals(entity.getUserState().toString())
					&&entity.getUserState().toString().equals(UserState.Normal.toString())) {
				user.setPinYin(getNowDate());
			}
			user.setUserState(entity.getUserState());
			user.setLoginAccount(entity.getLoginAccount());
		}/*else {
			if (StringUtils.isEmpty(entity.getLoginPasswd())) {
				throw new ApplicationException("请输入密码!");
			}
		}*/
		if (StringUtils.isEmpty(entity.getLoginPasswd())) {
			throw new ApplicationException("请输入密码!");
		}
		try{
			ResultJson json = new ResultJson(true);
			if (user!=null) {
				getService().save(user);
				//添加权限
				setOperation(aces.getItems(),user.getId());
				json.setEntity(user.getId());
			}else {
				entity.setId(getService().save(entity));
				//添加权限
				setOperation(aces.getItems(),entity.getId());
				json.setEntity(entity.getId());
			}
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return new ResultJson(e);
		}
	}
	/**
     * 获取登录用户IP地址
     * 
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        return ip;
    }
    private String getNowDate(){
    	Date date = new Date();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return format.format(date);
    }
    /**
     * 
     */
    @RequestMapping(value="/getOperation")
	@ResponseBody
	private ListJson getOperation(String userId){
    	ListJson json = new ListJson(true);
    	AceQuery aceQuery = new AceQuery();
    	aceQuery.setObjectId(userId);
    	List<Ace> list = aceService.getEntities(aceQuery);
    	json.setRows(list);
    	return json;
    }
    
    /**
     * 添加权限
     */
    public void setOperation(List<Ace> aces,String userId){
    	AceQuery aceQuery = new AceQuery();
    	aceQuery.setObjectId(userId);
    	List<Ace> list = aceService.getEntities(aceQuery);
    	//删除所有权限
    	for (Ace ace : list) {
    		aceService.deleteById(ace.getId());
		}
    	//重新添加
    	if (aces!=null) {
    		for (Ace ace : aces) {
    			ace.setObjectId(userId);
    			aceService.saveSimple(ace);
    		}
		}
    }
    
    @Override
    public ListJson list(UserQuery query, Integer rows, Integer page) {
    	// TODO Auto-generated method stub
    	try{
			if (getService()==null) {
				return new ListJson(false);
			}
			if (rows!=null && rows>0) {
				if (page==null) {
					page=1;
				}
				query.setPage(page, rows);
			}
			List<User> entities = getService().getEntities(query);
			for (User user : entities) {
				AceQuery aceQuery = new AceQuery();
		    	aceQuery.setObjectId(user.getId());
		    	List<Ace> list = aceService.getEntities(aceQuery);
		    	StringBuffer op = new StringBuffer(""); 
		    	for (Ace ace : list) {
		    		op.append(operationService.getById(ace.getOperationId()).getName()+"|");
				}
		    	if (op.length()>1) {
		    		user.setDescription(op.toString().substring(0, op.length()-1));
				}
			}
			ListJson listJson = new ListJson();
			listJson.setRows(entities);
			listJson.setPaging(query.getPaging());
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
    }
    @Override
    public ResultJson deleteJson(String id) {
    	// TODO Auto-generated method stub
    	User user = getService().getById(id);
    	if (user!=null&&!StringUtils.isEmpty(user.getPicId())
    			&&user.getPicId().equals("1")) {
			throw new ApplicationException("该账号不可删除！");
		}
    	return super.deleteJson(id);
    }
    @RequestMapping("logoutUser")
	public ModelAndView logoutUser(){
    	getSession().removeAttribute("user");
    	getSession().removeAttribute("operations");
		/*Enumeration<String> names = getSession().getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			getSession().removeAttribute(name);
		}*/
		return new ModelAndView("/system/user/login");
	}
    
}
