package com.xmszit.voip.ivr.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.ivr.dao.AiResponseDao;
import com.xmszit.voip.ivr.entity.AiResponse;
import com.xmszit.voip.ivr.entity.query.AiResponseQuery;

@Repository
public class AiResponseDaoImpl extends SimpleHibernate4Dao<AiResponse, AiResponseQuery> implements AiResponseDao{

}
