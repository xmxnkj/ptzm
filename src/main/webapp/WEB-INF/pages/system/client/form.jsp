<%@page import="javax.servlet.http.Cookie"%>
<%@page import="com.fasterxml.jackson.databind.util.EnumResolver"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.hp.hpl.sparta.Element"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/site.css">
    <script src="${pageContext.request.contextPath}/content/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/content/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/default/easyui.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/content/scripts/main/main.js"></script>



</head>
<body>
 
 <script src="../../content/scripts/common/form.js"></script>
                
<script type="text/javascript">
	var saveUrl = "createClient";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	var area = true;
	function formLoaded(data){
		if (data&&data.area) {
			var ids =data.area.areaContentId.split(",");
			var contents = data.area.areaContent.split("|");
			var json = [];
			for (var i = 0; i < ids.length; i++) {
				json.push({'id':ids[i],'name':contents[i]})
			}
			$("#province").combobox("loadData", json);
		}
		if (data&&data.clientMeal&&data.clientMeal!=null) {
			$("#clientMeal_id").combobox("setText",data.clientMeal.name)
		}
	}
	if (typeof params['delete'] != 'undefined') {
		saveUrl = "delClient";
	}else if (typeof eid != 'undefined') {
		saveUrl = "modifyClient";
	}
	$(function(){
		$("#area_id").combobox({
			onSelect:function(row){
				var ids = row.areaContentId.split(",");
				var contents = row.areaContent.split("|");
				var json = [];
				for (var i = 0; i < ids.length; i++) {
					json.push({'id':ids[i],'name':contents[i]})
				}
				$("#province").combobox("setValue","");
				$("#city").combobox("setValue","");
				$("#county").combobox("setValue","");
				$("#province").combobox("loadData", json);
			}
		})
		$("#province").combobox({
			onSelect:function(value){
				$("#city").combobox("setValue","");
				$("#county").combobox("setValue","");
				$("#city").combobox({
					url:'../../system/baseArea/list?parentId='+value.id,
				})
			},
			onChange:function(value){
				if (area) {
					var county = $("#county").combobox("getValue");
					var city = $("#city").combobox("getValue");
					$("#city").combobox({
						url:'../../system/baseArea/list?parentId='+value,
						onChange:function(value_s){
							if (value_s=='') {
								$("#county").combobox({
									url:'../../system/baseArea/list?parentId=000000',
											
								})
							}else {
								$("#county").combobox({
									url:'../../system/baseArea/list?parentId='+value_s,
											
								})
							}
							if (county!='') {
								$("#county").combobox("setValue",county)
								county='';
							}
						}
					})
					if (city!='') {
						$("#city").combobox("setValue",county)
						city='';
					}
					area =false
				}
			}
		})
	})
	function check(){
		/* alert($("#clientMeal_id").combobox("getValue"))
		return false; */
		var rst = verification();
		if (rst) {
			$("#address").val($("#province").combobox("getText")+"-"+
					$("#city").combobox("getText")+"-"+
					$("#county").combobox("getText"))
			doSubmit();
		}
	}
	
	function verification(){
		var rst = true;
		var info = "";
		var areaId = $("#area_id").combobox("getValue");
		var province = $("#province").combobox("getValue");
		var city = $("#city").combobox("getValue");
		var county = $("#county").combobox("getValue");
		var name = $("#name").val();
		var loginName = $("#loginName").val();
		
		var clientMealId = $("#clientMeal_id").combobox("getValue");
		
		if (areaId=='') {
			info += "请选择区域！<br>";
			rst = false;
		}
		if (province==''|| city==''|| county=='' ) {
			info += "请选择完整客户地址！<br>";
			rst = false;
		}
		/* if (areaId===province) {
		}else {
			info += "客户地址与区域需一致！<br>";
			rst = false;
		} */
		if (name=='') {
			info += "请填写账户名称！<br>";
			rst = false;
		}
		if (loginName=='') {
			info += "请填写客户名称！<br>";
			rst = false;
		}
		if (clientMealId=='') {
			info += "请选择套餐！<br>";
			rst = false;
		}
		
		if (!rst) {
			top.showAlert(info);
		}
		return rst;
	}
