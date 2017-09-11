package com.xmxnkj.voip.client.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.client.dao.LineDao;
import com.xmxnkj.voip.client.entity.Line;
import com.xmxnkj.voip.client.entity.query.LineQuery;
import com.xmxnkj.voip.client.service.LineService;

@Service
@Transactional
public class LineServiceImpl extends BusinessBaseServiceImpl<Line, LineQuery>  implements LineService{
	
	@Autowired 
	private LineDao dao;
    
	@Override
	public LineDao getDao(){
	   return dao;
    }
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer getMaxNumberLine(String deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptId", deptId);
		Integer maxNumber = jdbcTemplate.queryForInt("select max(NUMBER) FROM ct_line where deleted='0' and DEPT_ID=?", new Object[]{deptId});
		return maxNumber!=null?++maxNumber:1;
	}

	@Override
	public boolean hasLineOnCurrent(String clientUserId) {
		LineQuery lineQuery = new LineQuery();
		lineQuery.setClientUserId(clientUserId);
		lineQuery.setDeleted(false);
		return getEntityCount(lineQuery)>0;
	}
}