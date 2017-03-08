package com.xmszit.voip.client.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.voip.client.entity.ClientAndRole;
import com.xmszit.voip.client.entity.query.ClientAndRoleQuery;
import com.xmszit.voip.client.entity.query.UserRoleQuery;
import com.xmszit.voip.client.service.ClientAndRoleService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;

@Controller
@RequestMapping(value="client/clientAndRole")
public class ClientAndRoleController extends BaseController<ClientAndRole, ClientAndRoleQuery, ClientAndRole> {
    
	@Autowired 
	private ClientAndRoleService service;
    
	@Override
    public ClientAndRoleService getService(){
	   return service;
    }
	
	@RequestMapping(value="/clientRolelist")
	@ResponseBody
	public ListJson list(ClientAndRoleQuery query, Integer rows, Integer page){
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
			
			List<ClientAndRole> entities = getService().getEntities(query);
			
			ListJson listJson = null;
			listJson = new ListJson(entities);
			
			listJson.setPaging(query.getPaging());
			return listJson;
			
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	@RequestMapping(value="/getAmaldar")
	@ResponseBody
	public ListJson getAmaldar(ClientAndRoleQuery query, Integer rows, Integer page){
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
			List<ClientAndRole> entities = getService().getEntities(query);
			
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