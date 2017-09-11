package com.xmxnkj.voip.voice.test;

import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.Severity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.voice.dao.IVoiceTemplateDao;
import com.xmxnkj.voip.voice.entity.CallTimeSet;
import com.xmxnkj.voip.voice.entity.VoiceTemplate;
import com.xmxnkj.voip.voice.entity.emun.CallTimeState;
import com.xmxnkj.voip.voice.entity.emun.OperationState;
import com.xmxnkj.voip.voice.entity.query.CallTimeSetQuery;
import com.xmxnkj.voip.voice.service.ICallTimeSetService;
import com.xmxnkj.voip.voice.service.IVoiceTemplateService;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
public class VoiceTest {
	
	@Autowired
	private IVoiceTemplateService service;
	
	@Test
	public void add() {
		VoiceTemplate voice = new VoiceTemplate();
		voice.setName("模板1");
		voice.setPath("123");
		voice.setDescription("模板说明");
		voice.setPerson(new ClientUser());
		voice.setUploadDate(new Date());
		service.add(voice);
		System.out.println("保存成功");
	}
	
}
