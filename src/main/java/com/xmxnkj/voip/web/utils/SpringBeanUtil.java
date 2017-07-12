package com.xmszit.futures.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringBeanUtil implements ApplicationContextAware{
	private static ApplicationContext appContext;
	public void setApplicationContext(ApplicationContext applicationContext)
	throws BeansException {
		appContext = applicationContext;
	}
	public static Object getBean(String beanId)
	{
		//String[] beans = appContext.getBeanDefinitionNames();
		return appContext.getBean(beanId);
	}
	public static ApplicationContext getAppContext() {
		return appContext;
	}
	public static void setAppContext(ApplicationContext appContext) {
		SpringBeanUtil.appContext = appContext;
	}
}
