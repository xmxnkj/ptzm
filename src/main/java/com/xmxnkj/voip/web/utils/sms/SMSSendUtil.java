package com.xmxnkj.voip.web.utils.sms;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.xmxnkj.voip.web.utils.smms.sms.request.SmsSendRequest;
import com.xmxnkj.voip.web.utils.smms.sms.response.SmsSendResponse;
import com.xmxnkj.voip.web.utils.smms.sms.util.ChuangLanSmsUtil;

/**   
*    
* 项目名称：xmxnkj-COWELL
* 项目说明：运动APP项目
* 类名称：SMSSendUtil   
* 类描述：短信发送工具类
* 事件记录：
* 创建人：Administrator  
* 创建时间：2016年12月31日 上午10:14:49
* 厦门晟中信息科技有限公司
* @version 1.0 
*    
*/
public class SMSSendUtil {
   
	protected  Logger logger = LoggerFactory.getLogger(this.getClass());
	/*
	private final static String smsurl = "http://sms.253.com/msg/";// 应用地址
	private final static String smsun = "N5634157";// 账号
	private final static String smspw = "hkvJEXzftid290";// 密码(目前不知道问下杨总)
	*/
	public static final String charset = "utf-8";
	// 用户平台API账号(非登录账号,示例:N1234567)
	public static String account = "N2752432";
	// 用户平台API密码(非登录密码)
	public static String pswd = "cTm4LhQ2nO690d";
	// 普通短信地址
	public static String smsSingleRequestServerUrl = "http://vsms.253.com/msg/send/json";
	/**
	 * 单实例模式
	 */
	private static SMSSendUtil instance;

	private SMSSendUtil() {
		
	}

	public static synchronized SMSSendUtil getInstance() {
		if (instance == null) {
			instance = new SMSSendUtil();
		}
		return instance;
	}
	
	/**
	 * 
	* 方法描述:发送短信
	* 创建人: Administrator
	* 创建时间：2016年12月31日
	* @param sendPhone
	* @return
	* @throws ErrorException
	 */
	public void sendSMS(String sendPhone,String sendMsg)throws Exception{

		// 短信内容
		String msg = URLEncoder.encode(sendMsg,"utf-8");
				
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, pswd, msg, sendPhone);
				
		String requestJson = JSON.toJSONString(smsSingleRequest);
				
		System.out.println("before request string is: " + requestJson);
				
		String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);
				
		System.out.println("response after request result is :" + response);
				
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		
		System.out.println("response  toString is :" + smsSingleResponse);
	}
	
	public static void main(String[] args) throws Exception {
		 SMSSendUtil.getInstance().sendSMS("15959285933", "【汇智库】您的验证码:[123456]");
	}	
}