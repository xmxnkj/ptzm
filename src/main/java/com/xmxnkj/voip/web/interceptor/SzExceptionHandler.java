package com.xmxnkj.voip.web.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsit.common.exceptions.ApplicationException;
import com.xmxnkj.voip.system.exceptions.UserNotLoginException;
import com.xmxnkj.voip.web.models.ResultJson;
import com.xmxnkj.voip.web.models.ResultType;

/**
 * @ProjectName:lightning
 * @ClassName: SzExceptionHandler
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class SzExceptionHandler implements HandlerExceptionResolver {

	/* 
	 * 
	 * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		if (exception instanceof UserNotLoginException) {
			
			//若为页面则重定位到登录页
			if (handlerMethod.getMethod().getReturnType()!=null 
					&& "org.springframework.web.servlet.ModelAndView".equals(handlerMethod.getMethod().getReturnType().getName())) {
				return new ModelAndView("uac/user/login", "exception", exception);
			}else{
				//json输入默认错误Json
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", "用户未登录");
					jsonObject.put("resultType", ResultType.NotLogin.toString());
					jsonObject.put("success", false);
					
					response.getWriter().println(jsonObject.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
		
		if (exception instanceof ApplicationException) {
			if (handlerMethod.getMethod().getReturnType()!=null 
					&& "org.springframework.web.servlet.ModelAndView".equals(handlerMethod.getMethod().getReturnType().getName())) {
				//return new ModelAndView("uac/user/login", "exception", exception);
			}else{
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", exception.getMessage());
					jsonObject.put("resultType", ResultType.Exception.toString());
					jsonObject.put("success", false);
					
					response.getWriter().println(jsonObject.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}
			//handlerMethod.
			//ModelAndView view = new ModelAndView();
			//RedirectView redirectView = new RedirectView();
			//view.setView(new RedirectView(handlerMethod.getMethod().get));
		}else{
			if (handlerMethod.getMethod().getReturnType()!=null 
					&& "org.springframework.web.servlet.ModelAndView".equals(handlerMethod.getMethod().getReturnType().getName())) {
				//return new ModelAndView("uac/user/login", "exception", exception);
			}else{
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", "系统意外错误，请重试！");
					jsonObject.put("resultType", ResultType.Exception.toString());
					jsonObject.put("success", false);
					
					response.getWriter().println(jsonObject.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;

			}

		}
		
		
//		try {
//			response.getWriter().println("error:" + exception.getMessage());
//		} catch (IOException e) {
//			// TODO Auto-generated catch blockhand
//			e.printStackTrace();
//		}
		return null;
	}

}
