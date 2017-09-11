package com.xmxnkj.voip.customer.web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.exceptions.ApplicationException;
import com.xmxnkj.voip.client.entity.ClientUser;
import com.xmxnkj.voip.client.entity.Dept;
import com.xmxnkj.voip.client.entity.UserRole;
import com.xmxnkj.voip.client.entity.query.ClientUserQuery;
import com.xmxnkj.voip.client.entity.query.UserRoleQuery;
import com.xmxnkj.voip.client.service.ClientUserService;
import com.xmxnkj.voip.client.service.UserRoleService;
import com.xmxnkj.voip.customer.entity.CallPlan;
import com.xmxnkj.voip.customer.entity.ContactState;
import com.xmxnkj.voip.customer.entity.Customer;
import com.xmxnkj.voip.customer.entity.PlanState;
import com.xmxnkj.voip.customer.entity.query.CustomerQuery;
import com.xmxnkj.voip.customer.service.CallPlanService;
import com.xmxnkj.voip.customer.service.CallRecordService;
import com.xmxnkj.voip.customer.service.CustomerService;
import com.xmxnkj.voip.customer.web.models.CountCustomerType;
import com.xmxnkj.voip.customer.web.models.CustomerList;
import com.xmxnkj.voip.outBound.entity.AutodialerTask;
import com.xmxnkj.voip.outBound.entity.query.AutodialerTaskQuery;
import com.xmxnkj.voip.outBound.service.AutodialerTaskService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.models.ResultJson;

/**
 * 
 * @author zjx
 *
 */
@Controller
@RequestMapping("/customer/customer")
public class CustomerController extends BaseController<Customer, CustomerQuery, Customer> {
	@Autowired
	private CustomerService service;

	@Override
	public CustomerService getService() {
		return service;
	}
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private ClientUserService clientUserService;
	@Autowired
	private CallRecordService callRecordService;
	@Autowired
	private AutodialerTaskService autodialerTaskService;
	@Autowired
	private CallPlanService callPlanService;
	
