package com.xmxnkj.voip.web.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.StringUtils;

import com.hsit.common.MD5Util;
import com.hsit.common.http.HttpRequest;
import com.xmxnkj.voip.system.dao.ClientUserDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.timer.TaskTimer;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.utils.CommCodeUtil;
import com.xmxnkj.voip.web.utils.ZxingUtil;
import com.xmxnkj.voip.web.utils.bank.Main;
import com.xmxnkj.voip.web.utils.sms.SMSSendUtil;

@Controller
@RequestMapping("/web/clientUser")
public class ClientUserControllers extends BaseController<ClientUser, ClientUserQuery> {
	
	static Logger logger = LoggerFactory.getLogger("business");

	@Autowired
	private ClientUserService service;
	
	@Autowired
	private ClientUserDao dao;

	@Override
	public ClientUserService getService() {
		return service;
	}
	
	//登陆页面
	@RequestMapping("uacLogin")
	public String uacLogin(){
		return "web/login";
	}
	
	@RequestMapping("outLogin")
	public String outLogin(HttpSession session){
		session.setAttribute("loginUser",null);
		return "web/login";
	}
	//短信
	@RequestMapping("/msgValidate")
	@ResponseBody
	public ListJson msgValidate(String tel,HttpServletRequest request){
		ListJson listJson = new ListJson();
		//判断手机号能否注册
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setPhoneNumber(tel);
		clientUserQuery.setClientType(ClientTypeEnum.Normal);
		ClientUser clientUser = service.getEntity(clientUserQuery);
		if(clientUser!=null){
			listJson.setMessage("该手机号已经注册！！");
			listJson.setSuccess(false);
			return listJson;
		}
		//生成验证码
		String valiCode = (int)((Math.random()*9+1)*100000)+"";
		try {
			SMSSendUtil.getInstance().sendSMS(tel, "【汇智库】您好,欢迎使用汇智库旗下的“期宝通”，您的手机验证码是[" + valiCode + "]，请妥善保管好账号密码，若非本人操作，请忽略！");
			
			request.getSession().setAttribute(tel, valiCode);
			
			listJson.setMessage("验证码已发送！！");
			listJson.setSuccess(true);
			return listJson;
		} catch (Exception e) {
			listJson = new ListJson(e);
			listJson.setSuccess(false);
			return listJson;
		}
	}
	
	//提交注册信息
	@RequestMapping("/resigstrClientUser")
	@ResponseBody
	public Object validateCodeIsTrue(ClientUser clientUser,HttpSession session,HttpServletRequest request){
		ListJson listJson = new ListJson();
		try {

			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setPhoneNumber(clientUser.getPhoneNumber());
			clientUserQuery.setClientType(ClientTypeEnum.Normal);
			ClientUser cu = service.getEntity(clientUserQuery);
			if(cu!=null){
				listJson.setMessage("该手机号已经注册！！");
				listJson.setSuccess(false);
				return listJson;
			}
			
			//判断验证码有效性
			String code = (String) session.getAttribute(clientUser.getPhoneNumber());
			if(code!=null && !code.equals("") && code.equals(clientUser.getCheckCode())){
				listJson.setSuccess(true);
			}else{
				listJson.setSuccess(false);
				listJson.setMessage("该验证码无效！！");
				return listJson;
			}
			
			//上级id如果为空使用这个
			if(clientUser.getLeaderUserID()==null || clientUser.getLeaderUserID().equals("")){
				//判断推荐码的有效性
				clientUserQuery = new ClientUserQuery();
				clientUserQuery.setCommendCode(clientUser.getCommendCode());
				cu = service.getEntity(clientUserQuery);
				if(cu==null || clientUser.getCommendCode()==null || clientUser.getCommendCode().equals("")){
					listJson.setMessage("该推荐码无效！！");
					listJson.setSuccess(false);
					return listJson;
				}
				clientUser.setLeaderUserID(cu.getId());
			}else{
				//判断上级
				cu = service.getById(clientUser.getLeaderUserID());
				if(cu==null || clientUser.getCommendCode()==null || clientUser.getCommendCode().equals("")){
					listJson.setMessage("该上级id失效!");
					listJson.setSuccess(false);
					return listJson;
				}
			}
			
			//生成推荐码
			clientUser.setCommendCode(CommCodeUtil.createCommCode());
			//生成二维码
			//上传路径
			String path = request.getSession().getServletContext().getRealPath("")+"\\upload\\qr\\";
			String fileName = "";
			if(cu.getQrCode()==null|| cu.getQrCode().equals("")){
				fileName = UUID.randomUUID()+".png";
			}else{
				fileName = cu.getQrCode();
			}
			String qrCode = ZxingUtil.createZxing(request,path,clientUser,fileName);
			//保存二维码文件名
			clientUser.setQrCode(qrCode);
			clientUser.setPwd(MD5Util.MD5(clientUser.getPwd()).toUpperCase());
			//设置用户类型
			clientUser.setClientType(ClientTypeEnum.Normal);
			clientUser.setRegTime(new Date());
			service.save(clientUser);
			return listJson;
		} catch (Exception e) {
			listJson = new ListJson(e);
			listJson.setSuccess(false);
			return listJson;
		}
	}

