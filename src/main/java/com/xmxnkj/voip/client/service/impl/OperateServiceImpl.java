package com.xmszit.voip.client.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.client.dao.OperateDao;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.client.entity.query.OperateQuery;
import com.xmszit.voip.client.service.OperateService;

@Service
@Transactional
public class OperateServiceImpl extends BusinessBaseServiceImpl<Operate, OperateQuery>  implements OperateService{
   
	@Autowired 
	private OperateDao dao;
    
	@Override
	public OperateDao getDao(){
		return dao;
    }

	@Override
	public void deleteOperate(String id) {
		getDao().delete(id);
	}
	
	public Boolean userHasOperate(ClientUser clientUser, String code){
		if (clientUser!=null 
				&& !StringUtils.isEmpty(clientUser.getClientId()) 
				&& !StringUtils.isEmpty(clientUser.getId())) {
			if (clientUser.getIsManager()) {
				return true;
			}
			OperateQuery query = new OperateQuery();
			//query.setClientId(clientUser.getClientId());
			query.setClientUserId(clientUser.getId());
			query.setCode(code);
			return getEntity(query)!=null;
		}
		return false;
	}

	public List<Operate> queryUserOperate(ClientUser clientUser){
		if (clientUser!=null 
				&& !StringUtils.isEmpty(clientUser.getClientId()) 
				&& !StringUtils.isEmpty(clientUser.getId())) {
			
			if (clientUser.getIsManager()) {
				return getEntities(new OperateQuery());
			}
			OperateQuery query = new OperateQuery();
			//query.setClientId(clientUser.getClientId());
			query.setClientUserId(clientUser.getId());
			List<Operate> list  = getEntities(query);
			for (Operate operate : list) {
				if (operate.getGrade()==2) {
					operate.setUrl(getOperateUrl(list, operate));;
				}
			}
			return list;
		}
		return null;
	}
	
	public List<Operate> queryUserOperate(String clientId, String clientUserId){
		if(!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientUserId)){
			OperateQuery query = new OperateQuery();
			//query.setClientId(clientId);
			query.setClientUserId(clientUserId);
			return getEntities(query);
		}
		return null;
	}
	public String getOperateUrl(List<Operate> list,Operate operate_s){
		List<Operate> operates = list;
		if (operates!=null) {
			for (Operate operate : operates) {
				if ((operate_s.getGrade()+1==operate.getGrade())&&(operate_s.getId().equals(operate.getPid()))) {
					if (operate.getIsScript()) {
						return operate_s.getUrl();
					}else {
						return getOperateUrl(list,operate);
					}
				}
			}
		}
		return operate_s.getUrl();
	}
}
