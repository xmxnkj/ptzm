package com.xmxnkj.voip.customer.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.service.ClientUserService;
import com.xmxnkj.voip.customer.entity.CallPlan;
import com.xmxnkj.voip.customer.entity.CallState;
import com.xmxnkj.voip.customer.entity.ContactState;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.PlanState;
import com.xmxnkj.voip.customer.entity.query.CallPlanQuery;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;
import com.xmxnkj.voip.customer.service.CallPlanService;
import com.xmxnkj.voip.customer.service.CustomerService;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;
import com.xmxnkj.voip.outBound.entity.query.AutodialerTaskQuery;
import com.xmxnkj.voip.outBound.service.AutodialerTaskService;
import com.xmxnkj.voip.voice.entity.CallTimeSet;
import com.xmxnkj.voip.voice.service.ICallTimeSetService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ResultJson;

import net.sf.json.JSONObject;

/**
 * 
 * @author zjx
 *
 */
@Controller
@RequestMapping("/customer/callPlan")
public class CallPlanController extends BaseController<CallPlan, CallPlanQuery, CallPlan> {
	@Autowired
	private CallPlanService service;
	
	@Autowired
	private ClientUserService clientUserService;
	
	@Autowired
	private ICallTimeSetService iCallTimeSetService;
	
	@Autowired
	private AutodialerTaskService autodialerTaskService;
	
	@Override
	public CallPlanService getService() {
		return service;
	}
	@Autowired
	private CustomerService customerService;
	
	@Override
	protected void initEntityForAdd(CallPlan entity) {
		setCallTimeSet(entity);
		entity.setCallState(CallState.Planning);
		entity.setClientUserId(getLoginClientUserId());
		super.initEntityForAdd(entity);
	}
	
	@Override
	protected void initEntityForEdit(CallPlan entity){
		setCallTimeSet(entity);
	}

	@SuppressWarnings("unused")
	private void setCallTimeSet(CallPlan entity){
		if(StringUtils.isNotEmpty(entity.getCallTimeSets())){
			String[] ids = entity.getCallTimeSets().split(",");
			CallTimeSet CallTimeSet = null;
			entity.setCallTimeSet(new HashSet<CallTimeSet>());
			for(String id:ids){
				CallTimeSet = iCallTimeSetService.getById(id);
				if(CallTimeSet!=null){
					entity.getCallTimeSet().add(CallTimeSet);
				}
			}
		}
	}
	
	@RequestMapping("/changeCallState")
	@ResponseBody
	private ResultJson changeCallState(CallPlan callPlan,String goPlan) {
		ResultJson json = new ResultJson(true);
		CustomerQuery customerQuery = new CustomerQuery();
		customerQuery.setClientId(getLoginClientId());
		customerQuery.setCallPlanId(callPlan.getId());
		if (callPlan!=null&&callPlan.getCallState()!=null&&!StringUtils.isEmpty(callPlan.getId())) {
			if (callPlan.getCallState()==CallState.Pause) {
				if (!StringUtils.isEmpty(goPlan)) {
					callPlan.setCallState(CallState.Planning);
				}
				getService().saveSimple(callPlan);
			}else if (callPlan.getCallState()==CallState.Cancel) {
				getService().saveSimple(callPlan);
			}else if (callPlan.getCallState()==CallState.Planning) {
				List<Customer> customers = customerService.getEntities(customerQuery);
				getService().saveSimple(callPlan);
				for (Customer customer : customers) {
					customer.setContactState(ContactState.Contacting);
					customer.setPlanState(PlanState.Planned);
					customerService.saveSimple(customer);
				}
			}
		}else {
			json.setSuccess(false);
		}
		return json;
	}
	@RequestMapping("/stpTask")
	@ResponseBody
	public ResultJson stpTask() {
		CallPlanQuery callPlanQuery = new CallPlanQuery();
		callPlanQuery.setClientId(getLoginClientId());
		List<CallPlan> callPlans = getService().getEntities(callPlanQuery);
		for (CallPlan callPlan : callPlans) {
			callPlan.setCallState(CallState.Cancel);
			getService().saveSimple(callPlan);
		}
		return new ResultJson(true);
	}
	@Override
	public ResultJson deleteJson(String id) {
		// TODO Auto-generated method stub
		if (getService()==null) {
			return new ResultJson(false);
		}
		try{
			getService().delete(id);
			return new ResultJson(true);
		}catch(Exception e){
			return new ResultJson(e);
		}
	}
	
