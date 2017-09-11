package com.xmxnkj.voip.client.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsit.common.kfbase.entity.BusinessObject;
import com.hsit.common.uac.entity.User;
import com.xmxnkj.voip.system.entity.BaseArea;
import com.xmxnkj.voip.system.entity.ClientMeal;

/**
 * @ProjectName:voip
 * @ClassName: Client
 * @Description:
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
public class Client extends BusinessObject {
	

	private int idNumber=0;//

	private String loginName;// 登入名

	private String password;// 密码

	private String name;// 姓名

	private String cellPhone;// 电话

	private String divisionCode;// 部门代码

	private String division;// 部门

	private Date createTime;// 创建时间

	private String createUser;// 创建人

	private Date updateTime;// 更新时间

	//消费获得积分
	private Boolean salePoint;	
	//售卡获得积分
	private Boolean cardPoint;
	//所有客户获得积分
	private Boolean allCustomerPoint;
	//会员卡用户获得积分
	private Boolean cardCustomerPoint;
	//销售金额与积分的比例
	private Double salePointRate;
	//售卡金额与积分的比例
	private Double cardPointRate;
	//积分兑换比例
	private Double pointMoneyRate;
	//是否允许积分兑换
	private Boolean pointMoney;

	//跟单提成比例或金额
	private Double followAward;
	//检车提成方式或比例
	private Double checkAward;
	//检车跟单提成
	private Double checkCarFollowAward;
	
	
	//产品编号前缀
	private String productHeader;
	//产品编号长度
	private Integer productLen;
	//服务编号前缀
	private String serviceHeader;
	//服务编号长度
	private Integer serviceLen;
	//卡号前缀
	private String cardHeader;
	//卡号长度
	private Integer cardLen;
	
	//会员套餐到期提醒
	private Integer cardRemindDays;
	private Integer cardRemindTimes;
	private Integer cardRemindInterval;
	
	//保险到期提醒
	private Integer insuranceRemindDays;
	private Integer insuranceRemindTimes;
	private Integer insuranceRemindInterval;
	
	//年检到期提醒
	private Integer yearCheckRemindDays;
	private Integer yearCheckRemindTimes;
	private Integer yearCheckRemindInterval;
	
	//保养到期提醒
	private Integer careRemindDays;
	private Integer careRemindTimes;
	private Integer careRemindInterval;
	
	//售后到期提醒
	private Integer saleRemindDays;
	private Integer saleRemindTimes;
	private Integer saleRemindInterval;
	
	//生日到期提醒
	private Integer bornRemindDays;
	private Integer bornRemindInterval;
	
	private Double taxRate;
	
	
	private String wxAppId;
	private String wxSecret;
	//小程序appId 密匙
	private String wxMiniAppId;
	private String wxMiniSecret;
	//服务器配置的token
	private String wxToken;
	private String merchantId;
	private String payKey;
	//禁止信用卡支付
	private Boolean forbitCredit;
	
	
	//开单结算消息通知模板ID，提醒对象：客户
	private String saleMsgTplId;
	//施工消息模板，通知对象：跟单员、各明细施工员
	private String constructMsgTplId;
	//施工消息模板，通知对象：客户
	private String constructCustMsgTplId;
	
	
	//施工进度消息模板，通知对象：跟单员、客户
	private String dealConstructMsgTplId;
	
	//预约消息通知模板ID，提醒对象：客户
	private String preOrderMsgTplId;
	//开卡消息模板，提醒对象：客户
	private String cardMsgTplId;
	//检车消息模板，通知对象：跟单员、检车员
	private String checkMsgTplId;
	//完成检车消息模板，通知对象：跟单员、客户
	private String finishCheckMsgTplId;
	
	//报价消息模板，通知对象：跟单员、客户
	private String priceMsgTplId;
	//售后服务提醒消息模板，提醒对象：客户、指定服务顾问
	private String saleServiceMsgTplId;
	//会员套餐到期提醒消息模板，提醒对象：客户、指定服务顾问
	private String customerCardExpiredMsgTplId;
	//年检到期提醒消息模板，提醒对象：客户、指定服务顾问
	private String yearCheckMsgTplId;
	//保险到期提醒消息模板，提醒对象：客户、指定服务顾问
	private String insuranceMsgTplId;
	//养到期提醒消息模板，提醒对象：客户、指定服务顾问 
	private String careMsgTplId;
	//商城购买提醒消息模板，提醒对象：各个商品的指定人员
	private String mallOrderMsgTplId;
	
	//商城预约通知模块ID
	private String mallPreOrderMsgTplId;
	
	//轮播图、店况视频
	private String img_a;
	private String img_b;
	private String img_c;
	private String videoUrl;
	private String loginLimitTime;//登入限制时间

	//区域
	private BaseArea area;
	//省 市 县
	private String province;
	private String city;
	private String county;
	private String address;
	//负责人
	private String responsibleUser;
	//选择套餐（对象？ID？）
	private ClientMeal clientMeal;
	//有效期
	private Date effectiveDate;
	//状态
	private Boolean state;
	//备注
	private String description;
	//添加人
	private User addUser;
	//启用日期
	private Date useDate;
	
	
	
	public BaseArea getArea() {
		return area;
	}

	public void setArea(BaseArea area) {
		this.area = area;
	}

	public ClientMeal getClientMeal() {
		return clientMeal;
	}

	public void setClientMeal(ClientMeal clientMeal) {
		this.clientMeal = clientMeal;
	}

	public String getWxMiniAppId() {
		return wxMiniAppId;
	}

	public void setWxMiniAppId(String wxMiniAppId) {
		this.wxMiniAppId = wxMiniAppId;
	}

	public String getWxMiniSecret() {
		return wxMiniSecret;
	}

	public void setWxMiniSecret(String wxMiniSecret) {
		this.wxMiniSecret = wxMiniSecret;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh" , timezone="GMT+8")
	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public User getAddUser() {
		return addUser;
	}

	public void setAddUser(User addUser) {
		this.addUser = addUser;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(String responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", locale = "zh" , timezone="GMT+8")
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBornRemindInterval() {
		return bornRemindInterval;
	}

	public void setBornRemindInterval(Integer bornRemindInterval) {
		this.bornRemindInterval = bornRemindInterval;
	}

	public String getLoginLimitTime() {
		return loginLimitTime;
	}

	public void setLoginLimitTime(String loginLimitTime) {
		this.loginLimitTime = loginLimitTime;
	}

	public Double getCheckCarFollowAward() {
		return checkCarFollowAward;
	}

	public void setCheckCarFollowAward(Double checkCarFollowAward) {
		this.checkCarFollowAward = checkCarFollowAward;
	}
	public String getMallPreOrderMsgTplId() {
		return mallPreOrderMsgTplId;
	}

	public void setMallPreOrderMsgTplId(String mallPreOrderMsgTplId) {
		this.mallPreOrderMsgTplId = mallPreOrderMsgTplId;
	}

	public String getConstructCustMsgTplId() {
		return constructCustMsgTplId;
	}

	public void setConstructCustMsgTplId(String constructCustMsgTplId) {
		this.constructCustMsgTplId = constructCustMsgTplId;
	}

	public Boolean getForbitCredit() {
		return forbitCredit;
	}

	public void setForbitCredit(Boolean forbitCredit) {
		this.forbitCredit = forbitCredit;
	}

	public String getImg_a() {
		return img_a;
	}

	public void setImg_a(String img_a) {
		this.img_a = img_a;
	}

	public String getImg_b() {
		return img_b;
	}

	public void setImg_b(String img_b) {
		this.img_b = img_b;
	}

	public String getImg_c() {
		return img_c;
	}

	public void setImg_c(String img_c) {
		this.img_c = img_c;
	}

	
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}

	public String getConstructMsgTplId() {
		return constructMsgTplId;
	}

	public void setConstructMsgTplId(String constructMsgTplId) {
		this.constructMsgTplId = constructMsgTplId;
	}

	public String getCardMsgTplId() {
		return cardMsgTplId;
	}

	public void setCardMsgTplId(String cardMsgTplId) {
		this.cardMsgTplId = cardMsgTplId;
	}

	public String getCheckMsgTplId() {
		return checkMsgTplId;
	}

	public void setCheckMsgTplId(String checkMsgTplId) {
		this.checkMsgTplId = checkMsgTplId;
	}

	public String getFinishCheckMsgTplId() {
		return finishCheckMsgTplId;
	}

	public void setFinishCheckMsgTplId(String finishCheckMsgTplId) {
		this.finishCheckMsgTplId = finishCheckMsgTplId;
	}

	public String getDealConstructMsgTplId() {
		return dealConstructMsgTplId;
	}

	public void setDealConstructMsgTplId(String dealConstructMsgTplId) {
		this.dealConstructMsgTplId = dealConstructMsgTplId;
	}

	public String getPriceMsgTplId() {
		return priceMsgTplId;
	}

	public void setPriceMsgTplId(String priceMsgTplId) {
		this.priceMsgTplId = priceMsgTplId;
	}

	public String getSaleServiceMsgTplId() {
		return saleServiceMsgTplId;
	}

	public void setSaleServiceMsgTplId(String saleServiceMsgTplId) {
		this.saleServiceMsgTplId = saleServiceMsgTplId;
	}

	public String getCustomerCardExpiredMsgTplId() {
		return customerCardExpiredMsgTplId;
	}

	public void setCustomerCardExpiredMsgTplId(String customerCardExpiredMsgTplId) {
		this.customerCardExpiredMsgTplId = customerCardExpiredMsgTplId;
	}

	public String getYearCheckMsgTplId() {
		return yearCheckMsgTplId;
	}

	public void setYearCheckMsgTplId(String yearCheckMsgTplId) {
		this.yearCheckMsgTplId = yearCheckMsgTplId;
	}

	public String getInsuranceMsgTplId() {
		return insuranceMsgTplId;
	}

	public void setInsuranceMsgTplId(String insuranceMsgTplId) {
		this.insuranceMsgTplId = insuranceMsgTplId;
	}

	public String getCareMsgTplId() {
		return careMsgTplId;
	}

	public void setCareMsgTplId(String careMsgTplId) {
		this.careMsgTplId = careMsgTplId;
	}

	public String getMallOrderMsgTplId() {
		return mallOrderMsgTplId;
	}

	public void setMallOrderMsgTplId(String mallOrderMsgTplId) {
		this.mallOrderMsgTplId = mallOrderMsgTplId;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getPreOrderMsgTplId() {
		return preOrderMsgTplId;
	}

	public void setPreOrderMsgTplId(String preOrderMsgTplId) {
		this.preOrderMsgTplId = preOrderMsgTplId;
	}

	public String getSaleMsgTplId() {
		return saleMsgTplId;
	}

	public void setSaleMsgTplId(String saleMsgTplId) {
		this.saleMsgTplId = saleMsgTplId;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWxSecret() {
		return wxSecret;
	}

	public void setWxSecret(String wxSecret) {
		this.wxSecret = wxSecret;
	}

	public Double getTaxRate() {
		return taxRate!=null?taxRate:0;
	}
	public Double getTaxRateOriginal() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	public Double getFollowAward() {
		return followAward;
	}

	public void setFollowAward(Double followAward) {
		this.followAward = followAward;
	}

	public Double getCheckAward() {
		return checkAward;
	}

	public void setCheckAward(Double checkAward) {
		this.checkAward = checkAward;
	}

	public Boolean getSalePoint() {
		return true;
	}

	public void setSalePoint(Boolean salePoint) {
		this.salePoint = salePoint;
	}

	public Boolean getCardPoint() {
		return true;
	}

	public void setCardPoint(Boolean cardPoint) {
		this.cardPoint = cardPoint;
	}

	public Boolean getAllCustomerPoint() {
		return allCustomerPoint!=null?allCustomerPoint:false;
	}
	public Boolean getAllCustomerPointOriginal() {
		return allCustomerPoint;
	}

	public void setAllCustomerPoint(Boolean allCustomerPoint) {
		this.allCustomerPoint = allCustomerPoint;
	}

	public Boolean getCardCustomerPoint() {
		return cardCustomerPoint!=null?cardCustomerPoint:true;
	}
	public Boolean getCardCustomerPointOriginal() {
		return cardCustomerPoint;
	}

	public void setCardCustomerPoint(Boolean cardCustomerPoint) {
		this.cardCustomerPoint = cardCustomerPoint;
	}

	public Double getSalePointRate() {
		//默认为1
		return salePointRate!=null?salePointRate:1;
	}
	public Double getSalePointRateOriginal() {
		//默认为1
		return salePointRate;
	}

	public void setSalePointRate(Double salePointRate) {
		this.salePointRate = salePointRate;
	}

	public Double getCardPointRate() {
		//默认为1
		return cardPointRate!=null?cardPointRate:1;
	}
	public Double getCardPointRateOriginal() {
		//默认为1
		return cardPointRate;
	}

	public void setCardPointRate(Double cardPointRate) {
		this.cardPointRate = cardPointRate;
	}

	public Double getPointMoneyRate() {
		return pointMoneyRate;
	}

	public void setPointMoneyRate(Double pointMoneyRate) {
		this.pointMoneyRate = pointMoneyRate;
	}

	public Boolean getPointMoney() {
		return pointMoney!=null ? pointMoney : false;
	}
	public Boolean getPointMoneyOriginal() {
		return pointMoney;
	}

	public void setPointMoney(Boolean pointMoney) {
		this.pointMoney = pointMoney;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	private String updateUser;// 更新人

	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getProductHeader() {
		return productHeader;
	}

	public void setProductHeader(String productHeader) {
		this.productHeader = productHeader;
	}

	public Integer getProductLen() {
		return productLen;
	}

	public void setProductLen(Integer productLen) {
		this.productLen = productLen;
	}

	public String getServiceHeader() {
		return serviceHeader;
	}

	public void setServiceHeader(String serviceHeader) {
		this.serviceHeader = serviceHeader;
	}

	public Integer getServiceLen() {
		return serviceLen;
	}

	public void setServiceLen(Integer serviceLen) {
		this.serviceLen = serviceLen;
	}

	public String getCardHeader() {
		return cardHeader;
	}

	public void setCardHeader(String cardHeader) {
		this.cardHeader = cardHeader;
	}

	public Integer getCardLen() {
		return cardLen;
	}

	public void setCardLen(Integer cardLen) {
		this.cardLen = cardLen;
	}

	public Integer getCardRemindDays() {
		return cardRemindDays;
	}

	public void setCardRemindDays(Integer cardRemindDays) {
		this.cardRemindDays = cardRemindDays;
	}

	public Integer getCardRemindTimes() {
		return cardRemindTimes;
	}

	public void setCardRemindTimes(Integer cardRemindTimes) {
		this.cardRemindTimes = cardRemindTimes;
	}

	public Integer getCardRemindInterval() {
		return cardRemindInterval;
	}

	public void setCardRemindInterval(Integer cardRemindInterval) {
		this.cardRemindInterval = cardRemindInterval;
	}

	public Integer getInsuranceRemindDays() {
		return insuranceRemindDays;
	}

	public void setInsuranceRemindDays(Integer insuranceRemindDays) {
		this.insuranceRemindDays = insuranceRemindDays;
	}

	public Integer getInsuranceRemindTimes() {
		return insuranceRemindTimes;
	}

	public void setInsuranceRemindTimes(Integer insuranceRemindTimes) {
		this.insuranceRemindTimes = insuranceRemindTimes;
	}

	public Integer getInsuranceRemindInterval() {
		return insuranceRemindInterval;
	}

	public void setInsuranceRemindInterval(Integer insuranceRemindInterval) {
		this.insuranceRemindInterval = insuranceRemindInterval;
	}

	public Integer getYearCheckRemindDays() {
		return yearCheckRemindDays;
	}

	public void setYearCheckRemindDays(Integer yearCheckRemindDays) {
		this.yearCheckRemindDays = yearCheckRemindDays;
	}

	public Integer getYearCheckRemindTimes() {
		return yearCheckRemindTimes;
	}

	public void setYearCheckRemindTimes(Integer yearCheckRemindTimes) {
		this.yearCheckRemindTimes = yearCheckRemindTimes;
	}

	public Integer getYearCheckRemindInterval() {
		return yearCheckRemindInterval;
	}

	public void setYearCheckRemindInterval(Integer yearCheckRemindInterval) {
		this.yearCheckRemindInterval = yearCheckRemindInterval;
	}

	public Integer getCareRemindDays() {
		return careRemindDays;
	}

	public void setCareRemindDays(Integer careRemindDays) {
		this.careRemindDays = careRemindDays;
	}

	public Integer getCareRemindTimes() {
		return careRemindTimes;
	}

	public void setCareRemindTimes(Integer careRemindTimes) {
		this.careRemindTimes = careRemindTimes;
	}

	public Integer getCareRemindInterval() {
		return careRemindInterval;
	}

	public void setCareRemindInterval(Integer careRemindInterval) {
		this.careRemindInterval = careRemindInterval;
	}

	public Integer getSaleRemindDays() {
		return saleRemindDays;
	}

	public void setSaleRemindDays(Integer saleRemindDays) {
		this.saleRemindDays = saleRemindDays;
	}

	public Integer getSaleRemindTimes() {
		return saleRemindTimes;
	}

	public void setSaleRemindTimes(Integer saleRemindTimes) {
		this.saleRemindTimes = saleRemindTimes;
	}

	public Integer getSaleRemindInterval() {
		return saleRemindInterval;
	}

	public void setSaleRemindInterval(Integer saleRemindInterval) {
		this.saleRemindInterval = saleRemindInterval;
	}

	public Integer getBornRemindDays() {
		return bornRemindDays;
	}

	public void setBornRemindDays(Integer bornRemindDays) {
		this.bornRemindDays = bornRemindDays;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}
}