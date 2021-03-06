package com.xmxnkj.voip.client.dao.impl;

/**
 * @ProjectName:voip
 * @ClassName: ClientUserDao
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
import org.springframework.stereotype.Repository;
import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.client.dao.ClientUserDao;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.query.ClientUserQuery;

@Repository
public class ClientUserDaoImpl extends SimpleHibernate4Dao<ClientUser, ClientUserQuery> implements ClientUserDao{

}