	//注册页面
	@RequestMapping("resigstr")
	public String resigst(String commendCode,String leaderId,HttpServletRequest request){
		if(commendCode!=null && !commendCode.equals("business")){
			request.setAttribute("commendCode", commendCode);
		}
		if(commendCode!=null){
			request.setAttribute("leaderId", leaderId);
		}
		return "web/register";
	}
	
	//主页面(登陆)
	@RequestMapping("main")
	@ResponseBody
	public ListJson main(ClientUser clientUser,HttpSession session,HttpServletRequest request){
		ListJson listJson = new ListJson();
		StringBuffer buffer = new StringBuffer();
		buffer.append("FROM ClientUser where phoneNumber=:phoneNumber");
		buffer.append(" and pwd = :pwd");
		buffer.append(" and clientType in (:clientType1,:clientType2)");//会员单位也可以登录
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pwd", clientUser.getPwd().toUpperCase());
		map.put("phoneNumber", clientUser.getPhoneNumber());
		map.put("clientType1", ClientTypeEnum.MemberUnit);
		map.put("clientType2", ClientTypeEnum.Normal);
		ClientUser cu = dao.findHqlUniqueResult(buffer.toString(), map);
		//判断密码
		if(cu==null){
			listJson.setMessage("账号密码错误！");
			listJson.setSuccess(false);
			return listJson;
		}
		String ip = request.getRemoteAddr();
		
		//滞空
		listJson.setMessage("");
		
		Map<String,Object> singleMap = new HashMap<String,Object>();
		
		//判断是否已经登陆
		//已存在登陆的号码
		if(TaskTimer.LoginMap.containsKey(cu.getPhoneNumber())){
			//同台IP登陆
			if(TaskTimer.LoginMap.get(cu.getPhoneNumber()).get("ip").equals(request.getRemoteAddr())){
				//不做处理
			}else{
				try{
					//如果其登入状态有效
					if(((HttpSession) TaskTimer.LoginMap.get(cu.getPhoneNumber()).get("session")).getAttribute("loginUser")!=null){
						listJson.setMessage("当前账号在登陆,已强制其下线");
					}
					//解除其登录状态
					 ((HttpSession)(TaskTimer.LoginMap.get(cu.getPhoneNumber()).get("session"))).removeAttribute("loginUser");
					//不同机子登陆
					
				}catch(Exception e){
					System.out.println("被注销");
				}
				singleMap.put("session", session);	//保存session
				singleMap.put("ip", ip);
				TaskTimer.LoginMap.put(cu.getPhoneNumber(),singleMap);
			}
		}else{
			//直接写入
			singleMap.put("session", session);	//保存session
			singleMap.put("ip", ip);
			TaskTimer.LoginMap.put(cu.getPhoneNumber(), singleMap);
		}
		
		session.setAttribute("loginUser",cu);
		//期货页面访问次数
		session.setAttribute("requestCount",0);
		listJson.setSuccess(true);
		return listJson;
	}
	
	//跳转主页面
	@RequestMapping("index")
	public String main(){
		return "web/index";
	}
	
	//忘记密码页面
	@RequestMapping("forgetPwd")
	public String forgetPwd(){
		return "web/forgetPwd";
	}
	
