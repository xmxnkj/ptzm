package com.xmszit.voip.client.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsit.common.exceptions.ApplicationException;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateQuery;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;
import com.xmszit.voip.client.service.OperateRoleService;
import com.xmszit.voip.client.service.OperateService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;
import com.xmszit.voip.web.models.ResultJson;

@Controller
@RequestMapping(value="client/operate")
public class OperateController extends BaseController<Operate, OperateQuery, Operate> {
    
	@Autowired 
	private OperateService service;
   
	@Override
   public OperateService getService(){
	   return service;
   }
	@Autowired 
	private OperateRoleService roleService;
	
	/**
	 * 远程权限
	 * @return
	 */
	
	@RequestMapping(value="/saveOperate")
	@ResponseBody
	public ResultJson saveOperate(Operate entity){
		if (getService()==null) {
			return new ResultJson(false);
		}
		if (entity==null) {
			return new ResultJson(false);
		}
		/*OperateQuery query = new OperateQuery();//验证重名
		query.setClientId(getLoginClientId());
		query.setText(entity.getText().replaceAll(" ", ""));
		boolean adding = StringUtils.isEmpty(entity.getId());
		initEntityForSave();
		if (adding) {
			initEntityForAdd();
		}else{
			query.setNotId(entity.getId());
			initEntityForEdit();
		}
		query.setPid(entity.getPid());
		List<Operate> list = getService().getEntities(query);
		if (list != null && list.size() > 0) {
			throw new ApplicationException("该分类品名已经存在，请重新确认！");
		}*/
		try{
			//entity.setClientId(getLoginClientId());
			entity.setId(getService().save(entity));
			ResultJson json = new ResultJson(true);
			json.setEntity(entity.getId());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return new ResultJson(e);
		}
	}
	/**
	 * 加载父节点
	 * @param query
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/operateList")
	@ResponseBody
	public ListJson operateList(OperateQuery query, Integer rows, Integer page){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			if (rows!=null && rows>0) {
				
				if (page==null) {
					page=1;
				}
				query.setPage(page, rows);
			}
			//query.setClientId(getLoginClientId());
			query.setDisplayOrder(1);
			ListJson listJson = null;
			if (!StringUtils.isEmpty(query.getPid())) {
				query.setPid(null);
			}
			query.setGrade(1);//最上级
			List<Operate> entities = getService().getEntities(query);//第一级列表
			if (entities!=null && entities.size()>0) {
				for (Operate operate : entities) {
					query.setPid(operate.getId());//根据第一级ID查找其下的子节点数据
					query.setGrade(null);
					List<Operate> list_s = getService().getEntities(query);
					if (list_s!=null&&list_s.size()>0) {//若存在子节点state=‘close’
						operate.setState("closed");
						//operate.setChildren(firstChildren(list_s));//一次性载入
					}else {//若存在子节点state=‘open’
						operate.setState("open");
					}
				}
			}
			listJson = new ListJson(entities);
			listJson.setPaging(query.getPaging());
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	/**
	 * 判断是否存在子节点
	 * @param query
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/operateListJudge")
	@ResponseBody
	public ListJson operateListJudge(OperateQuery query){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			//query.setClientId(getLoginClientId());
			ListJson listJson = null;
			List<Operate> entities = getService().getEntities(query);
			listJson = new ListJson(entities);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	/**
	 * 逐级加载子节点加载
	 * @param query
	 * @return
	 */
	@RequestMapping(value="/operateListChildren")
	@ResponseBody
	public List<Operate> operateListChildren(OperateQuery query){
		//query.setClientId(getLoginClientId());
		query.setPid(query.getId());
		query.setId(null);
		List<Operate> entities = getService().getEntities(query);
		for (Operate operate : entities) {
			query.setPid(operate.getId());
			List<Operate> list_s = getService().getEntities(query);
			if (list_s!=null&&list_s.size()>0) {
				operate.setState("closed");
			}else {
				operate.setState("open");
			}
		}
		return entities;
	}
	
