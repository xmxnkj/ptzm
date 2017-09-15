package com.xmxnkj.voip.web.utils.smms;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.xmxnkj.voip.web.utils.smms.sms.request.SmsSendRequest;
import com.xmxnkj.voip.web.utils.smms.sms.response.SmsSendResponse;
import com.xmxnkj.voip.web.utils.smms.sms.util.ChuangLanSmsUtil;

public class Test1 {


	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2752432";
	// 用户平台API密码(非登录密码)
	public static String pswd = "cTm4LhQ2nO690d";

	public static void main(String[] args) throws UnsupportedEncodingException {

		// 普通短信地址
		String smsSingleRequestServerUrl = "http://vsms.253.com/msg/send/json";
		// 短信内容
	    String msg = "【253云通讯】你好,你的验证码是464545";
		//手机号码
		String phone = "15959285933";
		
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, phone);
		
		String requestJson = JSON.toJSONString(smsSingleRequest);
		
		//System.out.println("before request string is: " + requestJson);
		
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
		
		//System.out.println("response after request result is :" + response);
		
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		//System.out.println("response  toString is :" + smsSingleResponse);
		
	}

}
