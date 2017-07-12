package com.xmszit.futures.web.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 基于线程池的httpclient
 */
public class HttpClientUtils {
    /**
     * 日志记录
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 字符编码,默认为UTF-8
     */
    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 连接池
     */
    private static PoolingHttpClientConnectionManager cm;

    /**
     * 重试处理器
     */
    private static HttpRequestRetryHandler httpRequestRetryHandler;

    /**
     * 超时时间
     */
    private static Integer timeout = 60000;

    /**
     * 重试次数
     */
    public static final int retryTime = 5; // 如果已经重试了5次，就放弃

    static {
        // 请求重试处理
        httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= retryTime) {// 超过最大重试次数
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 连接中断
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接超时
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 创建HttpClient实例对象
     */
    public static HttpClient createHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }

    /**
     * post请求
     *
     * @param httpClient
     * @param url        请求地址
     * @param params     请求参数
     * @param encoding   字符编码
     * @return 响应内容
     */
    public static String post(HttpClient httpClient, String url, Map<String, String> params, String encoding) {
        return post(httpClient, url, params, null, encoding);
    }

    /**
     * post请求
     *
     * @param httpClient
     * @param url        请求地址
     * @param params     请求参数
     * @param headers    请求头参数
     * @param encoding   字符编码
     * @return 响应内容
     */
    public static String post(HttpClient httpClient, String url, Map<String, String> params,
                              Map<String, String> headers, String encoding) {
        HttpPost method = new HttpPost(url);
        try {
            setEntity(params, encoding, method);
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("Post Param Encoding failed: " + e.getMessage());
            return "";
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }
        // 2、执行请求
        return send(httpClient, method, headers, encoding);
    }

    private static void setEntity(Map<String, String> params, String encoding, HttpEntityEnclosingRequestBase method)
            throws UnsupportedEncodingException {
        // 1、 请求参数设置
        if (params != null) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Iterator<String> it = params.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = null == params.get(key) ? "" : String.valueOf(params.get(key));
                nvps.add(new BasicNameValuePair(key, value));
            }
            method.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        }
    }

    /**
     * 执行请求
     *
     * @param httpClient
     * @param request    请求对象
     * @param headers    请求头参数
     * @param encoding   字符编码
     * @return 响应内容
     */
    public static String send(HttpClient httpClient, HttpRequestBase request, Map<String, String> headers,
                              String encoding) {
        // 1、设置请求头
        if (null == headers || headers.size() == 0) {
            request.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows xp)");
            request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
            request.setHeader("Content-type", "application/x-www-form-urlencoded;");
        } else {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }
            if (!headers.containsKey("User-Agent")) {
                request.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows xp)");
            }
            if (!headers.containsKey("Accept")) {
                request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            }
            if (!headers.containsKey("Accept-Language")) {
                request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
            }
        }
        // 2、配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        request.setConfig(requestConfig);
        // 3、响应处理
        try {
            HttpResponse response = httpClient.execute(request);
            // 记录非成功请求的的响应状态
            if (response.getStatusLine().getStatusCode() < HttpStatus.SC_OK
                    || response.getStatusLine().getStatusCode() >= HttpStatus.SC_MULTIPLE_CHOICES) {
                LOGGER.info("Method failed: " + response.getStatusLine());
                if (response.getStatusLine().getStatusCode() >= 400) {
                    return "";
                }
            }
            // 获取响应的字符编码
            HttpEntity entity = response.getEntity();
            if (entity.getContentType() != null) {
                String contentType = entity.getContentType().getValue();
                if (null != contentType && !contentType.trim().equals("")) {
                    int index = contentType.lastIndexOf("charset");
                    if (index > 0) {
                        encoding = contentType.substring(index + 8).trim();
                        encoding = encoding.endsWith(";") ? encoding.substring(0, encoding.length() - 1) : encoding;
                    }
                }
            } else {
                encoding = "ISO-8859-1";
            }
            return null != encoding && !encoding.trim().equals("")
                    ? new String(EntityUtils.toString(entity).getBytes(encoding), "UTF-8")
                    : EntityUtils.toString(entity);
        } catch (Exception e) {
            LOGGER.error("URL: \"" + request.getURI().toString() + "\" 获取HTML失败：" + e.getMessage());
            return "";
        } finally {
            request.releaseConnection();
        }
    }
}
