package com.xmxnkj.voip.ivr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmxnkj.voip.ivr.entity.AiResponse;
import com.xmxnkj.voip.ivr.entity.query.AiResponseQuery;
import com.xmxnkj.voip.ivr.service.AiResponseService;
import com.xmxnkj.voip.web.BaseController;

@Controller
@RequestMapping(value="ivr/aiResponse")
public class AiResponseController extends BaseController<AiResponse, AiResponseQuery, AiResponse>{
	
	@Autowired 
	private AiResponseService service;
    
	@Override
    public AiResponseService getService(){
	   return service;
    }
	
}
