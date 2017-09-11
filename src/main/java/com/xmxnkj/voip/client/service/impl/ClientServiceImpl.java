package com.xmxnkj.voip.client.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hsit.common.MD5Util;
import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.service.UserService;
import com.hsit.common.utils.Utils;
import com.xmxnkj.voip.client.entity.ClientAndRole;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.client.entity.UserRole;
import com.xmxnkj.voip.client.entity.query.OperateQuery;
import com.xmxnkj.voip.system.entity.ClientMeal;
import com.xmxnkj.voip.client.entity.OperateRole;
import com.xmxnkj.voip.client.entity.query.UserRoleQuery;
import com.xmxnkj.voip.system.entity.ModifyClientRecord;
import com.xmxnkj.voip.client.dao.ClientDao;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.query.ClientQuery;
import com.xmxnkj.voip.client.service.ClientAndRoleService;
import com.xmxnkj.voip.client.service.ClientService;
import com.xmxnkj.voip.client.service.ClientUserService;
import com.xmxnkj.voip.client.service.OperateRoleService;
import com.xmxnkj.voip.client.service.OperateService;
import com.xmxnkj.voip.client.service.UserRoleService;
import com.xmxnkj.voip.system.service.BaseAreaService;
import com.xmxnkj.voip.system.service.ClientMealService;
import com.xmxnkj.voip.system.service.ModifyClientRecordService;

 
@Service("clientService")
public class ClientServiceImpl extends BusinessBaseServiceImpl<Client, ClientQuery> implements ClientService{
     
	@Autowired
	private ClientDao dao;
    
	@Override
	public ClientDao getDao(){
	  return dao;
	}
	@Autowired
	private ClientMealService clientMealService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private OperateRoleService operateRoleService;
	@Autowired
	private OperateService operateService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private ClientAndRoleService clientAndRoleService;
	/* 
	 * 
	 * @see com.hsit.common.service.AppBaseServiceImpl#save(com.hsit.common.kfbase.entity.DomainObject)
	 */
	@Override
	public String save(Client client) {
		
		if (client==null) {
			return null;
		}
		Client old = getEntity(client.getName());
		if (old!=null && !old.getId().equalsIgnoreCase(client.getId())) {
			throw new ApplicationException("客户已存在！");
		}
		
		return super.save(client);
	}
	@Override
	public void createClient(Client client) {
		// TODO Auto-generated method stub
//		throw new ApplicationException("待完善--client/service/imp/clientServiceImpl");
		System.out.println("name:" + client.getName());
		if (client.getState()!=null&&client.getState()) {
			client.setUseDate(new Date());
		}
		if (client.getClientMeal()==null||StringUtils.isEmpty(client.getClientMeal().getId())) {
			throw new ApplicationException("请选择套餐！");
		}
		save(client);
		//计算套餐使用数量
		countUsedMeal(client.getClientMeal().getId());
		//System.out.println("created");
		//创建系统管理员角色
		UserRole role = new UserRole();
		role.setRoleName("系统管理员");
		role.setClientId(client.getId());
		role.setDeleted(false);
		role.setRemark("系统设置");
		role.setAdminRole(true);
		userRoleService.save(role);
		dao.flush();
		//把所有权限给系统管理员角色
		OperateQuery operateQuery = new OperateQuery();
		//一级权限
		operateQuery.setGrade(1);
		operateQuery.setClientMealId(client.getClientMeal().getId());
		List<Operate> operates_1 = operateService.getEntities(operateQuery);
		for (Operate operate2 : operates_1) {
			saveOperateRole(role,operate2,1);
		}
		//二级权限
		operateQuery.setGrade(2);
		List<Operate> operates_2 = operateService.getEntities(operateQuery);
		//查询三级以下权限
		operateQuery.setGrade(null);
		operateQuery.setClientMealId(null);
		operateQuery.setOnlyOther(true);
		List<Operate> operates_All = operateService.getEntities(operateQuery);
		for (Operate operate2 : operates_2) {
			saveOperateRole(role,operate2,1);
			loopSaveOperate(operates_All, operate2.getId(),role);
		}
		//创建账号为admin、密码为123456的用户
		ClientUser clientUser = new ClientUser();
		clientUser.setAccount("admin");
		clientUser.setName("admin");
//		clientUser.setPasswd("123456");
		clientUser.setMd5Passwd(MD5Util.MD5("123456"));
		clientUser.setClientId(client.getId());
//		clientUser.setStatu(true);
		clientUser.setIsAllow(true);
//		clientUser.setIsChangeShop(true);
//		clientUser.setBillChooseType(BillChooseType.AllStore);
		clientUser.setNotLimit(true);
		//clientUser.setIsManager(true);
		clientUser.setIsUser(true);
		clientUserService.save(clientUser);
		//把用admin设为系统管理员
		ClientAndRole clientAndRole = new ClientAndRole();
		clientAndRole.setClientUser(clientUser);
		clientAndRole.setUserRole(role);
		clientAndRole.setDeleted(false);
		clientAndRoleService.save(clientAndRole);
		
	}
	
