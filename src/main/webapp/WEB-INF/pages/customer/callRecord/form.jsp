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
<script src="../../content/scripts/common/formUtils.js"></script>

<script type="text/javascript">
	var saveUrl = "saveJson";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "客户保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
	$(function(){
		$("#type").val(params['type'])
	})
	function formLoaded(){
		
	}
	
	function check(){
		var f = $("#frm").form("validate");
		if (f) {
			doSubmit();
		}
	}
	
</script>
<div class="pop" id="PerfectData" style="top:0%; width:400px; margin-left:-193px;">

      <div class="cont" style="margin-top:25px; width:100%;height:360px;">
      <form method="post" id="frm" style="width:85%; ">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="35%">客户名</th>
                <td width="65%"><input class="easyui-textbox"  name="name" id="name" style="width:90%;"></td>
            </tr>
            <tr>
                <th width="35%">公司名</th>
                <td width="65%"><input class="easyui-textbox" data-options="missingMessage:'请输入公司名！'"  name="companyName" id="companyName" style="width:90%;"></td>
            </tr>
            <tr>
                <th width="35%">联系人</th>
                <td width="65%"><input class="easyui-textbox"  name="contactUser" id="contactUser" style="width:90%;"></td>
            </tr>
            <tr>
                <th width="35%">电话号码</th>
                <td width="65%"><input class="easyui-textbox" data-options="required:true,missingMessage:'请输入电话号码！'" name="mobile" id="mobile" style="width:90%;"></td>
            </tr>
           <!--  <tr>
                <th width="35%">计划状态</th>
                <td width="65%">
	                <select class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'请选择计划状态！'" style="width:100px" name="planState" id="planState">
	                	<option value=""></option>
	                	<option value="UnPlan">未入计划</option>
	                	<option value="Planned">已入计划</option>
	                	<option value="Contact">已联系</option>
	                </select>
	            </td>
            </tr>
             <tr>
                <th width="35%">客户接听状态</th>
                <td width="65%">
	                <select class="easyui-combobox" data-options="editable:false" style="width:100px" name="receivingState" id="receivingState">
	                	<option value=""></option>
	                	<option value="Busy">线路忙（占线）</option>
	                	<option value="Empty">空号</option>
	                	<option value="IOException">系统IO异常</option>
	                	<option value="HangUpAfAns">接听后挂断</option>
	                	<option value="NoAnswer">无人接听</option>
	                	<option value="NoSpeakAfAns">接听后无人说话</option>
	                	<option value="Answer">正常接听</option>
	                </select>
	            </td>
            </tr>
            <tr>
                <th width="35%">客户分类</th>
                <td width="65%">
	                <select class="easyui-combobox" data-options="editable:false,required:true" style="width:100px" name="customerType" id="customerType">
	                	<option value=""></option>
	                	<option value="A">A</option>
	                	<option value="B">B</option>
	                	<option value="C">C</option>
	                	<option value="D">D</option>
	                	<option value="E">E</option>
	                	<option value="F">F</option>
	                </select>
	            </td>
            </tr>
            <tr>
                <th width="35%">客户联系状态</th>
                <td width="65%">
	                <select class="easyui-combobox" data-options="editable:false,required:true" style="width:100px" name="contactState" id="contactState">
	                	<option value=""></option>
	                	<option value="UnContact">未联系</option>
	                	<option value="Contacted">已联系</option>
	                	<option value="Contacting">联系中</option>
	                </select>
	            </td>
            </tr> -->
           <!--  <tr>
                <th width="35%">通话时长</th>
                <td width="65%"><input class="easyui-datebox" type="text" style="width: 90%;" name="talkTime" id="talkTime" ></td>
            </tr>
            <tr>
                <th width="35%">上次拨打</th>
                <td width="65%"><input class="easyui-datebox" type="text" style="width: 90%;" name="lastCallDate" id="lastCallDate" ></td>
            </tr> -->
            </tbody>
        </table>
          <input type="hidden" name="id" id="id">
          <input type="hidden" name="type" id="type">
        </form>
      </div>
      <div class="btn-box bg-gray">
	       <a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>
	       
	  </div>
</div>      
</body>
</html>