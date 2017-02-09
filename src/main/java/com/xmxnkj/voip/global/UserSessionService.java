package com.xmszit.voip.global;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hsit.common.spring.ApplicationContextUtils;
import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.service.ClientService;

/**
 * @ProjectName:voip
 * @ClassName: UserSession
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class UserSessionService {
	
	 public static Map<String, ClientSession> sessions = new HashMap<>();
	 public static Map<String, ClientSession> users = new HashMap<>();
	 public static Map<String, Client> clients = new HashMap<>();
	 
	 public static String addSession(ClientUser clientUser, Client client){
		 
		 if (clientUser!=null) {
			 ClientSession cs = new ClientSession();
			String sessionId = generateSessionId();
			if (users.containsKey(clientUser.getId())) {
				//将原登录踢下线
				String oldSessionId = users.get(clientUser.getId()).getSessionId();
				cs.setClientUser(clientUser);
				cs.setSessionId(sessionId);
				cs.setLoginTime(new Date());
				sessions.remove(oldSessionId);
				sessions.put(sessionId, cs);
				users.put(clientUser.getId(), cs);
				//return users.get(clientUser.getId()).getSessionId();
			}
			//String sessionId = generateSessionId();
			cs.setClientUser(clientUser);
			cs.setSessionId(sessionId);
			cs.setLoginTime(new Date());
			sessions.put(sessionId, cs);
			users.put(clientUser.getId(), cs);
			clients.put(clientUser.getClientId(), client);
			return sessionId;
		 }
		 return null;
	 }
	 
	 public static ClientSession getSession(String sessionId){
		 if (sessions.containsKey(sessionId)) {
			return sessions.get(sessionId);
		}
		 return null;
	 }
	 
	 public static Client getClient(String clientId){
		 return clients.containsKey(clientId)?clients.get(clientId):null;
	 }
	 
	 @Autowired
	 private ClientService clientService;
	 public Client getSessionClient(String clientId){
		 Client client = getClient(clientId);
		 if (client==null) {
			 client = clientService.getById(clientId);
			 if (client!=null) {
				clients.put(clientId, client);
			}
			 
		 }
		 return client;
	 }
	 
	 
	 
	 private static String generateSessionId(){
		 return UUID.randomUUID().toString().replace("-", "");
	 }
}
