package com.xmxnkj.voip.system.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.client.entity.OperateRole;
import com.xmxnkj.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: ClientMeal
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientMeal extends VoipEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3148099485755519218L;
	//套餐名称
	private String name;
	//使用数量
	private Integer useAmount;
	//年费价格
	private Double yearPrice;
	//套餐状态
	private Boolean mealState;
	//添加时间
	private Date addDate;
	//套餐内容
	private String mealContent; 
	//添加人
	private User addUser;
	//限制门店
	private Integer shopAmount;
	private List<OperateRole> operateMeals;
	
	
	public Integer getShopAmount() {
		return shopAmount;
	}
	public void setShopAmount(Integer shopAmount) {
		this.shopAmount = shopAmount;
	}
	public List<OperateRole> getOperateMeals() {
		return operateMeals;
	}
	public void setOperateMeals(List<OperateRole> operateMeals) {
		this.operateMeals = operateMeals;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUseAmount() {
		return useAmount;
	}
	public void setUseAmount(Integer useAmount) {
		this.useAmount = useAmount;
	}
	public Double getYearPrice() {
		return yearPrice;
	}
	public void setYearPrice(Double yearPrice) {
		this.yearPrice = yearPrice;
	}
	public Boolean getMealState() {
		return mealState;
	}
	public void setMealState(Boolean mealState) {
		this.mealState = mealState;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",locale="zh",timezone="GMT+8")
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public String getMealContent() {
		return mealContent;
	}
	public void setMealContent(String mealContent) {
		this.mealContent = mealContent;
	}
	public User getAddUser() {
		return addUser;
	}
	public void setAddUser(User addUser) {
		this.addUser = addUser;
	}
	
}