	//计算套餐使用数量
	public void countUsedMeal(String id){
		/*ClientQuery clientQuery = new ClientQuery();
		clientQuery.setClientMealId(id);
		List<Client> clients = getEntities(clientQuery);*/
		Integer amount = clientMealService.countMealAmount(id);//clients.size();//
		ClientMeal clientMeal = clientMealService.getById(id);
		clientMeal.setUseAmount(amount);
		clientMealService.save(clientMeal);
	}
	/**
	 * 设置权限
	 */
	public void saveOperateRole(UserRole role,Operate operate,Integer CheckState){
		OperateRole operateRole = new OperateRole();
		operateRole.setClientId(role.getClientId());
		operateRole.setUserRole(role);
		operateRole.setOperate(operate);
		operateRole.setCheckState(CheckState);
		operateRoleService.save(operateRole);
		dao.flush();
	}
	
	/**
	 * 循环保存
	 */
	public void loopSaveOperate(List<Operate> opeartes,String pid,UserRole role){
		for (Operate operateAll : opeartes) {
			if (operateAll.getPid().equals(pid)) {
				saveOperateRole(role,operateAll,1);
				loopSaveOperate(opeartes, operateAll.getId(),role);
			}
		}
	}
	
	@Override
	public void modifyClient(Client client, String managerPw, String modifyRemark, User user) {
//		throw new ApplicationException("待完善--client/service/imp/clientServiceImpl");
		Client oldClient = getById(client.getId());
		String md5pw = MD5Util.MD5(managerPw);
		if (!md5pw.equals(user.getLoginPasswd())) {
			throw new ApplicationException("总账号密码错误！");
		}
		if (client.getState()!=null&&client.getState()&&(oldClient.getState()==null || !oldClient.getState())) {
			client.setUseDate(new Date());
		}
		if (StringUtils.isEmpty(client.getClientMeal().getId())) {
			throw new ApplicationException("请选择套餐！");
		}
		if (!(oldClient.getClientMeal().getId().equals(client.getClientMeal().getId()))) {
			//换套餐更换权限
			//获取系统管理员角色
			UserRoleQuery roleQuery = new UserRoleQuery();
			roleQuery.setRoleName("系统管理员");
			roleQuery.setHideAdminRole(null);
			roleQuery.setAdminRole(true);
			roleQuery.setClientId(client.getId());
			UserRole role = userRoleService.getEntity(roleQuery);
			//删除原有权限
			operateRoleService.deleteOperateRole(role.getId());
			//把所有权限给系统管理员角色
			OperateQuery operateQuery = new OperateQuery();
			//一级权限
			operateQuery.setGrade(1);
			operateQuery.setClientMealId(client.getClientMeal().getId());
			List<Operate> operates_1 = operateService.getEntities(operateQuery);
			for (Operate operate2 : operates_1) {
				saveOperateRole(role,operate2,1);
			}
			//二级权限
			operateQuery.setGrade(2);
			List<Operate> operates_2 = operateService.getEntities(operateQuery);
			//查询三级以下权限
			operateQuery.setGrade(null);
			operateQuery.setClientMealId(null);
			operateQuery.setOnlyOther(true);
			List<Operate> operates_All = operateService.getEntities(operateQuery);
			for (Operate operate2 : operates_2) {
				saveOperateRole(role,operate2,1);
				String pid = operate2.getId();
				circulationList(pid, role, operates_All);
				/*for (Operate operateAll : operates_All) {
					if (operateAll.getPid().equals(operate2.getId())) {
						saveOperateRole(role,operateAll,1);
					}
				}*/
			}
			//去除原有账号已经不存在的权限
			operateRoleService.deleteOtherOperate(client.getId(), client.getClientMeal().getId());
		}
		save(client);
		//计算套餐使用数量新/旧套餐
		if (client.getClientMeal()!=null&&!StringUtils.isEmpty(client.getClientMeal().getId())) {
			countUsedMeal(client.getClientMeal().getId());
		}
		if (oldClient.getClientMeal()!=null&&!StringUtils.isEmpty(oldClient.getClientMeal().getId())) {
			countUsedMeal(oldClient.getClientMeal().getId());
		}
		//修改记录
		Client clientRecord = getById(client.getId()); 
		saveRecord(clientRecord, modifyRemark, user, true);
	}
	
