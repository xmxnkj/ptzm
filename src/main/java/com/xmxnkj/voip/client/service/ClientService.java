package com.xmxnkj.voip.client.service;

import com.hsit.common.service.BusinessBaseService;
import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.query.ClientQuery;

public interface ClientService extends BusinessBaseService<Client, ClientQuery> {

	public void modifyClient(Client client, String managerPw, String modifyRemark, User loginUser);

	public void createClient(Client client);

	public void delClient(Client client, String managerPw, String modifyRemark, User loginUser);
}
