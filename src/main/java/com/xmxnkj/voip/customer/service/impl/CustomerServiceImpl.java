package com.xmszit.voip.customer.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.global.PoiHelper;
import com.xmszit.voip.web.utils.WebContextHolder;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.customer.dao.CustomerDao;
import com.xmszit.voip.customer.entity.ContactState;
import com.xmszit.voip.customer.entity.Customer;
import com.xmszit.voip.customer.entity.CustomerType;
import com.xmszit.voip.customer.entity.ReceivingState;
import com.xmszit.voip.customer.entity.query.CustomerQuery;
import com.xmszit.voip.customer.service.CustomerService;
import com.xmszit.voip.customer.web.models.CountCustomerType;

@Service
public class CustomerServiceImpl extends BusinessBaseServiceImpl<Customer, CustomerQuery> implements CustomerService {
	@Autowired
	private CustomerDao dao;

	@Override
	public CustomerDao getDao() {
		return dao;
	}
	/**
	 * 判断是否超出可分配
	 * @param clientId
	 * @param clientUser
	 * @param num
	 * @return
	 */
	public Boolean judgePrivateSea(String clientId,ClientUser clientUser,Integer num){
		Integer privateSea = clientUser.getPrivateSea()!=null?clientUser.getPrivateSea():0;//用户最大坐席数量
		Integer havaSea = Integer.valueOf(privateSeaCount(clientId, clientUser.getId(),null).toString());//用户已拥有坐席数量
		privateSea = privateSea-havaSea;
		if ((privateSea-num)>=0) {
			return false;
		}
		return true;
	} 
	
	@Override
	public String importCustomer(XSSFSheet sheet, String type,String clientId,ClientUser clientUser) {
		StringBuffer stringBuffer = new StringBuffer();
		Date date = new Date();
		//判断是否超出给坐席可分配坐席：
		if (judgePrivateSea(clientId, clientUser, sheet.getLastRowNum())) {
			throw new ApplicationException("当前已超出可分配容量!");
		}
		for(int i=1; i<=sheet.getLastRowNum(); i++){
//			String name = PoiHelper.getString(sheet, i, 0);
			String mobile = PoiHelper.getString(sheet, i, 0);
			String companyName = PoiHelper.getString(sheet, i, 1);
			String contactUser = PoiHelper.getString(sheet, i, 2);
			//验证号码的正确性
			Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57]|19[0-9]|16[0-9])[0-9]{8}$"); 
			Matcher m = p.matcher(mobile); 
			if (!m.matches()) {
				stringBuffer.append("第"+(i+1)+"行电话号码：【"+mobile+"】格式错误！  \n");
				//throw new ApplicationException("第"+(i+1)+"行电话号码：【"+mobile+"】格式错误！");
				//格式错误的跳过
				continue;
			}
			if (isExistCustomer(mobile,clientId,null)) {
				stringBuffer.append("第"+(i+1)+"行号码已存在(客户库/坐席库)：【"+mobile+"】！ \n");
				//throw new ApplicationException("第"+(i+1)+"行电话号码已存在(客户库/坐席库)：【"+mobile+"】！");
				//重复的跳过
				continue;
			}
			/*String customerType = PoiHelper.getString(sheet, i, 4);
			String contactState = PoiHelper.getString(sheet, i, 5);
			String planState = PoiHelper.getString(sheet, i, 6);
			String receivingState = PoiHelper.getString(sheet, i, 7);*/
			if (!StringUtils.isEmpty(mobile)) {
				Customer customer = new Customer();
//				customer.setName(name);
				customer.setCompanyName(companyName);
				customer.setContactUser(contactUser);
				customer.setMobile(mobile);
				customer.setClientId(clientId);
				/*customer.setCustomerType(customerType);
				customer.setContactState(contactState);
				customer.setPlanState(planState);
				customer.setReceivingState(receivingState);*/
				customer.setType(Integer.valueOf(type));
				customer.setCreateDate(date);
				customer.setIsImport(true);
				if (type.equals("0")) {
					customer.setClientUser(clientUser);
				}
				save(customer);
			}
		}
		return stringBuffer.toString();
	}
	@Override
	public Boolean isExistCustomer(String mobile,String clientId,String id){
		CustomerQuery customerQuery = new CustomerQuery();
		customerQuery.setClientId(clientId);
		customerQuery.setDeleted(false);
		customerQuery.setMobile(mobile);
		if (!StringUtils.isEmpty(id)) {
			customerQuery.setNotId(id);
		}
		ClientUser c = (ClientUser)WebContextHolder.getSession().getAttribute("clientUser");
		Customer customer = getEntity(customerQuery);
		if (customer!=null && (customer.getIsPrivate()==null || (customer.getIsPrivate()!=null && customer.getIsPrivate()==0)) && (customer.getClientUser()==null || ( customer.getClientUser()!=null && c.getId().equals(customer.getClientUser().getId())) )) {
			return true;
		}
		return false;
	}
	
	public Customer queryCustomer(String mobile, String clientId){
		if(!StringUtils.isEmpty(mobile) && !StringUtils.isEmpty(clientId)){
			CustomerQuery query = new CustomerQuery();
			query.setClientId(clientId);
			query.setMobile(mobile);
			return getEntity(query);
		}
		return null;
	}
	

	@Override
	public void callBack(String mobile,
			ContactState contactState,
			ReceivingState receivingState,
			CustomerType customerType,
			String clientId){
		if (!StringUtils.isEmpty(mobile)&&
				contactState!=null&&
				receivingState!=null&&
				customerType!=null&&
				!StringUtils.isEmpty(clientId)) {
			CustomerQuery customerQuery = new CustomerQuery();
			customerQuery.setMobile(mobile);
			customerQuery.setClientId(clientId);
			Customer customer = getEntity(customerQuery);
			customer.setContactState(contactState);
			customer.setReceivingState(receivingState);
			customer.setCustomerType(customerType);
			customer.setComCustTypeDate(new Date());
			saveSimple(customer);
		}
	}
	
	
	@Override
	public void deleteData(String id) {
		getDao().delete(id);
	}
	@Override
	public void open(String customerId){
		getDao().openDao(customerId);
	}
	@Override
	public CountCustomerType countCustomerType(String clientId,String clientUserId,String type,String jDate,String deptId){
		return getDao().countCustomerType(clientId, clientUserId,type,jDate,deptId);
	}

	@Override
	public CountCustomerType countCustState(String clientId, String clientUserId,String deptId) {
		return getDao().countCustState(clientId, clientUserId,deptId);
	}
	@Override
	public BigInteger privateSeaCount(String clientId,String clientUserId,String deptId){
		return getDao().privateSeaCount(clientId, clientUserId,deptId);
	}
}
