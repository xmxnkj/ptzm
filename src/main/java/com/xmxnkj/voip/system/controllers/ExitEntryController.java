package com.xmxnkj.voip.system.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hsit.common.kfbase.entity.Paging;
import com.xmxnkj.voip.system.dao.ExitEntryDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.ExitEntry;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.emun.ExitOrEntryEnum;
import com.xmxnkj.voip.system.entity.query.ExitEntryQuery;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.ExitEntryService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.controllers.ExitEntryWebController;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.utils.pay.util.HttpClientUtil;

@Controller
@RequestMapping("/back/exitEntry")
public class ExitEntryController extends BaseController<ExitEntry, ExitEntryQuery> {
	
	@Autowired
	private ExitEntryService service;
	
	@Autowired
	private ClientUserService clientUserService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ExitEntryDao dao;

	@Override
	public ExitEntryService getService() {
		return service;
	}
	
	@RequestMapping(value="/exitEntryPage")
	public ModelAndView showLogin(){
		return new ModelAndView("exitEntry/list");
	}
	
	//普通用户出入金
	@RequestMapping(value="/exitEntryList")
	@ResponseBody
	public ListJson getList(ExitEntryQuery exitEntryQuery,
							String startTime,
							String endTime,
							String phoneNumber,
							Integer rows,
							Integer page,
							HttpSession session){
		
		ListJson LJ = new ListJson();
		try{
			//前缀
			StringBuffer hql = new StringBuffer();
			String listQ = "select e.ID,e.entryTime,e.entryMoney,e.exitMoney,e.exitTime,e.phoneNumber,e.exitOrEntry,e.bankCardNo,e.bankAddress,e.payOrderNumber,c.name,e.season ";
			String listC = "select count(*) ";
			String listS = "";
			
			if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
				listS = "select sum(entryMoney) as total ";
			}else if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass || 
					exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApply || 
					exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitFail || 
					exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitSuccess ||
					exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApplyNotPass){
				
				listS = "select sum(exitMoney) as total ";
			}
			
			//查询条件
			List<String> query = new ArrayList<String>();
			
			hql.append(" from exitentry e LEFT JOIN ClientUser c on e.userId=c.id where exitOrEntry=? and status='0'");
			query.add(exitEntryQuery.getExitOrEntry().ordinal()+"");
			//下级所有用户
			ClientUser cu = (ClientUser) session.getAttribute("manager");
			List<String> lista = new ArrayList<String>();
			lista.add(cu.getId());
			List<Map<String,Object>> clientList = clientUserService.getSonNormal(lista, "?");
			String accounts = "?,";
			query.add("");
			for(Map<String,Object> map:clientList){
				if(!phoneNumber.equals("")){
					if(((String) map.get("phoneNumber")).contains(phoneNumber)){
						query.add((String) map.get("phoneNumber"));
						accounts+="?,";
					}
				}
				
				if(phoneNumber.equals("")){
					query.add((String) map.get("phoneNumber"));
					accounts+="?,";
				}
			}

			hql.append(" and e.phoneNumber in ("+(accounts.substring(0,accounts.length()-1))+") ");

			Paging paging = new Paging();
			paging.setPage(page);
			paging.setPageCount(rows);
			
