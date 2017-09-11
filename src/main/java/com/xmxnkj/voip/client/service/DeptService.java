package com.xmxnkj.voip.client.service;

import java.util.List;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.client.entity.Dept;
import com.xmxnkj.voip.client.entity.query.DeptQuery;
import com.xmxnkj.voip.web.models.ListJson;

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
