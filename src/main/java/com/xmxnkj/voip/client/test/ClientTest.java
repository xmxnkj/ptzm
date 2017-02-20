/*package com.xmszit.voip.client.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xmszit.voip.client.entity.ClientRole;
import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.ClientUserRole;
import com.xmszit.voip.client.entity.Dept;
import com.xmszit.voip.client.entity.Operate;
import com.xmszit.voip.client.entity.query.ClientRoleQuery;
import com.xmszit.voip.client.entity.query.ClientUserQuery;
import com.xmszit.voip.client.entity.query.ClientUserRoleQuery;
import com.xmszit.voip.client.entity.query.DeptQuery;
import com.xmszit.voip.client.entity.query.OperateQuery;
import com.xmszit.voip.client.service.ClientRoleService;
import com.xmszit.voip.client.service.ClientUserRoleService;
import com.xmszit.voip.client.service.ClientUserService;
import com.xmszit.voip.client.service.DeptService;
import com.xmszit.voip.client.service.OperateRoleService;
import com.xmszit.voip.client.service.OperateService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ClientTest {
	
	*//**
	 * 权限
	 *//*
	@Autowired
	private ClientRoleService clientRoleService;

	*//**
	 * 用户
	 *//*
	@Autowired
	private ClientUserService clientUserService;
	
	*//**
	 * 用户-权限
	 *//*
	@Autowired	
	private ClientUserRoleService clientUserRoleService;
	
	*//**
	 * 操作
	 *//*
	@Autowired
	private OperateService operateService;
	
	*//**
	 * 操作-权限
	 *//*
	@Autowired
	private OperateRoleService operateRoleService;
	
	*//**
	 * 部门
	 *//*
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	//新增权限	
	@Test
	public void test1(){
		ClientRole clientRole1 = new ClientRole();
		clientRole1.setRoleName("系统管理员");
		clientRole1.setPowerContent("系统管理员");
		clientRoleService.save(clientRole1);
		
		ClientRole clientRole2 = new ClientRole();
		clientRole2.setRoleName("坐席");
		clientRole2.setPowerContent("坐席");
		clientRoleService.save(clientRole2);
		
		ClientRole clientRole3 = new ClientRole();
		clientRole3.setRoleName("业务经理");
		clientRole3.setPowerContent("业务经理");
		clientRoleService.save(clientRole3);
	}	
	
	//查询权限
	@Test
	public void test2(){
		List<ClientRole> list = clientRoleService.getEntities(new ClientRoleQuery());
		JsonUtils.toJsonString(list);
	}
	
	//新增用户
	@Test
	public void test3(){
		//clientUserService
		ClientUser clientUser = new ClientUser();
		clientUser.setAccount("admin");
		clientUser.setAddress("厦门湖里区金尚路");
		clientUser.setBirthday(new Date());
		clientUser.setIdCard("35052519920817003X");
		clientUser.setName("陈培捷");
		clientUser.setPasswd("123456");
		clientUserService.save(clientUser);
	}
	
	//给用户赋予角色
	@Test
	public void test4(){
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setName("陈培捷");
		ClientUser clientUser = clientUserService.getEntity(clientUserQuery);
		
		//clientRoleService
		ClientRoleQuery clientRoleQuery = new ClientRoleQuery();
		clientRoleQuery.setRoleName("系统管理员");
		ClientRole clientRole = clientRoleService.getEntity(clientRoleQuery);
		
		//新增权限clientUserRoleService
		ClientUserRole clientUserRole = new ClientUserRole();
		clientUserRole.setClientUserId(clientUser.getId());
		clientUserRole.setRoleId(clientRole.getId());
		clientUserRole.setRole(clientRole);
		clientUserRole.setClientUser(clientUser);
		clientUserRoleService.save(clientUserRole);
	}
	
	//查询用户的角色
	@Test
	public void test5(){
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setName("陈培捷");
		ClientUser clientUser = clientUserService.getEntity(clientUserQuery);
		
		ClientUserRoleQuery clientUserRoleQuery = new ClientUserRoleQuery();
		clientUserRoleQuery.setClientUserId(clientUser.getId());
		List<ClientUserRole> list = clientUserRoleService.getEntities(clientUserRoleQuery);
		JsonUtils.toJsonString(list);
	}
	
	//新增部门
	@Test
	public void test6(){
		ClientUserQuery clientUserQuery = new ClientUserQuery();
		clientUserQuery.setName("陈培捷");
		ClientUser clientUser = clientUserService.getEntity(clientUserQuery);
		
		Dept dept = new Dept();
		dept.setClientUserId(clientUser.getId());
		dept.setName("财务");
		deptService.save(dept);
	}
	
	//新增部门
	@Test
	public void test7(){
		List<ClientUserRole> list = clientUserRoleService.getEntities(new ClientUserRoleQuery());
		for(ClientUserRole clientUserRole:list){
			System.out.println(clientUserRole.getRole().getRoleName());
		}
		JsonUtils.toJsonString(deptService.getEntities(new DeptQuery()));
	}
	
	@Test
	public void test8(){
		//查询操作等级数
		List<Map<String, Object>> levels = jdbcTemplate.queryForList("select ID,PID,TEXT,LEVEL from ct_operate ORDER BY LEVEL");
		//JsonUtils.toJsonString(operateService.getEntities(operateQuery));
	}
	
	public List<Operate> a(String pid,String loginId){
		OperateQuery operateQuery = new OperateQuery();
		operateQuery.setClientId(loginId);
		operateQuery.setPid(pid);
		return operateService.getEntities(operateQuery);
	}
}*/