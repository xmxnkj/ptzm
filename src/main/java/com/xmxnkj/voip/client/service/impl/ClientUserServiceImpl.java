package com.xmszit.voip.client.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.MD5Util;
import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.dao.ClientUserDao;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.query.ClientAndRoleQuery;
import com.xmszit.voip.client.entity.query.ClientUserQuery;
import com.xmszit.voip.client.service.ClientAndRoleService;
import com.xmszit.voip.client.service.ClientService;
import com.xmszit.voip.client.service.ClientUserService;
import com.xmszit.voip.web.models.ListJson;

@Service("clientUserService")
@Transactional
public class ClientUserServiceImpl extends BusinessBaseServiceImpl<ClientUser, ClientUserQuery> implements ClientUserService{
 
	@Autowired
	private ClientUserDao dao;
	
	@Autowired
	private ClientAndRoleService clientAndRoleService;
	
	@Override
	public ClientUserDao getDao(){
		return dao;
	}

	@Autowired
	private ClientService clientService;
	@Override
	public ClientUser login(String clientName,String account, String passwd) {
		Client client = clientService.getEntity(clientName);
		if (client==null) {
			throw new ApplicationException("登录账号或密码错误！");
		}
		/*if (StringUtils.isEmpty(account)) {
			account = "admin";
		}*/
		ClientUser clientUser = getClientUser(account,client.getId());
		
		if (clientUser==null) {
			throw new ApplicationException("登录账号或密码错误！");
		}
		
		
		if (!encryptPasswd(passwd).equals(clientUser.getMd5Passwd())) {
			throw new ApplicationException("登录账号或密码错误！");
		}
		
		return clientUser;
	}
	private String encryptPasswd(String passwd){
		return MD5Util.MD5(passwd);
	}
	public ClientUser getClientUser(String account,String clientId){
		if(!StringUtils.isEmpty(account)){
			ClientUserQuery query = new ClientUserQuery();
			query.setClientId(clientId);
			query.setAccount(account);
			return getEntity(query);
		}
		return null;
	}
	@Override
	public void changePasswd(String clientUserId, String oldPasswd, String newPasswd){
		ClientUser clientUser = getById(clientUserId);
		if (clientUser!=null
				&& !StringUtils.isEmpty(oldPasswd)
				&& !StringUtils.isEmpty(newPasswd)) {
			String dec = encryptPasswd(oldPasswd);
			if (dec.equals(clientUser.getMd5Passwd())) {
				clientUser.setMd5Passwd(encryptPasswd(newPasswd));
				saveSimple(clientUser);
			}else{
				throw new ApplicationException("旧密码错误！");
			}
		}else{
			throw new ApplicationException("非法使用！");
		}
				
	}
	
	/**
	 * 删除用户
	 */
	@Override
	public ListJson deleteClientUser(String id) {
		ListJson listJson = new ListJson();
		try{
			ClientUser clientUser = getById(id);
			if(clientUser!=null){
				//判断是否已有部门
				if(clientUser.getDept()==null){
					deleteById(id);
					//删除角色
					clientAndRoleService.deleteRole(id);
					listJson.setSuccess(true);
				}else{
					listJson.setMessage("请先作废，再进行删除！");
					listJson.setSuccess(false);
					return listJson;
				}
			}else{
				listJson.setMessage("不存在！");
				listJson.setSuccess(false);
				return listJson;
			}
			
		}catch(Exception e){
			listJson.setMessage(e.getMessage());
			listJson.setSuccess(false);
			return listJson;
		}
		
		return listJson;
	}
}