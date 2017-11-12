package com.xmxnkj.voip.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.system.dao.OrderItemDao;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;

@Repository
public class OrderItemDaoImpl extends SimpleHibernate4Dao<OrderItem, OrderItemQuery> implements OrderItemDao{

}
