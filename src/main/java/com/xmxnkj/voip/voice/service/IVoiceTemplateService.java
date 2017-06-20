package com.xmszit.voip.voice.service;

import java.util.List;

import com.hsit.common.service.BusinessBaseService;
import com.xmszit.voip.voice.entity.VoiceTemplate;
import com.xmszit.voip.voice.entity.query.VoiceTemplateQuery;

public interface IVoiceTemplateService extends BusinessBaseService<VoiceTemplate, VoiceTemplateQuery>{

	/** 根据id获取结果 */
	VoiceTemplate findById(String id);
	/** 查询 */
	List<VoiceTemplate> find();
	/** 添加|更新 */
	void add(VoiceTemplate voice);
	
}
