package com.xmszit.voip.client.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsit.common.exceptions.ApplicationException;
import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.UserRole;
import com.xmszit.voip.client.entity.query.ClientAndRoleQuery;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;
import com.xmszit.voip.client.entity.query.UserRoleQuery;
import com.xmszit.voip.client.service.ClientAndRoleService;
import com.xmszit.voip.client.service.OperateRoleService;
import com.xmszit.voip.client.service.UserRoleService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;
import com.xmszit.voip.web.models.ResultJson;

@Controller
@RequestMapping(value="client/userRole")
public class UserRoleController extends BaseController<UserRole, UserRoleQuery, UserRole> {
    
	@Autowired 
	private UserRoleService service;
   
	@Override
   public UserRoleService getService(){
	   return service;
   }
	@Autowired 
	private OperateRoleService orService;
	@Autowired 
	private ClientAndRoleService clientAndRoleService;
	
	@RequestMapping(value="/rolelist")
	@ResponseBody
	public ListJson rolelist(UserRoleQuery query, Integer rows, Integer page){
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
			List<UserRole> entities = getService().getEntities(query);
			for (UserRole userRole : entities) {
				StringBuffer show = new StringBuffer();
				String roleId = userRole.getId();
				OperateRoleQuery query2 = new OperateRoleQuery();
				query2.setUserRoleId(roleId);
				List<OperateRole> operateRole = orService.getEntities(query2);
				for (OperateRole operateRole2 : operateRole) {
					String showName = operateRole2.getOperate().getText();
					show.append("|"+showName+"| ");
				}
				userRole.setShow(show.toString());
			}
			ListJson listJson = null;
			listJson = new ListJson(entities);
			listJson.setPaging(query.getPaging());
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	@RequestMapping(value="/roleShow")
	@ResponseBody
	public ListJson roleShow(UserRoleQuery query, Integer rows, Integer page){
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
			List<UserRole> entities = getService().getEntities(query);
			ListJson listJson = null;
			listJson = new ListJson(entities);
			listJson.setPaging(query.getPaging());
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	@RequestMapping(value="/saveRole")
	@ResponseBody
	public ResultJson saveRole(UserRole entity){
		if (getService()==null) {
			return new ResultJson(false);
		}
		if (entity==null) {
			return new ResultJson(false);
		}
		ResultJson resultJson = validateForm();
		if (resultJson!=null) {
			return resultJson;
		}
		UserRoleQuery query = new UserRoleQuery();
		query.setClientId(getLoginClientId());
		query.setRoleName(entity.getRoleName().replaceAll(" ", ""));
		boolean adding = StringUtils.isEmpty(entity.getId());
		initEntityForSave(entity);
		if (adding) {
			//initEntityForAdd();
		}else{
			query.setNotId(entity.getId());
			//initEntityForEdit();
		}
		List<UserRole> listRole = getService().getEntities(query);
		if (listRole != null && listRole.size() > 0) {
			throw new ApplicationException("名已经存在，请重新确认！");
		}
		try{
			entity.setClientId(getLoginClientId());
			
			entity.setId(getService().save(entity));
			String roleId = entity.getId();
			List<OperateRole> list = entity.getOperateRoles();
			orService.deleteOperateRole(roleId);//删除权限
			if (list != null) {
				for (OperateRole operateRole : list) {//保存权限
					operateRole.setUserRole(entity);
					operateRole.setClientId(getLoginClientId());
					orService.save(operateRole);
				}
			}
			afterEntitySaved();
			ResultJson json = new ResultJson(true);
			json.setEntity(entity.getId());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return new ResultJson(e);
		}
	}
	@RequestMapping(value="/deleteUserRole")
	@ResponseBody
	public ResultJson deleteUserRole(String id) {
		if (getService()==null) {
			return new ResultJson(false);
		}
		try{
			ClientAndRoleQuery query = new ClientAndRoleQuery();
			query.setUserRoleId(id);
			if (clientAndRoleService.getEntities(query).size() > 0) {
				throw new ApplicationException("正被使用！");
			}
			orService.deleteOperateRole(id);
			getService().deleteRole(id);
			return new ResultJson(true);
		}catch(Exception e){
			return new ResultJson(e);
		}
	}
}