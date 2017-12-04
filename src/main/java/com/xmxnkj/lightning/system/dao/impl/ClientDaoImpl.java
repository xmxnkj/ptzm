package com.xmxnkj.lightning.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.lightning.system.dao.ClientDao;
import com.xmxnkj.lightning.system.entity.Client;
import com.xmxnkj.lightning.system.entity.query.ClientQuery;

/**
 * @ProjectName:voip
 * @ClassName: ClientDaoImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Repository
public class ClientDaoImpl extends SimpleHibernate4Dao<Client, ClientQuery> implements ClientDao{

}
