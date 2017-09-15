package com.xmxnkj.voip.web.utils.pay.alipay;
import java.io.IOException;

import com.xmxnkj.voip.web.utils.pay.util.HttpClientUtil;

/**
 * Created by user on 17-5-8.
 */
public class Alipay {

    public void alipay(){


        String url = "http://api.fulaiyuanju.cn/pay/alipay.do";
        String p6_NotifyUrl = "http://118.178.19.140:8091/testpay.jsp";

        String p3_amount = "1";
        String p4_Cur = "1";
        String p5_ProductName = "衣服";

        String tranType = "1";


        String orderNo = String.valueOf(System.currentTimeMillis());

        String merchantNo = "PC00000001Q";
        String mackey = "09bc3c7b6cec3353484d7f2d2873c304";
        String requestUrl = merchantNo + orderNo + p3_amount + p4_Cur + p5_ProductName + p6_NotifyUrl + tranType;

        String hmac = org.apache.commons.codec.digest.DigestUtils.md5Hex(requestUrl + mackey);

        StringBuilder sb = new StringBuilder();
        sb.append("p1_MerchantNo=" + merchantNo);
        sb.append("&p2_OrderNo=" + orderNo);
        sb.append("&p3_Amount=" + p3_amount);
        sb.append("&p4_Cur=" + p4_Cur);
        sb.append("&p5_ProductName=" + p5_ProductName);
        sb.append("&p6_NotifyUrl=" + p6_NotifyUrl);
        sb.append("&tranType=" + tranType);
        sb.append("&sign=");
        sb.append(hmac);

        System.out.println(sb.toString());

        String response = null;
        try {
            response = HttpClientUtil.sendPostHTTP(url, sb.toString(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(response);
    }
}