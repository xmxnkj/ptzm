package com.xmszit.voip.client.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.voip.client.entity.OperateRole;
import com.xmszit.voip.client.entity.query.OperateRoleQuery;
import com.xmszit.voip.client.service.OperateRoleService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;

@Controller
@RequestMapping(value="client/operateRole")
public class OperateRoleController extends BaseController<OperateRole, OperateRoleQuery, OperateRole> {
    
	@Autowired 
	private OperateRoleService service;
   
	@Override
   public OperateRoleService getService(){
	   return service;
   }
	@RequestMapping(value="/operateRoleList")
	@ResponseBody
	public ListJson list(OperateRoleQuery query, Integer rows, Integer page){
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
			
			List<OperateRole> entities = getService().getEntities(query);
			
			ListJson listJson = null;
			listJson = new ListJson(entities);
			
			listJson.setPaging(query.getPaging());
			return listJson;
			
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	
}
