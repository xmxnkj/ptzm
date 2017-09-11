package com.xmxnkj.voip.web.system.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.MD5Util;
import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.uac.entity.Ace;
import com.hsit.common.uac.entity.Operation;
import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.entity.UserState;
import com.hsit.common.uac.entity.queryparam.AceQuery;
import com.hsit.common.uac.entity.queryparam.OperationQuery;
import com.hsit.common.uac.entity.queryparam.UserQuery;
import com.hsit.common.uac.service.AceService;
import com.hsit.common.uac.service.OperationService;
import com.hsit.common.uac.service.UserService;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.client.entity.OperateRole;
import com.xmxnkj.voip.client.entity.query.OperateQuery;
import com.xmxnkj.voip.client.entity.query.OperateRoleQuery;
import com.xmxnkj.voip.system.web.model.AceList;
import com.xmxnkj.voip.web.SystemBaseController;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.models.ResultJson;


/**
 * @ProjectName:voip
 * @ClassName: OperationController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/operations")
public class OperationsController extends SystemBaseController<Operation, OperationQuery, Operation> {
	@Autowired
	private OperationService service;

	@Override
	public OperationService getService() {
		return service;
	}
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/operationTreeList")
	@ResponseBody
	public ListJson operationTreeList(OperationQuery query,String adminId){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			ListJson listJson = null;
			//query.setClientId(getLoginClientId());
			List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
			List<Operation> entities = getService().getEntities(query);
			Boolean isAdmin = false;
			if (!StringUtils.isEmpty(adminId)) {
				User user = userService.getById(adminId);
				if (user!=null&&!StringUtils.isEmpty(user.getPicId())
						&&user.getPicId().equals("1")) {
					isAdmin = true;
				}
			}
			createComboTreeTree_All(entities, comboTreeList,isAdmin);
			listJson = new ListJson(comboTreeList);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	@Autowired
	private AceService aceService; 
	/**
	 * 获取远程控制
	 * @param query
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/operationAceList")
	@ResponseBody
	public ListJson operationAceList(AceQuery query){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			List<Ace> entities = aceService.getEntities(query);
			
			ListJson listJson = null;
			listJson = new ListJson(entities);
			
			return listJson;
			
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	/**
	 * 查询所有在进行判断(一)
	 * @param entities
	 * @param string
	 * @param comboTreeList
	 * @return
	 */
	private List<Operate> createComboTreeTree_All(List<Operation> entities, List<Map<String,Object>> comboTreeList,Boolean isAdmin) {
		for (int i = 0; i < entities.size(); i++) {  
	        Map<String, Object> map = null;  
	        Operation operation = (Operation) entities.get(i);  
	        if (operation.getCategoryId().equals("0")) {
	            map = new HashMap<String, Object>();  
	            //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text  
	            //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性  
	            map.put("id", operation.getId());         //id  
	            map.put("text",operation.getName());//角色名  
	            map.put("grade",operation.getDescription()); 
	            map.put("children", createComboTreeChildren_All(entities, operation.getId(),isAdmin));  
	        }  
	        if (map != null && (isAdmin || (!isAdmin && !operation.getName().equals("收款设置")))){
	        	comboTreeList.add(map);  
	        }  
	    }  
		return null;
	}
	/**
	 * 查询所有在进行判断(二)
	 * @param entities
	 * @param pid
	 * @return
	 */
	private Object createComboTreeChildren_All(List<Operation> entities, String pid,Boolean isAdmin) {
		// TODO Auto-generated method stub
		 List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();  
		    for (int j = 0; j < entities.size(); j++) {  
		        Map<String, Object> map = null;  
		        Operation treeChild = (Operation) entities.get(j);  
		        if (treeChild.getCategoryId().equals(pid)) {
	        		map = new HashMap<String, Object>();  
		            map.put("id", treeChild.getId());  
		            map.put("text", treeChild.getName());  
		            map.put("grade",treeChild.getDescription()); 
		            map.put("children", createComboTreeChildren_All(entities, treeChild.getId(),isAdmin));  
		        }  
		        if (map != null && (isAdmin || (!isAdmin && !treeChild.getName().equals("远程设置")))){
		        		childList.add(map);
		        }  
		    }  
		    return childList;  
	}
}