	//短信验证
	@RequestMapping("/forgetValide")
	@ResponseBody
	public ListJson forgetValide(String tel,HttpServletRequest request){
		ListJson LJ = new ListJson();
		try {
			String valiCode = (int)((Math.random()*9+1)*100000)+"";
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setPhoneNumber(tel);
			ClientUser cu = service.getEntity(clientUserQuery);
			if(cu==null){
				LJ.setMessage("该账号不存在");
				LJ.setSuccess(false);
				return LJ;
			}
			SMSSendUtil.getInstance().sendSMS(tel, "【汇智库】您好,欢迎使用汇智库旗下的“期宝通”，您的手机验证码是[" + valiCode + "]，请妥善保管好账号密码，若非本人操作，请忽略！");
			request.getSession().setAttribute(tel, valiCode);
			LJ.setMessage("验证码已发送");
			LJ.setSuccess(true);
			return LJ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LJ;
	}
	
	//提交修改
	@RequestMapping("/editPwd")
	@ResponseBody
	public ListJson editPwd(String tel,String pwd,String checkCode,HttpServletRequest request){
		
		ListJson LJ = new ListJson();
		
		if(!request.getSession().getAttribute(tel).equals(checkCode)){
			LJ.setMessage("验证码有误！");
			LJ.setSuccess(false);
			return LJ;
		}
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setPhoneNumber(tel);
		ClientUser clientUser = service.getEntity(clientUserQuery);
		clientUser.setPwd(MD5Util.MD5(pwd).toUpperCase());
		service.save(clientUser);
		LJ.setSuccess(true);
		return LJ;
	}
	
	@RequestMapping("myPage")
	public String myPage(HttpSession session,HttpServletRequest request){
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		request.setAttribute("myInfo", service.getById(cu.getId()));
		return "web/user";
	}
	
	@RequestMapping("myUserDetails")
	public String myUserDetails(HttpSession session,HttpServletRequest request){
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		request.setAttribute("myInfo", service.getById(cu.getId()));
		return "web/userDetails";
	}
	
	@RequestMapping("bindBankCard")
	public String bindBankCard(HttpServletRequest request, HttpSession session){
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		
		ClientUser clientUser = service.getById(cu.getId());
		request.setAttribute("user", clientUser);
		
		return "web/AddBankCard";
	}
	
	//校验银行卡是否有效
	@RequestMapping("validateBankCard")
	@ResponseBody
	public ListJson validateBankCard(String bankCardNo){
		ListJson LJ = new ListJson();
		bankCardNo = bankCardNo.replaceAll(" ", "");
		LJ.setSuccess(Main.checkBankCard(bankCardNo));
		return LJ;
	}
	
	//绑定银行卡
	@RequestMapping("bindBankCardData")
	@ResponseBody
	public ListJson bindBankCardData(ClientUser clientUser,HttpSession session){
		ListJson LJ = new ListJson();
		if(!Main.checkBankCard(clientUser.getBankCardCode().replaceAll(" ", ""))){
			LJ.setMessage("银行卡号无效");
			LJ.setSuccess(false);
			return LJ;
		}
		
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			LJ.setMessage("login");
			LJ.setSuccess(false);
			return LJ;
		}
		ClientUser CU = service.getById(cu.getId());
		CU.setBank(clientUser.getBank());
		CU.setBankAddress(clientUser.getBankAddress());
		CU.setBankCardCode(clientUser.getBankCardCode());
		CU.setCert(clientUser.getCert());
		CU.setName(clientUser.getName());
		CU.setBankCardType(clientUser.getBankCardType());
		service.save(CU);
		
		LJ.setSuccess(true);
		return LJ;
	}
	
	//我的二维码
	@RequestMapping("/myQr")
	public String myQr(HttpSession session,HttpServletRequest request){
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		request.setAttribute("Qr", service.getById(cu.getId()));
		return "web/myQr";
	}
	
	//修改密码页面
	@RequestMapping("changePwdJsp")
	public String changePwdJsp(HttpSession session){
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		return "web/changePwd";
	}
	
	//修改密码
	@RequestMapping("changePwd")
	@ResponseBody
	public Object changePwd(String pwd0,String pwd1,HttpSession session){
		ListJson LJ = new ListJson();
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			LJ.setSuccess(false);
			LJ.setMessage("请登录");
			return LJ;
		}
		
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setPwd(MD5Util.MD5(pwd0).toUpperCase());
		clientUserQuery.setId(cu.getId());
		ClientUser clientUser = service.getEntity(clientUserQuery);
		if(clientUser==null){
			LJ.setSuccess(false);
			LJ.setMessage("密码错误");
			return LJ;
		}
		
		clientUser.setPwd(MD5Util.MD5(pwd1).toUpperCase());
		service.save(clientUser);

		LJ.setSuccess(true);
		LJ.setMessage("密码修改完成");
		return LJ;
	}
	
	//编辑个人信息页面
	@RequestMapping("editPerson")
	public String editPerson(HttpServletRequest request,HttpSession session){
		
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			return "web/login";
		}
		
		ClientUser clientUser = service.getById(cu.getId());
		request.setAttribute("user", clientUser);
		return "web/editPerson";
	}
	
	//编辑个人信息
	@RequestMapping("editPersonMsg")
	@ResponseBody
	public ListJson editPersonMsg(String cert,String name,HttpSession session){
		
		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) session.getAttribute("loginUser");
		if(cu==null){
			LJ.setSuccess(false);
			LJ.setMessage("请登录");
			return LJ;
		}
		
		ClientUser clientUser = service.getById(cu.getId());
		
		clientUser.setCert(cert);
		clientUser.setName(name);
		
		service.save(clientUser);
		
		LJ.setSuccess(true);
		return LJ;
	}
}