package com.xmxnkj.voip.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.kfbase.entity.DomainEntity;
import com.hsit.common.kfbase.entity.EntityQueryParam;
import com.hsit.common.service.AppBaseService;
import com.hsit.common.uac.entity.UserRoleType;
import com.xmxnkj.voip.common.entity.VoipEntity;
import com.xmxnkj.voip.common.entity.query.VoipQuery;
import com.xmxnkj.voip.customer.web.editor.DateEditor;
import com.xmxnkj.voip.common.entity.query.VoipQuery;
import com.xmxnkj.voip.global.Constants;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.models.ResultJson;
import com.xmxnkj.voip.web.utils.CommonExport;
import com.xmxnkj.voip.web.utils.ExportExcel;

import bsh.StringUtil;


/**
 * @ProjectName:voip
 * @ClassName: BaseController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public abstract class BaseController<T extends DomainEntity, Q extends EntityQueryParam, M extends DomainEntity> extends BaseAction{
	
	protected String getListPage() {
		if(this.getClass().isAnnotationPresent(RequestMapping.class)){
			RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
			if (requestMapping!=null) {
				if(requestMapping.value()!=null && requestMapping.value().length>0){
					return requestMapping.value()[0] + "/list";
				}
				return requestMapping.name() + "/list";
			}
		}
		return "";
	}
	
	@RequestMapping("/showPage")
	public ModelAndView showPage(String pageName, String id){
		//Boolean viewCost = userHasOperate(Constants.OP_VIEW_COST);
		ModelAndView modelAndView = new ModelAndView(getPagePath()+"/" + pageName, "params", getRequestParams());
		modelAndView.addObject("id", id);
		//modelAndView.addObject("ViewCost", viewCost);
		return modelAndView;
	}
	
	protected String getPagePath() {
		if(this.getClass().isAnnotationPresent(RequestMapping.class)){
			RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
			if (requestMapping!=null) {
				if(requestMapping.value()!=null && requestMapping.value().length>0){
					return requestMapping.value()[0];
				}
				return requestMapping.name();
			}
		}
		return "";
	}
	
	@RequestMapping("/showList")
	public ModelAndView showList(){
		//Boolean viewCost = userHasOperate(Constants.OP_VIEW_COST);
		ModelAndView modelAndView = new ModelAndView(getListPage(), "params", getRequestParams());
		//modelAndView.addObject("ViewCost", viewCost);
		initPage(modelAndView);
		return modelAndView;
	}
	
	protected String getEditPage() {
		if(this.getClass().isAnnotationPresent(RequestMapping.class)){
			RequestMapping requestMapping = this.getClass().getAnnotation(RequestMapping.class);
			if (requestMapping!=null) {
				if(requestMapping.value()!=null && requestMapping.value().length>0){
					return requestMapping.value()[0] + "/form";
				}
				return requestMapping.name() + "/form";
			}
		}
		return "";
	}
	
	@RequestMapping("/showEdit")
	public ModelAndView showEdit(String id){
		//Boolean viewCost = userHasOperate(Constants.OP_VIEW_COST);
		ModelAndView modelAndView = new ModelAndView(getEditPage(), "id", id);
		modelAndView.getModel().put("params", getRequestParams());
		//modelAndView.addObject("ViewCost", viewCost);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/list")
	@ResponseBody
	public ListJson list(Q query, Integer rows, Integer page){
		try{
			if (getService()==null) {
				return new ListJson(false);
			}
			
			if (query==null) {
				Class<Q> clazz = (Class<Q>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
				query = clazz.newInstance();
			}
			
			if (query instanceof VoipQuery) {
				((VoipQuery) query).setClientId(getLoginClientId());
				dealQuery(query);
			}
				
			if (rows!=null && rows>0) {
				
				if (page==null) {
					page=1;
				}
				query.setPage(page, rows);
			}
			
			beforeQuery(query);
			
			List<T> entities = getService().getEntities(query);
			
			initQueryResult(entities);
			
			Class<T> clazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			Class<M> clazm = (Class<M>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[2];
			
			List<M> models = null;
			ListJson listJson = null;
			if(clazm.equals(clazz)){
				listJson = new ListJson(entities);
			}else{
				models = new ArrayList<>();
				if (entities!=null) {
					for (T t : entities) {
						M m = clazm.newInstance();
						m.copy(t);
						copyEntityToModel(t, m);
						models.add(m);
					}
				}
				listJson = new ListJson(models);
			}
			afterQuery(query,listJson);
			
			listJson.setPaging(query.getPaging());
			return listJson;
			
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	
	protected void copyEntityToModel(T t, M m) {
		m.copy(t);
	}
	
	@RequestMapping("/entity")
	@ResponseBody
	public T entity(String id){
		if (getService()==null) {
			return null;
		}
		return getService().getById(id);
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
	
	protected boolean canDeleteGlobal() {
		return false;
	}
	
	protected boolean needShopId() {
		return true;
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
		dealVoipEntity(entity);
		boolean adding = StringUtils.isEmpty(entity.getId());
		initEntityForSave(entity);
		if (adding) {
			initEntityForAdd(entity);
		}else{
			initEntityForEdit(entity);
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
	/**
	 * 导出数据
	 */
	@RequestMapping(value="/exportCommon")
	@ResponseBody
	public ResultJson exportCommon(Q query) {
		ResultJson json = new ResultJson(true);
		try{
			if (getService()==null) {
				return new ResultJson(false);
			}
			if (query instanceof VoipQuery) {
				((VoipQuery) query).setClientId(getLoginClientId());
				dealQuery(query);
			}
			if (getService()==null) {
				return new ListJson(false);
			}
			
			List<T> entities = getService().getEntities(query);
			String fillName = getExportFileName(entities);//getExportFileName(entities);
			if (fillName == null || fillName.equals("")) {
				json.setSuccess(false);
				json.setMessage("数据异常！");
			}else {
				json.setEntity(fillName);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
		return json;
	}
	
	public String getExportFileName(List<T> entities){
		CommonExport<T> commonExport = new CommonExport<>();
		if (entities!=null && entities.size() > 0) {
			try {
				return commonExport.getExportFileName(entities,entities.get(0),null);//userHasOperate(Constants.OP_VIEW_COST)
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 导出下载
	 * @param fileName
	 * @throws IOException
	 */
	@RequestMapping(value="/loadExcel")
	@ResponseBody
	public void loadExcel(String fileName) throws IOException{
		ExportExcel excel = new ExportExcel();
		excel.loadExcel(fileName);
		return;
	}
	
	
	protected void dealQuery(Q entity) {
		if (entity instanceof VoipQuery) {
			VoipQuery voipEntity = (VoipQuery)entity;
			voipEntity.setClientId(getLoginClientId());
			/*if(StringUtils.isEmpty(entity.getId())
					&& needShopId()){
				try {
					Method shopMethod = entity.getClass().getMethod("setShopId", String.class);
					if (shopMethod!=null) {
						shopMethod.invoke(entity, getLoginShopId());
					}
				} catch (NoSuchMethodException | SecurityException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				}
			}*/
		}
	}
	
	protected void dealVoipEntity(T entity) {
		if (entity instanceof VoipEntity) {
			VoipEntity voipEntity = (VoipEntity)entity;
			voipEntity.setClientId(getLoginClientId());
			/*if(StringUtils.isEmpty(entity.getId())){
				try {
					Method shopMethod = entity.getClass().getMethod("seClientId", Client.class);
					if (shopMethod!=null) {
						shopMethod.invoke(entity, getLoginShop());
					}
				} catch (NoSuchMethodException | SecurityException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				}
				try {
					Method shopMethod = entity.getClass().getMethod("setShopId", String.class);
					if (shopMethod!=null) {
						shopMethod.invoke(entity, getLoginShopId());
					}
				} catch (NoSuchMethodException | SecurityException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				}
				try {
					Method shopMethod = entity.getClass().getMethod("setOpUser", ClientUser.class);
					if (shopMethod!=null) {
						shopMethod.invoke(entity, getLoginClientUser());
					}
				} catch (NoSuchMethodException | SecurityException e) {
				} catch (IllegalAccessException e) {
				} catch (IllegalArgumentException e) {
				} catch (InvocationTargetException e) {
				}
				
			}*/
		}
		
	}
	protected void initQueryResult(List<T> entities) {
		
	}
	
	protected void beforeQuery(Q query) {
		
	}
	
	protected ResultJson validateForm(){
		return null;
	}
	
	protected void initPage(ModelAndView modelAndView){
		
	}
	
	protected void afterEntitySaved() {
		
	}
	
	protected void initEntityForAdd(T entity) {
		
	}
	
	protected void initEntityForEdit(T entity) {
		
	}
	
	protected void initEntityForSave(T entity) {
		
	}
	
	protected void initEditForm() {
		
	}
	
	protected void afterQuery(Q query,ListJson listJson) {
		
	}
	
	protected boolean isSysAdmin() {
		return getLoginUser()!=null && getLoginUser().getUserRoleType()==UserRoleType.SysAdmin;
	}
	
	protected boolean isAdmin() {
		return getLoginUser()!=null && (getLoginUser().getUserRoleType()==UserRoleType.SysAdmin || getLoginUser().getUserRoleType()==UserRoleType.AreaAdmin);
	}
	
	
	
	public boolean isAdminAuthed(){
		return getSession().getAttribute("AdminAuthed")!=null && (boolean)getSession().getAttribute("AdminAuthed");
	}
	
	public void adminAuth(){
		getSession().setAttribute("AdminAuthed", true);
	}
	
	
	protected abstract AppBaseService<T, Q> getService();
	
	protected Logger logger;
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    //binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.setAutoGrowCollectionLimit(1000);
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
}
