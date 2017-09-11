package com.xmxnkj.voip.client.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.uac.entity.User;
import com.hsit.common.uac.entity.queryparam.UserQuery;
import com.hsit.common.uac.service.UserService;
import com.xmxnkj.voip.system.entity.BaseArea;
import com.xmxnkj.voip.system.entity.ClientMeal;
import com.xmxnkj.voip.system.entity.PublishNotice;
import com.xmxnkj.voip.system.entity.query.BaseAreaQuery;
import com.xmxnkj.voip.system.entity.query.ClientMealQuery;
import com.xmxnkj.voip.system.entity.query.PublishNoticeQuery;
import com.xmxnkj.voip.system.service.BaseAreaService;
import com.xmxnkj.voip.system.service.ClientMealService;
import com.xmxnkj.voip.system.service.PublishNoticeService;
import com.xmxnkj.voip.system.entity.ClientPayRecord;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.client.entity.query.ClientQuery;
import com.xmxnkj.voip.client.service.ClientService;
import com.xmxnkj.voip.global.Constants;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import com.xmxnkj.voip.web.models.ResultJson;

/**
 * @ProjectName:voip
 * @ClassName: ClientController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/client/client")
public class ClientController extends BaseController<Client, ClientQuery, Client>{
	@Autowired
	private ClientService service;

	@Override
	public ClientService getService() {
		return service;
	}
	
	@RequestMapping("clientInfo")
	@ResponseBody
	public Client clientInfo(){
		return getLoginClient();
	}
	
	@RequestMapping("setting")
	public ModelAndView showSetting(){
		return new ModelAndView("/client/client/setting");
	}
	
	
	@Autowired
	private PublishNoticeService publishNoticeService;
	/**
	 * 通知
	 */
	@RequestMapping("noticeList")
	@ResponseBody
	public ListJson noticeList(PublishNoticeQuery query, Integer rows, Integer page){
		try{
			if (publishNoticeService==null) {
				return new ListJson(false);
			}
			if (rows!=null && rows>0) {
				
				if (page==null) {
					page=1;
				}
				query.setPage(page, rows);
			}
			List<PublishNotice> entities = publishNoticeService.getEntities(query);
			ListJson listJson = null;
			listJson = new ListJson(entities);
			listJson.setPaging(query.getPaging());
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	@RequestMapping("noticeEntity")
	@ResponseBody
	public PublishNotice noticeEntity(PublishNotice publishNotice){
		if (publishNoticeService==null) {
			return null;
		}
		publishNotice.setIsRead(true);
		publishNotice.setNoticeDate(null);
		publishNoticeService.save(publishNotice);
		return publishNoticeService.getById(publishNotice.getId());
	}
	
	
	@Autowired
	private BaseAreaService baseAreaService;
	@RequestMapping("baseAreaList")
	@ResponseBody
	public ListJson baseAreaList(BaseAreaQuery query){
		try{
			if (baseAreaService==null) {
				return new ListJson(false);
			}
			List<BaseArea> entities = baseAreaService.getEntities(query);
			ListJson listJson = null;
			listJson = new ListJson(entities);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	@Autowired
	private ClientMealService clientMealService;
	@RequestMapping("clientMealList")
	@ResponseBody
	public ListJson clientMealList(ClientMealQuery query){
		try{
			if (clientMealService==null) {
				return new ListJson(false);
			}
			List<ClientMeal> entities = clientMealService.getEntities(query);
			ListJson listJson = null;
			listJson = new ListJson(entities);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	@Autowired
	private UserService userService;
	@RequestMapping("userList")
	@ResponseBody
	public ListJson userList(UserQuery query){
		try{
			if (userService==null) {
				return new ListJson(false);
			}
			List<User> entities = userService.getEntities(query);
			ListJson listJson = null;
			listJson = new ListJson(entities);
			return listJson;
		}catch(Exception e){
			e.printStackTrace();
			return new ListJson(e);
		}
	}
	/**
	 * 客户缴费
	 */
	@RequestMapping(value="/savePayClient")
	@ResponseBody
	public ResultJson savePayClient(ClientPayRecord clientPayRecord) {
		ResultJson json = new ResultJson();
		/*if (clientPayRecord==null || clientPayRecord.getClient()==null) {
			json.setMessage("请填写相关数据");
			json.setSuccess(false);
			return json;
		}*/
		System.out.println("sessionid："+getRequest().getSession().getId());
		clientPayRecord.setSid(getRequest().getSession().getId());
		try {
			throw new ApplicationException("待完善----client/web/clientController");
			/*String code = getService().savePayClient(clientPayRecord,getLoginClient());
			json.setSuccess(true);
			json.setEntity(code);*/
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultJson(e);
		}
		//return json;
	}
}
