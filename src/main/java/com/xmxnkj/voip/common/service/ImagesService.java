package com.xmxnkj.voip.common.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hsit.common.service.BusinessBaseService;
import com.xmxnkj.voip.common.entity.Images;
import com.xmxnkj.voip.common.entity.query.ImagesQuery;

/**
 * 
 * @author zjx
 *
 */
public interface ImagesService extends BusinessBaseService<Images, ImagesQuery>{
	public String upload(MultipartFile file, String clientId, String objectId, String objectName) throws IllegalStateException, IOException;
	
	public List<Images> queryImages(String objectId, String objectName);
	
	public void deleteImage(String objectId, String filePath);
}
