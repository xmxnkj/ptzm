package com.xmszit.futures.web.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求校验工具类
 */
public class SignKit {
	
    static Logger logger = LoggerFactory.getLogger("business");

    /**
     * 签名字符串
     *
     * @param map 需要签名的数组
     * @return 签名结果
     */
    public static String sign(String appId, String appSecrect, String timestamp, Map<String, Object> map) {
        String sign = "";
        try {
            // 除去数组中的空值和签名参数
            Map<String, Object> signMap = paraFilter(map);
//            System.out.println("appId:"+appId);
//            System.out.println("appSecrect:"+appSecrect);
//            System.out.println("timestamp:"+timestamp);
            // 把数组所有元素排序
            String sortStr = createLinkString(signMap);
            //System.out.println(sortStr);
            String signStr = appId + appSecrect + sortStr + timestamp;
            //System.out.println(signStr);
            // 签名
            sign = EncoderMd5(signStr);
            //System.out.println(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回签名
        return sign;
    }


    public static String sign(String key, Map<String, Object> map) {
        String sign = "";
        try {
            // 除去数组中的空值和签名参数
            Map<String, Object> signMap = paraFilter(map);
            // 把数组所有元素排序
            String sortStr = createLinkString(signMap);
            String signStr = sortStr + "&" + key;
            // 签名
            System.out.println("加密字符串："+signStr);
            logger.info("加密字符串："+signStr);

            //sign = EncoderMd5(signStr);
            sign = DigestUtils.md5Hex(signStr.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回签名
        return sign;
    }


    /**
     * 签名是否有效验证
     *
     * @param appid
     * @param appSecrect
     * @param timestamp
     * @param sign
     * @param paras
     * @return
     */
    public static boolean checkSign(String appid, String appSecrect, String timestamp, String sign, Map<String, Object> paras) {
        String signV = SignKit.sign(appid, appSecrect, timestamp, paras);
        return signV.equals(sign);
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    /*private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }*/

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, Object> paraFilter(Map<String, Object> sArray) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (sArray == null || sArray.size() <= 0)
            return result;
        for (String key : sArray.keySet()) {
            if (sArray.get(key) != null) {
                String value = (String) sArray.get(key);
                if ("".equals(value) || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
                    continue;
                }
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, Object> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = (String) params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 利用MD5进行加密
     */
    public static String EncoderMd5(String plainText) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try {
            md.update(plainText.getBytes("UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer(200);
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset] & 0xff;
            if (i < 16) buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }
}