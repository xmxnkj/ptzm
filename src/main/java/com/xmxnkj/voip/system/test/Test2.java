package com.xmxnkj.voip.system.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.Test;

import com.xmxnkj.voip.web.models.ListJson;


public class Test2 {
	
	final static String status = "http://hq.sinajs.cn/list=";	//实时行情
	final static String fiveMin = "http://stock2.finance.sina.com.cn/voip/api/json.php/IndexService.getInnervoipMiniKLine5m?symbol=";	//行情
	final static String fifteenMin = "http://stock2.finance.sina.com.cn/voip/api/json.php/IndexService.getInnervoipMiniKLine15m?symbol=";	//行情
	final static String thirtyMin = "http://stock2.finance.sina.com.cn/voip/api/json.php/IndexService.getInnervoipMiniKLine30m?symbol=";	//行情
	final static String hourMin = "http://stock2.finance.sina.com.cn/voip/api/json.php/IndexService.getInnervoipMiniKLine60m?symbol=";	//行情

	//获取行情
	@Test
	public void getFutureMarket() throws MalformedURLException, IOException{
		
		InputStream is = new URL("http://hq.sinajs.cn/list=M0").openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      System.out.println(jsonText);
	    } finally {
	      is.close();
	    }
	}
	
	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
	 }
	 
	 public static void main(String[] args) throws IOException {
		 
			    URL url = new URL("http://hq.sinajs.cn/list=M0");  
			       HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			       conn.setConnectTimeout(5 * 1000);    
			       conn.setDoOutput(true);    
			       conn.setRequestMethod("GET");    
			       conn.setRequestProperty("Accept", "*/*");    
			       conn.setRequestProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");    
			       conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");    
			       conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");    
			       conn.setRequestProperty("Connection", "keep-alive");    
			       conn.setRequestProperty("Cookie", "JSESSIONID=XXXXXXXXXXXXXXXXXXXXX");    
			       conn.setRequestProperty("Host", "ptlogin2.qq.com");    
			       conn.setRequestProperty("Referer", "http://www.qq.com");    
			       conn.setRequestProperty("User-Agent",     
			               "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 Safari/537.31");    
			       conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");  
			       conn.connect();         //获取连接  
			       InputStream is = conn.getInputStream();  
			       BufferedReader buffer = new BufferedReader(new InputStreamReader(is));  
			       StringBuffer bs = new StringBuffer();  
			       String l = null;  
			       while((l=buffer.readLine())!=null){  
			           bs.append(l);  
			       }  
			       System.out.println(bs.toString());  
			}  
	}

