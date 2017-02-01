package com.xmszit.voip.system.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.testng.annotations.IFactoryAnnotation;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hsit.common.exceptions.ApplicationException;
import com.xmszit.voip.client.entity.Client;
import com.xmszit.voip.client.entity.query.ClientQuery;
import com.xmszit.voip.client.service.ClientService;
import com.xmszit.voip.system.entity.PublishNotice;
import com.xmszit.voip.system.entity.query.PublishNoticeQuery;
import com.xmszit.voip.system.service.PublishNoticeService;
import com.xmszit.voip.web.SystemBaseController;
import com.xmszit.voip.web.models.ResultJson;

/**
 * @ProjectName:voip
 * @ClassName: PublishNoticeController
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Controller
@RequestMapping("/system/publishNotice")
public class PublishNoticeController extends SystemBaseController<PublishNotice, PublishNoticeQuery, PublishNotice>{
	@Autowired
	private PublishNoticeService service;

	@Override
	public PublishNoticeService getService() {
		return service;
	}
	@Autowired
	private ClientService clientService;
	@Override
	public ResultJson saveJson(PublishNotice entity, String objName) {
		//查询对应区域的client
		if (entity.getNoticeArea()!=null&&!StringUtils.isEmpty(entity.getNoticeArea().getId())) {
			String areaId = entity.getNoticeArea().getId();
			ClientQuery clientQuery = new ClientQuery();
//			if (1==1) {
//				throw new ApplicationException("待完善---com/xmszit/voip/system/web/PublishNoticeController.java");
//			}
			clientQuery.setAreaId(areaId);
			List<Client> clients = clientService.getEntities(clientQuery);
			Date date = new Date();
			//发布通知
			for (Client client : clients) {
				PublishNotice publishNotice = new PublishNotice(date,
						entity.getContent(),
						entity.getTheme(),
						entity.getUser(), 
						entity.getNoticeArea(),
						client.getId(),
						false);
				publishNotice.setShowDays(entity.getShowDays());
				getService().save(publishNotice);
			}
		}
		
		
		return super.saveJson(entity, objName);
	}
}
