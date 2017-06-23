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
<script src="../../content/scripts/format/format.js"></script>
<script src="../../content/scripts/common/list.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'client/remindOpRecord/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加客户";
	var editTitle = "修改客户";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 400;
	var dlgHeight = 500;
	toolbars=undefined;
	function  edit(){}
	$(function(){
		var a = $("#remind").find("a");
		var upper = changeNumber('${client.bornRemindDays}');
		var lower = upper-changeNumber('${client.bornRemindInterval}');
		var reUrl = $("#remind").find("a").eq(1).attr('href')+'&birthdayRemindLower='+lower+'&birthdayRemindUpper='+upper;
		a.eq(1).attr('href',reUrl);
		upper = changeNumber('${client.insuranceRemindDays}');
		lower = upper-changeNumber('${client.insuranceRemindInterval}');
		reUrl = $("#remind").find("a").eq(2).attr('href')+'&insuranceRemindLower='+lower+'&insuranceRemindUpper='+upper;
		a.eq(2).attr('href',reUrl);
		upper = changeNumber('${client.yearCheckRemindDays}');
		lower = upper-changeNumber('${client.yearCheckRemindInterval}');
		reUrl = $("#remind").find("a").eq(3).attr('href')+'&yearCheckRemindLower='+lower+'&yearCheckRemindUpper='+upper;
		a.eq(3).attr('href',reUrl);
		upper = changeNumber('${client.careRemindDays}');
		lower = upper-changeNumber('${client.careRemindInterval}');
		reUrl = $("#remind").find("a").eq(4).attr('href')+'&careRemindLower='+lower+'&careRemindUpper='+upper;
		a.eq(4).attr('href',reUrl);
		upper = changeNumber('${client.saleRemindDays}');
		lower = upper-changeNumber('${client.saleRemindInterval}');
		reUrl = $("#remind").find("a").eq(5).attr('href')+'&remindWorkLower='+lower+'&remindWorkUpper='+upper;
		a.eq(5).attr('href',reUrl);
		if (typeof params['onlyCustomer'] != 'undefined') {
			var as = $("#tb").find("a");
		 	upper = changeNumber('${client.insuranceRemindDays}');
			lower = upper-changeNumber('${client.insuranceRemindInterval}');
			reUrl = as.eq(0).attr('href')+'&insuranceRemindLower='+lower+'&insuranceRemindUpper='+upper;
			as.eq(0).attr('href',reUrl);
		}else if(typeof params['onlyBillItem'] != 'undefined'){
			var as = $("#tb").find("a");
			upper = changeNumber('${client.saleRemindDays}');
			lower = upper-changeNumber('${client.saleRemindInterval}');
			reUrl = as.eq(0).attr('href')+'&remindWorkLower='+lower+'&remindWorkUpper='+upper;
			as.eq(0).attr('href',reUrl);
		}
	})
</script>
<div class="page-body" style="height:100%;">
	<div id="remind" class="ope-bar clearfix">
	<c:import url="../../header.jsp">
    </c:import>
    </div>
	<div style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
    <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <thead>
        <tr>
        	<th data-options="field:'dealDate'" style="width:150px;">处理时间</th>
            <th data-options="field:'opUser',formatter(val,row){return val?val.name:''}" style="width:100px;">处理人员</th>
            <th data-options="field:'clientUser',formatter(val,row){return val?val.name:''}" style="width:100px;">操作人员</th>
            <th data-options="field:'shop',formatter(val,row){return val?val.name:''}" style="width:100px;">操作门店</th>
            <th data-options="field:'description'" style="width:100px;">处理说明</th>
            <th data-options="field:'insuranceDate'" style="width:100px;">保险到期时间</th>
            <th data-options="field:'insuranceCompany'" style="width:100px;">保险公司</th>
            <th data-options="field:'insuranceType'" style="width:100px;">购买险种</th>
        </tr>
        </thead>
    </table>
    </div>
</div>
</body>
</html>