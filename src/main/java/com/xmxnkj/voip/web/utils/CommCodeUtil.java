package com.xmszit.futures.web.utils;

import java.util.Random;
import java.util.UUID;

import com.hsit.common.MD5Util;

//生成推荐码
public class CommCodeUtil {
	
	public static String createCommCode() {
		String code = MD5Util.MD5(UUID.randomUUID().toString());
		Random random = new Random();
        int start = random.nextInt(25);
        System.out.println(start);
		String result = code.substring(start, start+6);
		return result;
	}
}
