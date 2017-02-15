package com.xmszit.voip.outBound.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.voip.outBound.entity.AutodialerTask;
import com.xmszit.voip.outBound.entity.query.AutodialerTaskQuery;
import com.xmszit.voip.outBound.service.AutodialerTaskService;
import com.xmszit.voip.web.BaseController;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 测试	autodialer_number_1 表
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="outBound/autodialerNumber")
public class AutodialerNumberController extends BaseController<AutodialerTask, AutodialerTaskQuery, AutodialerTask>{
	
	@Autowired 
	private AutodialerTaskService service;
    
	@Override
    public AutodialerTaskService getService(){
	   return service;
    }
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//查询autodialer_number_1表的数据
	@RequestMapping("getNumbers")
	@ResponseBody
	public JSONObject getNumbers(){
		JSONObject json = new JSONObject();
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from autodialer_number_1");
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		for(Map<String, Object> map:list){
			obj = new JSONObject();
			obj.put("number", map.get("number"));
			array.add(obj);
		}
		json.put("rows", array);
		return json;
	}
	
	@RequestMapping("testImport")
	@ResponseBody
	public JSONObject testImport(String numbers){
		JSONObject json = new JSONObject();
		for(String str:numbers.split(";")){
			if(StringUtils.isNotEmpty(str)){
				jdbcTemplate.update("insert into autodialer_number_1(number) values(?)",new Object[]{str});
			}
		}
		return json;
	}
	
	@RequestMapping("delTest")
	@ResponseBody
	public JSONObject delTest(){
		JSONObject json = new JSONObject();
		jdbcTemplate.update("DELETE from autodialer_number_1");
		return json;
	}
}