package com.xmxnkj.voip.system.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.MD5Util;
import com.hsit.common.kfbase.entity.Paging;
import com.mysql.jdbc.StringUtils;
import com.xmxnkj.voip.system.dao.ClientUserDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.utils.ZxingUtil;

/**
 * @ProjectName:voip
 * @ClassName: ClientController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller("ClientUserController")
@RequestMapping("/back/clientUser")
public class ClientUserController extends BaseController<ClientUser, ClientUserQuery> {
	
	//登录session
	
	@Autowired
	private ClientUserService service;
	
	@Autowired
	private ClientUserDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	static Logger logger = LoggerFactory.getLogger("business");


	@Override
	public ClientUserService getService() {
		return service;
	}
	
	//后台登入
	@RequestMapping(value="/login")
	@ResponseBody
	public ListJson login(String account, String passwd, HttpServletRequest request){
		try{
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setAcount(account);
			clientUserQuery.setPwd(MD5Util.MD5((passwd)).toUpperCase());
			ClientUser cu = service.getEntity(clientUserQuery);
			
			if(cu==null){
				return new ListJson("账号或密码错误！");
			}
	
			if(cu.getClientType()!=ClientTypeEnum.Normal){
				//session
				request.getSession().setAttribute("manager", cu);
				//return "main";
				return new ListJson("OK");
			}else{
				return new ListJson("您无权限登入！");
			}

		}catch(Exception E){
			
		}
		return new ListJson();
	}
	
	
	
	//主页面
	@RequestMapping(value="/main")
	public String main(HttpSession session){
		try{
			if(session.getAttribute("manager")==null){
				return "uac/user/login";
			}
			return "main";
		}catch(Exception E){
		
		}
		return "";
	}
	
	//修改密码页面
	@RequestMapping(value="/modufyPwd")
	public String modufyPwd(HttpSession session){
		if(session.getAttribute("manager")==null){
			return "uac/user/login";
		}
			
		return "changepwd";
	}
	
	//修改密码页面
	@RequestMapping(value="/modufy")
	@ResponseBody
	public ListJson modufy(String old,String new1,HttpSession session){
		
		ListJson LJ = new ListJson();
		
		try{
			
			ClientUser cu = (ClientUser) session.getAttribute("manager");
			
			if(cu!=null){
				
				cu = service.getById(cu.getId());
				
				if(cu.getPwd().equals(MD5Util.MD5(old))){
					
					cu.setPwd(MD5Util.MD5(new1));
					
					LJ.setSuccess(true);
					
					LJ.setMessage("修改成功");
					
					service.save(cu);
					
					return LJ;
				
				}else{
					
					LJ.setSuccess(false);
					
					LJ.setMessage("密码不正确");
					
					return LJ;
				}
			}
			return LJ;
		}catch(Exception E){
			
		}
		
		return LJ;
	}
	
	
	//退出登入
	@RequestMapping("loginOut")
	public String loginOut(HttpSession session){
		session.setAttribute("manager",null);
		return "uac/user/login";
	}
	
	@RequestMapping(value="/showUserList")
	public ModelAndView showLogin(){
		return new ModelAndView("client/list");
	}
	
	//客户层级树
	@RequestMapping("getList")
	public ListJson getList(Integer rows, Integer page){
		try{
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setPage(1, rows);
			List<ClientUser> entities = service.getEntities(clientUserQuery);
			
			return new ListJson(entities);
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	//客户层级树
	@RequestMapping("sonList")
	@ResponseBody
	public ListJson sonList(ClientUserQuery userQuery,
							Integer rows,
							Integer page,
							String sort,
							HttpSession session){
		ListJson LJ = new ListJson();
		try{
			ClientUser cu = (ClientUser) session.getAttribute("manager");
			//查询 下级(包括自己)
			List<String> queryId = new ArrayList<String>();
			queryId.add(cu.getId());
			
			List<String> qlist = new ArrayList<String>();
			qlist.add(cu.getId());
			List<Map<String, Object>> list = service.getSonNormal(qlist, "?", userQuery.getClientType());
			
			String accounts = "";
			List<String> listQ = new ArrayList<String>();
			
			for(Map<String, Object> map:list){
				
				if(!userQuery.getPhoneNumber().equals("")){
					
					if(map.get("phoneNumber").toString().contains(userQuery.getPhoneNumber())){
						listQ.add(map.get("ID").toString());
						accounts+="?,";
					}
					
				}else{
					listQ.add(map.get("ID").toString());
					accounts+="?,";
				}
			}
			
			//无结果
			if(listQ.size()==0){
				LJ.setRows(new ArrayList());
				return LJ;
			}
			
			Object[] obj = new Object[listQ.size()];
			for(int i=0;i<listQ.size();i++){
				obj[i] = listQ.get(i);
			}
			
			List<Map<String, Object>> resultList = jdbcTemplate.queryForList("select c1.name,c1.phoneNumber,c1.agentName,c1.clientType,c1.acount,c1.enableMoney,c1.commendCode,c1.enableMoney,c1.bailCash,c1.regTime,c2.name as leaderName,c2.phoneNumber as leaderPhoneNumber,c2.name as leadName,c2.agentName as leadAgentName,c2.clientType as leadClientType from clientuser c1,clientuser c2 where c1.leaderUserID=c2.ID and c1.ID in ("+(accounts.substring(0, accounts.length()-1))+") order by c1.regTime "+sort,obj);
			//可用余额的统计总额
			LJ.setRows(resultList);
			return LJ;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	@RequestMapping("fatherList")
	@ResponseBody
	public ListJson fatherList(String clientType){
		try{
			StringBuffer buffer = new StringBuffer();
			buffer.append("FROM ClientUser where 1=1");
			Map<String,Object> map = new HashMap<String,Object>();
				switch(clientType){
					case "OperationCenter":
						buffer.append(" and clientType = :clientType ");
						map.put("clientType", ClientTypeEnum.Bourse);
						break;
					case "MemberUnit":
						buffer.append(" and clientType = :clientType ");
						map.put("clientType", ClientTypeEnum.OperationCenter);
						break;
					case "AGENT":
						buffer.append(" and clientType = :clientType ");
						map.put("clientType", ClientTypeEnum.MemberUnit);
						break;
					case "Normal":
						buffer.append(" and clientType in (:clientType1,:clientType2)");
						map.put("clientType1", ClientTypeEnum.AGENT);
						map.put("clientType2", ClientTypeEnum.Normal);
						break;
				}
			List<ClientUser> entities = dao.findHql(buffer.toString(), map);
			return new ListJson(entities);
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}

	//客户编辑
	@RequestMapping("editJsp")
	public String editJsp(HttpServletRequest request,String id,String type){
		return "client/edit";
	}
	
	//客户编辑
	@RequestMapping("addJsp")
	public String addJsp(HttpServletRequest request,String id,String type){
		return "client/add";
	}
	
	@RequestMapping(value="getTree")
	@ResponseBody
	public ListJson getTree(HttpSession session){
		ClientUser cu = (ClientUser) session.getAttribute("manager");
		try{
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setId(cu.getId());
			ClientUser entitiy = service.getEntity(clientUserQuery);
			Map<String,Object> map = null;
			
			map = new TreeMap<String,Object>();
			map.put("id", entitiy.getId());
			map.put("text", entitiy.getClientType()==ClientTypeEnum.Normal?entitiy.getPhoneNumber():entitiy.getName());
			map.put("type", entitiy.getClientType());
			map.put("son", setTreeMap(entitiy));
			
			return new ListJson(map);
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	//迭代
	public List<Map<String,Object>> setTreeMap(ClientUser cu){
		Map<String,Object> map = null;
		//查询cu的下一级
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setLeaderUserID(cu.getId());
		List<ClientUser> entities = service.getEntities(clientUserQuery);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(ClientUser clientUser:entities){
			map = new TreeMap<String,Object>();
			map.put("id", clientUser.getId());
			if(clientUser.getClientType()==ClientTypeEnum.Normal){
				map.put("text",clientUser.getPhoneNumber());
			}else if(clientUser.getClientType()==ClientTypeEnum.MemberUnit){
				map.put("text",StringUtils.isNullOrEmpty(clientUser.getAgentName())?"":clientUser.getAgentName());
			}else{
				map.put("text",clientUser.getName());
			}
			
			map.put("type", clientUser.getClientType());
			map.put("son", this.setTreeMap(clientUser));
			list.add(map);
		}
		return list;
	}
	
	@RequestMapping(value="getClientUser")
	@ResponseBody
	public ListJson getClientUser(String id){
		try{
			ClientUser clientUser = service.getById(id);
			//查出上级
			clientUser.setLeaderUser(service.getById(clientUser.getLeaderUserID()));
			
			//他的上级列表
			StringBuffer buffer = new StringBuffer();
			buffer.append("FROM ClientUser where 1=1");
			Map<String,Object> map = new HashMap<String,Object>();
				switch(clientUser.getClientType().toString()){
					case "MemberUnit":
						buffer.append(" and clientType = :clientType ");
						map.put("clientType", ClientTypeEnum.OperationCenter);
						break;
					case "AGENT":
						buffer.append(" and clientType = :clientType ");
						map.put("clientType", ClientTypeEnum.MemberUnit);
						break;
					case "Normal":
						buffer.append(" and clientType in (:clientType1,:clientType2)");
						map.put("clientType1", ClientTypeEnum.AGENT);
						map.put("clientType2", ClientTypeEnum.Normal);
						break;
				}
			List<ClientUser> entities = dao.findHql(buffer.toString(), map);
			if(clientUser!=null){
				map.put("obj", clientUser);
				map.put("leader", entities);
			}
			return new ListJson(map);
		}catch(Exception e){
			return new ListJson(e);
		}
	}
	
	@RequestMapping(value="saveData",method=RequestMethod.POST)
	@ResponseBody
	public ListJson saveData(ClientUser clientUser,HttpServletRequest request){
		
		ListJson LJ = new ListJson();
		//要修改的数据
		if(clientUser.getId()!=null && !clientUser.getId().equals("")){
			//update
			//修改的本体
			ClientUser cu = service.getById(clientUser.getId());
			
			logger.info("修改后********用户手机："+cu.getPhoneNumber()+":余额："+cu.getEnableMoney());

			//修改号码
			ClientUserQuery clientUserQuery = null;
			
			//会员单位    普通会员要校验手机
			if(cu.getClientType()==ClientTypeEnum.Normal || 
					cu.getClientType()==ClientTypeEnum.MemberUnit){
				if(!clientUser.getPhoneNumber().trim().equals(cu.getPhoneNumber().trim())){
					clientUserQuery = new ClientUserQuery();
					clientUserQuery.setPhoneNumber(clientUser.getPhoneNumber());
					ClientUser c = service.getEntity(clientUserQuery);
					if(c!=null){
						LJ.setMessage("该手机号已存在！");
						LJ.setSuccess(false);
						return LJ;
					}
				}
			}
			
			if(cu.getClientType()==ClientTypeEnum.OperationCenter || 
					cu.getClientType()==ClientTypeEnum.AGENT ||
							cu.getClientType()==ClientTypeEnum.MemberUnit){
				
				if(!clientUser.getAcount().trim().equals(cu.getAcount().trim())){
					if(clientUser.getAcount()!=null && !clientUser.getAcount().equals("")){
						clientUserQuery = new ClientUserQuery();
						clientUserQuery.setAcount(clientUser.getAcount());
						ClientUser c = service.getEntity(clientUserQuery);
						if(c!=null){
							LJ.setMessage("该账号已存在！");
							LJ.setSuccess(false);
							return LJ;
						}
					}
				}
			}
			cu.setPhoneNumber(clientUser.getPhoneNumber());
			cu.setName(clientUser.getName());
			cu.setAcount(clientUser.getAcount());
			cu.setAgentName(clientUser.getAgentName());
			cu.setLeaderUserID(clientUser.getLeaderUserID());
			if(!clientUser.getPwd().equals(cu.getPwd())){
				cu.setPwd(MD5Util.MD5(clientUser.getPwd()));
			}
			logger.info("修改后********用户手机："+cu.getPhoneNumber()+":余额："+cu.getEnableMoney());
			service.save(cu);
			/*String path = request.getSession().getServletContext().getRealPath("")+"\\upload\\qr\\";
			String fileName = "";
			if(cu.getQrCode()==null || cu.getQrCode().equals("")){
				fileName = UUID.randomUUID()+".png";
			}else{
				fileName = cu.getQrCode();
			}
			clientUser.setCommendCode(cu.getCommendCode());//获取上级的推荐码
			//生成二维码
			String qrCode = ZxingUtil.createZxing(request, path,clientUser,fileName);
			clientUser.setQrCode(qrCode);
			if(!clientUser.getPwd().equals(cu.getPwd())){
				clientUser.setPwd(MD5Util.MD5(clientUser.getPwd()));
			}
			clientUser.setAgentName(clientUser.getAgentName());
			service.save(clientUser);*/
		}else{
			//insert
			
			//校验账号  手机号 是否重复
			
			ClientUserQuery clientUserQuery = null;
			ClientUser cu = null;
			//运营中心 代理商 会员中心
			if(clientUser.getClientType()==ClientTypeEnum.OperationCenter || 
					clientUser.getClientType()==ClientTypeEnum.AGENT ||
						clientUser.getClientType()==ClientTypeEnum.MemberUnit){
				if(clientUser.getAcount()!=null && !clientUser.getAcount().equals("")){
					clientUserQuery = new ClientUserQuery();
					clientUserQuery.setAcount(clientUser.getAcount());
					cu = service.getEntity(clientUserQuery);
					if(cu!=null){
						LJ.setMessage("该账号已存在！");
						LJ.setSuccess(false);
						return LJ;
					}
				}
			}
			
			if(clientUser.getClientType()==ClientTypeEnum.Normal || 
					clientUser.getClientType()==ClientTypeEnum.MemberUnit){
				if(clientUser.getPhoneNumber()!=null && !clientUser.getPhoneNumber().equals("")){
					clientUserQuery = new ClientUserQuery();
					clientUserQuery.setPhoneNumber(clientUser.getPhoneNumber());
					cu = service.getEntity(clientUserQuery);
					if(cu!=null){
						LJ.setMessage("该账号已存在！");
						LJ.setSuccess(false);
						return LJ;
					}
				}
			}
			
			String path = request.getSession().getServletContext().getRealPath("")+"\\upload\\qr\\";
			String fileName = UUID.randomUUID()+".png";//uuid
			//生成二维码
			String qrCode = ZxingUtil.createZxing(request, path,clientUser,fileName);
			clientUser.setAcount(clientUser.getAcount().trim());//账号
			clientUser.setPhoneNumber(clientUser.getPhoneNumber().trim());//手机
			clientUser.setQrCode(qrCode);//二维码路径
			clientUser.setPwd(MD5Util.MD5(clientUser.getPwd()));//密码
			clientUser.setAgentName(clientUser.getAgentName());
			clientUser.setRegTime(new Date());
			service.save(clientUser);
		}
		return new ListJson(true);
	}
	
	
	//保证金页面
	@RequestMapping(value="/bailCashJsp")
	public String bailCashJsp(){
		return "client/bailCash";
	}
	
	//保证金页面
	@RequestMapping(value="/getbailCashAccount")
	@ResponseBody
	public ListJson getbailCashAccount(String phoneNumber,
								   HttpSession session,
								   Integer page,
								   Integer pageSize){
		ListJson LJ = new ListJson();
		Paging paging = new Paging();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		
		ClientUser cu = (ClientUser) session.getAttribute("manager");
		
		List<String> qlist = new ArrayList<String>();
		qlist.add(cu.getId());
		
		StringBuffer sql = new StringBuffer(" from clientuser where clientType='"+ClientTypeEnum.MemberUnit.ordinal()+"' ");
		
		List<Map<String, Object>> list = service.getSonNormal(qlist, "?", ClientTypeEnum.MemberUnit);
		
		Map<String,Object> mapMyself = new HashMap<String,Object>();
		if(cu.getClientType()==ClientTypeEnum.MemberUnit){
			mapMyself.put("ID", cu.getId());
			mapMyself.put("phoneNumber", cu.getPhoneNumber());
			list.add(mapMyself);
		}
		
		String accounts = "";
		List<String> listQ = new ArrayList<String>();
		
		for(Map<String,Object> map:list){
			if(!phoneNumber.equals("")){
				if(map.get("phoneNumber").toString().trim().equals(phoneNumber)){
					listQ.add((String) map.get("phoneNumber"));
					accounts+="?,";
				}
			}else{
				listQ.add((String) map.get("phoneNumber"));
				accounts+="?,";
			}
		}
		
		if(!accounts.equals("")){
			sql.append(" and phoneNumber in ("+(accounts.substring(0,accounts.length()-1))+") ");
		}else{
			paging.setRecordCount(0);
			paging.setPageCount(0);
			LJ.setPaging(paging);
			LJ.setRows(new ArrayList());
			return LJ;
		}
		
		Object[] obj = new Object[listQ.size()];
		for(int i = 0; i<listQ.size();i++){
			obj[i] = listQ.get(i);
		}
		
		//总数据量
		int total = jdbcTemplate.queryForInt("select count(*) "+sql, obj);
		List<Map<String,Object>> listMap = jdbcTemplate.queryForList("select * "+sql, obj);
		paging.setRecordCount(total);
		paging.setPageCount((int)Math.ceil((float)paging.getRecordCount()/paging.getPageSize()));
		LJ.setPaging(paging);
		LJ.setRows(listMap);
		return LJ;
	}
	
	//保证金额度页面
	@RequestMapping("changeBailCashJsp/{id}")
	public String changeBailCashJsp(@PathVariable String id,HttpServletRequest request){
		
		request.setAttribute("user", service.getById(id));
		return "client/changeBailCash";
	}
	
	//保证金额度页面
	@RequestMapping("changeBailCash")
	@ResponseBody
	public ListJson changeBailCash(String id,double bailCash,HttpServletRequest request){
		
		ListJson LJ = new ListJson();
		
		ClientUser cu = (ClientUser) request.getSession().getAttribute("manager");
		
		if(cu==null || cu.getClientType()!=ClientTypeEnum.Bourse){
			LJ.setMessage("您无权限编辑");
			LJ.setSuccess(false);
			return LJ;
		}

		ClientUser c = service.getById(id);
		//额度
		c.setBailCash(new BigDecimal(bailCash));
		//可用额度 = 额度 - 占用保证金
		c.setEnableBailCash(c.getBailCash().subtract(c.getSeatBailCash()));
		
		service.save(c);
		
		LJ.setSuccess(true);
		
		return LJ;
	}
}