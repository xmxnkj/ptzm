package com.xmszit.voip.ivr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmszit.voip.ivr.entity.AiResponse;
import com.xmszit.voip.ivr.entity.query.AiResponseQuery;
import com.xmszit.voip.ivr.service.AiResponseService;
import com.xmszit.voip.web.BaseController;

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
