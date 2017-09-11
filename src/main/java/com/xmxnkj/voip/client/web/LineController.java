package com.xmxnkj.voip.client.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.Dept;
import com.xmxnkj.voip.client.entity.Line;
import com.xmxnkj.voip.client.entity.query.LineQuery;
import com.xmxnkj.voip.client.service.ClientUserService;
import com.xmxnkj.voip.client.service.DeptService;
import com.xmxnkj.voip.client.service.LineService;
import com.xmxnkj.voip.web.BaseController;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/client/line")
public class LineController extends BaseController<Line, LineQuery, Line>{
	
	@Autowired
	private LineService service;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private DeptService deptService;
	
	@Override
	public LineService getService() {
		return service;
	}
	
	//校验唯一性
	@RequestMapping("saveDate")
	@ResponseBody
	public Object saveDate(Line line,HttpServletRequest req){
		JSONObject.fromObject(req.getParameterMap());
		Map<String,Object> result = new HashMap<String,Object>();
		line.setClientId(getLoginClientId());
		line.setDeleted(false);
		try{
			if(StringUtils.isNotEmpty(line.getLingTel())){
				LineQuery lineQuery = new LineQuery();
				lineQuery.setLingTel(line.getLingTel());
				//lineQuery.setClientId(getLoginClientId());
				lineQuery.setDeleted(false);
				Line history = service.getEntity(lineQuery);//是否有同号码的线路
				
				ClientUser clientUser = clientUserService.getById(getLoginClientUserId());	//坐席
				
				Dept dept = clientUser.getDept();
				if(dept==null){
					result.put("success", false);
					result.put("errorMessage", "该远程部门不存在");
					return result;
				}else{
					
					if(StringUtils.isEmpty(dept.getCode())){
						deptService.randomCode(dept);
						deptService.save(dept);
					}
					line.setDeptId(dept.getId());
					line.setClientUserId(clientUser.getId());
				}
				
				if(StringUtils.isNotEmpty(line.getId())){	//编辑
					if(history!=null && !line.getId().equals(history.getId())){
						result.put("success", false);
						result.put("errorMessage", "该号码已存在！");
						return result;
					}
					
				}else{	//新增
					if(history!=null){
						/*result.put("success", false);
						result.put("errorMessage", "该号码已存在！");
						return result;*/
					}else{
						//查看编号最大值
						line.setNumber(service.getMaxNumberLine(line.getDeptId()));
						//生成部门编码
						line.setSerialNumber(dept.getCode()+"-"+createSerialNumber(line.getNumber(), 8));
					}
				}
				
				service.save(line);
				result.put("success", true);
				return result;
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("errorMessage", "数据异常！");
			return result;
		}
		return result;
	}
	
	/**
	 * 
	 * @param num		序号
	 * @param length	数字长度
	 * @return
	 */
	public static String createSerialNumber(Integer num,Integer length) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<length;i++){
			buffer.append("0");
		}
		buffer.append(num);
		//System.out.println(buffer.substring(buffer.length()-length,buffer.length()));
		return buffer.toString();
	}
}
