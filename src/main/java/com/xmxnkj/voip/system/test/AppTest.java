package com.xmxnkj.voip.system.test;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.xmxnkj.voip.web.utils.HttpClientUtils;
import com.xmxnkj.voip.web.utils.SignKit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class AppTest {
	
	 @Test
	    /**
	     * 银行卡预支付Demo
	     */
	    public void rfAppTest1() {
	        Map param = new HashMap();
	        param.put("userId", "20180102095418488680");
	        param.put("orderAmt", "10.00");
	        param.put("payType", "02");
	        param.put("payMode", "2");
	        param.put("backUrl", "http://payment-test.rxhpay.com:8020/cross-three/RhAppNotis");
	        param.put("openid", "");
	        param.put("name", "薛祥康");
	        param.put("cardNo", "6221682258122808");
	        param.put("cardType", "01");
	        param.put("expireDate", "2501");
	        param.put("cvv", "");
	        param.put("mobile", "13665088871");
	        param.put("idNo", "350181198607031699");
	        param.put("transFlag", "01");
	        param.put("transNums", "0");
	        param.put("orderId", "");
	        param.put("tradeNo", "");
	        param.put("vcode", "");
	        param.put("sign", SignKit.sign("2FE255", param));
	        param.put("key", "2FE255");

	        String url = "http://app.xmfastpay.com:81/fpc/trade/quotaPay";
	        String context = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param, HttpClientUtils.DEFAULT_ENCODING);
	        System.out.println("同步返回:" + context);

	        JSONObject jsonParam = JSONObject.parseObject(context);
	        Map param1 = new HashMap();
	        // 状态码 0成功 其他失败
	        param1.put("code", jsonParam.getString("code"));
	        // 结果描述
	        param1.put("msg", jsonParam.getString("msg"));
	        // 二维码地址 可将该参数值生成二维码展示出来进行扫码支付
	        param1.put("codeUrl", jsonParam.getString("codeUrl"));
	        // 订单金额
	        param1.put("orderAmt", jsonParam.getString("orderAmt"));
	        // 交易订单号
	        param1.put("orderId", jsonParam.getString("orderId"));
	        // 订单创建时间
	        param1.put("orderTime", jsonParam.getString("orderTime"));
	        // 流水号
	        param1.put("tradeNo", jsonParam.getString("tradeNo"));

	        String sign = SignKit.sign("2FE255", param1);

	        System.out.println("签名验证:" + jsonParam.getString("sign").equals(sign));
	    }
	 
	 @Test
	 public void rfAppTest2() {
	        Map param = new HashMap();
	        param.put("userId", "20180102095418488680");
	        param.put("orderAmt", "10.00");
	        param.put("payType", "02");
	        param.put("payMode", "2");
	        param.put("backUrl", "http://payment-test.rxhpay.com:8020/cross-three/RhAppNotis");
	        param.put("openid", "");
	        param.put("name", "薛祥康");
	        param.put("cardNo", "6221682258122808");
	        param.put("cardType", "01");
	        param.put("expireDate", "2501");
	        param.put("cvv", "");
	        param.put("mobile", "13665088871");
	        param.put("idNo", "350181198607031699");
	        param.put("transFlag", "01");
	        param.put("transNums", "0");
	        param.put("orderId", "");
	        param.put("tradeNo", "");
	        param.put("vcode", "");
	        param.put("sign", SignKit.sign("2FE255", param));
	        param.put("key", "391467");
	        String url = "http://app.xmfastpay.com:81/fpc/trade/quotaPay";
	        String context = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param, HttpClientUtils.DEFAULT_ENCODING);
	        System.out.println("同步返回:" + context);

	        JSONObject jsonParam = JSONObject.parseObject(context);
	        Map param1 = new HashMap();
	        // 状态码 0成功 其他失败
	        
	        if(jsonParam.getString("code").equals("0")){
	        	param1.put("userId", "20180102095418488680");
	        	param1.put("orderAmt", "10.00");
	        	param1.put("payType", "02");
	        	param1.put("payMode", "3");
	        	param1.put("backUrl", "http://payment-test.rxhpay.com:8020/cross-three/RhAppNotis");
	 	        // 商户订单号
	        	param1.put("orderId", jsonParam.getString("orderId"));
	 	        // 预支付流水号
	        	param1.put("tradeNo", jsonParam.getString("tradeNo"));
	 	        // 短信验证码
	        	param1.put("vcode", "617047");
	        	param1.put("sign", SignKit.sign("2FE255", param1));
	        	param1.put("key", "2FE255");
	        	//String results = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param1, HttpClientUtils.DEFAULT_ENCODING);
		        //System.out.println("同步返回:" + results);
	        }
	    }
	 
	 	@Test
	 	public void payTest(){
	        Map param1 = new HashMap();

	 		param1.put("userId", "20180102095418488680");
        	param1.put("orderAmt", "10.00");
        	param1.put("payType", "02");
        	param1.put("payMode", "3");
        	param1.put("backUrl", "http://payment-test.rxhpay.com:8020/cross-three/RhAppNotis");
 	        // 商户订单号
        	param1.put("orderId", "20180102175511412736");
 	        // 预支付流水号
        	param1.put("tradeNo", "2018010230275404");
 	        // 短信验证码
        	param1.put("vcode", "792764");
        	param1.put("sign", SignKit.sign("2FE255", param1));
        	param1.put("key", "2FE255");
	        String url = "http://app.xmfastpay.com:81/fpc/trade/quotaPay";

        	String results = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param1, HttpClientUtils.DEFAULT_ENCODING);
	        System.out.println("同步返回:" + results);
	 	}
	 
	    @Test
	    /**
	     * 银行卡验证支付Demo
	     */
	    public void rhAppTest2() {
	        Map param = new HashMap();
	        param.put("userId", "");
	        param.put("orderAmt", "1.00");
	        param.put("payType", "02");
	        param.put("payMode", "3");
	        param.put("backUrl", "");
	        param.put("openid", "");
	        param.put("name", "");
	        param.put("cardNo", "");
	        param.put("cardType", "");
	        param.put("expireDate", "");
	        param.put("cvv", "");
	        param.put("mobile", "");
	        param.put("idNo", "");
	        param.put("transFlag", "");
	        param.put("transNums", "");
	        // 商户订单号
	        param.put("orderId", "");
	        // 预支付流水号
	        param.put("tradeNo", "");
	        // 短信验证码
	        param.put("vcode", "");
	        param.put("sign", SignKit.sign("", param));
	        param.put("key", "");
	        String url = "http://app.xmfastpay.com:81/fpc/trade/quotaPay";
	        String context = HttpClientUtils.post(HttpClientUtils.createHttpClient(), url, param, HttpClientUtils.DEFAULT_ENCODING);
	        System.out.println("同步返回:" + context);

	        JSONObject jsonParam = JSONObject.parseObject(context);
	        Map param1 = new HashMap();

	        param1.put("code", jsonParam.getString("code"));
	        param1.put("msg", jsonParam.getString("msg"));
	        param1.put("codeUrl", jsonParam.getString("codeUrl"));
	        param1.put("orderAmt", jsonParam.getString("orderAmt"));
	        param1.put("orderId", jsonParam.getString("orderId"));
	        param1.put("orderTime", jsonParam.getString("orderTime"));
	        param1.put("tradeNo", jsonParam.getString("tradeNo"));
	        String sign = SignKit.sign("79A8AB", param1);
	        System.out.println("签名验证:" + jsonParam.getString("sign").equals(sign));
	    }

	    @Test
	    /**
	     * 签名验证Demo
	     */
	    public void sign() {
	        String context = "{'tradeNo':'2018010230254778','code':0,'msg':'预交易成功','orderId':'20180102162726370565','orderTime':'0102162629','sign':'7901cf7033cc35b0fd0c1d18a2ec06ec'}";
	        JSONObject jsonParam = JSONObject.parseObject(context);
	        Map param1 = new HashMap();
	        param1.put("code", jsonParam.getString("code"));
	        param1.put("msg", jsonParam.getString("msg"));
	        param1.put("codeUrl", jsonParam.getString("codeUrl"));
	        param1.put("orderAmt", jsonParam.getString("orderAmt"));
	        param1.put("orderId", jsonParam.getString("orderId"));
	        param1.put("orderTime", jsonParam.getString("orderTime"));
	        param1.put("tradeNo", jsonParam.getString("tradeNo"));
	        String sign = SignKit.sign("79A8AB", param1);
	        System.out.println("签名验证:" + jsonParam.getString("sign").equals(sign));
	    }
	    
	    @Test
	    public void check() {
	    	//需要调用的接口
	    	String url = "http://www.caacts.org.cn/admin/jiekou_visitors.action?checkcode="+"FOCFOC17027";

	      	String aaString= test(url);  
			String bbString ="";
			System.out.println(aaString);
			//JSONObject jsonobject=JSONObject.fromObject(bbString);

//			String jsonObjectValue = jsonobject.getString("visitor");
//			JSONObject jsonObject2 = JSONObject.fromObject(jsonObjectValue);

			System.out.println(aaString);
			System.out.println(bbString);

	    }
	    
	    public static String test(String ADD_URL){
			StringBuffer sb = new StringBuffer();
			try {
			    //创建连接
				URL url = new URL(ADD_URL);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				connection.setRequestProperty("content-type", "text/json");
//				connection.setRequestProperty("Accept-Charset", "UTF-8");
//				connection.setRequestProperty("contentType", "UTF-8");
				connection.connect();
			    
				//post请求
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),"UTF-8"));
				out.flush();
				out.close();
				
				
				
				//读取响应
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
				String lines;
				while ((lines=reader.readLine())!=null) {
				    lines = new String(lines.getBytes());
				    sb.append(lines);
					
				}
				reader.close();
				connection.disconnect();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		   
		    System.out.println(sb.toString());
			return sb.toString();
			
		}
}