package com.xmxnkj.voip.web.system.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.uac.entity.Operation;
import com.hsit.common.uac.entity.queryparam.OperationQuery;
import com.hsit.common.uac.service.OperationService;
import com.xmxnkj.voip.client.entity.Operate;
import com.xmxnkj.voip.web.BaseAction;

/**
 * @ProjectName:voip
 * @ClassName: SystemController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
 * @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
public class SystemController extends BaseAction{
	@RequestMapping("/system/main")
	public ModelAndView main(){
		ModelAndView modelAndView = new ModelAndView("system/main");
		List<Operation> operations = getOperation("1");
		modelAndView.addObject("topOperations", operations);
		modelAndView.addObject("topMenusLength",operations!=null?operations.size():0);
		if (operations!=null&&operations.size()>0) {
			modelAndView.addObject("firstUrl",operations.get(0).getUrlPath());
		}
		return modelAndView;
	}
	@Autowired
	private OperationService operationService;
	/**
	 * 获取主页权限
	 */
	public List<Operation> getOperation(String grade){
		List<Operation> operations = getLoginOperations();
		List<Operation> operations2 = new ArrayList<Operation>();
		Integer i = 0;
		if (operations!=null) {
			for (Operation operation : operations) {
				if (operation.getDescription().equals(grade)) {
					operation.setDisplayOrder(i);
					i++;
					operations2.add(operation);
				}
			}
		}
		
		return operations2;
	}
	
	public List<Operation> getLoginOperates(String grade, String parentId){
		List<Operation> operates = getLoginOperations();
		List<Operation> gradeOperates = new ArrayList<>();
		if (operates!=null) {
			for (Operation Operation : operates) {
				if (Operation.getDescription().equals(grade)
						&& parentId.equals(Operation.getCategoryId())) {
					gradeOperates.add(Operation);
				}
			}
		}
		return gradeOperates;
	}
	
}
