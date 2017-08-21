package com.xmszit.futures.web.utils.pay.singpay;

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
public class SingPay {
	public void singPayTest() {
        String url = "http://api.fulaiyuanju.cn/settle/systemSettle.do";
        String notify_url = "http://118.178.19.140:8091/testpay.jsp";


        String merchantNo = "PC00000001Q";
        String mackey = "09bc3c7b6cec3353484d7f2d2873c304";


        String orderNo = System.currentTimeMillis()+"";
        String p3_amount = "1";
        String trxType = "1";//1.weixin 3.alipay
        String acctType = "2";
        String accName = "��Ұ";
        String acctNo = "6217000010101191735";
        String bankName = "123123";
        String bankSettNo = "123123";
        String bankCode = "CMBCHINA";
        String province = "";
        String city = "";
        String cnapsName = "123123";
        String Nbr = "13811960447";
        String certificateCode = "230106198602011712";

        StringBuilder hsb = new StringBuilder()
                .append(merchantNo).append(orderNo).append(p3_amount)
                .append(trxType).append(acctType).append(accName).append(acctNo).append(bankName).append(bankSettNo).append(bankCode).append(province).append(city).append(cnapsName).append(Nbr).append(certificateCode).append(notify_url).append(mackey);

        String rhmac = DigestUtils.md5Hex(hsb.toString());


        StringBuilder sb = new StringBuilder();
        sb.append("merchantNo=" + merchantNo);
        sb.append("&orderNo=" + orderNo);
        sb.append("&amount=" + p3_amount);
        sb.append("&trxType=" + trxType);
        sb.append("&acctType=" + acctType);
        sb.append("&acctName=" + accName);
        sb.append("&acctNo=" + acctNo);
        sb.append("&bankName=" + bankName);
        sb.append("&bankSettNo=" + bankSettNo);
        sb.append("&bankCode=" + bankCode);
        sb.append("&province=" + province);
        sb.append("&city=" + city);
        sb.append("&cnapsName=" + cnapsName);
        sb.append("&mobile=" + Nbr);
        sb.append("&certificateCode=" + certificateCode);
        sb.append("&retUrl=" + notify_url);
        sb.append("&sign=");
        sb.append(rhmac);

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
        map.put("trxType", jsonObject.getString("trxType"));
        map.put("ra_Status", jsonObject.getString("ra_Status")); 
        if(null !=jsonObject.getString("rb_Code")&&!"".equals(jsonObject.getString("rb_Code"))){
        	
        	map.put("rb_Code", jsonObject.getString("rb_Code"));
        }
        map.put("rc_CodeMsg", jsonObject.getString("rc_CodeMsg"));

        Set<String> set = map.keySet();


        StringBuilder responseSb = new StringBuilder();
        for (String key : set) {
        	responseSb.append(map.get(key));
        }
        String sign = DigestUtils.md5Hex(responseSb.toString() + mackey);
        System.out.println(responseSb.toString());
        if(sign.equals(jsonObject.get("sign"))){
       	 	System.out.println("ok");
        }

    }
}