</script>
    <div class="pop" id="PerfectData" style="top:10%; width:400px; margin-left:-280px;">
    <div>
 
   </div>
      <div class="cont" style="max-height: 500px">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
            	<th width="30%">区域</th>
            	<td><select class="easyui-combobox" id="area_id" name="area.id" data-options="editable:true,valueField:'id',textField:'name',width:220,url:'../../system/baseArea/list?parentId=00',loadFilter:loadFilter">
            	</select></td>
            </tr>
            <tr>
                <th width="30%">客户名称</th>
                <td width="70%"><input class="inp2" type="text" name="loginName" id="loginName"></td>
            </tr>
            <tr>
                <th width="30%">客户地址</th>
                <td width="70%">
                <select class="easyui-combobox" id="province" name="province" data-options="editable:true,valueField:'id',textField:'name',width:60"></select>
		                省<select class="easyui-combobox" id="city" name="city" data-options="editable:false,valueField:'id',textField:'name',width:60,loadFilter:loadFilter"></select>
		                市<select class="easyui-combobox" id="county" name="county" data-options="editable:false,valueField:'id',textField:'name',width:60,loadFilter:loadFilter"></select>
		                县</td>
	            <input type="hidden" id="address" name="address">
            </tr>
            <tr>
                <th width="30%">负责人</th>
                <td width="70%"><input class="inp2" type="text" name="responsibleUser" id="responsibleUser"></td>
            </tr>
            <tr>
                <th width="30%">电话</th>
                <td width="70%"><input class="inp2" type="text" name="cellPhone" id="cellPhone"></td>
            </tr>
            <tr>
                <th width="30%">账号名称</th>
                <td width="70%"><input class="inp2" type="text" name="name" id="name"></td>
            </tr>
            <tr>
                <th width="30%">选择套餐</th>
                <td><select class="easyui-combobox" id="clientMeal_id" name="clientMeal.id" data-options="editable:true,valueField:'id',textField:'name',width:180,url:'../../system/clientMeal/list?mealState=true',loadFilter:loadFilter">
            	</select></td>
            </tr>
          <!--   <tr>
                <th width="30%">密码</th>
                <td width="70%"><input class="inp2" type="password" name="password" id="password"></td>
            </tr>
            <tr>
                <th width="30%">确认密码</th>
                <td width="70%"><input class="inp2" type="password" name="comPw" id="comPw"></td>
            </tr> -->
            <tr>
                <th width="30%">账号有效期</th>
            	<td width="70%"><input class="easyui-datebox"  name="effectiveDate" id="effectiveDate"/></td>
            </tr>
            <c:if test="${empty params['delete'] }">
            	<tr>
	                <th width="30%">账号状态</th>
	                <td width="70%"><input type="radio" name="state"  id="state" value="true"> 开启
	               					<input  type="radio" name="state"  id="state"  value="false">停用</td>
	            </tr>
            	<c:if test="${empty id }">
	            	 <tr>
		                <th width="30%">备注</th>
		                <td width="70%"><textarea class="inp2" name="description" id="description" style="width: 200px;height: 100px"></textarea></td>
		            </tr>
	            </c:if>
	           <c:if test="${!empty id }">
	           		 <tr>
		                <th width="30%">总账号密码</th>
		                <td width="70%"><input class="inp2" type="password" name="managerPw" id="managerPw" ></td>
		            </tr>
	            	 <tr>
		                <th width="30%">修改备注</th>
		                <td width="70%"><textarea class="inp2" name="modifyRemark" id="modifyRemark" style="width: 200px;height: 100px"></textarea></td>
		            </tr>
	            </c:if>
            </c:if>
             <c:if test="${!empty params['delete'] }">
             	<tr>
	                <th width="30%">总账号密码</th>
	                <td width="70%"><input class="inp2" type="password" name="managerPw" id="managerPw" ></td>
	            </tr>
            	 <tr>
	                <th width="30%">删除说明</th>
	                <td width="70%"><textarea class="inp2" name="modifyRemark" id="modifyRemark" style="width: 200px;height: 100px"></textarea></td>
	            </tr>
             </c:if>
            </tbody>
        </table>

		 <input type="hidden" name="id" id="id">
          
        </form>
      </div>
    </div>
     <c:if test="${empty params['delete'] }">
     	<div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">
          		<c:if test="${!empty id }">
          			修改
          		</c:if>
          		<c:if test="${empty id }">
          			提交
          		</c:if>
          	</a>         
    	</div>
     </c:if>
	<c:if test="${!empty params['delete'] }">
	<div class="btn-box bg-gray">
		<a class="btn2 " id="PerfectDataSubmit" onclick="check();">删除</a>        </div> 
	</c:if>

</body>
</html>