	@RequestMapping("/addCustomer")
	public String addCustomer(String callPlanId,HttpServletRequest request) {
		request.setAttribute("callPlanId", callPlanId);
		return "customer/callPlan/addCustomer";
	}
	
	//加入客户同时加入计划
	@RequestMapping("/save")
	@ResponseBody
	public Object save(String callPlanId,Customer customer) {
		JSONObject json = new JSONObject();
		try{
			customer.setClientId(getLoginClientId());
			customer.setType(0);
			customer.setCallPlan(service.getById(callPlanId));
			//判断号码
			customer.setCreateDate(new Date());
			customer.setPlanState(PlanState.Planned);
			customer.setContactState(ContactState.UnContact);
			ClientUser clientUser = clientUserService.getById(getLoginClientUserId());
			Integer privateSea = countOverCust(clientUser);
			if (privateSea-1 < 0) {
				json.put("isSuccess", false);
				json.put("message", "已满！");
				return json;
			}
			customer.setClientUser(getLoginClientUser());
			customer.setType(0);	
			customer.setIsPrivate(1);	//私人客户 不能添加至公海
			
			CustomerQuery customerQuery = new CustomerQuery();
			customerQuery.setMobile(customer.getMobile());
			customerQuery.setClientId(getLoginClientId());
			customerQuery.setClientUserId(getLoginClientUserId());
			List<Customer> customers = customerService.getEntities(customerQuery);
			for(Customer c:customers){
				if(!c.getDeleted()){
					json.put("isSuccess", false);
					json.put("message", "号码不能重复！");
					return json;
				}
			}
			/*if(customerService.getEntityCount(customerQuery)>0){
				
			}*/
			customerService.save(customer);
			//查询关联的任务
			AutodialerTaskQuery autodialerTaskQuery = new AutodialerTaskQuery();
			autodialerTaskQuery.setCallPlanId(callPlanId);
			autodialerTaskQuery.setDeleted(false);
			AutodialerTask autodialerTask = autodialerTaskService.getEntity(autodialerTaskQuery);
			if(autodialerTask==null){
				//新添加任务
			//	CallPlan callPlan = getService().getById(callPlanId); 
				autodialerTask = new AutodialerTask();
				autodialerTask.setCallPlanId(callPlanId);
				autodialerTask.setCreateTime(new Date());
				autodialerTask.setStart(1);
				autodialerTask.setMaximumcall(1);
				autodialerTask.setDestinationExtension("8888");
				autodialerTask.setAlterDatetime(new Date());
				autodialerTask.setDestinationContext("XML");
				autodialerTask.setDestinationContext("default");
				autodialerTask.setDialFormat("user/goip");
				autodialerTask.setCallPerSecond(10000);
				autodialerTask.setDeleted(false);
				autodialerTask.setClientId(getLoginClientId());
				autodialerTaskService.save(autodialerTask);
				//动态号码表	表名：	autodialer_number_{AutodialerTask的ID}	
			}
			//是否已生成表
			autodialerTaskService.createNumber(autodialerTask);
			
			List<Customer> list = new ArrayList<Customer>();
			
			list.add(customer);
			//插入任务号码表
			autodialerTaskService.insertInto(autodialerTask, list);
			
			json.put("isSuccess", true);
		}catch(Exception e){
			json.put("isSuccess", false);
			json.put("message", "接口异常！");
		}

		return json;
	}
	
	/**
	 * 计算剩余数量
	 */
	private Integer countOverCust(ClientUser clientUser){
		Integer privateSea = clientUser.getPrivateSea()!=null?clientUser.getPrivateSea():0;//用户最大坐席数量
		Integer havaSea = Integer.valueOf(customerService.privateSeaCount(getLoginClientId(), clientUser.getId(),null).toString());//用户已拥有坐席数量
		privateSea = privateSea-havaSea;
		return privateSea;
	}
}
