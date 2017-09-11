package com.xmxnkj.voip.voice.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.EntityOrder;
import com.hsit.common.dao.QueryParam;
import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.hsit.common.kfbase.entity.Paging;
import com.hsit.common.kfbase.entity.RelaParam;
import com.xmxnkj.voip.voice.dao.IVoiceTemplateDao;
import com.xmxnkj.voip.voice.entity.VoiceTemplate;
import com.xmxnkj.voip.voice.entity.query.VoiceTemplateQuery;

@Repository
public class VoiceTemplateDaoImpl extends SimpleHibernate4Dao<VoiceTemplate, VoiceTemplateQuery> implements IVoiceTemplateDao {

	@Override
	public Boolean add(VoiceTemplate voice) {
		
		return null;
	}

	@Override
	public Boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean update(String id, VoiceTemplate voice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VoiceTemplate> find() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VoiceTemplate findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}



}
