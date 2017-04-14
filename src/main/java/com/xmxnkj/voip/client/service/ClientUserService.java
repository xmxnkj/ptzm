package com.xmszit.voip.client.service;


import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.query.ClientUserQuery;
import com.xmszit.voip.web.models.ListJson;

public interface ClientUserService  extends BusinessBaseService<ClientUser, ClientUserQuery>{
	public ClientUser login(String clientName,String account, String passwd);

	public void changePasswd(String clientUserId, String oldPasswd, String newPasswd);
	
	public ListJson deleteClientUser(String id);
}
