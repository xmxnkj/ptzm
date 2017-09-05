package com.xmxnkj.voip.web;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.management.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.kfbase.entity.DomainEntity;
import com.hsit.common.kfbase.entity.EntityQueryParam;
import com.hsit.common.service.AppBaseService;
import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.models.ResultJson;

/**
 * @ProjectName:lightning
 * @ClassName: BaseController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public abstract class BaseController<T extends DomainEntity, Q extends EntityQueryParam> extends BaseAction{
	
	protected String getListPage() {
		return "";
	}
	
	@RequestMapping("/showList")
	public ModelAndView showList(){
		return new ModelAndView(getListPage());
	}
	
	
	@RequestMapping(value="/list")
	@ResponseBody
	public ListJson list(Q query, Integer rows, Integer page){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			
			beforeQuery();
			
			if (rows!=null && rows>0) {
				if (query==null) {
					Class<Q> clazz = (Class<Q>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
					query = clazz.newInstance();
				}
				if (page==null) {
					page=1;
				}
				query.setPage(page, rows);
			}
			
			List<T> entities = getService().getEntities(query);
			
			initQueryResult();
			
			return new ListJson(entities);
			
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	@RequestMapping(value="/query")
	@ResponseBody
	public ResultJson query(String id){
		try{
			if (getService()==null) {
				return new ResultJson(false);
			}
			
			T entity = getService().getById(id);
			
			if (entity==null) {
				return new ResultJson(false);
			}
			
			initEditForm();
			
			ResultJson resultJson = new ResultJson(true);
			resultJson.setEntity(entity);
			
			return resultJson;
			
		}catch(Exception e){
			return new ResultJson(e);
		}
	}
	
	
	@RequestMapping(value="/deleteJson")
	@ResponseBody
	public ResultJson deleteJson(String id) {
		if (getService()==null) {
			return new ResultJson(false);
		}
		try{
			getService().deleteById(id);
			return new ResultJson(true);
		}catch(Exception e){
			return new ResultJson(e);
		}
	}
	
	
	@RequestMapping(value="/saveJson")
	@ResponseBody
	public ResultJson saveJson(T entity, String objName){
		if (getService()==null) {
			return new ResultJson(false);
		}
		
		if (entity==null) {
			return new ResultJson(false);
		}
		
		//以name为参数，会莫名其妙的出错，故用objName代替
		if (!StringUtils.isEmpty(objName)) {
			entity.setName(objName);
		}
		
		ResultJson resultJson = validateForm();
		if (resultJson!=null) {
			return resultJson;
		}
		
		boolean adding = StringUtils.isEmpty(entity.getId());
		initEntityForSave();
		if (adding) {
			initEntityForAdd();
		}else{
			initEntityForEdit();
		}
		
		try{
			entity.setId(getService().save(entity));
			afterEntitySaved();
			
			ResultJson json = new ResultJson(true);
			json.setEntity(entity.getId());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return new ResultJson(e);
		}
	}
	
	
	protected void initQueryResult() {
		
	}
	
	protected void beforeQuery() {
		
	}
	
	protected ResultJson validateForm(){
		return null;
	}
	
	protected void afterEntitySaved() {
		
	}
	
	protected void initEntityForAdd() {
		
	}
	
	protected void initEntityForEdit() {
		
	}
	
	protected void initEntityForSave() {
		
	}
	
	protected void initEditForm() {
		
	}
	
	
	
	
	
	
	protected abstract AppBaseService<T, Q> getService();
	
	protected Logger logger = LoggerFactory.getLogger("operate");
}
