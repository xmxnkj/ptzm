package com.xmxnkj.voip.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hsit.common.exceptions.ApplicationException;
import com.xmxnkj.voip.global.Constants;



public class ExportExcel {
	private static final String fileSuf= ".xlsx";
	public String exportExcel(Object [][] str,String [] head) throws IOException{
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		//创建excel文档
		XSSFWorkbook wb = new XSSFWorkbook();
		//添加一个sheet
		XSSFSheet sheet = wb.createSheet("voip");
		 sheet.setHorizontallyCenter(true);
		 sheet.setVerticallyCenter(true);
		//添加表头（第0行）
		XSSFRow row = null;
		//创建一个单元格，设置值表头，设置表头居中
		XSSFCellStyle style = wb.createCellStyle();
		XSSFCellStyle style_a = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 12);
		style.setBorderBottom((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		style.setBorderTop((short) 1);
		/*style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//表头居中
		style.setAlignment(HSSFCellStyle.VERTICAL_CENTER);*/
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setAlignment(CellStyle.VERTICAL_CENTER);
		style.setWrapText(true);
		style_a.setFont(font);
		style_a.setBorderBottom((short) 1);
		style_a.setBorderLeft((short) 1);
		style_a.setBorderRight((short) 1);
		style_a.setBorderTop((short) 1);
		/*style_a.setAlignment(HSSFCellStyle.ALIGN_CENTER);//表头居中
		style_a.setAlignment(HSSFCellStyle.VERTICAL_CENTER);*/
		style_a.setAlignment(CellStyle.ALIGN_CENTER);
		style_a.setAlignment(CellStyle.VERTICAL_CENTER);
		style_a.setWrapText(true);
		/*String[] strList = str.split("&");
		String[] head = null;*/
		HttpServletRequest request = attr.getRequest();
		Integer rows = Integer.valueOf(request.getParameter("rows")==null?"0":request.getParameter("rows"));
		Integer columns = Integer.valueOf(request.getParameter("columns")==null?"0":request.getParameter("columns"));
		if (head != null) {
			if (str.length > 0 && str[0].length > 0) {
				rows = str.length;
				columns = str[0].length;
				//表头
				row = sheet.createRow(0);
				for (int i = 0; i < head.length; i++) {
					String value = head[i];
					XSSFCell cell = row.createCell((short)i);
					if (value != null && value.indexOf("\t") > -1) {
						value = value.replaceAll("\n", "").replaceAll("\t", "").trim();
					}
					cell.setCellValue(value);
					cell.setCellStyle(style_a);
				}
				//表数据
				for (int i = 0; i < str.length; i++) {
					row = sheet.createRow(i+1);
					for (int j = 0; j < str[i].length; j++) {
						String value = str[i][j]==null?"":isDouble(str[i][j]).replaceAll("</br>","\r\n");
						XSSFCell cell = row.createCell((short)j);
						if (value!=null && value.indexOf("\r\n") > -1) {
							value = value.substring(0, value.length()-1).trim();
						}
						if (value != null && value.indexOf("\t") > -1) {
							value = value.replaceAll("\n", "").replaceAll("\t", "").trim();
						}
						cell.setCellValue(value);
						cell.setCellStyle(style);
					}
				}
			}else {
				throw new ApplicationException("当前无数据");
			}
		}else {
			for (int i = 0; i < rows; i++) {
				row = sheet.createRow(i);
				for (int j = 0; j < columns; j++) {
					String value = request.getParameter("datas_"+i+"_"+j);
					XSSFCell cell = row.createCell((short)j);
					if (value!=null && value.indexOf("\r\n") > -1) {
						value = value.substring(0, value.length()-1).trim();
					}
					if (value != null && value.indexOf("\t") > -1) {
						value = value.replaceAll("\n", "").replaceAll("\t", "").trim();
					}
					cell.setCellValue(value);
					if (i==0) {
						cell.setCellStyle(style_a);
					}else {
						cell.setCellStyle(style);
					}
				}
			}
		}
		/*for (int i = 0; i < strList.length; i++) {
			row = sheet.createRow(i);
			String[] strs = strList[i].split(",");
			if (i==0) {
				head = strs;
			}
			for (int j = 0; j < strs.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(strs[j]);
				if (i==0) {
					cell.setCellStyle(style_a);
				}else {
					cell.setCellStyle(style);
				}
			}
		}*/
		for (int i = 0; i < columns; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i)+500);
			//System.out.println(sheet.getColumnWidth(i));
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = df.format(new Date())+"-"+UUID.randomUUID()+fileSuf;
		String baseUrl = "upload"+Constants.SEPARATOR+"excelLoad"+Constants.SEPARATOR;
		String filePath = attr.getRequest().getServletContext().getRealPath("/") + baseUrl;
		System.out.println(filePath);
		File fp = new File(filePath);
		if (!fp.exists()) {
			fp.mkdirs();
		}
		String path = filePath + fileName;
		File newFile = new File(path);
		if (newFile.exists()) {
			newFile.delete();
		}
		OutputStream out = new FileOutputStream(newFile);
		wb.write(out);
		return fileName;
	}
	/**
	 * 导出下载
	 * @param fileName
	 * @throws IOException
	 */
	public void loadExcel(String fileName) throws IOException{
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		String baseUrl = "upload"+Constants.SEPARATOR+"excelLoad"+Constants.SEPARATOR;
		String filePath = attr.getRequest().getServletContext().getRealPath("/") + baseUrl;
		File file = new File(filePath+ fileName);
		System.out.println(filePath+ fileName);
		if (file.getName() == null) {
			throw new ApplicationException("导出文件异常！");
		}
		HttpServletResponse response = attr.getResponse();
		response.reset();
    	response.setContentType("application/msexcel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+fileName.substring(0,14)+fileSuf);
    	OutputStream output = response.getOutputStream();
    	int l = 1024*10;
    	byte [] b = new byte [l];
    	InputStream input = new FileInputStream(file);
    	/*BufferedInputStream bis = new BufferedInputStream(input, l);
    	int len = bis.read(b);*/
    	int len = -1;
    	while ((len = input.read(b)) != -1) {
    		output.write(b, 0, len);
		}
    	input.close();
    //	bis.close();
    	output.close();
	}
	/**
	 * 导入客户数据模板下载
	 * @param value
	 * @return
	 */
	public static void loadExcelTemplate(String fileName) throws IOException{
		fileName = "importCustomer"+fileSuf;
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		String baseUrl = "upload"+Constants.SEPARATOR+"excelTemplate"+Constants.SEPARATOR;
		String filePath = attr.getRequest().getServletContext().getRealPath("/") + baseUrl;
		File file = new File(filePath+ Constants.SEPARATOR + fileName);
		if (file.getName() == null) {
			throw new ApplicationException("导出文件异常！");
		}
		HttpServletResponse response = attr.getResponse();
		response.reset();
    	response.setContentType("application/msexcel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=importCustomerTemplate"+fileSuf);
    	OutputStream output = response.getOutputStream();
    	int l = 1024*10;
    	byte [] b = new byte [l];
    	InputStream input = new FileInputStream(file);
    	/*BufferedInputStream bis = new BufferedInputStream(input, l);
    	int len = bis.read(b);*/
    	int len = -1;
    	while ((len = input.read(b)) != -1) {
    		output.write(b, 0, len);
		}
    	input.close();
    //	bis.close();
    	output.close();
	}
	public String changeStr(Object value){
		String str = value==null?"":value+"";
		return str;
	}
	public String isDouble(Object value){
		if (value != null && value instanceof Double) {
			DecimalFormat df = new DecimalFormat("#0.00");
			return df.format(value);
		}
		return value==null?"":value+"";
	}
	
}
