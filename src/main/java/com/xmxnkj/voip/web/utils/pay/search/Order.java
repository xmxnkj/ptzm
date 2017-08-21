package com.xmszit.futures.web.utils.pay.search;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmszit.futures.web.utils.pay.util.HttpClientUtil;

public class Order {

	public void orderSearch() {
		
		String url = "http://api.fulaiyuanju.cn//order/queryOrder.do";

        String merchantNo = "PC00000001Q";
        String mackey = "09bc3c7b6cec3353484d7f2d2873c304";
        String p2_OrderNo="1494136550735";
        String hmac = org.apache.commons.codec.digest.DigestUtils.md5Hex(merchantNo + p2_OrderNo + mackey);

        StringBuilder sb = new StringBuilder();
        sb.append("&p1_MerchantNo=" + merchantNo);
        sb.append("&p2_OrderNo=" + p2_OrderNo);
        sb.append("&sign=");
        sb.append(hmac);


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
        map.put("r4_ProductName", jsonObject.getString("r4_ProductName"));
        map.put("r5_TrxNo", jsonObject.getString("r5_TrxNo"));
        map.put("ra_Status", jsonObject.getString("ra_Status"));
        map.put("rb_Code", jsonObject.getString("rb_Code"));
        map.put("rc_CodeMsg", jsonObject.getString("rc_CodeMsg"));

        Set<String> set = map.keySet();


        StringBuilder responseSb = new StringBuilder();
        for (String key : set) {
        	responseSb.append(map.get(key));
        }
        String sign = DigestUtils.md5Hex(responseSb.toString() + mackey);
        
        if(sign.equals(jsonObject.get("sign"))){
       	 	System.out.println(jsonObject.getString("ra_Status"));
        }
	}
}
