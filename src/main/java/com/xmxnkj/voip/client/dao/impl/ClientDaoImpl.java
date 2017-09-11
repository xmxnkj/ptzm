package com.xmxnkj.voip.client.dao.impl;


import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.client.dao.ClientDao;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.query.ClientQuery;
@Repository
public class ClientDaoImpl extends SimpleHibernate4Dao<Client, ClientQuery>  implements ClientDao{

}
