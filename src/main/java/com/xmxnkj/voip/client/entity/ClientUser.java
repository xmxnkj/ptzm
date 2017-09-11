package com.xmxnkj.voip.client.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmxnkj.voip.client.entity.en.IsOnSeatGroup;
import com.xmxnkj.voip.common.entity.VoipEntity;

/**
 * @ProjectName:voip
 * @ClassName: ClientUser
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class ClientUser extends VoipEntity{
	/**
	 * 
	 */
	public ClientUser() {
		super();
	}
	
	public ClientUser(String id){
		this();
		this.setId(id);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4498610731669803309L;

	private Integer  sex;// 性别	0 男 1 女
	
	private Date  birthday;//生日
	
	private String  idCard;//身份证
	
	private String address;//地址
	
	private String  nowAddress;//现居地址 
	
	private String  phone;//手机号
	
	private String  urgentPhone;//紧急电话

	private Date  entryTime;//入职时间

	private String  remark;//备注
	
	private String email;	//邮箱
	
	private Integer privateSea;	//私海容量
	
	private Integer aiCount;//AI数
	
	private String deptId;	//部门id
	
	private Dept dept;		//部门
	
	private String  roleIds;	//权限ID
	
	private String roleNames;	//职位
	
	private String seatId="0000";	//所属座机		0000为未分配
		
	private IsOnSeatGroup isOnSeatGroup;	//是否为坐席组	YES NO
	
	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	private String account;
	private String passwd;
	private String md5Passwd;
	
	private Boolean isUser;//是否是用户
	private Boolean isAllow;//是否允许登陆
	private Boolean isManager;//是否是管理员
	private String describes;//MAC地址
	private Boolean notLimit;//不受登入时间与MAC限制(不显示在任何地方)
	private Date lastLoginTime;//最后登录时间
	private Date lastExitTime;//最后登出时间
	
	private Boolean onlineStatu;//是否在线
	
	private Integer loginAmount;
	
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	
	public IsOnSeatGroup getIsOnSeatGroup() {
		return isOnSeatGroup;
	}

	public void setIsOnSeatGroup(IsOnSeatGroup isOnSeatGroup) {
		this.isOnSeatGroup = isOnSeatGroup;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPrivateSea() {
		return privateSea;
	}

	public void setPrivateSea(Integer privateSea) {
		this.privateSea = privateSea;
	}

	public Integer getAiCount() {
		return aiCount;
	}

	public void setAiCount(Integer aiCount) {
		this.aiCount = aiCount;
	}

	public Boolean getNotLimit() {
		return notLimit;
	}

	public void setNotLimit(Boolean notLimit) {
		this.notLimit = notLimit;
	}

	public Integer getLoginAmount() {
		return loginAmount;
	}

	public void setLoginAmount(Integer loginAmount) {
		this.loginAmount = loginAmount;
	}

	public Boolean getOnlineStatu() {
		return onlineStatu;
	}

	public void setOnlineStatu(Boolean onlineStatu) {
		this.onlineStatu = onlineStatu;
	}

	public Boolean getIsAllow() {
		return isAllow;
	}

	public void setIsAllow(Boolean isAllow) {
		this.isAllow = isAllow;
	}

	private List<UserRole> items;

	public List<UserRole> getItems() {
		return items;
	}

	public void setItems(List<UserRole> items) {
		this.items = items;
	}
	

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getLastExitTime() {
		return lastExitTime;
	}

	public void setLastExitTime(Date lastExitTime) {
		this.lastExitTime = lastExitTime;
	}

	public Boolean getIsUser() {
		return isUser;
	}

	public void setIsUser(Boolean isUser) {
		this.isUser = isUser;
	}

	public Boolean getIsManager() {
		return isManager!=null?isManager:false;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public String getMd5Passwd() {
		return md5Passwd;
	}

	public void setMd5Passwd(String md5Passwd) {
		this.md5Passwd = md5Passwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUrgentPhone() {
		return urgentPhone;
	}

	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
