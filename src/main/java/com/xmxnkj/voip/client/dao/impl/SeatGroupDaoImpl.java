package com.xmszit.voip.client.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.client.dao.SeatGroupDao;
import com.xmszit.voip.client.entity.SeatGroup;
import com.xmszit.voip.client.entity.query.SeatGroupQuery;

@Repository
public class SeatGroupDaoImpl extends SimpleHibernate4Dao<SeatGroup, SeatGroupQuery> implements SeatGroupDao{

}
