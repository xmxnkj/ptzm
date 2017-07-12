package com.xmszit.futures.web.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

//订单生成
public class OrderNoUtil {
	
	public static Integer seq = 0;
	
	public static synchronized String getUniqueNumber(JdbcTemplate jdbcTemplate){
		
		int number = jdbcTemplate.queryForInt("select seq from OrderSeq where id='1'");
		
		jdbcTemplate.execute("update OrderSeq set seq=seq+1 where id='1'");
		
		String orderNumber = "000000000000"+number;
		
		if(orderNumber.length()>12){
			orderNumber = orderNumber.substring(orderNumber.length()-12 ,orderNumber.length());
		}
		
		return orderNumber;
	}
}