	@Override
	public ListJson list(CustomerQuery query, Integer rows, Integer page) {
		// TODO Auto-generated method stub
		ClientUser clientUser = getLoginClientUser();
		Dept dept = clientUser.getDept();
		if (dept!=null&&
				!StringUtils.isEmpty(dept.getClientUserId())&&
				getLoginClientUserId().indexOf(dept.getClientUserId()) > -1) {
			if (query.getType()==0) {
				query.setDeptId(dept.getId());
			}
		}else {
			if (query.getType()==0) {
				query.setClientUserId(getLoginClientUserId());
			}
		}
		
		return super.list(query, rows, page);
	}
	@Override
	public ModelAndView showPage(String pageName, String id) {
		BigInteger g = null;
		if (pageName.equals("seatList")||pageName.equals("customerList")) {
			/*String clientUserId = null;
			String notClientUser = getRequest().getParameter("notClientUser");
			if (StringUtils.isEmpty(notClientUser)||!notClientUser.equals("1")) {
				clientUserId = getLoginClientUserId();
			}*/
			g = getService().privateSeaCount(getLoginClientId(), getLoginClientUserId(),null);
		}
		
		ModelAndView modelAndView = new ModelAndView(getPagePath()+"/" + pageName, "params", getRequestParams());
		modelAndView.addObject("id", id);
		modelAndView.addObject("privateSeaCount", g!=null?g:0);
		modelAndView.addObject("clientUserPrivateSea", clientUserService.getById(getLoginClientUserId()).getPrivateSea());
		//modelAndView.addObject("ViewCost", viewCost);
		return modelAndView;
	}
	/**
	 * 导入
	 * @param file
	 * @param type
	 * @return
	 */
	@RequestMapping("/importCustomer")
	private ModelAndView importCustomer(MultipartFile file,String type) {
		ModelAndView view = new ModelAndView("customer/customer/import");
		Map<String, String> params =new HashMap<String, String>();
		params.put("type", type);
		view.addObject("params",params);
		String name = file.getOriginalFilename();
		if (StringUtils.isEmpty(name)||file.getSize()==0||StringUtils.isEmpty(type)) {
			view.addObject("rst", "<font color='red'>导入失败！</font>");
			view.addObject("error", "<font color='red'>上传文件异常！请重试!</font>");
			return view;
		}
		String postFix = name.substring(name.lastIndexOf(".") + 1, name.length());
		if (!postFix.equals("xlsx")&&!postFix.equals("xls")) {
			view.addObject("rst", "<font color='red'>导入失败！</font>");
			view.addObject("error", "<font color='red'>请选择excel文件!</font>");
			return view;
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			String result = service.importCustomer(sheet,type,getLoginClientId(),getLoginClientUser());
			if(StringUtils.isEmpty(result)){
				view.addObject("rst", "<font color='green'>导入成功！</font>");
			}else{
				view.addObject("rst", "<font color='green'>"+result+"</font>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			view.addObject("rst", "<font color='red'>导入失败！</font>");
			view.addObject("error", "<font color='red'>"+e.getMessage()+"</font>");
		}finally {
			if (workbook!=null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return view;
	}
	/**
	 * 批量删除
	 */
	@Override
	public ResultJson deleteJson(String id) {
		if (StringUtils.isEmpty(id)) {
			return  new ResultJson(false);
		}
		String [] ids = id.split(",");
		callRecordService.delCallRecord(ids);
		for (String delId : ids) {
			getService().deleteData(delId);
		}
		return  new ResultJson(true);
	}
	@Override
	protected void initEntityForAdd(Customer entity) {
		// TODO Auto-generated method stub
		entity.setCreateDate(new Date());
		entity.setPlanState(PlanState.UnPlan);
		entity.setContactState(ContactState.UnContact);
		if (entity.getType()==0) {
			ClientUser clientUser = clientUserService.getById(getLoginClientUserId());
			/*Integer privateSea = clientUser.getPrivateSea()!=null?clientUser.getPrivateSea():0;//用户最大坐席数量
			Integer havaSea = Integer.valueOf(getService().privateSeaCount(getLoginClientId(), clientUser.getId(),null).toString());//用户已拥有坐席数量
			privateSea = privateSea-havaSea-1;*/
			Integer privateSea = countOverCust(clientUser);
			if (privateSea-1 < 0) {
				throw new ApplicationException("已满！");
			}
			entity.setClientUser(getLoginClientUser());
		}
		super.initEntityForAdd(entity);
	}
	@Override
	protected void initEntityForSave(Customer entity) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(entity.getMobile())) {
			throw new ApplicationException("号码不能为空！");
		}else {
			if (getService().isExistCustomer(entity.getMobile(), getLoginClientId(),entity.getId())) {
				throw new ApplicationException("号码重复！");
			}
			
		}
		super.initEntityForSave(entity);
	}
	
	
	@RequestMapping("/open")
	@ResponseBody
	public ResultJson open(CustomerList customerList) {
		ResultJson resultJson = new ResultJson(true);
		resultJson.setMessage("开放客户库成功！");
		List<Customer> list = customerList.getItems();
		StringBuffer openFailMessage = new StringBuffer();
		if (list!=null && list.size() > 0) {
			for (Customer customer : list) {
				customer = service.getById(customer.getId());
				if(customer.getIsPrivate()==null || (customer.getIsPrivate()!=null && customer.getIsPrivate()!=1)){
					getService().open(customer.getId());
				}else{
					openFailMessage.append(customer.getMobile()+" ");
				}
			}
		}
		if(StringUtils.isNotEmpty(openFailMessage.toString())){
			openFailMessage.append(" 为私有，不能开放!");
			resultJson.setMessage(openFailMessage.toString());
		}
		return  resultJson;
	}
	
	
	@RequestMapping("/showSeat")
	@ResponseBody
	public ListJson showSeat() {
		ListJson json = new ListJson(true);
		UserRoleQuery roleQuery = new UserRoleQuery();
		roleQuery.setRoleName("remote");
		UserRole clientRole = userRoleService.getEntity(roleQuery);
		if (clientRole!=null) {
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setClientId(getLoginClientId());
			clientUserQuery.setClientRoleId(clientRole.getId());
			List<ClientUser> list = clientUserService.getEntities(clientUserQuery);
			json.setRows(list);
		}
		return json;
	}
	/**
	 * romote分配
	 */
	@RequestMapping("/distributionSeat")
	@ResponseBody
	public ResultJson distributionSeat(CustomerList customerList,CustomerQuery query,String judge) {
		// TODO Auto-generated method stub
		ClientUser clientUser = clientUserService.getById(customerList.getClientUser().getId());
		/*Integer privateSea = clientUser.getPrivateSea()!=null?clientUser.getPrivateSea():0;//用户最大坐席数量
		Integer havaSea = Integer.valueOf(getService().privateSeaCount(getLoginClientId(), clientUser.getId(),null).toString());//用户已拥有坐席数量
		privateSea = privateSea-havaSea;*/
		Integer privateSea = countOverCust(clientUser);
		List<Customer> list = null;
		if (judge.equals("all")) {
			//分配当前搜索所有客户
			query.setClientId(getLoginClientId());
			query.setType(1);
			list = getService().getEntities(query);
			/*for (Customer customer : list) {
				if (customer.getClientUser()!=null) {
					throw new ApplicationException("客户:"+customer.getMobile()+"已被分配，请刷新重新选择");
				}
			}*/
		}else {
			//分配选择用户
			list = customerList.getItems();
			for (Customer customer : list) {
				if (getService().getEntityById(customer.getId()).getClientUser()!=null) {
					throw new ApplicationException("客户:"+customer.getMobile()+"已被分配，请刷新重新选择");
				}
			}
		}
		if (privateSea < list.size()) {
			throw new ApplicationException("超出可！");
		}
		if (list!=null&&clientUser!=null) {
			for (Customer customer : list) {
				customer.setClientUser(clientUser);
				customer.setType(0);
				getService().saveSimple(customer);
			}
		}
		return  new ResultJson(true);
	}
	/**
	 * 加入计划
	 */
	@RequestMapping("/addCallPlan")
	@ResponseBody
	public ResultJson addCallPlan(CustomerList customerList,String judge) {
		// TODO Auto-generated method stub
		CallPlan callPlan = customerList.getCallPlan();
		//查询关联的任务
		AutodialerTaskQuery autodialerTaskQuery = new AutodialerTaskQuery();
		autodialerTaskQuery.setCallPlanId(callPlan.getId());
		autodialerTaskQuery.setDeleted(false);
		AutodialerTask autodialerTask = autodialerTaskService.getEntity(autodialerTaskQuery);
		if(autodialerTask==null){
			//新添加任务
			callPlan = callPlanService.getById(callPlan.getId()); 
			autodialerTask = new AutodialerTask();
			autodialerTask.setCallPlanId(callPlan.getId());
			autodialerTask.setCreateTime(new Date());
			autodialerTask.setStart(1);
			autodialerTask.setMaximumcall(1);
			autodialerTask.setCreateTime(new Date());
			autodialerTask.setDestinationExtension("8888");
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
		
		List<Customer> list = null;
		list = customerList.getItems();
		if (list!=null&&callPlan!=null) {
			//重置			
			for (Customer customer : list) {
				customer.setCallPlan(callPlan);
				customer.setPlanState(PlanState.Planned);
				customer.setContactState(ContactState.UnContact);
				getService().saveSimple(customer);
			}
			//插入任务号码表
			autodialerTaskService.insertInto(autodialerTask, list);
		}
		
		//加入计划电话
		
		return  new ResultJson(true);
	}
	/**
	 * 计算客户种类数量
	 */
	@RequestMapping("/countCustomerType")
	@ResponseBody
	private ResultJson countCustomerType(String notClientUser,String type,String jDate) {
		// TODO Auto-generated method stub
		ResultJson resultJson = new ResultJson(true);
		ClientUser clientUser = clientUserService.getById(getLoginClientUserId());
		Dept dept = clientUser.getDept();
		if (dept!=null&&
				!StringUtils.isEmpty(dept.getClientUserId())&&
				getLoginClientUserId().indexOf(dept.getClientUserId()) > -1) {
			//是部门领导（计算当前部门及底下部门所有坐席的customer）
			CountCustomerType countCustomerType = getService().countCustomerType(getLoginClientId(), null,type,jDate,dept!=null?dept.getId():null);
			countCustomerType.setHavaPriSea(getService().privateSeaCount(getLoginClientId(), getLoginClientUserId(), null));
			countCustomerType.setPriSeaAll(clientUser.getPrivateSea());
			resultJson.setEntity(countCustomerType);
		}else {
			//不是部门领导（只计算当前坐席地下的customer）
			CountCustomerType countCustomerType = getService().countCustomerType(getLoginClientId(), null,type,jDate,dept!=null?dept.getId():null);
			countCustomerType.setHavaPriSea(getService().privateSeaCount(getLoginClientId(), getLoginClientUserId(), null));
			countCustomerType.setPriSeaAll(clientUser.getPrivateSea());
			resultJson.setEntity(countCustomerType);
		}
		return resultJson;
	}
	/**
	 * 计算客户联系状态数量（contactState）
	 */
	@RequestMapping("/countNum")
	@ResponseBody
	public ResultJson countconStaNum() {
		ClientUser clientUser = getLoginClientUser();
		Dept dept = clientUser.getDept();
		ResultJson resultJson = new ResultJson(true);
		if (dept!=null&&
				!StringUtils.isEmpty(dept.getClientUserId())&&
				getLoginClientUserId().indexOf(dept.getClientUserId()) > -1) {
			resultJson.setEntity(getService().countCustState(getLoginClientId(), null,dept.getId()));
		}else {
			resultJson.setEntity(getService().countCustState(getLoginClientId(), getLoginClientUserId(),null));;
		}
		return resultJson;
	}
	/**
	 * 计算用户拥有的私海客户数量
	 */
	@RequestMapping("/privateSeaCount")
	@ResponseBody
	public ResultJson privateSeaCount(String notClientUser) {
		ClientUser clientUser = clientUserService.getById(getLoginClientUserId());
		Dept dept = clientUser.getDept();
		ResultJson resultJson = new ResultJson(true);
//		if (dept!=null&&
//				!StringUtils.isEmpty(dept.getClientUserId())&&
//				getLoginClientUserId().indexOf(dept.getClientUserId()) > -1) {
//			//是部门领导（计算当前部门及底下部门所有坐席的customer）
//			resultJson.setEntity(getService().privateSeaCount(getLoginClientId(), null,dept.getId())); ;
//		}else {
			//不是部门领导（只计算当前坐席地下的customer）
			resultJson.setEntity(getService().privateSeaCount(getLoginClientId(), getLoginClientUserId(),null));
//		}
		return resultJson;
	}
	/**
	 * 计算剩余remote量
	 */
	private Integer countOverCust(ClientUser clientUser){
		Integer privateSea = clientUser.getPrivateSea()!=null?clientUser.getPrivateSea():0;//用户最大坐席数量
		Integer havaSea = Integer.valueOf(getService().privateSeaCount(getLoginClientId(), clientUser.getId(),null).toString());//用户已拥有坐席数量
		privateSea = privateSea-havaSea;
		return privateSea;
	}
}