	/**
	 * 全部载入，性能较差
	 * @param list
	 * @return
	 */
	public List<Operate> firstChildren(List<Operate> list){
		List<Operate> list_s = null;
		if (list!=null && list.size()>0) {
			for (Operate operate : list) {
				OperateQuery query = new OperateQuery();
				//query.setClientId(getLoginClientId());
				query.setPid(operate.getId());
				list_s = getService().getEntities(query);
				if (list_s!=null&&list_s.size()>0) {
					operate.setState("closed");
					//operate.setChildren(firstChildren(list_s));//一次性载入
				}else {
					operate.setState("open");
				}
			}
			return list_s;
		}else {
			return null;
		}
	}
	
	@RequestMapping(value="/operateTreeList")
	@ResponseBody
	public ListJson operateTreeList(OperateQuery query,String only){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			ListJson listJson = null;
			//query.setClientId(getLoginClientId());
			List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
			/*if (!StringUtils.isEmpty(only)) {
				List<Operate> entities = getService().getEntities(query);
				createComboTreeTree_All(entities,comboTreeList); 
			}else {
				query.setGrade(1);
				query.setClientMealId(getLoginClient().getClientMeal().getId());
				System.out.println(getLoginClient().getClientMeal().getId());
				List<Operate> entities = getService().getEntities(query);
				createComboTreeTree(entities, comboTreeList, query,getLoginClient().getClientMeal().getId());
			}*/
			if (!StringUtils.isEmpty(only)) {
				List<Operate> entities = getService().getEntities(query);
				createComboTreeTree_All(entities,comboTreeList); 
			}else {
				query.setGrade(1);
				//query.setClientMealId(getLoginClient().getClientMeal().getId());
				List<Operate> entities_1 = getService().getEntities(query);//一级权限
				query.setGrade(2);
				List<Operate> entities_2 = getService().getEntities(query);//二级权限
				query.setGrade(null);
				query.setClientMealId(null);
				query.setOnlyOther(true);
				List<Operate> entities_All = getService().getEntities(query);////二级以下权限
				createComboTreeTree(entities_1,entities_2,entities_All,comboTreeList);
			}
			listJson = new ListJson(comboTreeList);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	@RequestMapping(value="/systemOperateTreeList")
	@ResponseBody
	public ListJson systemOperateTreeList(OperateQuery query,String only){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			ListJson listJson = null;
			List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
			List<Operate> entities = getService().getEntities(query);
			createComboTreeTree_All(entities,comboTreeList); 
			listJson = new ListJson(comboTreeList);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	/**
	 * 一级一级查询
	 */
	private List<Operate> createComboTreeTree(List<Operate> entities_1
			,List<Operate> entities_2
			,List<Operate> entities_All
			,List<Map<String,Object>> comboTreeList) {
		for (int i = 0; i < entities_1.size(); i++) {  
	        Map<String, Object> map = null;  
	        Operate operate = (Operate) entities_1.get(i);  
            map = new HashMap<String, Object>();  
            //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text  
            //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性  
            map.put("id", operate.getId());         //id  
            map.put("text",operate.getText());//角色名  
            map.put("grade",operate.getGrade()); 
            map.put("children", createComboTreeChildren(entities_2,entities_All,operate.getId()));  
	        if (map != null)  
	            comboTreeList.add(map);  
	    }  
		return null;
	}
	private Object createComboTreeChildren(List<Operate> entities_2,List<Operate> entities_All,String pid) {
		// TODO Auto-generated method stub
		 List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
		 if (entities_2!=null) {
			 for (int j = 0; j < entities_2.size(); j++) {  
		        Map<String, Object> map = null;  
		        Operate treeChild = (Operate) entities_2.get(j);  
		        if (treeChild.getPid().equals(pid)) {
		        	if (!treeChild.getText().equals("权限设置")) {//不显示权限设置
		        		map = new HashMap<String, Object>();  
			            map.put("id", treeChild.getId());  
			            map.put("text", treeChild.getText());  
			            map.put("grade",treeChild.getGrade()); 
			            map.put("children", createComboTreeChildren(null,entities_All, treeChild.getId()));  
					}
		        }  
		        if (map != null)  
		            childList.add(map);  
		    }
		  }else {
			  for (int j = 0; j < entities_All.size(); j++) {  
			        Map<String, Object> map = null;  
			        Operate treeChild = (Operate) entities_All.get(j);  
			        if (treeChild.getPid().equals(pid)) {
			        	if (!treeChild.getText().equals("权限设置")) {//不显示权限设置
			        		map = new HashMap<String, Object>();  
				            map.put("id", treeChild.getId());  
				            map.put("text", treeChild.getText());  
				            map.put("grade",treeChild.getGrade()); 
				            map.put("children", createComboTreeChildren(null,entities_All, treeChild.getId()));  
						}
			        }  
			        if (map != null)  
			            childList.add(map);  
			    }
		  }
		  return childList;  
	}

	/**
	 * 查询所有在进行判断(一)
	 * @param entities
	 * @param string
	 * @param comboTreeList
	 * @return
	 */
	private List<Operate> createComboTreeTree_All(List<Operate> entities, List<Map<String,Object>> comboTreeList) {
		for (int i = 0; i < entities.size(); i++) {  
	        Map<String, Object> map = null;  
	        Operate operate = (Operate) entities.get(i);  
	        if (operate.getPid()==null||operate.getPid().equals("")) {  
	            map = new HashMap<String, Object>();  
	            //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text  
	            //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性  
	            map.put("id", entities.get(i).getId());         //id  
	            map.put("text",entities.get(i).getText());//角色名  
	            map.put("grade",entities.get(i).getGrade()); 
	            map.put("children", createComboTreeChildren_All(entities, operate.getId()));  
	        }  
	        if (map != null)  
	            comboTreeList.add(map);  
	    }  
		return null;
	}
	/**
	 * 查询所有在进行判断(二)
	 * @param entities
	 * @param pid
	 * @return
	 */
	private Object createComboTreeChildren_All(List<Operate> entities, String pid) {
		// TODO Auto-generated method stub
		 List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();  
		    for (int j = 0; j < entities.size(); j++) {  
		        Map<String, Object> map = null;  
		        Operate treeChild = (Operate) entities.get(j);  
		        if (treeChild.getPid()!=null&&treeChild.getPid().equals(pid)) {
		        	if (!treeChild.getText().equals("权限设置")) {//不显示权限设置
		        		map = new HashMap<String, Object>();  
			            map.put("id", entities.get(j).getId());  
			            map.put("text", entities.get(j).getText());  
			            map.put("grade",entities.get(j).getGrade()); 
			            map.put("children", createComboTreeChildren_All(entities, treeChild.getId()));  
					}
		        }  
		        if (map != null)  
		            childList.add(map);  
		    }  
		    return childList;  
	}
	
	@RequestMapping(value="/deleteOperate")
	@ResponseBody
	public ResultJson deleteOperate(String id) {
		if (getService()==null) {
			return new ResultJson(false);
		}
		try{
			OperateQuery query = new OperateQuery();
			query.setPid(id);
			List<Operate> opList = getService().getEntities(query);
			if (opList.size() > 0) {
				throw new ApplicationException("该权限下有子权限，无法删除！");
			}
			OperateRoleQuery operateRoleQuery = new OperateRoleQuery();
			operateRoleQuery.setOperateId(id);
			List<OperateRole> list = roleService.getEntities(operateRoleQuery);
			if (list.size() > 0) {
				throw new ApplicationException("该权限正在使用，无法删除！");
			}else {
				getService().deleteOperate(id);
			}
			return new ResultJson(true);
		}catch(Exception e){
			return new ResultJson(e);
		}
	}
	/**
	 * 小程序权限
	 * @param operateQuery
	 * @return
	 */
	@RequestMapping(value="/getAppOperate")
	@ResponseBody
	public ListJson getAppOperate(OperateQuery operateQuery){
		ListJson json  = null;
		if (operateQuery==null) {
			operateQuery = new OperateQuery();
		}
		//operateQuery.setClientId(getLoginClientId());
		operateQuery.setClientUserId(getLoginClientUserId());
		List<Operate> list = getService().getEntities(operateQuery);
		if (list!=null&&list.size()>0) {
			operateQuery.setPid(list.get(0).getId());
			operateQuery.setCode(null);
			list = getService().getEntities(operateQuery);
		}
		Boolean flag = true;
		for (Operate operate : list) {
			if (operate.getCode().equals("1001")) {
				flag = false;
				break;
			}
		}
		json = new ListJson(list);
		ClientUser clientUser = null;
		if (flag) {
			clientUser = getLoginClientUser();
			//json.setEntity(clientUser.getShop());
		}
		return json;
	}
}
