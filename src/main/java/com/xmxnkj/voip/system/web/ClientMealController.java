package com.xmxnkj.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hsit.common.exceptions.ApplicationException;
import com.xmxnkj.voip.client.entity.OperateRole;
import com.xmxnkj.voip.client.entity.UserRole;
import com.xmxnkj.voip.client.entity.query.UserRoleQuery;
import com.xmxnkj.voip.client.service.OperateRoleService;
import com.xmxnkj.voip.system.entity.ClientMeal;
import com.xmxnkj.voip.system.entity.query.ClientMealQuery;
import com.xmxnkj.voip.system.service.ClientMealService;
import com.xmxnkj.voip.web.SystemBaseController;
import com.xmxnkj.voip.web.models.ResultJson;

/**
 * @ProjectName:voip
 * @ClassName: operationController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/clientMeal")
public class ClientMealController extends SystemBaseController<ClientMeal, ClientMealQuery, ClientMeal>{
	@Autowired
	private ClientMealService service;

	@Override
	public ClientMealService getService() {
		return service;
	}
	@Autowired
	private OperateRoleService operateRoleService;
	@RequestMapping(value="/saveMeal")
	@ResponseBody
	public ResultJson saveMeal(ClientMeal entity){
		if (getService()==null) {
			return new ResultJson(false);
		}
		if (entity==null) {
			return new ResultJson(false);
		}
		ResultJson resultJson = validateForm();
		if (resultJson!=null) {
			return resultJson;
		}
		ClientMealQuery query = new ClientMealQuery();
		query.setName(entity.getName().replaceAll(" ", ""));
		boolean adding = StringUtils.isEmpty(entity.getId());
		initEntityForSave();
		if (adding) {
			initEntityForAdd();
			entity.setAddDate(new Date());
		}else{
			query.setNotId(entity.getId());
			initEntityForEdit();
		}
		ClientMeal clientMeal = getService().getEntity(query);
		if (clientMeal != null ) {
			throw new ApplicationException("该套餐名已经存在，请重新确认！");
		}
		entity.setAddUser(getLoginUser());
		try{
			entity.setId(getService().save(entity));
			String clientMealId = entity.getId();
			List<OperateRole> list = entity.getOperateMeals();
			operateRoleService.deleteOperateMeal(clientMealId);//删除
			StringBuffer mealContent = new StringBuffer("");
			if (list != null) {
				for (OperateRole operateRole : list) {//保存
					operateRole.setClientMealId(clientMealId);
					mealContent.append(operateRole.getOperateName()+"|");
					operateRoleService.save(operateRole);
				}
			}
			entity.setMealContent(mealContent.toString());
			getService().save(entity);
			afterEntitySaved();
			ResultJson json = new ResultJson(true);
			json.setEntity(entity.getId());
			return json;
		}catch(Exception e){
			e.printStackTrace();
			return new ResultJson(e);
		}
	}
	
}
