package com.xmxnkj.voip.system.controllers;

import com.hsit.common.kfbase.entity.Paging;
import com.xmxnkj.voip.system.entity.ClientUser;
import com.xmxnkj.voip.system.entity.OrderItem;
import com.xmxnkj.voip.system.entity.emun.ClientTypeEnum;
import com.xmxnkj.voip.system.entity.emun.EveningUpEnum;
import com.xmxnkj.voip.system.entity.query.OrderItemQuery;
import com.xmxnkj.voip.system.service.ClientUserService;
import com.xmxnkj.voip.system.service.voipervice;
import com.xmxnkj.voip.system.service.OrderItemService;
import com.xmxnkj.voip.web.BaseController;
import com.xmxnkj.voip.web.models.ListJson;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/back/orderControl"})
public class OrderItemController
  extends BaseController<OrderItem, OrderItemQuery>
{
  @Autowired
  private OrderItemService service;
  @Autowired
  private ClientUserService clientUserService;
  @Autowired
  private voipervice voipervice;
  @Autowired
  private JdbcTemplate jdbcTemplate;
  
  public OrderItemService getService(){
    return this.service;
  }
  
  @RequestMapping({"realTimeMonitoringJsp"})
  public String realTimeMonitoringJsp(HttpServletRequest req){
    req.setAttribute("voipList", this.service.getEntities(null));
    return "orderItem/realTimeMonitoring";
  }
  
  @RequestMapping({"transactionQueryJsp"})
  public String transactionQueryJsp(){
    return "orderItem/transactionQuery";
  }
  
  @RequestMapping({"transactionQueryDetail"})
  public String transactionQueryDetail(){
    return "orderItem/transactionQueryDetail";
  }
  
  
  @RequestMapping({"realTimeMonitoringList"})
  @ResponseBody
  public ListJson realTimeMonitoringList(EveningUpEnum isEveningUp, String phoneNumber, String startTime, String endTime, Integer page, Integer pageSize, HttpSession session){
    ListJson LJ = new ListJson();
    try{
	    Paging paging = new Paging();
	    paging.setPage(page.intValue());
	    paging.setPageSize(pageSize.intValue());
	    
	    StringBuffer sql = new StringBuffer(" from orderitem where isEveningUp='" + isEveningUp.ordinal() + "'");
	    
	    ClientUser cu = (ClientUser)session.getAttribute("manager");
	    List<String> lista = new ArrayList();
	    lista.add(cu.getId());
	    
	
	    List<Map<String, Object>> clientList = this.clientUserService.getSonNormal(lista, "?");
	    String accounts = "?,";
	    List<String> list = new ArrayList();
	    list.add("");
	    for (Map<String, Object> map : clientList) {
	      if (!phoneNumber.equals("")){
	        if (((String)map.get("phoneNumber")).equals(phoneNumber)){
	          list.add((String)map.get("phoneNumber"));
	          accounts = accounts + "?,";
	        }
	      }else{
	        list.add((String)map.get("phoneNumber"));
	        accounts = accounts + "?,";
	      }
	    }
	    if (!accounts.equals("")) {
	      sql.append(" and account in (" + accounts.substring(0, accounts.length() - 1) + ") ");
	    }
	    if ((startTime != null) && (!startTime.equals(""))){
	      sql.append(" and createOrderTime>?");
	      list.add(startTime);
	    }
	    if ((endTime != null) && (!endTime.equals(""))){
	      sql.append(" and createOrderTime<?");
	      list.add(endTime);
	    }
	    Object[] obj = new Object[list.size()];
	    for (int i = 0; i < list.size(); i++) {
	      obj[i] = list.get(i);
	    }
	    int total = this.jdbcTemplate.queryForInt("select count(*) " + sql, obj);
	    List<Map<String, Object>> listMap = this.jdbcTemplate.queryForList("select * " + sql + " order by clientOrderNo desc limit " + (page.intValue() - 1) * pageSize.intValue() + "," + pageSize, obj);
	    
	
	    Map<String, Object> totalMap = null;
	    if (isEveningUp == EveningUpEnum.YES)
	    {
	      totalMap = this.jdbcTemplate.queryForMap("select sum(exchangePoundageMoney) as poundageMoney,sum(exchangeInfomationMoney) as messageMoney,sum(offsetGainAndLoss) as offsetGainAndLoss " + sql, obj);
	      LJ.setEntity(totalMap);
	    }
	    List<Map<String, Object>> minPriceMap = null;
	    if (isEveningUp == EveningUpEnum.NO)
	    {
	      minPriceMap = this.jdbcTemplate.queryForList("select futureCode,unit from future");
	      LJ.setEntity(minPriceMap);
	    }
	    paging.setRecordCount(total);
	    paging.setPageCount((int)Math.ceil((float)paging.getRecordCount() / paging.getPageSize()));
	    LJ.setPaging(paging);
	    LJ.setRows(listMap);
	    return LJ;
    }catch(Exception e){
    	
    }
    return LJ;
  }
  
  //查询当前登录者所有下级用户的成交记录
  @RequestMapping({"sonOrder"})
  @ResponseBody
  public ListJson sonOrder(String ID, Integer page, String startTime, String endTime, Integer pageSize){
    ListJson LJ = new ListJson();
    try{
	    Paging paging = new Paging();
	    paging.setPage(page.intValue());
	    paging.setPageSize(pageSize.intValue());
	    
	    ClientUser cu = (ClientUser)this.clientUserService.getById(ID);
	    
	
	    List<String> qlist = new ArrayList();
	    qlist.add(ID);
	    List<Map<String, Object>> list = this.clientUserService.getSonNormal(qlist, "?", ClientTypeEnum.Normal);
	    
	    StringBuffer term = new StringBuffer();
	    qlist = new ArrayList();
	    if (cu.getClientType() == ClientTypeEnum.Normal)
	    {
	      term.append("?,");
	      qlist.add(cu.getPhoneNumber());
	    }
	    for (Map<String, Object> map : list)
	    {
	      term.append("?,");
	      qlist.add(map.get("phoneNumber").toString());
	    }
	    if (qlist.size() == 0)
	    {
	      LJ.setRows(new ArrayList());
	      return LJ;
	    }
	    StringBuffer sql = new StringBuffer();
	    sql.append("from orderitem where isEveningUp='1' and account in (" + term.substring(0, term.length() - 1) + ") ");
	    if ((startTime != null) && (!startTime.equals("")))
	    {
	      sql.append(" and createOrderTime>?");
	      qlist.add(startTime);
	    }
	    if ((endTime != null) && (!endTime.equals("")))
	    {
	      sql.append(" and createOrderTime<?");
	      qlist.add(endTime);
	    }
	    Object[] obj = new Object[qlist.size()];
	    for (int i = 0; i < qlist.size(); i++) {
	      obj[i] = qlist.get(i);
	    }
	    Integer total = Integer.valueOf(this.jdbcTemplate.queryForInt("select count(*) " + sql, obj));
	    List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select * " + sql + " order by clientOrderNo desc limit " + (page.intValue() - 1) * pageSize.intValue() + "," + pageSize, obj);
	    
	
	    Map<String, Object> map = this.jdbcTemplate.queryForMap("select sum(exchangePoundageMoney) as poundageMoney,sum(exchangeInfomationMoney) as messageMoney,sum(offsetGainAndLoss) as offsetGainAndLoss " + sql, obj);
	    
	
	    paging.setPageCount(total.intValue() / pageSize.intValue() + 1);
	    paging.setRecordCount(total.intValue());
	    LJ.setEntity(map);
	    LJ.setRows(rows);
	    LJ.setPaging(paging);
	    return LJ;
    }catch(Exception e){
    	
    }
    return LJ;
  }
  
  //导出成交记录
  @RequestMapping({"uploadExcel"})
  public void uploadExcel(HttpServletRequest request, HttpServletResponse response, EveningUpEnum isEveningUp, String phoneNumber, String startTime, String endTime)
    throws IOException, ParseException{
    StringBuffer sql = new StringBuffer(" from orderitem where isEveningUp='" + isEveningUp.ordinal() + "'");
    
    ClientUser cu = (ClientUser)request.getSession().getAttribute("manager");
    List<String> lista = new ArrayList();
    lista.add(cu.getId());
    

    List<Map<String, Object>> clientList = this.clientUserService.getSonNormal(lista, "?");
    String accounts = "?,";
    List<String> list = new ArrayList();
    list.add("");
    for (Map<String, Object> map : clientList) {
      if (!phoneNumber.equals("")){
        if (((String)map.get("phoneNumber")).equals(phoneNumber)){
          list.add((String)map.get("phoneNumber"));
          accounts = accounts + "?,";
        }
      }else{
        list.add((String)map.get("phoneNumber"));
        accounts = accounts + "?,";
      }
    }
    if (!accounts.equals("")) {
      sql.append(" and account in (" + accounts.substring(0, accounts.length() - 1) + ") ");
    }
    if ((startTime != null) && (!startTime.equals("")))
    {
      sql.append(" and createOrderTime>?");
      list.add(startTime);
    }
    if ((endTime != null) && (!endTime.equals("")))
    {
      sql.append(" and createOrderTime<?");
      list.add(endTime);
    }
    Object[] obj = new Object[list.size()];
    for (int i = 0; i < list.size(); i++) {
      obj[i] = list.get(i);
    }
    Object rows = this.jdbcTemplate.queryForList("select * " + sql + " order by clientOrderNo desc ", obj);
    

    Map<String, Object> totalMap = null;
    if (isEveningUp == EveningUpEnum.YES) {
      totalMap = this.jdbcTemplate.queryForMap("select sum(exchangePoundageMoney) as poundageMoney,sum(exchangeInfomationMoney) as messageMoney,sum(offsetGainAndLoss) as offsetGainAndLoss " + sql, obj);
    }
    String fileName = request.getServletContext().getRealPath("/") + "\\file\\成交记录.xls";
    
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    

    HSSFWorkbook wb = new HSSFWorkbook(bis);
    
    HSSFSheet sheet = wb.getSheetAt(0);
    
    HSSFRow row = null;
    
    HSSFCell cell = null;
    
    Integer index = Integer.valueOf(4);
    
    //会员单位保证金不足样式
    HSSFCellStyle style = wb.createCellStyle();
    HSSFFont font = wb.createFont();
    font.setBoldweight((short)700);
    style.setFont(font);
    style.setAlignment((short)2);
    style.setVerticalAlignment((short)1);
    style.setBorderBottom((short)1);
    style.setBorderLeft((short)1);
    style.setBorderTop((short)1);
    style.setBorderRight((short)1);
    style.setAlignment((short)2);
    style.setFillForegroundColor(IndexedColors.ROSE.getIndex());
    style.setFillPattern((short)1);
    
    //强制平仓样式
    HSSFCellStyle style1 = wb.createCellStyle();
    HSSFFont font1 = wb.createFont();
    font1.setBoldweight((short)700);
    style1.setFont(font);
    style1.setAlignment((short)2);
    style1.setVerticalAlignment((short)1);
    style1.setBorderBottom((short)1);
    style1.setBorderLeft((short)1);
    style1.setBorderTop((short)1);
    style1.setBorderRight((short)1);
    style1.setAlignment((short)2);
    style1.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
    style1.setFillPattern((short)1);
    
    row = getRow(sheet, 1);
    cell = getCell(row, 1);
    cell.setCellValue((!startTime.equals("") ? startTime : "") + "~" + (!endTime.equals("") ? endTime : ""));
    for (int i = 1; i <= ((List)rows).size(); i++)
    {
      row = getRow(sheet, index.intValue());
      
      String warn = ((Map)((List)rows).get(i - 1)).get("warn").toString();
      
      cell = getCell(row, 0);
      cell.setCellValue(i);
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 1);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("account").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 2);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("orderSysId").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 3);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("voipCode").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 4);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("createOrderTime").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 5);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("voipName").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 6);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("eveningUpPrice").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 7);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("closeTime").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 8);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("unitPrice").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 9);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("createOrderTime").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 10);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("counts").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 11);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("orderWay").toString().equals("0") ? "多" : "空");
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 12);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("offsetGainAndLoss").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 13);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("exchangePoundageMoney").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 14);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("exchangeInfomationMoney").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 15);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("stopLoss").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }else if(warn.equals("2")){
    	  cell.setCellStyle(style1);
      }
      
      cell = getCell(row, 16);
      cell.setCellValue(((Map)((List)rows).get(i - 1)).get("targetProfit").toString());
      index = Integer.valueOf(index.intValue() + 1);
    }
    response.setHeader("content-disposition", 
      "attachment;filename=" + URLEncoder.encode("交易记录", "UTF-8") + ".xls");
    response.setContentType("application/vnd.ms-excel");
    OutputStream bos = response.getOutputStream();
    
    wb.write(bos);
    bos.flush();
    bos.close();
  }
  
  @RequestMapping({"uploadExcelEX"})
  public void uploadExcelEX(String ID, String startTime, String endTime, HttpServletRequest request, HttpServletResponse response)
    throws IOException{
    ClientUser cu = (ClientUser)this.clientUserService.getById(ID);
    
    List<String> qlist = new ArrayList();
    qlist.add(ID);
    List<Map<String, Object>> list = this.clientUserService.getSonNormal(qlist, "?", ClientTypeEnum.Normal);
    
    StringBuffer term = new StringBuffer();
    qlist = new ArrayList();
    if (cu.getClientType() == ClientTypeEnum.Normal)
    {
      term.append("?,");
      qlist.add(cu.getPhoneNumber());
    }
    for (Map<String, Object> map : list)
    {
      term.append("?,");
      qlist.add(map.get("phoneNumber").toString());
    }
    List<Map<String, Object>> rows = new ArrayList();
    if (qlist.size() > 0)
    {
      StringBuffer sql = new StringBuffer();
      sql.append("from orderitem where isEveningUp='1' and account in (" + term.substring(0, term.length() - 1) + ") ");
      if ((startTime != null) && (!startTime.equals("")))
      {
        sql.append(" and createOrderTime>?");
        qlist.add(startTime);
      }
      if ((endTime != null) && (!endTime.equals("")))
      {
        sql.append(" and createOrderTime<?");
        qlist.add(endTime);
      }
      sql.append(" order by clientOrderNo desc ");
      
      Object[] obj = new Object[qlist.size()];
      for (int i = 0; i < qlist.size(); i++) {
        obj[i] = qlist.get(i);
      }
      rows = this.jdbcTemplate.queryForList("select * " + sql, obj);
    }
    String fileName = request.getServletContext().getRealPath("/") + "\\file\\成交记录.xls";
    
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
    
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    

    HSSFWorkbook wb = new HSSFWorkbook(bis);
    
    HSSFSheet sheet = wb.getSheetAt(0);
    
    HSSFRow row = null;
    
    HSSFCell cell = null;
    
    Integer index = Integer.valueOf(4);
    

    HSSFCellStyle style = wb.createCellStyle();
    HSSFFont font = wb.createFont();
    font.setBoldweight((short)700);
    style.setFont(font);
    style.setAlignment((short)2);
    style.setVerticalAlignment((short)1);
    style.setBorderBottom((short)1);
    style.setBorderLeft((short)1);
    style.setBorderTop((short)1);
    style.setBorderRight((short)1);
    style.setAlignment((short)2);
    style.setFillForegroundColor((short)53);
    style.setFillPattern((short)1);
    
    row = getRow(sheet, 1);
    cell = getCell(row, 1);
    cell.setCellValue((!startTime.equals("") ? startTime : "") + "~" + (!endTime.equals("") ? endTime : ""));
    for (int i = 1; i <= rows.size(); i++)
    {
      row = getRow(sheet, index.intValue());
      
      String warn = ((Map)rows.get(i - 1)).get("warn").toString();
      
      cell = getCell(row, 0);
      cell.setCellValue(i);
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 1);
      cell.setCellValue(((Map)rows.get(i - 1)).get("account").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 2);
      cell.setCellValue(((Map)rows.get(i - 1)).get("clientOrderNo").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 3);
      cell.setCellValue(((Map)rows.get(i - 1)).get("voipCode").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 4);
      cell.setCellValue(((Map)rows.get(i - 1)).get("createOrderTime").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 5);
      cell.setCellValue(((Map)rows.get(i - 1)).get("voipName").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 6);
      cell.setCellValue(((Map)rows.get(i - 1)).get("eveningUpPrice").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 7);
      cell.setCellValue(((Map)rows.get(i - 1)).get("closeTime").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 8);
      cell.setCellValue(((Map)rows.get(i - 1)).get("unitPrice").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 9);
      cell.setCellValue(((Map)rows.get(i - 1)).get("createOrderTime").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 10);
      cell.setCellValue(((Map)rows.get(i - 1)).get("counts").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 11);
      cell.setCellValue(((Map)rows.get(i - 1)).get("orderWay").toString().equals("0") ? "多" : "空");
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 12);
      cell.setCellValue(((Map)rows.get(i - 1)).get("offsetGainAndLoss").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 13);
      cell.setCellValue(((Map)rows.get(i - 1)).get("exchangePoundageMoney").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 14);
      cell.setCellValue(((Map)rows.get(i - 1)).get("exchangeInfomationMoney").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 15);
      cell.setCellValue(((Map)rows.get(i - 1)).get("stopLoss").toString());
      if (warn.equals("1")) {
        cell.setCellStyle(style);
      }
      cell = getCell(row, 16);
      cell.setCellValue(((Map)rows.get(i - 1)).get("targetProfit").toString());
      
      index = Integer.valueOf(index.intValue() + 1);
    }
    response.setHeader("content-disposition", 
      "attachment;filename=" + URLEncoder.encode("交易记录", "UTF-8") + ".xls");
    response.setContentType("application/vnd.ms-excel");
    OutputStream bos = response.getOutputStream();
    
    wb.write(bos);
    bos.flush();
    bos.close();
  }
  
  public static HSSFCell getCell(HSSFRow row, int index)
  {
    return row.getCell((short)index) != null ? row.getCell((short)index) : row.createCell((short)index);
  }
  
  public static HSSFRow getRow(HSSFSheet sheet, int index)
  {
    return sheet.getRow(index) == null ? sheet.createRow(index) : sheet.getRow(index);
  }
}
