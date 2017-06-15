package com.xmszit.voip.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.MD5Util;
import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.hsit.common.uac.entity.User;
import com.hsit.common.utils.DateUtil;
import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.service.ClientService;
import com.xmszit.voip.system.entity.PayType;
import com.xmszit.voip.system.dao.ClientMealDao;
import com.xmszit.voip.system.dao.ClientPayRecordDao;
import com.xmszit.voip.system.entity.ClientMeal;
import com.xmszit.voip.system.entity.ClientPayRecord;
import com.xmszit.voip.system.entity.query.ClientMealQuery;
import com.xmszit.voip.system.entity.query.ClientPayRecordQuery;
import com.xmszit.voip.system.service.ClientMealService;
import com.xmszit.voip.system.service.ClientPayRecordService;

/**
 * @ProjectName:voip
 * @ClassName: ClientPayRecordServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class ClientPayRecordServiceImpl extends BusinessBaseServiceImpl<ClientPayRecord, ClientPayRecordQuery> implements ClientPayRecordService{
	@Autowired
	private ClientPayRecordDao dao;

	@Override
	public ClientPayRecordDao getDao() {
		return dao;
	}
	@Autowired
	private ClientService clientService;
	@Override
	public void savePay(ClientPayRecord clientPayRecord,User user,String managerPw) {
		// TODO Auto-generated method stub
		if (clientPayRecord.getCancel()) {
			String userPw = user.getLoginPasswd();
			if (!userPw.equals(MD5Util.MD5(managerPw))) {
				throw new ApplicationException("总账户密码错误！");
			}
			ClientPayRecordQuery query = new ClientPayRecordQuery();
			Client client = clientService.getById(clientPayRecord.getClient().getId());
			query.setClientId(client.getId());
			String date = DateUtil.formatDate(new Date());
			query.setPayDate(date);
			query.setCancel(false);
			List<ClientPayRecord> list = getEntities(query);
			if (list!=null&&list.size()>0) {
				if (!list.get(0).getId().equals(clientPayRecord.getId())) {
					throw new ApplicationException("只能废除当日最新！");
				}
			}else {
				throw new ApplicationException("只能废除当日最新！");
			}
			client.setEffectiveDate(clientPayRecord.getLastExpireDate());
			clientService.save(client);
			clientPayRecord.setCancelDate(new Date());
			clientPayRecord.setCancelUser(user);
			save(clientPayRecord);
		}else {
			Client client = clientService.getById(clientPayRecord.getClient().getId());
			client.setEffectiveDate(clientPayRecord.getNextExpireDate());
			clientPayRecord.setUser(user);
			clientPayRecord.setIsPay(true);
			//clientPayRecord.setCancel(false);
			clientPayRecord.setPayDate(new Date());
			clientPayRecord.setPayType(PayType.Cash);
			clientService.save(client);
			save(clientPayRecord);
		}
	}
	@Override
	public ClientPayRecord getClientPayRecord(String code, String clientId) {
		ClientPayRecordQuery query = new ClientPayRecordQuery();
		query.setPayCode(code);
		query.setClientId(clientId);
		ClientPayRecord clientPayRecord = getEntity(query);
		return clientPayRecord;
	}
	/**
	 * 生成
	 * @param clientId
	 * @return
	 */
	@Override
	public  String generateCode(String clientId){
		//GregorianCalendar calendar = new GregorianCalendar();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String code = format.format(date);
		Integer a = -1;
		while (a < 0 ) {
			int s= (int)(Math.random()*9000+1000);
			code = code+s;
			if (!checkNumber(clientId,code)) {
				a++;
			}
		}
		return code;
	}
	/**
	 * 检测是否重复
	 */
	public Boolean checkNumber(String clientId,String code){
		Boolean flag = false;
		ClientPayRecordQuery query = new ClientPayRecordQuery();
		query.setClientId(clientId);
		query.setPayCode(code);
		List<ClientPayRecord> list = getEntities(query);
		if (list!=null && list.size() > 0) {
			flag = true;
		}
		return flag;
	}
	
}
