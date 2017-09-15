package com.xmxnkj.voip.web.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.utils.YB.DigestUtil;
import com.xmxnkj.voip.web.utils.YB.YeepayService;
import com.xmxnkj.lightning.system.entity.Client;
import com.xmxnkj.lightning.system.entity.query.ClientQuery;
import com.xmxnkj.lightning.system.service.ClientService;

@Controller
@RequestMapping("/web/payController")
public class PayController  extends BaseController<Client, ClientQuery> {
	
	@Autowired
	private ClientService service;

	@Override
	public ClientService getService() {
		return service;
	}
	
	//支付地址
	final static String REQPAY = "https://www.yeepay.com/app-merchant-proxy/node";
	//商户密钥
	final static String _KEY = "mf945Ybf01mz174K53Zo201H574oJgP7H2T6Yp9JsdVxoS9a8QH3Ht3i6V6S";
	//商户编号
	final static String NUMBER = "10015674212";
	
	@RequestMapping("payReq")
	public String payReq() throws UnsupportedEncodingException{
		
		String orderNo = (System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
		String p0_Cmd = "Buy";
		String price = "0.01";
		String p4_Cur = "CNY";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("p0_Cmd", p0_Cmd);
		params.put("p1_MerId", NUMBER);
		params.put("p2_Order", System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
		params.put("p3_Amt", price);
		params.put("p4_Cur", p4_Cur);
		String resultUrl = YeepayService.getPayURL(params);
		System.out.println(resultUrl);
		return "redirect:"+resultUrl;
	}
	
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("p0_Cmd", "Buy");
		params.put("p1_MerId", NUMBER);
		params.put("p2_Order", System.currentTimeMillis()+""+(new Random().nextInt(100)+1));
		params.put("p3_Amt", "0.01");
		params.put("p4_Cur", "CNY");
		System.out.println(YeepayService.getPayURL(params));
	}
}