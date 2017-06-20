package com.xmszit.voip.voice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmszit.voip.voice.dao.IVoiceTemplateDao;
import com.xmszit.voip.voice.entity.VoiceTemplate;
import com.xmszit.voip.voice.entity.query.VoiceTemplateQuery;
import com.xmszit.voip.voice.service.IVoiceTemplateService;

@Service
public class VoiceTemplateServiceImpl extends BusinessBaseServiceImpl<VoiceTemplate, VoiceTemplateQuery> implements IVoiceTemplateService {

	@Autowired
	private IVoiceTemplateDao dao;

	@Override
	protected IVoiceTemplateDao getDao() {
		return dao;
	}

	@Override
	public VoiceTemplate findById(String id) {
		VoiceTemplate voice = getEntityById(id);
		return voice;
	}

	@Override
	public List<VoiceTemplate> find() {
		List<VoiceTemplate> list = getEntities(null);
		return list;
	}

	@Override
	public void add(VoiceTemplate voice) {
		save(voice);
	}
	

}
