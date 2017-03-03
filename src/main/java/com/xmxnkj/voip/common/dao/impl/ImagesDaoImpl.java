package com.xmszit.voip.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hsit.common.dao.hibernate.SimpleHibernate4Dao;
import com.xmszit.voip.common.dao.ImagesDao;
import com.xmszit.voip.common.entity.Images;
import com.xmszit.voip.common.entity.query.ImagesQuery;

/**
 * 
 * @author zjx
 *
 */
@Repository
public class ImagesDaoImpl extends SimpleHibernate4Dao<Images, ImagesQuery> implements ImagesDao {
	
}
