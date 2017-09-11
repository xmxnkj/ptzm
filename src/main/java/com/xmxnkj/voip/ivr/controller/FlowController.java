package com.xmxnkj.voip.ivr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmxnkj.voip.ivr.entity.Flow;
import com.xmxnkj.voip.ivr.entity.query.FlowQuery;
import com.xmxnkj.voip.ivr.service.FlowService;
import com.xmxnkj.voip.web.BaseController;

@Controller
@RequestMapping(value="ivr/flow")
public class FlowController extends BaseController<Flow, FlowQuery, Flow>{
	
	@Autowired 
	private FlowService service;
    
	@Override
    public FlowService getService(){
	   return service;
    }
	
	@RequestMapping("/getEntityById")
	@ResponseBody
	public Object getEntityById(String id){
		if (getService()==null) {
			return null;
		}
		Flow flow = getService().getById(id);
		return flow;
	}
}
