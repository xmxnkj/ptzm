package com.xmszit.voip.web.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSONObject;
import com.hsit.common.exceptions.ApplicationException;
import com.xmszit.voip.web.models.ResultType;
import com.xmszit.voip.web.system.exceptions.ClientUserNotLoginException;
import com.xmszit.voip.web.system.exceptions.UserNotLoginException;

import oracle.net.aso.e;

/**
 * @ProjectName:voip
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
		if (exception instanceof ClientUserNotLoginException) {
			//若为页面则重定位到登录页
			if (handlerMethod.getMethod().getReturnType()!=null 
					&& "org.springframework.web.servlet.ModelAndView".equals(handlerMethod.getMethod().getReturnType().getName())) {
				RedirectView redirectView = new RedirectView(request.getContextPath() + "/client/clientUser/login");
				ModelAndView view = new ModelAndView();
				view.setView(redirectView);
				return view;
//				return new ModelAndView("/client/clientUser/login", "exception", exception);
			}else{
				//json输入默认错误Json
				ModelAndView view = new ModelAndView();
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", exception.getMessage());
					jsonObject.put("resultType", ResultType.NotLogin.toString());
					jsonObject.put("success", false);
					
					response.setStatus(HttpStatus.OK.value());
					response.getWriter().println(jsonObject.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return view;
			}
		}
		if (exception instanceof UserNotLoginException) {
			
			//若为页面则重定位到登录页
			if (handlerMethod.getMethod().getReturnType()!=null 
					&& "org.springframework.web.servlet.ModelAndView".equals(handlerMethod.getMethod().getReturnType().getName())) {
				return new ModelAndView("/system/user/login", "exception", exception);
			}else{
				//json输入默认错误Json
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", "用户未登录");
					jsonObject.put("resultType", ResultType.NotLogin.toString());
					jsonObject.put("success", false);
					
					response.setStatus(HttpStatus.OK.value());
					response.setHeader("content-type", "text/json;charset=UTF-8");
					response.getWriter().println(jsonObject.toString());
					response.getWriter().flush();
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
				ModelAndView view = new ModelAndView();
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", exception.getMessage());
					jsonObject.put("resultType", ResultType.Exception.toString());
					jsonObject.put("success", false);
					
					response.setStatus(HttpStatus.OK.value());
					response.setHeader("content-type", "text/json;charset=UTF-8");
					response.getWriter().println(jsonObject.toString());
					response.getWriter().flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return view;

			}
			//handlerMethod.
			//ModelAndView view = new ModelAndView();
			//RedirectView redirectView = new RedirectView();
			//view.setView(new RedirectView(handlerMethod.getMethod().get));
		}else{
			exception.printStackTrace();
			if (handlerMethod.getMethod().getReturnType()!=null 
					&& "org.springframework.web.servlet.ModelAndView".equals(handlerMethod.getMethod().getReturnType().getName())) {
				//return new ModelAndView("uac/user/login", "exception", exception);
			}else{
				ModelAndView view = new ModelAndView();
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("message", "系统意外错误，请重试！");
					jsonObject.put("resultType", ResultType.Exception.toString());
					jsonObject.put("success", false);
					
					response.setStatus(HttpStatus.OK.value());
					response.setHeader("content-type", "text/json;charset=UTF-8");
					response.getWriter().println(jsonObject.toString());
					//response.getWriter().println(jsonObject.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return view;

			}

		}
		
		ModelAndView view = new ModelAndView();
//		try {
//			response.getWriter().println("error:" + exception.getMessage());
//		} catch (IOException e) {
//			// TODO Auto-generated catch blockhand
//			e.printStackTrace();
//		}
		return view;
	}

}