	private void circulationList(String pid,UserRole role,List<Operate> operates_All){
		for (Operate operate : operates_All) {
			if (operate.getPid().equals(pid)) {
				saveOperateRole(role,operate,1);
				circulationList(operate.getId(), role, operates_All);
			}
		}
		
	}
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private ModifyClientRecordService modifyClientRecordService;
	/**
	 * 保存修改或删除记录
	 * @param client
	 * @param remark
	 * @param user
	 * @param flag
	 */
	private void saveRecord(Client clientRecord, String remark,User user,Boolean flag){
		ModifyClientRecord record = new ModifyClientRecord();
		if (clientRecord.getArea()!=null) {
			record.setArea(baseAreaService.getById(clientRecord.getArea().getId()).getName());
		}
		record.setAddress(clientRecord.getAddress());
		record.setLoginAccount(clientRecord.getLoginName());
		record.setName(clientRecord.getName());
		record.setResponsibleUser(clientRecord.getResponsibleUser());
		record.setMobile(clientRecord.getCellPhone());
		if (clientRecord.getClientMeal()!=null) {
			record.setClientMeal(clientMealService.getById(clientRecord.getClientMeal().getId()).getName());
		}
		record.setAccountState(clientRecord.getState());
		record.setEffectiveDate(clientRecord.getEffectiveDate());
		record.setAddUser(clientRecord.getAddUser()!=null?clientRecord.getAddUser().getLoginAccount():"");
		if (flag) {
			record.setModifyUser(user.getLoginAccount());
		}else {
			record.setDelUser(user.getLoginAccount());
		}
		record.setModifyDate(new Date());
		record.setUseDate(clientRecord.getUseDate());
		record.setRemark(remark);
		modifyClientRecordService.save(record);
	}
	
	
	@Override
	public void delClient(Client client, String managerPw, String modifyRemark, User loginUser) {
//		throw new ApplicationException("待完善--client/service/imp/clientServiceImpl");
		String md5pw = MD5Util.MD5(managerPw);
		if (!md5pw.equals(loginUser.getLoginPasswd())) {
			throw new ApplicationException("总账号密码错误！");
		}
		deleteById(client.getId());
		Client clientRecord = getById(client.getId()); 
		saveRecord(clientRecord, modifyRemark, loginUser, false);
	}
	
}
