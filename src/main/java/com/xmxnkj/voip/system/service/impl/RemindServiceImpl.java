package com.xmxnkj.voip.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsit.common.exceptions.ApplicationException;
import com.hsit.common.service.BusinessBaseServiceImpl;
import com.hsit.common.utils.DateUtil;
import com.xmxnkj.voip.client.entity.Client;
import com.xmxnkj.voip.system.dao.RemindDao;
import com.xmxnkj.voip.system.entity.PublishNotice;
import com.xmxnkj.voip.system.entity.Remind;
import com.xmxnkj.voip.system.entity.query.PublishNoticeQuery;
import com.xmxnkj.voip.system.entity.query.RemindQuery;
import com.xmxnkj.voip.system.service.PublishNoticeService;
import com.xmxnkj.voip.system.service.RemindService;

/**
 * @ProjectName:voip
 * @ClassName: RemindServiceImpl
 * @Description: 
 * @UpdateUser: 
 * @UpdateDate: 
 * @UpdateRemark: 
* @Copyright: 2017 厦门西牛科技有限公司
 * @versions:1.0
 */
@Service
public class RemindServiceImpl extends BusinessBaseServiceImpl<Remind, RemindQuery> implements RemindService{
	@Autowired
	private RemindDao dao;

	@Override
	public RemindDao getDao() {
		return dao;
	}
	@Autowired
	private PublishNoticeService publishNoticeService;
	@Override
	public List<PublishNotice> updateNotice(Client client){
		//获取通知地区通知
		Date effectiveDate = client.getEffectiveDate();
		Date now = new Date();
		Long d = DateUtil.dateDiff(now, effectiveDate);
		//RemindQuery remindQuery = new RemindQuery();
		//Remind remind = getEntity(remindQuery);
		List<PublishNotice> notices = new ArrayList<PublishNotice>();
		if (15>=d) {//if (remind.getDate()!=null&&remind.getDate()>=d) {
			PublishNotice publishNotice = new PublishNotice();
			if (d<0) {
				Integer d1 = 7+d.intValue(); 
				publishNotice.setContent("<font color='red'>您的系统于"+effectiveDate.toString().substring(0,10)+"过期,系统将在"+(d1==0?"今日凌晨关闭":d1+"天后关闭")+",请联系管理员或在线缴费！</font>");
				//publishNotice.setContent("<font color='red'>"+remind.getContent()+"</font>");
			}else {
				//publishNotice.setContent("<font color='red'>"+remind.getContent()+"</font>");
				publishNotice.setContent("<font color='blue'>您的系统还剩"+d+"天过期，请联系管理员或在线缴费！</font>");
			}
			notices.add(publishNotice);
		}
		
		if (client.getArea()!=null&&!StringUtils.isEmpty(client.getArea().getId())) {
			PublishNoticeQuery publishNoticeQuery = new PublishNoticeQuery();
			publishNoticeQuery.setNoticeAreaId(client.getArea().getId());
			publishNoticeQuery.setClientId(client.getId());
			publishNoticeQuery.setNotRead(true);
			publishNoticeQuery.setShowDay(true);
			List<PublishNotice> list = publishNoticeService.getEntities(publishNoticeQuery);
			notices.addAll(list);
			//getSession().setAttribute("notices", notices);
		}
		return notices;
	}
}
