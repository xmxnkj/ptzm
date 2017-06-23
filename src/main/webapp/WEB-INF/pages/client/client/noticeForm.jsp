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
	var entityUrl = "noticeEntity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	setTimeout(() => {
		top.refreshContent();
	}, 100);
</script>
    <!-- <div class="pop" id="PerfectData" style="top:10%; width:100%; margin-left:-280px;">
      <div class="cont" style="max-height: 500px">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            	<tr>
	            	<th width="30%">区域</th>
	            	<td><select class="easyui-combobox" id="noticeArea_id" name="noticeArea.id" data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:299,url:'../../client/client/baseAreaList?parentId=00',loadFilter:loadFilter">
	            	</select></td>
	            </tr>
	            <tr>
	            	<th width="30%">提醒主题</th>
	            	<td width="70%"><input  style="width:299px;" class="inp2"  name="theme" id="theme" readonly="readonly"></td>
	            	<td><select class="easyui-combobox" id="theme" name="theme" data-options="editable:true,width:299">
	            		<option value=""></option>
	            		<option value="???">？？？</option>
	            	</select></td>
	            </tr>
	            <tr>
	            	<th width="30%">发布人</th>
	            	<td><select class="easyui-combobox" id="user_id" name="user.id" data-options="disabled:true,editable:true,valueField:'id',textField:'loginAccount',width:299,url:'../../client/client/userList',loadFilter:loadFilter">
	            	</select></td>
	            </tr>
	            <tr>
	                <th width="30%">提醒内容</th>
	                <td width="70%"><textarea readonly="readonly"  style="width:299px;height: 166px" class="inp2"  name="content" id="content" ></textarea></td>
	            </tr>
	            <input type="hidden" id="id" name="id">
            </tbody>
        </table>
        </form>
      </div>
    </div> -->
<div class="wrap">
    <div class="main">
         <form method="post" id="frm" style="width:85%; ">
        <div class="Input">
            <div class="InputContent">
                <p>区域</p>
                <select class="easyui-combobox" id="noticeArea_id" name="noticeArea.id" data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:299,url:'../../client/client/baseAreaList?parentId=00',loadFilter:loadFilter">
	            	</select>
            </div>
            <div class="InputContent">
                <p>提醒主题</p>
               <input  style="width:299px;background-color: rgba(187, 185, 185, 0.33)"  name="theme" id="theme" readonly="readonly">
            </div>
            <div class="InputContent">
                <p>发布人</p>
                <select class="easyui-combobox" id="user_id" name="user.id" data-options="disabled:true,editable:true,valueField:'id',textField:'loginAccount',width:299,url:'../../client/client/userList',loadFilter:loadFilter">
	            	</select>
            </div>
             <div class="InputContent">
                <p>提醒内容</p>
               <textarea readonly="readonly"  style="width:299px;height: 166px;background-color: rgba(187, 185, 185, 0.33)" name="content" id="content" ></textarea>
            </div>
             <input type="hidden" name="id" id="id">
        </div>
        </form>
        <!-- <div class="Button">
            <a href="#" class="button button-action button-pill button-small" onclick="check()">保存</a>
            <a href="#" class="button button-border button-pill button-action button-small"  onclick="closeDialog()">取消</a>
        </div> -->
    </div>
</div>
  
    
</body>
</html>