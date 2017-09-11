package com.xmxnkj.voip.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.uac.entity.Operation;
import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.entity.UserRoleType;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.common.entity.Terminal;
import com.xmxnkj.voip.web.utils.WebContextHolder;

/**
 * @ProjectName:voip
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
	
	public HttpServletRequest getRequest(){
		return WebContextHolder.getRequest();
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
	
	protected String formatNumber(Float value) {
		if (value!=null) {
			return new DecimalFormat("0.##").format(value);
		}
		return "";
	}
	
	public void setLoginOperates(List<Operate> operates){
		getSession().setAttribute("operates", operates);
	}
	
	public List<Operate> getLoginOperates(){
		return (List<Operate>)getSession().getAttribute("operates");
	}
	
	public void setLoginOperations(List<Operation> operations){
		getSession().setAttribute("operations", operations);
	}
	
	public List<Operation> getLoginOperations(){
		return (List<Operation>)getSession().getAttribute("operations");
	}
	
	public boolean userHasOperate(String code){
		if (getLoginClientUser().getIsManager()) {
			return true;
		}
		List<Operate> operates = getLoginOperates();
		for (Operate operate : operates) {
			if (code.equals(operate.getCode())) {
				return true;
			}
		}
		return false;
	}
	
	public List<Operate> getLoginOperates(int grade, String parentId){
		List<Operate> operates = getLoginOperates();
		List<Operate> gradeOperates = new ArrayList<>();
		if (operates!=null) {
			for (Operate operate : operates) {
				if (operate.getGrade()==grade
						&& parentId.equals(operate.getPid())) {
					gradeOperates.add(operate);
				}
			}
		}
		return gradeOperates;
	}
	
	public List<Operate> getLoginOperates(int grade){
		List<Operate> operates = getLoginOperates();
		List<Operate> gradeOperates = new ArrayList<>();
		if (operates!=null) {
			for (Operate operate : operates) {
				if (operate.getGrade()==grade) {
					gradeOperates.add(operate);
				}
			}
		}
		return gradeOperates;
	}
	
	public Client getLoginClient(){
		return (Client)getSession().getAttribute("client");
	}
	
	public void setLoginClient(Client client){
		getSession().setAttribute("client", client);
	}
	
	public ClientUser getLoginClientUser(){
		return (ClientUser)getSession().getAttribute("clientUser");
	}
	
	public void setLoginClientUser(ClientUser clientUser){
		if (clientUser.getPrivateSea()==null) {
			clientUser.setPrivateSea(0);
		}
		getSession().setAttribute("clientUser", clientUser);
	}
	
	public String getLoginClientUserId(){
		ClientUser user = getLoginClientUser();
		return user!=null?user.getId():null;
	}
	public String getLoginClientId(){
		ClientUser user = getLoginClientUser();
		return user!=null?user.getClientId():null;
	}
	/*
	public Shop getLoginShop(){
		return (Shop)getSession().getAttribute("shop");
	}
	
	public void setLoginShop(Shop shop){
		getSession().setAttribute("shop", shop);
	}
	
	public String getLoginShopId(){
		Shop shop = getLoginShop();
		return shop!=null?shop.getId():null;
	}
	
	public String getLoginClientId(){
		ClientUser user = getLoginClientUser();
		return user!=null?user.getClientId():null;
	}*/
	
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
	
	public ModelAndView createModelAndView(String viewName, String modelName, Object model){
		ModelAndView view = createModelAndView(viewName);
		view.addObject(modelName, model);
		
		return view;
	}
	
	public ModelAndView createModelAndView(String viewName){
		ModelAndView view = new ModelAndView(viewName);
		
		view.addObject("user", getLoginUser());
		
		return view;
	}
	
	public boolean isLoginUserSysAdmin(){
		return getLoginUser()!=null && getLoginUser().getUserRoleType()==UserRoleType.SysAdmin;
	}
	
	public boolean isLoginUserBigAreaAdmin() {
		return getLoginUser()!=null && getLoginUser().getUserRoleType()==UserRoleType.Admin;
	}
	
	public boolean isLoginUserAreaAdmin(){
		return getLoginUser()!=null && getLoginUser().getUserRoleType()==UserRoleType.AreaAdmin;
	}
	
	public void setTerminal(Terminal terminal){
		getSession().setAttribute("terminal", terminal);
	}
	
	public Terminal getTerminal(){
		return (Terminal)getSession().getAttribute("terminal");
	}
	
	protected Map<String, String> getRequestParams(){
		HttpServletRequest request = getRequest();
		Map<String, String> params =new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			params.put(name, request.getParameter(name));
		}
		return params;
	}
}
