package com.xmxnkj.voip.system.test;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmxnkj.voip.system.controllers.ExitEntryController;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:applicationContext.xml"})
public class Test4 {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Test
	public void test(){
		String sql = "select * from exitentry where exitOrEntry=0 and entryTime>?";
		Date date = new Date();
		sdf.format(date);
		List<Map<String, Object>>  list = jdbcTemplate.queryForList(sql, new Object[]{sdf.format(date)});
		String orderNumber = "";
		for(Map<String, Object> map:list){
			orderNumber = map.get("payOrderNumber").toString();
			String result = Test4.queryPayOrder(orderNumber);
			if(StringUtils.isNotEmpty(result)){
				JSONObject jsonObject = JSON.parseObject(result.toString());
				if(jsonObject.containsKey("status") && jsonObject.get("status").toString().equals("00")){
					//进行结算
					//System.out.println("结算订单："+orderNumber);
				}
			}
		}
	}
	
	public static String queryPayOrder(String orderId){
		StringBuffer str = new StringBuffer("http://114.80.54.74:8082/quickpay-front/quickPayWap/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderNo="+orderId);
		String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderNo="+orderId+"&key=xxk115303021");
		str.append("&mac="+mac.toUpperCase());
		
		HttpClient client = new HttpClient();  
	    HttpMethod method=null;
		method = new GetMethod(str.toString());
		String result = "";
	    try {  
	        int statusCode = client.executeMethod(method);  
	        System.out.println(statusCode);  
	  
	        byte[] responseBody = null;  
	  
	        responseBody = method.getResponseBody();  
	  
	        result = new String(responseBody);
	  
	        System.out.println(result);  
	  
	    } catch (HttpException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  catch(Exception e){
	    	 e.printStackTrace(); 
	    }
		return result;
	}
	
	
	public static void main(String[] args){
		//支付查询
		String orderId = "151192521069083";
		StringBuffer str = new StringBuffer("http://114.80.54.74:8082/quickpay-front/quickPayWap/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderNo="+orderId);
		String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderNo="+orderId+"&key=xxk115303021");
		str.append("&mac="+mac.toUpperCase());
		
		HttpClient client = new HttpClient();  
	    HttpMethod method=null;
		method = new GetMethod(str.toString());
	    try {  
	        int statusCode = client.executeMethod(method);  
	        System.out.println(statusCode);  
	  
	        byte[] responseBody = null;  
	  
	        responseBody = method.getResponseBody();  
	  
	        String result = new String(responseBody);
	  
	        System.out.println(result);  
	  
	    } catch (HttpException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  catch(Exception e){
	    	 e.printStackTrace(); 
	    }
		
		//代付查询
		/*String orderId = "151114591872261";
		StringBuffer str = new StringBuffer("http://114.80.54.73:8081/unspay-external/delegatePay/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderId="+orderId);
		String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderId="+orderId+"&key=xxk115303021");
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
	  
	        System.out.println(result);  
	  
	    } catch (HttpException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  catch(Exception e){
	    	 e.printStackTrace(); 
	    }*/
	}
}