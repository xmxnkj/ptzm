package com.xmxnkj.voip.web.interceptor;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.service.UserService;
import com.xmxnkj.voip.system.exceptions.UserNotLoginException;

/**
 * @ProjectName:lightning
 * @ClassName: SzInterceptor
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class SzInterceptor implements HandlerInterceptor{

	@Autowired
	private UserService useService;
	
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
		
		User user = (User)request.getSession().getAttribute("user");
		
		if (user==null) {
			
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			String url = getMappingUrl(handlerMethod);
			if (excludeUrl!=null && excludeUrl.contains(url)) {
				return true;
			}
			if(uncludeUrl!=null && !uncludeUrl.contains(url)){
				return true;
			}
			throw new UserNotLoginException();
		}else{
			return true;
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
				url+=value[0];
			}
		}
		
		return url;
	}

	private List<String> excludeUrl;
	
	private List<String> uncludeUrl;
	
	public List<String> getUncludeUrl() {
		return uncludeUrl;
	}

	public void setUncludeUrl(List<String> uncludeUrl) {
		this.uncludeUrl = uncludeUrl;
	}

	public void setExcludeUrl(List<String> excludeUrl) {
		this.excludeUrl = excludeUrl;
	}
}
