package com.xmxnkj.voip.system.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Htbw {

	private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/microshop";
	    String username = "root";
	    String password = "root";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	public static void insert(String ID,String type,String openid,String nickname,String gender,String city,String country,String province,String merchatID,String shopId) {
	    Connection conn = getConn();
	    String sql = "insert into wx_user_info (ID,type,openid,nickname,gender,city,country,province,merchantID,shopID) "
	    		+ "values(?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, ID);
	        pstmt.setInt(2, (int)Double.parseDouble(type));
	        pstmt.setString(3, openid);
	        pstmt.setString(4, nickname);
	        pstmt.setInt(5, (int)Double.parseDouble(gender));
	        pstmt.setString(6, city);
	        pstmt.setString(7, country);
	        pstmt.setString(8, province);
	        pstmt.setInt(9, (int)Double.parseDouble(merchatID));
	        pstmt.setInt(10, (int)Double.parseDouble(shopId));
	        pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally{
	    	try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public static void main(String[] args) throws Exception {
		
		InputStream is = new FileInputStream(new File("C:\\Users\\Administrator.XSOOY-20170326P\\Desktop\\数据迁移\\迁移.xls"));
		HSSFWorkbook wb = new HSSFWorkbook(is);
        
		HSSFSheet sheet = wb.getSheetAt(0);
        
        HSSFRow row = null;
        
        for (int rowNum = 369; rowNum <= sheet.getLastRowNum(); rowNum++) {
        	row = sheet.getRow(rowNum)!=null?sheet.getRow(rowNum):sheet.createRow(rowNum);
            if (row != null) {
                String ID = getCell(row,0).getStringCellValue();
                String type = getCell(row,4).getNumericCellValue()+"";
                String openid = getCell(row,5).getStringCellValue()+"";
                String nickname = getCell(row,6).getStringCellValue();
                String gender = getCell(row,7).getNumericCellValue()+"";
                String city = getCell(row,8).getStringCellValue();
                String country = getCell(row,9).getStringCellValue();
                String province = getCell(row,10).getStringCellValue();
                String merchatID = getCell(row,16).getNumericCellValue()+"";
                String shopId = getCell(row,17).getNumericCellValue()+"";
            	//读取第三列数据
                System.out.println("ID:"+row.getCell((short) 0)+
                					" type:"+row.getCell((short) 4)+
                					" openid:"+row.getCell((short) 5)+
                					" nickname:"+row.getCell((short) 6)+
                					" gender:"+row.getCell((short) 7)+
                					" city:"+row.getCell((short) 8)+
                					" country:"+row.getCell((short) 9)+
                					" province:"+row.getCell((short) 10)+
                					" merchatID:"+row.getCell((short) 16)+
                					" shopId:"+row.getCell((short) 17));
                
                Htbw.insert(ID, type, openid, nickname, gender, city, country, province, merchatID, shopId);
            }
        }
        is.close();
	}
	
	public static HSSFCell getCell(HSSFRow row,int index){
		return row.getCell((short) index)!=null?row.getCell((short) index):row.createCell((short) index);
	}
}
