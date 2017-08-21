package com.xmszit.futures.web.utils.pay.weixin;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmszit.futures.web.utils.pay.util.HttpClientUtil;

/**
 * Created by Administrator on 2017-5-7.
 */
public class Weixin {

	public void weixinTest() {
        String url = "http://api.fulaiyuanju.cn/weixin/api.do";//����
        String p6_NotifyUrl = "http://118.178.19.140:8091/testpay.jsp";

        String p3_amount = "1";
        String p4_Cur = "1";
        String p5_ProductName = "�·�";



        String orderNo = String.valueOf(System.currentTimeMillis());

        String merchantNo = "PC00000001Q";
        String mackey = "09bc3c7b6cec3353484d7f2d2873c304";

        String requestUrl = merchantNo + orderNo + p3_amount + p4_Cur + p5_ProductName + p6_NotifyUrl;

        String hmac = org.apache.commons.codec.digest.DigestUtils.md5Hex(requestUrl + mackey);

        StringBuilder sb = new StringBuilder();
        sb.append("p1_MerchantNo=" + merchantNo);
        sb.append("&p2_OrderNo=" + orderNo);
        sb.append("&p3_Amount=" + p3_amount);
        sb.append("&p4_Cur=" + p4_Cur);
        sb.append("&p5_ProductName=" + p5_ProductName);
        sb.append("&p6_NotifyUrl=" + p6_NotifyUrl);
        sb.append("&sign=");
        sb.append(hmac);

        System.out.println(sb.toString());

        String response = null;
        try {
            response = HttpClientUtil.sendPostHTTP(url, sb.toString(), "utf-8");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        JSONObject jsonObject = JSON.parseObject(response);
        Map<String, String> map = new TreeMap<String, String>();

        map.put("r1_MerchantNo", jsonObject.getString("r1_MerchantNo"));
        map.put("r2_OrderNo", jsonObject.getString("r2_OrderNo"));
        map.put("r3_Amount", jsonObject.getString("r3_Amount"));
        map.put("ra_Status", jsonObject.getString("ra_Status"));
        map.put("ra_Code", jsonObject.getString("ra_Code"));
        map.put("rc_CodeMsg", jsonObject.getString("rc_CodeMsg"));

        Set<String> set = map.keySet();


        StringBuilder responseSb = new StringBuilder();
        for (String key : set) {
        	responseSb.append(map.get(key));
        }
        String sign = DigestUtils.md5Hex(responseSb.toString() + mackey);
        if(sign.equals(jsonObject.get("sign"))){
       	 	System.out.println(jsonObject.getString("ra_Code"));
        }

    }
}
