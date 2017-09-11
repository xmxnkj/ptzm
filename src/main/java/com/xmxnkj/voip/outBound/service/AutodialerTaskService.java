package com.xmxnkj.voip.outBound.service;

import java.util.List;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;
import com.xmxnkj.voip.outBound.entity.query.AutodialerTaskQuery;

public interface AutodialerTaskService extends BusinessBaseService<AutodialerTask, AutodialerTaskQuery>{
	
	//动态生成号码表
	public void createNumber(AutodialerTask autodialerTask);
	
	//批量插入到号码表
	public void insertInto(AutodialerTask autodialerTask,List<Customer> list);

	public Boolean isHasnTable(String tableName);
}
