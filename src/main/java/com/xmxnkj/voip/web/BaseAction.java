package com.xmxnkj.voip.web;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.web.utils.WebContextHolder;

/**
 * @ProjectName:lightning
 * @ClassName: BaseAction
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class BaseAction {
	public HttpSession getSession(){
		return WebContextHolder.getSession();
	}
	
	protected String formatDate(Date date) {
		if(date!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.format(date);
		}
		return "";
	}
	
	protected String formatDateTime(Date date) {
		if(date!=null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(date);
		}
		return "";
	}
	
	protected Date formatDate(String date) {  
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    try {
			return sd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String formatNumber(Float value) {
		if (value!=null) {
			return new DecimalFormat("0.##").format(value);
		}
		return "";
	}
	
	public User getLoginUser(){
		return (User)getSession().getAttribute("user");
	}
	
	public String getLoginUserId(){
		User user = getLoginUser();
		return user!=null?user.getId():null;
	}
	
	public void setLoginUser(User user){
		getSession().setAttribute("user", user);
	}
}
