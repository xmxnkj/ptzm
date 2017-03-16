package com.xmszit.voip.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.system.dao.PublishNoticeDao;
import com.xmszit.voip.system.entity.PublishNotice;
import com.xmszit.voip.system.entity.query.PublishNoticeQuery;
/**
 * NoticeDaoImp
 * @author Administrator
 *
 */
@Repository
public class PublishNoticeDaoImp extends SimpleHibernate4Dao<PublishNotice, PublishNoticeQuery> implements PublishNoticeDao{ 

}
