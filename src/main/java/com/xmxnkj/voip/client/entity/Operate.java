package com.xmxnkj.voip.client.entity;

import java.util.List;

import com.fasterxml.jackson.databind.Module;
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
public class Operate extends VoipEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1218054920636140395L;
	
	private String clientId;
	private String url;//地址或脚本
	private String pid ;//所属父ID
	private Integer grade;//级别
	private Module module;//模块
	private String text;//权限名称
	private String code;//权限代码
	private Boolean isScript;//url是脚本还是url
	private Integer displayOrder;//显示顺序
	private Boolean isShow;//是否显示
	private List<Operate> children;//子节点
	private String state;//默认展开状态
	private String css;
	
	
	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public List<Operate> getChildren() {
		return children;
	}

	public void setChildren(List<Operate> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getIsScript() {
		return isScript;
	}

	public void setIsScript(Boolean isScript) {
		this.isScript = isScript;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
}
