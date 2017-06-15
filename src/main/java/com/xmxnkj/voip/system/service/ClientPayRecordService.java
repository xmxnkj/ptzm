package com.xmszit.voip.system.service;



import com.hsit.common.service.BusinessBaseService;
import com.hsit.common.uac.entity.User;
import com.xmszit.voip.system.entity.ClientPayRecord;
import com.xmszit.voip.system.entity.query.ClientPayRecordQuery;

/**
 * @ProjectName:voip
 * @ClassName: ClientPayRecordService
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public interface ClientPayRecordService extends BusinessBaseService<ClientPayRecord, ClientPayRecordQuery>{
	public void savePay(ClientPayRecord clientPayRecord,User user,String managerPw);
	public ClientPayRecord getClientPayRecord(String code,String clientId);
	String generateCode(String clientId);
}
