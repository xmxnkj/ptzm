package com.xmxnkj.voip.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmxnkj.voip.common.dao.ImagesDao;
import com.xmxnkj.voip.common.entity.Images;
import com.xmxnkj.voip.common.entity.query.ImagesQuery;

/**
 * 
 * @author zjx
 *
 */
@Repository
public class ImagesDaoImpl extends SimpleHibernate4Dao<Images, ImagesQuery> implements ImagesDao {
	
}
