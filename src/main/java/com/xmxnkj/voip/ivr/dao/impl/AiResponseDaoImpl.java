package com.xmxnkj.voip.ivr.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.ivr.dao.AiResponseDao;
import com.xmxnkj.voip.ivr.entity.AiResponse;
import com.xmxnkj.voip.ivr.entity.query.AiResponseQuery;

@Repository
public class AiResponseDaoImpl extends SimpleHibernate4Dao<AiResponse, AiResponseQuery> implements AiResponseDao{

}
