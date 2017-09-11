package com.xmxnkj.voip.voice.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件操作工具类
 * 
 * @author chenxin
 *
 */
public class FileHelper {

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @param path
	 *            文件存储的路径
	 */
	public static void upload(HttpServletRequest request, String basePath, String path) {
		// 创建文件工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 将文件保存在内存还是磁盘临时文件夹的临界值
		factory.setSizeThreshold(1024 * 200);
		File tempDirectory = new File(path);
		factory.setRepository(tempDirectory);
		// 文件上传类
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置上传的文件大小临界值，超出会报错
		upload.setSizeMax(1024 * 1024 * 5);
		String fileName = null;
		String name = null;
		String pcDate = null;
		// 接收用户上传信息
		try {
			List<FileItem> items = upload.parseRequest(request);
			// 遍历items
			for (FileItem item : items) {
				// 一般表单域
				if (item.isFormField()) {
					name = item.getFieldName();
					pcDate = item.getString();
				}
				// 若是文件域则把文件保存到d盘临时文件夹
				else {
					String fieldName = item.getFieldName();
					// 上传的文件名
					fileName = item.getName();
					// 上传的文件类型
					String contentType = item.getContentType();
					// 上传的文件大小
					long sizeInBytes = item.getSize();

					// 可判断文件类型，若不符合类型要求则不保存，这里没有处理文件类型
					InputStream in = item.getInputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					File file = new File(basePath + path + fileName);
					item.write(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
