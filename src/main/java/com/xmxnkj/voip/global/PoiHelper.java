/**
 * File Name: PoiHelper.java
 * Encoding UTF-8
 * Version: 1.0
 * History:
 */
package com.xmxnkj.voip.global;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hsit.common.utils.Converter;
import com.hsit.common.utils.DateUtil;

/**
 * @ClassName:PoiHelper
 * @date 2013-7-6 下午2:21:54
 * 
 */
public class PoiHelper {
	//private static DecimalFormat formatter = new DecimalFormat("#.#"); 
	public static String getString(XSSFSheet sheet, int row, int col) {
		XSSFRow row2 = sheet.getRow(row);
		if(row2 != null){
			
			XSSFCell cell = row2.getCell(col);
			
			if (cell != null) {
				if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					String val = cell.getStringCellValue();
					if (!StringUtils.isEmpty(val)) {
						val=val.trim();
					}
					return val;
				}else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					return formatDouble(cell.getNumericCellValue());
				}
			}
		}
		return "";
	}
	
	
	public static String getStringFixed(XSSFSheet sheet, int row, int col){
		XSSFRow row2 = sheet.getRow(row);
		if(row2 != null){
			XSSFCell cell = row2.getCell(col);
			if (cell != null) {
				if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					return cell.getStringCellValue();
				}else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					if (cell.getCellStyle().getDataFormatString().indexOf("%") != -1) {
						return formatDouble(cell.getNumericCellValue()*100)+"%";
					}
					return String.valueOf(cell.getNumericCellValue());
				}
			}
		}
		return "";
	}
	
	private static String formatDouble(double value){
		DecimalFormat format = new DecimalFormat("#.###");
		return format.format(value);
	}
	
	public static Date getDate(XSSFSheet sheet, int row, int col){
		XSSFRow row2 = sheet.getRow(row);
		if(row2 != null){
			XSSFCell cell = row2.getCell(col);
			if (cell != null) {
				if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					return cell.getDateCellValue();
				}else if (cell.getCellType()==XSSFCell.CELL_TYPE_STRING) {
					try{
						return DateUtil.parseDate(cell.getStringCellValue());
					}catch(Exception e){
						
					}
				}
			}
		}
		return null;
	
	}
	
	public static double getNumeric(XSSFSheet sheet, int row, int col) {
		XSSFRow row2 = sheet.getRow(row);
		if(row2 != null){
			XSSFCell cell = row2.getCell(col);
			if (cell != null) {
				
				if (cell.getCellType() ==XSSFCell.CELL_TYPE_NUMERIC) {
					return cell.getNumericCellValue();
				}else if (cell.getCellType()== XSSFCell.CELL_TYPE_STRING) {
					return Converter.toFloat(cell.getStringCellValue());
				}
				
			}
		}
		return 0;
	}
	
	public static Double getNumericEx(XSSFSheet sheet, int row, int col) {
		XSSFRow row2 = sheet.getRow(row);
		if(row2 != null){
			XSSFCell cell = row2.getCell(col);
			if (cell != null) {
				
				if (cell.getCellType() ==XSSFCell.CELL_TYPE_NUMERIC) {
					return cell.getNumericCellValue();
				}else if (cell.getCellType()== XSSFCell.CELL_TYPE_STRING) {
					return (double) Converter.toFloat(cell.getStringCellValue());
				}
				
			}
		}
		return null;
	}
	
	public static boolean getBoolean(XSSFSheet sheet, int row, int col) {
		XSSFRow row2 = sheet.getRow(row);
		if(row2 != null){
			XSSFCell cell = row2.getCell(col);
			if (cell != null) {
				if (cell.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN) {
					return cell.getBooleanCellValue();
				}
				String value = cell.getStringCellValue();
				return "是".equals(value);
			}
		}
		return false;
	}
	
	
	public static void writeValue(XSSFSheet sheet, int rowIndex, int colIndex, String value){
		writeValue(sheet, rowIndex, colIndex, value, null);
	}
	
	public static void writeValue(XSSFSheet sheet, int rowIndex, int colIndex, String value, CellStyle style){
		XSSFRow row = createRow(sheet, rowIndex);
		writeValue(row, colIndex, value, style);
	}
	
	public static void writeValue(XSSFRow row, int colIndex, String value){
		writeValue(row, colIndex, value, null);
	}
	
	public static void writeValue(XSSFRow row, int colIndex, String value, CellStyle style){
		XSSFCell cell = row.getCell(colIndex);
		if (cell==null) {
			cell = row.createCell(colIndex);
		}
		cell.setCellValue(value);
		if (style != null) {
			cell.setCellStyle(style);
		}
	}
	
	
	
	/**  
     * 合并单元格  
     * @param sheet 要合并单元格的excel 的sheet
     * @param cellLine  要合并的列  
     * @param startRow  要合并列的开始行  
     * @param endRow    要合并列的结束行  
     */  
    public static void addMergedRegion(XSSFSheet sheet, int cellLine, int startRow, int endRow,XSSFWorkbook workBook){   
           
     XSSFCellStyle style = workBook.createCellStyle(); // 样式对象    
     
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直    
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平    
        //获取第一行的数据,以便后面进行比较    
        String s_will = getString(sheet, startRow, cellLine);// sheet.getRow(startRow).getCell(cellLine).getStringCellValue();   
     
        int count = 0;
        boolean flag = false;
        for (int i = 1; i <= endRow; i++) {   
         String s_current = getString(sheet, i, 0); //sheet.getRow(i).getCell(0).getStringCellValue(); 
         if(s_will.equals(s_current))
         {
          s_will = s_current;
          if(flag)
          {
           sheet.addMergedRegion(new CellRangeAddress(startRow-count,startRow,cellLine,cellLine));
           XSSFRow row = sheet.getRow(startRow-count);
           String cellValueTemp = getString(sheet, startRow-count, 0); //sheet.getRow(startRow-count).getCell(0).getStringCellValue(); 
           XSSFCell cell = row.createCell(0);
           cell.setCellValue(cellValueTemp); // 跨单元格显示的数据    
                  cell.setCellStyle(style); // 样式    
           count = 0;
           flag = false;
          }
          startRow=i;
          count++;          
         }else{
          flag = true;
          s_will = s_current;
         }
  //由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
         if(i==endRow&&count>0)
         {
          sheet.addMergedRegion(new CellRangeAddress(endRow-count,endRow,cellLine,cellLine));   
          String cellValueTemp = getString(sheet, startRow-count, 0); //sheet.getRow(startRow-count).getCell(0).getStringCellValue(); 
          XSSFRow row = sheet.getRow(startRow-count);
       XSSFCell cell = row.createCell(0);
       cell.setCellValue(cellValueTemp); // 跨单元格显示的数据    
                cell.setCellStyle(style); // 样式    
         }
        }
    }
    
    public static XSSFRow createRow(XSSFSheet sheet, int index){
    	XSSFRow row = sheet.getRow(index);
    	if (row==null) {
			row = sheet.createRow(index);
		}
    	return row;
    }
    
    public static XSSFCell createCell(XSSFRow row, int colIndex, CellStyle cellStyle){
    	XSSFCell cell = row.getCell(colIndex);
    	if (cell == null) {
			cell = row.createCell(colIndex);
		}
    	cell.setCellStyle(cellStyle);
    	return cell;
    }
    
    public static XSSFCell createCell(XSSFSheet sheet, int rowIndex, int colIndex, CellStyle cellStyle){
    	XSSFRow row = createRow(sheet, rowIndex);
    	return createCell(row, colIndex, cellStyle);
    }
}
