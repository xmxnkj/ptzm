package com.xmxnkj.voip.web.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hsit.common.exceptions.ApplicationException;
import com.xmxnkj.voip.customer.entity.ContactState;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.CustomerType;
import com.xmxnkj.voip.customer.entity.PlanState;
import com.xmxnkj.voip.customer.entity.ReceivingState;



public class CommonExport<T>{

	public String getExportFileName(List<T> list,T t,Boolean flag) throws IOException{
		if (t instanceof Customer) {
			System.out.println("customer导出");
			return customer(list);
		} 
		return null;
	}
	
	public String customer(List<T> list) throws IOException{
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		String strHead = request.getParameter("strHead");
		String j = request.getParameter("j");
		String [] head = strHead.split(",");
		ExportExcel excel = new ExportExcel();
		try {
				Object  [][] str = new Object [list.size()][head.length];
				for (int i = 0; i < list.size(); i++) {
					Customer customer = (Customer) list.get(i);
					str[i][0] = customer.getCreateDate();//时间
					str[i][1] = customer.getCompanyName();//公司名
					str[i][2] = customer.getContactUser();//联系人
					str[i][3] = customer.getMobile();//电话号码
					str[i][4] = customer.getCustomerType();//客户分类
					str[i][5] = getContactState(customer.getContactState());//客户联系状态
					str[i][6] = getPlanState(customer.getPlanState());//计划状态
					str[i][7] = getReceivingState(customer.getReceivingState());//接听状态
					str[i][8] = customer.getCallPlan()!=null?customer.getCallPlan().getName():"";//拨打计划
					str[i][9] = customer.getCallPlan()!=null?(customer.getCallPlan().getVoiceTemplate()!=null?customer.getCallPlan().getVoiceTemplate().getName():""):"";//语音模板
					str[i][10] = customer.getClientUser()!=null?customer.getClientUser().getName():"";//所属坐席
					str[i][11] = customer.getLastCallDate();//上次拨打
					str[i][12] = customer.getTalkTime();//通话时长
				}
				return excel.exportExcel(str,head);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String changeStr(Object value){
		String str = value==null?"":value+"";
		return str;
	}
	//客户联系状态
	private String getContactState(ContactState contactState) {
		String s = "";
		if (contactState==null) {
			return s;
		}
		switch (contactState.toString()) {
		case "Contacted":
			s = "已联系";
			break;
		case "Contacting":
			s = "联系中";		
			break;
		case "UnContact":
			s = "未联系";
			break;
		default:
			break;
		}
		return s;
	}
	//计划状态
	private String getPlanState(PlanState planState) {
		String s = "";
		if (planState==null) {
			return s;
		}
		switch (planState.toString()) {
		case "Contact":
			s = "已联系";
			break;
		case "Planned":
			s = "已入进化";		
			break;
		case "UnPlan":
			s = "未入计划";
			break;
		default:
			break;
		}
		return s;
	}
	//接听状态
	private String getReceivingState(ReceivingState receivingState) {
		String s = "";
		if (receivingState==null) {
			return s;
		}
		switch (receivingState.toString()) {
		case "Answer":
			s = "正常接听";
			break;
		case "Busy":
			s = "线路忙（占线）";		
			break;
		case "Empty":
			s = "空号";
			break;
		case "HangUpAfAns":
			s = "接听后挂断";		
			break;
		case "IOException":
			s = "系统IO异常";
			break;
		case "NoAnswer":
			s = "无人接听";		
			break;
		case "NoSpeakAfAns":
			s = "接听后无人说话";
			break;
		default:
			break;
		}
		return s;
	}
}
