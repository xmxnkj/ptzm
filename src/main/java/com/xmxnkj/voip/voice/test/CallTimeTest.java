package com.xmxnkj.voip.voice.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xmxnkj.voip.voice.entity.CallTimeSet;
import com.xmxnkj.voip.voice.entity.emun.CallTimeState;
import com.xmxnkj.voip.voice.entity.emun.OperationState;
import com.xmxnkj.voip.voice.entity.query.CallTimeSetQuery;
import com.xmxnkj.voip.voice.service.ICallTimeSetService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class CallTimeTest {

	@Autowired
	private ICallTimeSetService service;
	
	@Test
	public void addTest() {
		CallTimeSet time = new CallTimeSet();
		time.setName("设置2");
		time.setStartTime(new Date());
		time.setEndTime(new Date());
		time.setState(CallTimeState.Weekend);
		time.setCreateTime(new Date());
		time.setOperation(OperationState.Open);
		service.add(time);
	}
	@Test
	public void query() {
		List<CallTimeSet> list = service.getEntities(new CallTimeSetQuery());
		System.out.println(JSONArray.fromObject(list));
	}
	
	@Test
	public void update() {
		String id = "e4cca500-0132-4c44-9af1-cf68ff2ee740";
		CallTimeSet time = service.getEntityById(id);
		time.setEndTime(new Date());
		service.save(time);
		System.out.println(JSONObject.fromObject(time));
	}
	@Test
	public void delete() {
		String id = "e4cca500-0132-4c44-9af1-cf68ff2ee740";
		service.deleteById(id);//逻辑删除
		System.out.println("删除成功");
	}
	@Test
	public void del() {
		String id = "e4cca500-0132-4c44-9af1-cf68ff2ee740";
		service.delById(id);//物理删除
		System.out.println("删除成功");
	}
	
}
