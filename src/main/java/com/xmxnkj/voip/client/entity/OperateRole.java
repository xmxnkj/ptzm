package com.xmxnkj.voip.client.entity;

import com.xmxnkj.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: Operate
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class OperateRole extends VoipEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1218054920636140395L;
	private String clientId;
	private UserRole userRole;
	private Operate operate;
	private String clientMealId;
	private Integer checkState;
	private String operateName;
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getClientMealId() {
		return clientMealId;
	}
	public void setClientMealId(String clientMealId) {
		this.clientMealId = clientMealId;
	}
	public Integer getCheckState() {
		return checkState;
	}
	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Operate getOperate() {
		return operate;
	}
	public void setOperate(Operate operate) {
		this.operate = operate;
	}
}
