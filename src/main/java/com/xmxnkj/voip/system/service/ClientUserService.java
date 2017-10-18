package com.xmxnkj.voip.system.service;

import java.util.List;
import java.util.Map;

import com.hsit.common.service.AppBaseService;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.query.ClientUserQuery;

public interface ClientUserService extends AppBaseService<ClientUser, ClientUserQuery>{
	
	public List<ClientUser> getAllLower(List<String> ids);
	
	//获取下级的普通用户
	public List<Map<String,Object>> getSonNormal(List<String> qlist,String seat);
	
	//获取下级
	public List<Map<String,Object>> getSonNormal(List<String> qlist,String seat,ClientTypeEnum clientTypeEnum);

	//查询上级会员单位
	public ClientUser parentMember(String parentId);
	
	//查询会员单位下的所有用户订单的状态变更为非警告
	public void isNotWarn(ClientUser user);
	
	//查询上级会员单位
	public void cancelOrder(OrderItem orderItem);
	
	//获取占用保证金
	public void getSeatBail(String ID);
}
