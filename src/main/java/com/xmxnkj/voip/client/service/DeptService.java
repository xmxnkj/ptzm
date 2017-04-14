package com.xmszit.voip.client.service;

import java.util.List;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.client.entity.Dept;
import com.xmszit.voip.client.entity.query.DeptQuery;
import com.xmszit.voip.web.models.ListJson;

public interface DeptService extends BusinessBaseService<Dept, DeptQuery>{
	
	public boolean hasSonDept(String deptId);
	
	public List<Dept> sonDeptList(String deptId);
	
	public void randomCode(Dept dept);
	
	public void flushManager(Dept dept);
	
	public ListJson saveDept(Dept dept);
	
	public void changeSonDeptLevel(Dept dept,Integer difference);
	
	/**
	 * 远程控制
	 * @return
	 */
	public boolean isSonDept(String ownDeptId,String higherDeptId);
	
}
