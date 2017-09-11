package com.xmxnkj.voip.outBound.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.service.CustomerService;
import com.xmxnkj.voip.outBound.dao.AutodialerTaskDao;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;
import com.xmxnkj.voip.outBound.entity.query.AutodialerTaskQuery;
import com.xmxnkj.voip.outBound.service.AutodialerTaskService;

import net.sf.json.JSONObject;

@Service
public class AutodialerTaskServiceImpl extends BusinessBaseServiceImpl<AutodialerTask, AutodialerTaskQuery> implements AutodialerTaskService{
	
	@Autowired
	private AutodialerTaskDao dao;

	@Override
	public AutodialerTaskDao getDao() {
		return dao;
	}
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 判断表示是否存在
	 * 
	 */
	@Override
	public Boolean isHasnTable(String tableName){
		try{
			Map<String, Object> map = jdbcTemplate.queryForMap("SELECT table_name FROM information_schema.TABLES WHERE table_name ='"+tableName+"'");
			if(map!=null && map.get("table_name").toString().equals(tableName)){
				return true;
			}
		}catch(Exception e){
			return false;
		}		
		return false;
	}
	
	/**
	 * 建表
	 */
	@Override
	public void createNumber(AutodialerTask autodialerTask) {
		String tableName = "autodialer_number_"+autodialerTask.getUuid();
		if(!isHasnTable(tableName)){
			StringBuffer createSql  =  new StringBuffer();
			createSql.append("CREATE TABLE  if not exists "+tableName+"(");
			createSql.append("id INT(11) NOT NULL AUTO_INCREMENT,");
			createSql.append("number VARCHAR(20) NOT NULL,");
			createSql.append("state INT(11) NULL DEFAULT NULL,");
			createSql.append("description VARCHAR(255) NULL DEFAULT NULL,");
			createSql.append("recycle INT(11) NULL DEFAULT NULL,");
			createSql.append("callid VARCHAR(255) NULL DEFAULT NULL,");
			createSql.append("calldate DATETIME NULL DEFAULT NULL,");
			createSql.append("answerdate DATETIME NULL DEFAULT NULL,");
			
			createSql.append("hangupdate DATETIME NULL DEFAULT NULL,");
			createSql.append("bill INT(11) NULL DEFAULT NULL,");
			createSql.append("duration INT(11) NULL DEFAULT NULL,");
			createSql.append("hangupcause VARCHAR(255) NULL DEFAULT NULL,");
			createSql.append("bridge_callid VARCHAR(255) NULL DEFAULT NULL,");
			createSql.append("bridge_number VARCHAR(20) NULL DEFAULT NULL,");
			createSql.append("bridge_calldate DATETIME NULL DEFAULT NULL,");
			
			createSql.append("bridge_answerdate DATETIME NULL DEFAULT NULL,");
			createSql.append("recordfile VARCHAR(255) NULL DEFAULT NULL,");
			createSql.append("status VARCHAR(100) NULL DEFAULT NULL,");
			createSql.append("PRIMARY KEY (id)");
			createSql.append(")");
			
			jdbcTemplate.update(createSql.toString());
		}
	}
	
	@Override
	public void insertInto(AutodialerTask autodialerTask, List<Customer> list) {
		if(autodialerTask!=null && list!=null){
			Map<String, Object> map = jdbcTemplate.queryForMap("select GROUP_CONCAT(number SEPARATOR ',') as numbers from autodialer_number_"+autodialerTask.getUuid());
			String numbers = map.get("numbers")==null?"":map.get("numbers").toString();
			for(Customer customer:list){
				customer = customerService.getById(customer.getId());
					//号码不重复
				if(StringUtils.isNotEmpty(numbers) && numbers.contains(customer.getMobile())){
					jdbcTemplate.update("update autodialer_number_"+autodialerTask.getUuid()+" set state=null where number=?", new Object[]{customer.getMobile()});
				}else{
					jdbcTemplate.update("insert into autodialer_number_"+autodialerTask.getUuid()+"(number) values (?)", new Object[]{customer.getMobile()});
				}
			}
		}
	}
}
