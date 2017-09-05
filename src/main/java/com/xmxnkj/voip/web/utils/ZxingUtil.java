package com.xmxnkj.voip.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hsit.common.MD5Util;
import com.xmxnkj.voip.system.entity.ClientUser;

public class ZxingUtil {

	//生成二维码
	/**
	 * 
	 * @param path			保存路径
	 * @param commendCode	推荐码
	 * @return 
	 */
	public static String createZxing(HttpServletRequest request,String filePath,ClientUser clientUser,String fileName){
		File f = new File(filePath+fileName);
        if(f.exists()){
        	return fileName;
        }
        if(clientUser.getCommendCode()==null || clientUser.getCommendCode().equals("")){
        	clientUser.setCommendCode(CommCodeUtil.createCommCode());
        }
       // String content = request.getScheme()+"://192.168.1.6/"+request.getContextPath()+"/"+"web/clientUser/resigstr"+"?commendCode="+clientUser.getCommendCode()+"&id="+UUID.randomUUID();
        //参数1  推荐码 （所属组织）参数2 leaderId （当前会员id）
        String content = request.getScheme()+"://www.zhzbfx.com/voip/web/clientUser/resigstr?commendCode="+clientUser.getCommendCode()+"&leaderId="+clientUser.getId();
        int width = 200; // 图像宽度  
        int height = 200; // 图像高度  
        String format = "png";// 图像类型 
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix;
		try {
			bitMatrix = new MultiFormatWriter().encode(content,  
			        BarcodeFormat.QR_CODE, width, height, hints);
			  Path path = FileSystems.getDefault().getPath(filePath, fileName);  
		      MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
		} catch (WriterException e) {
			e.printStackTrace();
		}catch (IOException e){
		}
		return fileName;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.MD5("hzk888@999.."));
	}
}