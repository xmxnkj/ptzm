<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 
 <script src="../../content/scripts/common/form.js"></script>
                
<script type="text/javascript">
	var saveUrl = "saveJson";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	function check(){
		var f = $("#frm").form("validate")
		if (f) {
			doSubmit()
		} 
	}
</script>
   <!-- <div class="ope-bar clearfix" >
   	    <div class="fl">
           <a class="btn" href="../../system/remind/showEdit">提醒设置</a>
           <a class="btn2 active" href="showEdit">发布通知</a>
           <a class="btn" href="showList">通知列表</a>
       </div>
   </div> -->
   <div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
        </div>
    <div class="pop" id="PerfectData" style="top:10%; width:600px; margin-left:-280px;">
      <div class="cont" style="max-height: 500px">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            	<tr>
	            	<th width="30%">区域</th>
	            	<td><select class="easyui-combobox" id="noticeArea_id" name="noticeArea.id" data-options="required:true,editable:true,valueField:'id',textField:'name',width:180,url:'../../system/baseArea/list?parentId=00',loadFilter:loadFilter">
	            	</select></td>
	            </tr>
	            <tr>
	            	<th width="30%">提醒主题</th>
	            	<td width="70%"><input  style="width:180px;" class="easyui-textbox"  data-options="required:true" name="theme" id="theme" ></td>
	            	<!-- <td><select class="easyui-combobox" id="theme" name="theme" data-options="editable:true,width:180">
	            		<option value=""></option>
	            		<option value="???">？？？</option>
	            	</select></td> -->
	            </tr>
	            <tr>
	            	<th width="30%">发布人</th>
	            	<td><select class="easyui-combobox" id="user_id" name="user.id" data-options="required:true,editable:true,valueField:'id',textField:'loginAccount',width:180,url:'../../system/user/list',loadFilter:loadFilter">
	            	</select></td>
	            </tr>
	            <tr>
	                <th width="30%">提醒内容</th>
	                <td width="70%"><textarea  style="width:180px;height: 100px"  name="content" id="content" ></textarea></td>
	            </tr>
	            <tr>
	                <th width="30%">消息显示天数</th>
	                <td width="70%"><input  style="width:180px" class="easyui-numberbox"  data-options="required:true"  name="showDays" id="showDays" value="3"/></td>
	            </tr>
	            <input type="hidden" id="id" name="id">
            </tbody>
        </table>
        </form>
      </div>
      <div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>    
   	  </div>
    </div>
	
   
</body>
</html>