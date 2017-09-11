package com.xmxnkj.voip.system.service;



import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.system.entity.ClientMeal;
import com.xmxnkj.voip.system.entity.query.ClientMealQuery;

/**
 * @ProjectName:voip
 * @ClassName: ClientMealService
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public interface ClientMealService extends BusinessBaseService<ClientMeal, ClientMealQuery>{
	public Integer countMealAmount(String mealId);

}