			if(startTime!=null && !startTime.equals("")){
				if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
					hql.append(" and e.entryTime>?");
				}
				if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass){
					hql.append(" and e.exitTime>?");
				}
				query.add(startTime);
			}
			if(endTime!=null && !endTime.equals("")){
				if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
					hql.append(" and e.entryTime<?");
				}
				if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass){
					hql.append(" and e.exitTime<?");
				}
				query.add(endTime);
			}
			Object[] obj = new Object[query.size()];
			for(int i=0;i<query.size();i++){
				obj[i] = query.get(i);
			}
			long total = jdbcTemplate.queryForInt(listC+hql.toString(),obj);
			Map<String,Object> resultTotal = jdbcTemplate.queryForMap(listS+hql.toString(),obj);
			List<Map<String,Object>> list = jdbcTemplate.queryForList(listQ+hql.toString()+"  order by "+(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass?"e.entryTime":"e.exitTime")+" desc limit "+((page-1)*rows)+","+rows,obj);
			paging.setRecordCount(total);
			LJ.setPaging(paging);
			LJ.setPageSize((int)Math.ceil((float)paging.getRecordCount()/paging.getPageCount()));
			LJ.setRows(list);
			LJ.setEntity(resultTotal);
			LJ.setSuccess(true);
			return LJ;
		}catch(Exception e){
			return new ListJson(e);
		}
	}
	
	//总计
	@RequestMapping("getTotalMoney")
	@ResponseBody
	public ListJson getTotalMoney(ExitEntryQuery exitEntryQuery,
								String startTime,
								String endTime){
		ListJson LJ = new ListJson();
		StringBuffer hql = new StringBuffer();
		List<String> query = new ArrayList<String>();
		hql.append("select "+(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass?"sum(entryMoney)":"sum(exitMoney)")+" as totalMoney from ExitEntry where exitOrEntry=?");
		query.add(exitEntryQuery.getExitOrEntry().ordinal()+"");
		if(startTime!=null && !startTime.equals("")){
			if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
				hql.append(" and entryTime>?");
			}
			if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass){
				hql.append(" and exitTime>?");
			}
			query.add(startTime);
		}
		if(endTime!=null && !endTime.equals("")){
			if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
				hql.append(" and entryTime<?");
			}
			if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.ExitApplyPass){
				hql.append(" and exitTime<?");
			}
			query.add(endTime);
		}
		Object[] obj = new Object[query.size()];
		for(int i=0;i<query.size();i++){
			obj[i] = query.get(i);
		}
		LJ.setEntity(jdbcTemplate.queryForMap(hql.toString(), obj));
		return LJ;
	}
	
	@RequestMapping(value="/menunitListJsp")
	public String menunitListJsp(){
		return "exitEntry/menunitList";
	}
	
	//会员单位出入金
	@RequestMapping(value="/getMemberUnitEntryExitList")
	@ResponseBody
	public ListJson getMemberUnitEntryExitList(ExitEntryQuery exitEntryQuery,
								String startTime,
								String endTime,
								String phoneNumber,
								Integer rows,
								Integer page,
								HttpSession session){
			
			ListJson LJ = new ListJson();
			Paging paging = new Paging();
			paging.setPage(page);
			paging.setPageCount(rows);
			try{
				//前缀
				StringBuffer hql = new StringBuffer();
				String listQ = "select e.ID as ID,c.phoneNumber as phoneNumber,c.name as name,c.agentName as agentName,e.entryMoney as entryMoney,e.entryTime as entryTime,e.exitMoney as exitMoney,e.exitTime as exitTime,e.exitOrEntry as exitOrEntry,e.bankAddress as bankAddress,e.season as season,e.bankCardNo as bankCardNo,e.payOrderNumber as payOrderNumber ";
				String listC = "select count(*) ";
				String listS = "";
				
				if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
					listS = "select sum(entryMoney) as total";
				}else{
					listS = "select sum(exitMoney) as total";
				}
				//查询条件
				List<String> query = new ArrayList<String>();
				
				hql.append(" from exitentry e LEFT JOIN ClientUser c on e.userId=c.id where 1=1 and e.status='1'");
				
				if(exitEntryQuery.getExitOrEntry()!=null){
					hql.append(" and e.exitOrEntry=? ");
					query.add(exitEntryQuery.getExitOrEntry().ordinal()+"");
				}
				
				//下级所有用户
				ClientUser cu = (ClientUser) session.getAttribute("manager");
				List<String> lista = new ArrayList<String>();
				lista.add(cu.getId());
				List<Map<String,Object>> clientList = clientUserService.getSonNormal(lista, "?",ClientTypeEnum.MemberUnit);
				String accounts = "";
				
				Map<String,Object> mapMyself = new HashMap<String,Object>();
				if(cu.getClientType()==ClientTypeEnum.MemberUnit){
					mapMyself.put("ID", cu.getId());
					mapMyself.put("phoneNumber", cu.getPhoneNumber());
					clientList.add(mapMyself);
				}
				for(Map<String,Object> map:clientList){
					if(!phoneNumber.equals("")){
						if(((String) map.get("phoneNumber")).contains(phoneNumber)){
							query.add((String) map.get("phoneNumber"));
							accounts+="?,";
						}
					}
					
					if(phoneNumber.equals("")){
						query.add((String) map.get("phoneNumber"));
						accounts+="?,";
					}
				}
				
				if(accounts.equals("")){
					paging.setPageCount(0);
					paging.setRecordCount(0);
					LJ.setPaging(paging);
					LJ.setRows(new ArrayList());
					return LJ;
				}
				
				hql.append(" and e.phoneNumber in ("+(accounts.substring(0,accounts.length()-1))+") ");

				if(startTime!=null && !startTime.equals("")){
					if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
						hql.append(" and e.entryTime>?");
					}else{
						hql.append(" and e.exitTime>?");
					}
					query.add(startTime);
				}
				if(endTime!=null && !endTime.equals("")){
					if(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
						hql.append(" and e.entryTime<?");
					}else{
						hql.append(" and e.exitTime<?");
					}
					query.add(endTime);
				}
				Object[] obj = new Object[query.size()];
				for(int i=0;i<query.size();i++){
					obj[i] = query.get(i);
				}
				long total = jdbcTemplate.queryForInt(listC+hql.toString(),obj);
				Map<String,Object> resultTotal = jdbcTemplate.queryForMap(listS+hql.toString(),obj);
				List<Map<String,Object>> list = jdbcTemplate.queryForList(listQ+hql.toString()+" order by "+(exitEntryQuery.getExitOrEntry()==ExitOrEntryEnum.EntryPass?"e.entryTime":"e.exitTime")+" desc limit "+((page-1)*paging.getPageSize())+","+rows,obj);
				paging.setRecordCount(total);
				LJ.setPaging(paging);
				LJ.setPageSize((int)Math.ceil((float)paging.getRecordCount()/paging.getPageCount()));
				LJ.setRows(list);
				LJ.setEntity(resultTotal);
				LJ.setSuccess(true);
				return LJ;
			}catch(Exception e){
				return new ListJson(e);
			}
		}
		
		@RequestMapping(value="/applyPassOrNotPass")
		@ResponseBody
		public ListJson applyPassOrNotPass(String ID,String result,String name) throws Exception{
			
			ListJson LJ = new ListJson();
			
			ExitEntry exitEntry = service.getById(ID);
			
			if(exitEntry==null){
				LJ.setSuccess(false);
				return LJ;
			}
			
			ClientUser cu = clientUserService.getById(exitEntry.getUserId());
			
			//出金申请
			if(result.equals("PASS")){
				
				if(exitEntry==null){
					LJ.setSuccess(false);
					LJ.setMessage("该申请不存在");
					return LJ;
				}
				
				if(cu==null){
					LJ.setSuccess(false);
					LJ.setMessage("该用户不存在");
					return LJ;
				}
				
				//出金成功动作
				//service.outMoney(cu.getId(), exitEntry);
				
				//出金申请
				applyOutMoney(exitEntry);
				
				LJ.setSuccess(true);
				return LJ;
			}

			if(result.equals("NotPASS")){
				exitEntry.setExitOrEntry(ExitOrEntryEnum.ExitApplyNotPass);
				exitEntry.setSeason("审核不通过");
				service.save(exitEntry);
				
				//冻结金额返还
				service.failExitMoney(cu, exitEntry);
				
				LJ.setMessage("审核不通过");
				LJ.setSuccess(true);
				return LJ;
			}
			LJ.setSuccess(true);
			return LJ;
		}
		
		static String reqUrl = "http://114.80.54.73:8081/unspay-external/delegatePay/pay?";
		
		static String back = "http://www.zhzbfx.com/voip/back/exitEntry/reback";
		//static String back = "http://yangwch.myqnapcloud.com:8023/voip/back/exitEntry/reback";
		//请求出金接口
		public void applyOutMoney(ExitEntry e) throws Exception{
			
			if(e.getExitOrEntry()==ExitOrEntryEnum.ExitApply){
				
				DecimalFormat df = new DecimalFormat("#.00");
				
				String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
				e.setPayOrderNumber(orderNo);
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append("accountId="+ExitEntryWebController.accountId);
				stringBuffer.append("&name="+e.getName());
				stringBuffer.append("&cardNo="+e.getBankCardNo());
				stringBuffer.append("&orderId="+orderNo);
				stringBuffer.append("&purpose=outMoney");
				stringBuffer.append("&amount="+df.format(e.getExitMoney().doubleValue()));
				stringBuffer.append("&responseUrl="+back);

				String before = stringBuffer.toString()+"&key="+ExitEntryWebController.key;
				
				System.out.println("加密前："+before );
				
				//加密结果
				String mac = EncoderByMd5(before.trim()).toUpperCase();
				
				System.out.println("加密后："+mac);
				
				stringBuffer.append("&mac="+mac);

				String response = "";
				
				try {
		            response = HttpClientUtil.sendPostHTTP(reqUrl, stringBuffer.toString(), "utf-8");
		            System.out.println(response);
		        } catch (IOException ioe) {
		        	ioe.printStackTrace();
		        }
		        
		        JSONObject jsonObject = JSON.parseObject(response);
		        Map<String, String> map = new TreeMap<String, String>();
		        map.put("result_code", jsonObject.getString("result_code"));
		        map.put("result_msg", jsonObject.getString("result_msg"));
				if(map.get("result_code").toString().equals("0000")){
					//受理通过
					e.setExitOrEntry(ExitOrEntryEnum.ExitApplyPass);
				}else{
					//失败
					e.setSeason((map.get("result_msg").toString()));
					e.setExitOrEntry(ExitOrEntryEnum.ExitFail);
					service.failExitMoney(clientUserService.getById(e.getUserId()), e);
				}
				e.setPayOrderNumber(orderNo);
				service.save(e);
			}
		}
		
		//出金结果
		@RequestMapping(value="/reback")
		@ResponseBody
		public void reback(String result_code,
							String result_msg,
							String amount,
							String orderId,
							String mac){
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("accountId="+ExitEntryWebController.accountId);
			stringBuffer.append("&orderId="+orderId);
			stringBuffer.append("&amount="+amount);
			stringBuffer.append("&result_code="+result_code);
			stringBuffer.append("&result_msg="+result_msg);
			stringBuffer.append("&key="+ExitEntryWebController.key);
			
			ExitEntryQuery entryQuery = new ExitEntryQuery();
			entryQuery.setPayOrderNumber(orderId);
			ExitEntry e = service.getEntity(entryQuery);
			logger.info("出金回调**单号："+orderId+",额度："+amount+",状态码："+result_code);
			try {
				String md5 = EncoderByMd5(stringBuffer.toString());
				if(md5.toUpperCase().equals(mac)){
					if(result_code.equals("0000")){
						service.outMoney(e.getUserId(), e);
					}else{
						e.setExitOrEntry(ExitOrEntryEnum.ExitFail);
						e.setSeason(result_msg);
						service.failExitMoney(clientUserService.getById(e.getUserId()), e);
						service.save(e);
					}
				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}
		
		//MD5
		public static String EncoderByMd5(String str) {  
		    String result = "";  
		    MessageDigest md5 = null;  
		    try {  
		        md5 = MessageDigest.getInstance("MD5");  
		        // 这句是关键  
		        md5.update(str.getBytes("UTF-8"));
		    } catch (NoSuchAlgorithmException e) {  
		        e.printStackTrace();  
		    } catch (UnsupportedEncodingException e) {  
		        e.printStackTrace();  
		    }  
		    byte b[] = md5.digest();  
		    int i;  
		    StringBuffer buf = new StringBuffer("");  
		    for (int offset = 0; offset < b.length; offset++) {  
		        i = b[offset];  
		        if (i < 0)  
		            i += 256;  
		        if (i < 16)  
		            buf.append("0");  
		        buf.append(Integer.toHexString(i));  
		    }  
		    result = buf.toString();  
		  
		    return result;  
		}
		
		//支付订单查询
		@RequestMapping("queryOrder")
		@ResponseBody
		public Object queryOrder(String orderNo,HttpSession session){
			JSONObject jsonObject = new JSONObject();
			try {
				StringBuffer str = new StringBuffer("http://114.80.54.74:8082/quickpay-front/quickPayWap/queryOrderStatus?");
				str.append("accountId=2120171102100529001");
				str.append("&orderNo="+orderNo);
				String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderNo="+orderNo+"&key=xxk115303021");
				str.append("&mac="+mac.toUpperCase());
				
				HttpClient client = new HttpClient();  
			    HttpMethod method=null;
				method = new GetMethod(str.toString());
			    client.getHttpConnectionManager().getParams().setConnectionTimeout(3000000);  
			    client.getHttpConnectionManager().getParams().setSoTimeout(3000000);  
			    int statusCode = client.executeMethod(method);  
		        System.out.println(statusCode);  
		  
		        byte[] responseBody = null;  
		  
		        responseBody = method.getResponseBody();  
		  
		        String result = new String(responseBody,"UTF-8");  
		  
		        System.out.println(result);  
		        
		        jsonObject = JSON.parseObject(result);

		        if(jsonObject.get("status").equals("00")){
		        	ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
		        	exitEntryQuery.setPayOrderNumber(orderNo);
		        	ExitEntry exitEntry = service.getEntity(exitEntryQuery);
		        	if(exitEntry!=null){
		        		//申请中
		        		if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.EntryApply){
		        			session.setAttribute("orderNumber_",orderNo);
		        			jsonObject.put("isBalance", "0");
		        		}else if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
		        			//已结算
		        			jsonObject.put("isBalance", "1");
		        		}
		        	}
		        }
			} catch (Exception e) {
				e.printStackTrace();
				jsonObject.put("error", "查询失败");
			} 
		return jsonObject;
	}
	
	//手动结算支付订单
	@RequestMapping("balance")
	@ResponseBody
	public JSONObject balance(HttpSession session){
		JSONObject jsonObject = new JSONObject();
		try{
			String orderNo = (String) session.getAttribute("orderNumber_");
			
			if(!StringUtils.isEmpty(orderNo)){
				ExitEntryQuery exitEntryQuery = new ExitEntryQuery();
	        	exitEntryQuery.setPayOrderNumber(orderNo);
	        	ExitEntry exitEntry = service.getEntity(exitEntryQuery);
	        	if(exitEntry!=null){
	        		if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.EntryApply){
	        			//结算操作
	        			service.EntryMoney(exitEntry.getUserId(), exitEntry);
	        		}else if(exitEntry.getExitOrEntry()==ExitOrEntryEnum.EntryPass){
	        			jsonObject.put("isSuccess", false);
	        			jsonObject.put("status", "该订单已结算！");
	        		}
	        	}else{
	        		jsonObject.put("isSuccess", false);
	    			jsonObject.put("status", "不存在该订单");
	        	}
			}else{
				jsonObject.put("isSuccess", false);
				jsonObject.put("status", "未成功");
			}
		}catch(Exception e){
			
		}
		return jsonObject;
	}
	
	
	//代付订单查询
	@RequestMapping("delegatePayQuery")
	@ResponseBody
	public JSONObject delegatePayQuery(String orderNo){
		JSONObject jsonObject = new JSONObject();
		StringBuffer str = new StringBuffer("http://114.80.54.73:8081/unspay-external/delegatePay/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderId="+orderNo);
		String mac = ExitEntryController.EncoderByMd5("accountId="+ExitEntryWebController.accountId+"&orderId="+orderNo+"&key="+ExitEntryWebController.key);
		str.append("&mac="+mac.toUpperCase());
		HttpClient client = new HttpClient();  
		  
	    HttpMethod method=null;
		try {
			method = new GetMethod(str.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}  
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(3000000);  
	    client.getHttpConnectionManager().getParams().setSoTimeout(3000000);  
	    try {  
	        int statusCode = client.executeMethod(method);  
	        System.out.println(statusCode);  
	        byte[] responseBody = null;  
	        responseBody = method.getResponseBody();  	  
	        String result = new String(responseBody);
	        jsonObject = JSON.parseObject(result.toString());
	    } catch (HttpException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  catch(Exception e){
	    	 e.printStackTrace(); 
	    }
		return jsonObject;
	}
	
	//手动结算支付订单
	@RequestMapping("delegateBalance")
	@ResponseBody
	public void delegateBalance(String payOrderNumber,String result){
		try {
			ExitEntryQuery entryQuery = new ExitEntryQuery();
			entryQuery.setPayOrderNumber(payOrderNumber);
			ExitEntry e = service.getEntity(entryQuery);
			if(e!=null){
				logger.info("出金手动结算*******订单号："+payOrderNumber+",额度："+e.getExitMoney());
				if(result.equals("success")){
					service.outMoney(e.getUserId(), e);
				}else{
					e.setExitOrEntry(ExitOrEntryEnum.ExitFail);
					service.save(e);
					service.failExitMoney(clientUserService.getById(e.getUserId()), e);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}