package com.xmxnkj.voip.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 期货行情
 * @author Administrator
 *
 */
public class TimeUtil {
	
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * @param nextDay	几天前
	 * @return
	 */
	public static String getTime(int nextDay){
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(new Date());  
        calendar.add(Calendar.DAY_OF_MONTH, -nextDay);
        String dateStr = sdf.format(calendar.getTime());
        return dateStr;
	}
}
