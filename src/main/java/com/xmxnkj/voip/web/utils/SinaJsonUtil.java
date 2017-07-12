package com.xmszit.futures.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.xmszit.futures.system.controllers.ExitEntryController;
import com.xmszit.futures.system.entity.emun.FutureTypeEnum;

//新浪接口数据
public class SinaJsonUtil {
	
	//实时行情
	private static String actualTime = "http://hq.sinajs.cn/list=";
	
	//5分
	private static String history5 = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine5m?symbol=";
	//15分
	private static String history15 = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine15m?symbol=";
	//30分
	private static String history30 = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine30m?symbol=";
	//60分
	private static String history60 = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesMiniKLine60m?symbol=";
	//日K
	private static String historyDay = "http://stock2.finance.sina.com.cn/futures/api/json.php/IndexService.getInnerFuturesDailyKLine?symbol=";

	//k线图
	public static String loadJson (FutureTypeEnum futureTypeEnum,String code) {
		String url = "";
		switch(futureTypeEnum){
			case ACTUAL:	//实时
				url = actualTime+code.toUpperCase();
				break;
			case HISTORY5:	//5分
				url = history5+code.toUpperCase();
				break;
			case HISTORY15:	//15分
				url = history15+code.toUpperCase();
				break;
			case HISTORY30:	//30分
				url = history30+code.toUpperCase();
				break;
			case HISTORY60:	//60分
				url = history60+code.toUpperCase();
				break;
			case historyDay:	//日K
				url = historyDay+code.toUpperCase();
				break;
		}
	    StringBuilder json = new StringBuilder();
	    URLConnection uc = null;
	    BufferedReader in = null;
	    try {  
	        URL urlObject = new URL(url);  
	        uc = urlObject.openConnection();  
	        in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"GBK"));
	        String inputLine = null;  
	        while ( (inputLine = in.readLine()) != null) {  
	            json.append(inputLine);  
	        }  
	    } catch (MalformedURLException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }finally{
	    	try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return json.toString();  
	} 
	
	//最新价
	public static BigDecimal getNewPrice(String code){
		String re = SinaJsonUtil.loadJson(FutureTypeEnum.ACTUAL,code);
		re = re.substring(re.indexOf("\"")+1, re.length()-2);
		return new BigDecimal(re.split(",")[8]);//第八个索引为最新价
	}
	
	//查询支付订单
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
	
}
