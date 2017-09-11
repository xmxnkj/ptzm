package com.xmxnkj.voip.ivr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmxnkj.voip.ivr.entity.FlowTemplate;
import com.xmxnkj.voip.ivr.entity.query.FlowTemplateQuery;
import com.xmxnkj.voip.ivr.service.FlowTemplateService;
import com.xmxnkj.voip.web.BaseController;

@Controller
@RequestMapping(value="ivr/flowTemplate")
public class FlowTemplateController extends BaseController<FlowTemplate, FlowTemplateQuery, FlowTemplate> {
	
	@Autowired 
	private FlowTemplateService service;
    
	@Override
    public FlowTemplateService getService(){
	   return service;
    }
}
