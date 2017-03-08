package com.xmszit.voip.client.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.voip.client.dao.DeptDao;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Dept;
import com.xmszit.voip.client.entity.en.IsOnSeatGroup;
import com.xmszit.voip.client.entity.query.ClientUserQuery;
import com.xmszit.voip.client.entity.query.DeptQuery;
import com.xmszit.voip.client.service.ClientUserService;
import com.xmszit.voip.client.service.DeptService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/client/dept")
public class DeptController  extends BaseController<Dept, DeptQuery, Dept>{
	
	@Autowired
	private DeptService service;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Override
	public DeptService getService() {
		return service;
	}
	
	@RequestMapping("getTreeDegrid")
	@ResponseBody
	public ListJson getTreeDegrid(DeptQuery query, Integer rows, Integer page){
		
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
			query.setClientId(getLoginClientId());
			query.setLevel(1);
			ListJson listJson = null;
			List<Dept> entities = getService().getEntities(query);//第一级列表
			if (entities!=null && entities.size()>0) {
				for (Dept dept : entities) {
					query = new DeptQuery();
					query.setDpid(dept.getId());//根据第一级ID查找其下的子节点数据
					Integer sonSize = (int) getService().getEntityCount(query);
					if (sonSize>0) {//若存在子节点state=‘close’
						dept.setState("closed");
						//operate.setChildren(firstChildren(list_s));//一次性载入
					}else {//若存在子节点state=‘open’
						dept.setState("open");
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
	
	@RequestMapping(value="/getDeptChildren")
	@ResponseBody
	public List<Dept> getDeptChildren(DeptQuery query){
		query.setDpid(query.getId());
		query.setId(null);
		query.setClientId(getLoginClientId());
		List<Dept> entities = getService().getEntities(query);
		for (Dept dept : entities) {
			query = new DeptQuery();
			query.setClientId(getLoginClientId());
			query.setDpid(dept.getId());
			List<Dept> list_s = getService().getEntities(query);
			if (list_s!=null&&list_s.size()>0) {
				dept.setState("closed");
			}else {
				dept.setState("open");
			}
		}
		return entities;
	}
	
	@RequestMapping(value="/saveDept")
	@ResponseBody
	public ListJson saveDept(Dept dept){
		dept.setClientId(getLoginClientId());
		ListJson listJson = getService().saveDept(dept);
		return listJson;
	}
	
	
	@RequestMapping(value="/deleteDept")
	@ResponseBody
	public ListJson deleteDept(String id){
		ListJson listJson = new ListJson();
		try{
			if(StringUtils.isNotEmpty(id)){
				Dept dept = getService().getById(id);
				
				//是否有成员
				ClientUserQuery clientUserQuery = new ClientUserQuery();
				clientUserQuery.setClientId(getLoginClientId());
				clientUserQuery.setDeleted(false);
				clientUserQuery.setDeptId(dept.getId());
				if(clientUserService.getEntityCount(clientUserQuery)>0){
					listJson.setSuccess(false);
					listJson.setMessage("不合法！");
					return listJson;
				}
				//
				if(getService().hasSonDept(dept.getId())){
					listJson.setSuccess(false);
					listJson.setMessage("已经存在！");
					return listJson;
				}
			}else{
				listJson.setSuccess(false);
				listJson.setMessage("不存在，请确认！");
			}
			
		}catch(Exception e){
			
		}
		return listJson;
	}
	
	//手机端查看部门 及相关信息
	/**
	 * 
	 * @param deptId	
	 * @return
	 */
	@RequestMapping("getDeptTreeAndSthMsg")
	@ResponseBody
	public Object getDeptTreeAndSthMsg(String deptId){
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			if(StringUtils.isNotEmpty(deptId)){
				Dept dept = service.getById(deptId);
				if(dept==null){
					result.put("success", false);
					result.put("message", "部门不存在！");
					return result;
				}
				
				result.put("id", dept.getId());				//id
				result.put("deptName", dept.getName());		//名称
				result.put("level", dept.getLevel());		//等级
				result.put("managers", dept.getClientUserNames());	//部门经理
				result.put("managerIds", dept.getClientUserId());		//部门经理id
				result.put("remarks", dept.getRemarks());		//备注
				//加载部门坐席
				getSeats(dept);
				
				List<Map<String,Object>> seats = new ArrayList<Map<String,Object>>();
				Map<String,Object> map = null;
				if(dept.getSeats()!=null){
					for(ClientUser cu:dept.getSeats()){
						map = new HashMap<String,Object>();
						map.put("parentId", dept.getId());
						map.put("parentName", dept.getName());
						map.put("seatId", cu.getId());
						map.put("seatName", cu.getName());
						map.put("id", cu.getId());
						map.put("phone", cu.getPhone());
						seats.add(map);
					}
					result.put("seats", seats);		//坐席
				}
				
				//子部门
				DeptQuery deptQuery = new DeptQuery();
				deptQuery.setDeleted(false);
				deptQuery.setDpid(dept.getId());
				deptQuery.setClientId(getLoginClientId());
				List<Dept> sonDepts = service.getEntities(deptQuery);
				
				List<Map<String,Object>> sonDept = new ArrayList<Map<String,Object>>();
				for(Dept d:sonDepts){
					map = new HashMap<String,Object>();
					map.put("parentId", dept.getId());
					map.put("parentName", dept.getName());
					map.put("parentRemarks", dept.getRemarks());
					map.put("sonId", d.getId());
					map.put("sonName", d.getName());
					map.put("sonRemarks", d.getRemarks());
					map.put("sonManagers", d.getClientUserNames());
					map.put("sonManagerIds", d.getClientUserId());
					map.put("level", d.getLevel());		//等级
					sonDept.add(map);
				}
				result.put("sonDepts", sonDept);	//子部门
				
				//上级
				if(dept.getLevel()>1){
					Dept deptFather = service.getById(dept.getDpid());
					//加载部门领导
					result.put("fatherDeptName", deptFather.getName());
					result.put("fatherDeptId", deptFather.getId());	//上级部门ID
					result.put("fatherDeptManagers", deptFather.getClientUserNames());	//上级部门领导
					result.put("fatherDeptRemarks", deptFather.getRemarks());	//上级部门备注
				}
				result.put("success", true);
			}
			
		}catch(Exception e){
			result.put("success", false);
			
		}
		return result;
	}
	
	//加载管理员
	public void getManagers(String clientUserIds,Dept dept){
		if(StringUtils.isNotEmpty(clientUserIds)){
			String[] ids = clientUserIds.split(",");
			ClientUser cu = null;
			List<ClientUser> list = new ArrayList<ClientUser>();
			for(String id:ids){
				cu = clientUserService.getEntityById(id);
				if(cu!=null){
					list.add(cu);
				}
			}
			dept.setManagers(list);
		}
	}
	//部门的坐席
	public void getSeats(Dept dept){
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setDeptId(dept.getId());
		clientUserQuery.setClientId(getLoginClientId());
		clientUserQuery.setDeleted(false);
		dept.setSeats(clientUserService.getEntities(clientUserQuery));
	}
}