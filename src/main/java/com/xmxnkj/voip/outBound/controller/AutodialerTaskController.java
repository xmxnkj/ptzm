package com.xmxnkj.voip.outBound.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmxnkj.voip.customer.service.CallPlanService;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;
import com.xmxnkj.voip.outBound.entity.query.AutodialerTaskQuery;
import com.xmxnkj.voip.outBound.service.AutodialerTaskService;
import com.xmxnkj.voip.web.BaseController;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="outBound/autodialerTask")
public class AutodialerTaskController extends BaseController<AutodialerTask, AutodialerTaskQuery, AutodialerTask>{
	
	@Autowired 
	private AutodialerTaskService service;
	
	@Override
    public AutodialerTaskService getService(){
	   return service;
    }
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询动态表
	 */
	@RequestMapping("getNumberList")
	@ResponseBody
	public JSONObject getNumberList(String id,HttpServletRequest req){
		JSONObject json = new JSONObject();
		List<Map<String, Object>>  list = new ArrayList<Map<String, Object>>();;
		if(StringUtils.isNotEmpty(id)){
			AutodialerTaskQuery autodialerTaskQuery = new AutodialerTaskQuery();
			autodialerTaskQuery.setCallPlanId(id);
			AutodialerTask autodialerTask = getService().getEntity(autodialerTaskQuery);
			if(autodialerTask!=null){
				if(getService().isHasnTable("autodialer_number_"+autodialerTask.getUuid())){
					list = jdbcTemplate.queryForList("select IFNULL(state,'') as state,IFNULL(number,'') as number,IFNULL(calldate,'') as calldate,IFNULL(answerdate,'') as answerdate,IFNULL(hangupdate,'') as hangupdate,IFNULL(hangupcause,'') as hangupcause,IFNULL(recordfile,'') as recordfile from autodialer_number_"+autodialerTask.getUuid());
				}
			}
		}
		String realPath=req.getServletContext().getRealPath("/");
		for(Map<String, Object> map:list){
			if(StringUtils.isNotEmpty(map.get("recordfile").toString())){
				map.put("recordfile",map.get("recordfile").toString().replace(realPath, ""));
			}
		}
		json.put("rows", list);
		return json;
	}
	
	@RequestMapping("delNumber")
	@ResponseBody
	public JSONObject delNumber(String id,HttpServletRequest request){
		String[] numbers = request.getParameterMap().get("numbers[]");
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(id)){
			//任务
			AutodialerTaskQuery autodialerTaskQuery = new AutodialerTaskQuery();
			autodialerTaskQuery.setCallPlanId(id);
			AutodialerTask autodialerTask = getService().getEntity(autodialerTaskQuery);
			if(autodialerTask!=null){
				for(String number:numbers){
					jdbcTemplate.update("delete from autodialer_number_"+autodialerTask.getUuid()+" where number = ?" ,new Object[]{number});
				}
			}
		}
		return json;
	}
}
