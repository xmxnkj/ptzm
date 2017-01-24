package com.xmszit.voip.web.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.entity.UserState;
import com.hsit.common.utils.DateUtil;
import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.service.ClientService;
import com.xmszit.voip.global.ClientSession;
import com.xmszit.voip.global.UserSessionService;
import com.xmszit.voip.web.servletListener.ApplicationContextUtil;
import com.xmszit.voip.web.system.exceptions.ClientUserNotLoginException;
import com.xmszit.voip.web.system.exceptions.UserNotLoginException;

/**
 * @ProjectName:voip
 * @ClassName: SzInterceptor
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class SzInterceptor implements HandlerInterceptor{

	/* 
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//System.out.println("after complete");
	}

	/* 
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
//			System.out.println(handlerMethod.getMethod().getParameterTypes());
//			Class<?>[] pts = handlerMethod.getMethod().getParameterTypes();
//			for (Class<?> class1 : pts) {
//				System.out.println("class:" + class1.getName());
//			}
			if (handlerMethod.getMethod().getReturnType()!=null) {
				//System.out.println(handlerMethod.getMethod().getReturnType().getName());
			}else{
				//System.out.println("return null");
			}
			
			if(modelAndView!=null){
				//System.out.println("view not null");
			}else{
				//System.out.println("view is null");
			}
			
		}
	}

	/* 
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String ss = request.getHeader("Cookie");
		String sid = request.getParameter("sid");
		if (!StringUtils.isEmpty(sid)) {
			ClientSession cs = UserSessionService.getSession(sid);
			if (cs!=null) {
				request.getSession().setAttribute("clientUser", cs.getClientUser());
			}
		}
		ClientUser clientUser = (ClientUser)request.getSession().getAttribute("clientUser");
		User user = (User)request.getSession().getAttribute("user");
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		String url = getMappingUrl(handlerMethod);
//		System.out.println("url:"+url+"-----cookies:"+ss);
		if(clientUser==null && user==null){
			if (clientExcludeUrl!=null && clientExcludeUrl.contains(url)
					|| excludeUrl!=null && excludeUrl.contains(url)
					|| EshopUrl !=null && EshopUrl.contains(url)) {
				return true;
			}
			throw new ClientUserNotLoginException();
		}else{
			if (user==null 
					&& url.startsWith("/system/")
					&& !excludeUrl.contains(url)) {
				throw new UserNotLoginException();
			}
			if(clientUser!=null){
				String sessionId = (String) request.getSession().getAttribute("sessionId");
				if (!UserSessionService.sessions.containsKey(sessionId)) {
					clearSession(request);
					throw new ClientUserNotLoginException("您的账号已经在另一处登录了,你被迫下线!");
				}
				/*if(!StringUtils.isEmpty(clientUser.getClientId()) && !url.startsWith("/system/")){
					ClientService clientService = (ClientService) ApplicationContextUtil.getBean("clientService");
					Client client = clientService.getById(clientUser.getClientId());
					if (client.getState()==null || !client.getState()) {
						clearSession(request);
						throw new ClientUserNotLoginException("您的主账号已停用!");
					}
					Date effectiveDate = client.getEffectiveDate();
					Date now = new Date();
					Long d = DateUtil.dateDiff(now, effectiveDate);
					//System.out.println("剩余："+d+"可延长："+(d.intValue()+7));
					if ((d.intValue()+7)<0) {
						clearSession(request);
						throw new ClientUserNotLoginException("您的主账号已过期，请联系管理员进行缴费!");
					}
					request.setAttribute("clientId", clientUser.getClientId());
				}*/
			}else {
				if (!url.startsWith("/system/")) {
					if (clientExcludeUrl!=null && clientExcludeUrl.contains(url)
							|| excludeUrl!=null && excludeUrl.contains(url)
							|| EshopUrl !=null && EshopUrl.contains(url) ) {
						return true;
					}
					throw new ClientUserNotLoginException();
				}
			}
			if (user!=null) {
				
			}
		}
		return true;
//		User user = (User)request.getSession().getAttribute("user");
//		
//		if (user==null) {
//			
//			HandlerMethod handlerMethod = (HandlerMethod)handler;
//			String url = getMappingUrl(handlerMethod);
//			if (excludeUrl!=null && excludeUrl.contains(url)) {
//				return true;
//			}
//			throw new UserNotLoginException();
//		}else{
//			return true;
//		}
	}
	
	private void clearSession(HttpServletRequest request){
		Enumeration<String> names = request.getSession().getAttributeNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			request.getSession().removeAttribute(name);
		}
	}
	
	private String getMappingUrl(HandlerMethod handlerMethod){
		String url = "";
		RequestMapping rm = handlerMethod.getBean().getClass().getAnnotation(RequestMapping.class);
		if(rm!=null){
			String[] value = rm.value();
			if (value!=null && value.length>0) {
				url=value[0];
			}
		}
		rm = handlerMethod.getMethodAnnotation(RequestMapping.class);
		if(rm!=null){
			String[] value = rm.value();
			if(value!=null && value.length>0){
				if (!value[0].startsWith("/")) {
					url+="/";
				}
				url+=value[0];
			}
		}
		
		return url;
	}
	
	private List<String> clientExcludeUrl;

	public void setClientExcludeUrl(List<String> clientExcludeUrl) {
		this.clientExcludeUrl = clientExcludeUrl;
	}

	private List<String> excludeUrl;

	public void setExcludeUrl(List<String> excludeUrl) {
		this.excludeUrl = excludeUrl;
	}
	
	private List<String> EshopUrl;

	public void setEshopUrl(List<String> eshopUrl) {
		this.EshopUrl = eshopUrl;
	}
	
}
