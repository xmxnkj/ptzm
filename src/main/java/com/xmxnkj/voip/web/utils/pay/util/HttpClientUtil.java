package com.xmszit.futures.web.utils.pay.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil {

    private static HttpClient client = null;
    private static final int HTTP_SO_TIMEOUT = 20 * 1000; //连接超时时间，1分钟
    private static final int HTTP_DATA_TIMEOUT = 20 * 1000; //数据读取超时时间，1分钟

    static {
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setSoTimeout(HTTP_DATA_TIMEOUT);
        client.getHttpConnectionManager().getParams().setConnectionTimeout(HTTP_SO_TIMEOUT);
    }

    /**
     * 以get方式发送请求，并得到响应
     *
     * @param url
     * @param params
     * @return
     */
    public static String getResponseByGet(String url, NameValuePair[] params) {
        GetMethod method = new GetMethod(url);
        if (params != null)
            method.setQueryString(params);
        BufferedInputStream is = null;
        BufferedReader br = null;
        String rel = "";
        try {
            client.executeMethod(method);
            is = new BufferedInputStream(method.getResponseBodyAsStream());
            br = new BufferedReader(new InputStreamReader(is, "GBK"));
            StringBuffer result = new StringBuffer();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                result.append(temp);
                result.append("\n");
            }
            rel = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.abort();
            method.releaseConnection();
            method = null;
            try {
                if (null != br) {
                    br.close();
                    br = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rel;
    }

    /**
     * 以get方式发送请求，并得到响应
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String getResponseByGet(String url, NameValuePair[] params, String charset) {
        GetMethod method = new GetMethod(url);
        if (params != null)
            method.setQueryString(params);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");//
        BufferedInputStream is = null;
        BufferedReader br = null;
        String rel = "";
        try {
            client.executeMethod(method);
            is = new BufferedInputStream(method.getResponseBodyAsStream());
            br = new BufferedReader(new InputStreamReader(is, charset));
            StringBuffer result = new StringBuffer();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                result.append(temp);
                result.append("\n");
            }
            rel = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.abort();
            method.releaseConnection();
            method = null;
            try {
                if (null != br) {
                    br.close();
                    br = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rel;
    }

    /**
     * 以post方式发送请求，并得到响应
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String getResponseByPost(String url, NameValuePair[] params, String charset) {
        PostMethod method = new PostMethod(url);
        if (params != null)
            method.setQueryString(params);
        method.setRequestBody(params);
        BufferedInputStream is = null;
        BufferedReader br = null;
        String rel = "";
        try {
            client.executeMethod(method);
            is = new BufferedInputStream(method.getResponseBodyAsStream());
            br = new BufferedReader(new InputStreamReader(is, charset));
            StringBuffer result = new StringBuffer();
            String temp = null;
            while ((temp = br.readLine()) != null) {
                result.append(temp);
                result.append("\n");
            }
            rel = result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.abort();
            method.releaseConnection();
            method = null;
            try {
                if (null != br) {
                    br.close();
                    br = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rel;
    }

    /**
     * 发送post请求Http报文，并获得响应
     *
     * @param url     url地址
     * @param content 发送内容
     * @param charset 编码
     * @return
     */
    public static String sendPostHTTP(String url, String content, String charset) throws IOException {
        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);

        StringBuffer responseXml = new StringBuffer("");
        BufferedReader br = null;
        InputStream is = null;
        try {
            System.out.println("+++++++++++send post http ==========>>>>> url=" + url + "; postContent=" + content);
            method.setRequestEntity(new StringRequestEntity(content, "application/x-www-form-urlencoded", charset));
            client.executeMethod(method);
            is = method.getResponseBodyAsStream();
            br = new BufferedReader(new InputStreamReader(is, charset));
            String line = br.readLine();
            while (line != null) {
                responseXml.append(line);
                line = br.readLine();
            }
            System.out.println("+++++++++++response message <<<<<============:" + responseXml.toString());
        } catch (UnsupportedEncodingException e) {
            //字符编码转化失败
           System.out.println("Error:resolving http response by charset " + charset);
            e.printStackTrace();
        } catch (Exception e) {
           System.out.println("++++++++++++++send post message error");
            e.printStackTrace();
            throw new IOException();
        } finally {
            method.abort();
            method.releaseConnection();
            method = null;
            try {
                if (null != br) {
                    br.close();
                    br = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return responseXml.toString();
    }

    /**
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public static String getResponseByPostMethod(String url, NameValuePair[] params, String charset) {
        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        method.setRequestBody(params);
        String rel = "";
        try {
            client.executeMethod(method);
            rel = new String(method.getResponseBodyAsString().getBytes(charset), charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.abort();
            method.releaseConnection();
            method = null;
        }
        return rel;
    }


    public static String MD5(String str) {
        return DigestUtils.md5Hex(str);
    }




}
