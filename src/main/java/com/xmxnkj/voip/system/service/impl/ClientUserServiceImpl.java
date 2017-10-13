package com.xmxnkj.voip.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hsit.common.service.AppBaseServiceImpl;
import com.xmxnkj.voip.system.dao.ClientUserDao;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.OrderItemService;

@Service
@Transactional
public class ClientUserServiceImpl extends AppBaseServiceImpl<ClientUser, ClientUserQuery> implements ClientUserService{
	
	@Autowired
	private ClientUserDao dao;
	
	@Autowired
	private OrderItemService orderService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ClientUserDao getDao() {
		return dao;
	}

	//获取所有下级
	@Override
	public List<ClientUser> getAllLower(List<String> ids) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("leaderUserID",ids);
		List<ClientUser> list = dao.findHql("From ClientUser where leaderUserID in :leaderUserID", map);
		List<String> queryId = new ArrayList<String>();
		for(ClientUser cu:list){
			queryId.add(cu.getId());
		}
		if(queryId.size()>0){
			list.addAll(this.getAllLower(queryId));
		}
		return list;
	}
	
	//下级所有普通用户
	@Override
	public List<Map<String, Object>> getSonNormal(List<String> qlist, String seat) {
		
		//所有用户的ID
				List<Map<String,Object>> clientUserIdList = new ArrayList<Map<String,Object>>();
				
				StringBuffer sql = new StringBuffer("select ID,clientType,phoneNumber from clientuser where leaderUserID in ("+seat+")");
				Object[] obj = new Object[qlist.size()];
				for(int i = 0; i<qlist.size();i++){
					obj[i] = qlist.get(i);
				}
				List<Map<String,Object>> result = jdbcTemplate.queryForList(sql.toString(),obj);
				
				//查询下级
				List<String> leaderUserIDList = new ArrayList<String>();
				String seize = "";
				for(Map<String,Object> map:result){
					//不是用户
					if(((int)map.get("clientType"))!=4){
						leaderUserIDList.add((String) map.get("ID"));
						seize+="?,";
					}
					//是用户
					if(((int)map.get("clientType"))==4){
						clientUserIdList.add(map);
						//是用户 查询是否有下级
						leaderUserIDList.add((String) map.get("ID"));
						seize+="?,";
					}
				}
				
				if(leaderUserIDList.size()>0){
					clientUserIdList.addAll(getSonNormal(leaderUserIDList, seize.substring(0,seize.length()-1)));
				}
				
				return clientUserIdList;
	}

	@Override
	public List<Map<String, Object>> getSonNormal(List<String> qlist,
			String seat, ClientTypeEnum clientTypeEnum) {
		List<Map<String,Object>> clientUserIdList = new ArrayList<Map<String,Object>>();
		
		StringBuffer sql = new StringBuffer("select ID,clientType,phoneNumber,agentName from clientuser where leaderUserID in ("+seat+")");
		Object[] obj = new Object[qlist.size()];
		for(int i = 0; i<qlist.size();i++){
			obj[i] = qlist.get(i);
		}
		List<Map<String,Object>> result = jdbcTemplate.queryForList(sql.toString(),obj);
		
		//查询下级
		List<String> leaderUserIDList = new ArrayList<String>();
		String seize = "";
		for(Map<String,Object> map:result){
			//不是用户
			if(((int)map.get("clientType"))!=clientTypeEnum.ordinal()){
				leaderUserIDList.add((String) map.get("ID"));
				seize+="?,";
			}
			//是用户
			if(((int)map.get("clientType"))==clientTypeEnum.ordinal()){
				clientUserIdList.add(map);
				//是用户 查询是否有下级
				if(clientTypeEnum==ClientTypeEnum.Normal){
					leaderUserIDList.add((String) map.get("ID"));
					seize+="?,";
				}
			}
		}
		
		if(leaderUserIDList.size()>0){
			clientUserIdList.addAll(getSonNormal(leaderUserIDList, seize.substring(0,seize.length()-1),clientTypeEnum));
		}
		
		return clientUserIdList;
	}

	@Override
	public ClientUser parentMember(String parentId) {
		
		//查找上级会员单位
		ClientUser user = null;
		boolean flag = true;
		while(flag){
			user = getById(parentId);
			if(user.getClientType()==ClientTypeEnum.MemberUnit){
				flag = false;
			}else{
				parentId = user.getLeaderUserID();
			}
		}
		return user;
	}
	
	//警告状态的变更
	@Override
	public void isNotWarn(ClientUser user) {
		if(user==null){
			return;
		}
		//占用保证金 < 保证金额度 时   警告状态解除
		if(user.getSeatBailCash().doubleValue()>user.getBailCash().doubleValue()){
			return;
		}
		List<String> qlist = new ArrayList<String>();
		qlist.add(user.getId());
		List<Map<String, Object>> list = getSonNormal(qlist, "?", ClientTypeEnum.Normal);
		qlist = new ArrayList<String>();
		String account = "";
		for(Map<String, Object> map:list){
			qlist.add(map.get("phoneNumber").toString());
			account+="?,";
		}
		if(qlist.size()==0){
			return;
		}
		Object[] obj = new Object[qlist.size()];
		for(int i=0;i<qlist.size();i++){
			obj[i] = qlist.get(i);
		}
		//
		jdbcTemplate.update("update orderitem set warn='0' where account in ("+(account.substring(0,account.length()-1))+") and warn='1'",obj);
	}

	@Override
	public synchronized void cancelOrder(OrderItem OI) {
		
		//再做一次查询
		if(orderService.getById(OI.getId())!=null){
			
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setPhoneNumber(OI.getAccount());
			clientUserQuery.setClientType(ClientTypeEnum.Normal);
			ClientUser cu = getEntity(clientUserQuery);
			
			cu.setEnableMoney(cu.getEnableMoney().add(OI.getPayMoney()));	//返回金额
			save(cu);
			
			jdbcTemplate.update("delete from orderitem where ID=?", new Object[]{OI.getId()});
			//orderService.deleteById(OI.getId());
		}
	}

	
	//占用保证金  	计算
	@Override
	public void getSeatBail(String ID) {
		
		List<String> qlist = new ArrayList<String>();
	    qlist.add(ID);
	    List<Map<String, Object>> list = getSonNormal(qlist, "?", ClientTypeEnum.Normal);
		
	    String sql = "select sum(payMoney) as total from orderItem where isEveningUp='0' and account in ";
	    
	    String account = "";
	    qlist = new ArrayList<String>();
	    
	    for (Map<String, Object> map : list) {
	    	qlist.add((String)map.get("phoneNumber"));
	    	account = account + "?,";
	    }
	    
	    ClientUser cu = getById(ID);
	    
	    Object[] obj = new Object[qlist.size()];
	    
	    if(qlist.size()>0){
	    	
	    	for(int i=0;i<qlist.size();i++){
	    		obj[i] = qlist.get(i);
	    	}
	    	
	    	Map<String, Object> map = jdbcTemplate.queryForMap(sql+"("+(account.substring(0,account.length()-1))+")",obj);
	    	
	    	//修改占用保证金

	    	if(cu!=null){
	    		if(map.get("total")==null){
	    			cu.setSeatBailCash(new BigDecimal(0));
	    			cu.setEnableBailCash(cu.getBailCash().subtract(cu.getSeatBailCash()));
	    		}else{
	    			cu.setSeatBailCash(new BigDecimal(Double.parseDouble(map.get("total").toString())));
	    			cu.setEnableBailCash(cu.getBailCash().subtract(cu.getSeatBailCash()));
	    		}
	    		save(cu);
	    	}
	    }else{
	    	//为0
	    	cu.setSeatBailCash(new BigDecimal(0));
	    }
	}
}
