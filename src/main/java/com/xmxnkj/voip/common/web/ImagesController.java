package com.xmszit.voip.common.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xmszit.voip.common.entity.Images;
import com.xmszit.voip.common.entity.query.ImagesQuery;
import com.xmszit.voip.common.service.ImagesService;
import com.xmszit.voip.web.BaseController;

/**
 * 
 * @author zjx
 *
 */
@Controller
@RequestMapping("/common/images")
public class ImagesController extends BaseController<Images, ImagesQuery, Images>{
	@Autowired
	private ImagesService service;

	@Override
	public ImagesService getService() {
		return service;
	}
	
	/*@RequestMapping("upload")
	public ModelAndView upload(MultipartFile file, String objectId, String objectName) throws IllegalStateException, IOException{
		String filePath = null;
		if(file!=null){
			filePath = service.upload(file, getLoginClientId(), objectId, objectName);
		}
		return new ModelAndView("/common/images/form", "filePath", filePath);
	}*/
}
