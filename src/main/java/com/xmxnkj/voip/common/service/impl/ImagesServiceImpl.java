package com.xmxnkj.voip.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.hsit.common.service.BusinessBaseServiceImpl;
import com.xmxnkj.voip.common.dao.ImagesDao;
import com.xmxnkj.voip.common.entity.Images;
import com.xmxnkj.voip.common.entity.query.ImagesQuery;
import com.xmxnkj.voip.common.service.ImagesService;

/**
 * 
 * @author zjx
 *
 */
@Service
public class ImagesServiceImpl extends BusinessBaseServiceImpl<Images, ImagesQuery> implements ImagesService {
	@Autowired
	private ImagesDao dao;

	@Override
	public ImagesDao getDao() {
		return dao;
	}
	
	public List<Images> queryImages(String objectId, String objectName){
		if (!StringUtils.isEmpty(objectId)) {
			ImagesQuery query = new ImagesQuery();
			query.setObjectId(objectId);
			query.setObjectName(objectName);
			return getEntities(query);
		}
		return null;
	}
	
	public void deleteImage(String objectId, String filePath){
		if(!StringUtils.isEmpty(objectId) && !StringUtils.isEmpty(filePath)){
			ImagesQuery query = new ImagesQuery();
			query.setObjectId(objectId);
			query.setFilePath(filePath);
			Images images = getEntity(query);
			if (images!=null) {
				deleteById(images.getId());
			}
		}
	}

	
	public String upload(MultipartFile file, String clientId, String objectId, String objectName) throws IllegalStateException, IOException{
		//Images images = new Images();
		Images images = new Images();
		images.setObjectId(objectId);
		images.setObjectName(objectName);
		images.setOriginalFileName(file.getOriginalFilename());
		images.setFileName(getFilePath(images.getId(), file.getOriginalFilename()));
		images.setFilePath("upload/images/" + clientId.replace("-", "")+"/"+images.getFileName());
		save(images);
		
		String filePath = copyFile(file, clientId, images.getId());
		
		
		
		return filePath;
	}
	
	private String copyFile(MultipartFile file, String clientId, String imageId) throws IllegalStateException, IOException{
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		String fileName = getFilePath(imageId, file.getOriginalFilename());
		String cid = clientId.replace("-", "");
		String baseUrl = "\\upload\\images\\";
		String filePath = attr.getRequest().getServletContext().getRealPath("/") + baseUrl + cid;
		File fp = new File(filePath);
		if (!fp.exists()) {
			fp.mkdirs();
		}
		String path = filePath + "\\" + fileName;
		File newFile = new File(path);
		if (newFile.exists()) {
			newFile.delete();
		}
		file.transferTo(newFile);
		return "upload/images/" + cid + "/" + fileName;
	}
	
	public static String getFilePath(String imageId, String originalFileName){
		return imageId.replace("-", "") + "." + getExtensionName(originalFileName);
	}
	
	public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    }  
}
