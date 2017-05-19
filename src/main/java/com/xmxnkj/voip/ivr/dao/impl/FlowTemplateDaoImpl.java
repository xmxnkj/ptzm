package com.xmszit.voip.ivr.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.ivr.dao.FlowTemplateDao;
import com.xmszit.voip.ivr.entity.FlowTemplate;
import com.xmszit.voip.ivr.entity.query.FlowTemplateQuery;

@Repository
public class FlowTemplateDaoImpl extends SimpleHibernate4Dao<FlowTemplate, FlowTemplateQuery> implements FlowTemplateDao{

}
