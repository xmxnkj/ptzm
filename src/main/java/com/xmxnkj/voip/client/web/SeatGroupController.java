package com.xmszit.voip.client.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmszit.voip.client.entity.ClientUser;
import com.xmszit.voip.client.entity.SeatGroup;
import com.xmszit.voip.client.entity.en.IsOnSeatGroup;
import com.xmszit.voip.client.entity.query.ClientUserQuery;
import com.xmszit.voip.client.entity.query.SeatGroupQuery;
import com.xmszit.voip.client.service.ClientUserService;
import com.xmszit.voip.client.service.LineService;
import com.xmszit.voip.client.service.SeatGroupService;
import com.xmszit.voip.web.BaseController;
import com.xmszit.voip.web.models.ListJson;

@Controller
@RequestMapping(value="client/seatGroup")
public class SeatGroupController extends BaseController<SeatGroup, SeatGroupQuery, SeatGroup> {
	
	@Autowired 
	private SeatGroupService service;
   
	@Autowired 
	private ClientUserService clientUserService;
	
	@Autowired 
	private LineService lineService;
	
	@Override
   public SeatGroupService getService(){
	   return service;
   }
	
	//移入/移出 坐席库
	@RequestMapping("moveOrAdd")
	@ResponseBody
	public ListJson moveOrAdd(String deptId,String seatId,String clientUserIds){
		ListJson listJson = new ListJson();
		try{
			if(StringUtils.isNotEmpty(seatId) && StringUtils.isNotEmpty(clientUserIds) && StringUtils.isNotEmpty(deptId)){
				SeatGroup seatGroup = service.getById(seatId);
				if(seatGroup==null){
					listJson.setMessage("不存在！");
					listJson.setSuccess(false);
					return listJson;
				}
				
				String[] clientUserIdArr = clientUserIds.split(",");
				
				ClientUser clientUser = null;
				for(String cuid:clientUserIdArr){
					clientUser = clientUserService.getById(cuid);
					if(clientUser.getDept().getId().equals(deptId)){
						
						if(clientUser.getSeatId().equals("0000")){
							clientUser.setSeatId(seatId);
							clientUser.setIsOnSeatGroup(IsOnSeatGroup.YES);
						}else{
							/*//判断是否有线路
							if(lineService.hasLineOnCurrent(clientUser.getId())){
								listJson.setSuccess(false);
								return listJson;
							}*/
							clientUser.setSeatId("0000");
							clientUser.setIsOnSeatGroup(IsOnSeatGroup.NO);
						}
						clientUserService.save(clientUser);
						listJson.setSuccess(true);
					}else{
						listJson.setMessage("不可跨部门分配坐席！");
						listJson.setSuccess(false);
						return listJson;
					}
				}
				
			}
		}catch(Exception e){
			listJson.setMessage("数据异常！");
			listJson.setSuccess(false);
		}
		return listJson;
	}
	
	//编辑坐席库
	@RequestMapping("saveDate")
	@ResponseBody
	public ListJson saveDate(SeatGroup seatGroup){
		ListJson listJson = new ListJson();
		try{
			seatGroup.setClientId(getLoginClientId());
			getService().save(seatGroup);
			listJson.setSuccess(true);
		}catch(Exception e){
			listJson.setSuccess(false);
		}
		return listJson;
	}
	
	//编辑坐席库
	@RequestMapping("drop")
	@ResponseBody
	public ListJson drop(String id){
		ListJson listJson = new ListJson();
		try{
			SeatGroup seatGroup = getService().getById(id);
			ClientUserQuery clientUserQuery = new ClientUserQuery();
			clientUserQuery.setClientId(getLoginClientId());
			clientUserQuery.setDeptId(seatGroup.getDeptId());
			clientUserQuery.setSeatId(seatGroup.getId());
			clientUserQuery.setIsOnSeatGroup(IsOnSeatGroup.YES);
			if(clientUserService.getEntityCount(clientUserQuery)>0){
				listJson.setSuccess(false);
				listJson.setMessage("请先移除！");
				return listJson;
			}
			getService().deleteById(id);
			listJson.setSuccess(true);
		}catch(Exception e){
			listJson.setSuccess(false);
		}
		return listJson;
	}
}