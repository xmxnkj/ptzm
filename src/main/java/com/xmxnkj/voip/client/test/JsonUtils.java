package com.xmxnkj.voip.client.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {
	

	public static void toJsonString(Object obj){
		if(obj.getClass().toString().contains("List")){
			System.out.println(JSONArray.fromObject(obj));
		}else{
			System.out.println(JSONObject.fromObject(obj));
		}
	}
	
}
