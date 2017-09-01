package com.xmszit.futures.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.futures.system.dao.FutureDao;
import com.xmszit.futures.system.entity.ClientUser;
import com.xmszit.futures.system.entity.Future;
import com.xmszit.futures.system.entity.emun.FutureTypeEnum;
import com.xmszit.futures.system.entity.query.FutureQuery;
import com.xmszit.futures.system.service.ClientUserService;
import com.xmszit.futures.system.service.FutureService;
import com.xmszit.futures.web.BaseController;
import com.xmszit.futures.web.models.ListJson;
import com.xmszit.futures.web.utils.SinaJsonUtil;
import com.xmszit.futures.web.utils.TimeUtil;

@Controller("FutureController")
@RequestMapping("/web/futures")
public class FutureController extends BaseController<Future, FutureQuery> {
	
	@Autowired
	private FutureService service;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private FutureDao dao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public FutureService getService() {
		return service;
	}
	
	@RequestMapping(value="/futurePage")
	public String showLogin(HttpServletRequest req,HttpSession session){
		try{
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				return "web/login";
			}
			req.setAttribute("futuresList", service.getEntities(null));
			session.setAttribute("requestCount", ((int)session.getAttribute("requestCount"))+1);
			return "web/futures";
		}catch(Exception E){
			
		}
		return "";
	}
	
	@RequestMapping(value="/showBusiness/{code}")
	public String showBusiness(@PathVariable String code,HttpServletRequest req,HttpSession session){
		try{
			FutureQuery futureQuery = new FutureQuery();
			futureQuery.setFutureCode(code);
			//可用余额
			ClientUser cu = (ClientUser) session.getAttribute("loginUser");
			if(cu==null){
				return "web/login";
			}
			req.setAttribute("futureCode", service.getEntity(futureQuery));
			req.setAttribute("enableMoney", clientUserService.getById(cu.getId()).getEnableMoney());
			return "web/business";
		}catch(Exception E){
			
		}
		return "";
	}
	
	//获取新浪数据  k 5 30 
	@RequestMapping(value="/futureSina")
	@ResponseBody
	public ListJson futureSina(String futureCode){
		try{
			Map<String, Object> data = new HashMap<String,Object>();
			//获取分时线图
			List<Map<String, Object>> list = jdbcTemplate.queryForList("select distinct time,code,newPrice from marketdata where code='"+futureCode+"' order by time desc");
			data.put("fenshi", list);
			//
			data.put("rik", SinaJsonUtil.loadJson(FutureTypeEnum.historyDay,futureCode));
			data.put("5fen", SinaJsonUtil.loadJson(FutureTypeEnum.HISTORY5,futureCode));
			data.put("30fen", SinaJsonUtil.loadJson(FutureTypeEnum.HISTORY30,futureCode));
			data.put("60fen", SinaJsonUtil.loadJson(FutureTypeEnum.HISTORY60,futureCode));
			return new ListJson(data);
		}catch(Exception E){
			
		}
		return new ListJson();
	}
	
	//获取新浪实时数据
	@RequestMapping(value="/futureSinaByTime")
	@ResponseBody
	public ListJson futureSinaByTime(String futureCode){
		try{
			return new ListJson(SinaJsonUtil.loadJson(FutureTypeEnum.ACTUAL,futureCode));
		}catch(Exception E){
			
		}
		return new ListJson();
	}
	
	//获取新浪实时数据
	@RequestMapping(value="/futureSinaByTimeAll")
	@ResponseBody
	public ListJson futureSinaByTimeAll(String futureCode){
		try{
			return new ListJson(SinaJsonUtil.loadJson(FutureTypeEnum.ACTUAL,futureCode));
		}catch(Exception E){
		
		}
		return new ListJson();
	}

}