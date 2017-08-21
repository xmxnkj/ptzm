package com.xmszit.futures.web.utils.pay.search;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmszit.futures.web.utils.pay.util.HttpClientUtil;

public class SingPayOrder {

	public void search() {
		String url = "http://api.fulaiyuanju.cn/order/querySettlement.do";

        String merchantNo = "PC00000001Q";
        String mackey = "09bc3c7b6cec3353484d7f2d2873c304";
        String orderNo = "1494138938941";

        String trxType = "1";

        StringBuilder sb = new StringBuilder();

        sb.append(merchantNo).append(orderNo).append(trxType).append(mackey);

        String sign1 = DigestUtils.md5Hex(sb.toString());

        StringBuilder request = new StringBuilder();
        request.append("p1_MerchantNo=").append(merchantNo);
        request.append("&p2_OrderNo=").append(orderNo);
        request.append("&trxType=").append(trxType);
        request.append("&sign=").append(sign1);


        String response = null;

        try {
            response = HttpClientUtil.sendPostHTTP(url, request.toString(), "utf-8");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        JSONObject jsonObject = JSON.parseObject(response);
        Map<String, String> map = new TreeMap<String, String>();

        map.put("merchantNo", jsonObject.getString("merchantNo"));
        map.put("resultCode", jsonObject.getString("resultCode"));
        map.put("resultStatus", jsonObject.getString("resultStatus"));
        map.put("resultDesc", jsonObject.getString("resultDesc")); 

        Set<String> set = map.keySet();


        StringBuilder responseSb = new StringBuilder();
        for (String key : set) {
        	responseSb.append(map.get(key));
        }
        String sign = DigestUtils.md5Hex(responseSb.toString() + mackey);
        System.out.println(responseSb.toString());
        System.out.println(sign);
        if(sign.equals(jsonObject.get("sign"))){
       	 	System.out.println(jsonObject.getString("resultStatus"));
        }
	}
}
