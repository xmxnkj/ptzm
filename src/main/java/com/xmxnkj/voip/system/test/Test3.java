package com.xmxnkj.voip.system.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.xmxnkj.voip.system.controllers.ExitEntryController;

/*@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:applicationContext.xml"})  */
public class Test3 {
	
	
	@Test
	public void t(){
		String orderId = "151089759409721";
		StringBuffer str = new StringBuffer("http://wap.unspay.com:8082/quickpay-front/quickPayWap/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderNo="+orderId);
		String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderNo="+orderId+"&key=xxk115303021");
		str.append("&mac="+mac.toUpperCase());
		
		HttpClient client = new HttpClient();  
		  
	    HttpMethod method = new GetMethod("http://wap.unspay.com:8082/quickpay-front/quickPayWap/queryOrderStatus?accountId=2120171102100529001&orderNo=151089759409721&mac=D03625C7D71A0C22D858FEBB4247C3C2");  
	    client.getHttpConnectionManager().getParams().setConnectionTimeout(30000000);  
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
	    }  
	}
	
	public static void main(String[] args) throws IOException {
		String orderId = "151089759409721";
		StringBuffer str = new StringBuffer("http://wap.unspay.com:8082/quickpay-front/quickPayWap/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderNo="+orderId);
		String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderNo="+orderId+"&key=xxk115303021");
		str.append("&mac="+mac.toUpperCase());
		
		 InputStream is = new URL(str.toString()).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = rd.readLine();
		      System.out.println(jsonText);
		    } finally {
		      is.close();
		     // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
		    }
	}
	
	@Test
	public void test() throws Exception{
		String orderId = "151089759409721";
		StringBuffer str = new StringBuffer("http://wap.unspay.com:8082/quickpay-front/quickPayWap/queryOrderStatus?");
		str.append("accountId=2120171102100529001");
		str.append("&orderNo="+orderId);
//		String mac = ExitEntryController.EncoderByMd5("accountId=2120171102100529001&orderNo="+orderId+"&key=xxk115303021");
//		str.append("&mac="+mac.toUpperCase());
		
		HttpClient client = new HttpClient();  
		  
	    HttpMethod method=null;
		try {
			method = new GetMethod("http://wap.unspay.com:8082/quickpay-front/quickPayWap/queryOrderStatus?accountId=2120171102100529001&orderNo=151089759409721&mac=D03625C7D71A0C22D858FEBB4247C3C2");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
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
	    }
	}
}