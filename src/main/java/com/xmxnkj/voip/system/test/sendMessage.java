package com.xmxnkj.voip.system.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.xmxnkj.voip.web.utils.smms.sms.request.SmsSendRequest;
import com.xmxnkj.voip.web.utils.smms.sms.response.SmsSendResponse;
import com.xmxnkj.voip.web.utils.smms.sms.util.ChuangLanSmsUtil;
import com.xmxnkj.voip.web.utils.sms.SMSSendUtil;

public class sendMessage {
	
	public static void main(String[] args) throws Exception {
		 SMSSendUtil.getInstance().sendSMS("15959285933", "【汇智库】您的验证码:[123456]");
	}
	
	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2752432";
	// 用户平台API密码(非登录密码)
	public static String pswd = "cTm4LhQ2nO690d";
	// 普通短信地址
	public static String smsSingleRequestServerUrl = "http://vsms.253.com/msg/send/json";
	
	public void sendSMS(String sendPhone,String sendMsg)throws Exception{

		// 短信内容
		String msg = URLEncoder.encode(sendMsg,"utf-8");
				
		Map map = new HashMap();
		map.put("account", account);
		map.put("password", pswd);
		map.put("msg", msg);
		map.put("phone", sendPhone);
		String requestJson = JSON.toJSONString(map);
				
		System.out.println("before request string is: " + requestJson);
				
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
				
		System.out.println("response after request result is :" + response);
				
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
	}
	
	public static String sendSmsByPost(String path, String postContent) {
		URL url = null;
		try {
			url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			// conn.setConnectTimeout(10000);//连接超时 单位毫秒
			// conn.setReadTimeout(2000);//读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			
			httpURLConnection.connect();
			OutputStream os = httpURLConnection.getOutputStream();
			os.write(postContent.getBytes("utf-8"));
			os.flush();

			StringBuilder sb = new StringBuilder();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				// 开始获取数据
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
