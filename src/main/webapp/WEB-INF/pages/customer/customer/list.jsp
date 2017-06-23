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
<script src="../../content/scripts/customer/customer.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'customer/customer/showEdit?type='+params['type'];
	var deleteUrl = 'deleteJson';
	var showName = params['type']!='1'?'私海':'公海';
	var addTitle="添加"+showName+"客户";
	var editTitle = "修改"+showName+"客户";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	singleSelect = false;
	var dlgWidth = 400;
	var dlgHeight = 500;
	/* if (params['type']!=1) {
		toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
		toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
		toolbars[2] = {iconCls:'icon-redo', text:'导入'+showName+'客户', handler:importCustomer};
		toolbars[3] = {iconCls:'icon-search', text:'搜索'+showName+'客户', handler:showSearch};
		toolbars[4] = {iconCls:'icon-search', text:'导出'+showName+'客户', handler:exportExcel};
		toolbars[5] = {iconCls:'icon-undo', text:'开放至公海', handler:open};
		toolbars[5] = {iconCls:'icon-undo', text:'加入计划', handler:showPlan};
	}else {
		toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
		toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
		toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
		toolbars[3] = {iconCls:'icon-redo', text:'导入'+showName+'客户', handler:importCustomer};
		toolbars[4] = {iconCls:'icon-search', text:'搜索'+showName+'客户', handler:showSearch};
		toolbars[5] = {iconCls:'icon-undo', text:'分配坐席', handler:showSeat};
	} */
	var gridOpt = {};
	gridOpt["onDblClickRow"] = onDbClick;
	function onDbClick(index,row){
		$("#tbl").datagrid("selectRow",index);
		edit();
	}
	function importCustomer(){
		var importUrl = 'customer/customer/showPage?pageName=import&type='+params['type'];
		top.openDialog(importUrl,"导入"+showName+"客户", 300,300);
	}
	$(function(){
		if (params['type']=='1') {
			$("#css-1").attr("class","btn2 active");
			$("#css-2").attr("class","btn");
		}else {
			$("#css-2").attr("class","btn2 active");
			$("#css-1").attr("class","btn");
		}
	})
	function closeSeat(){
		$("#divSeat ").dialog("close");
	}
	function showSeat(){
		$("#divSeat").dialog({
			title:'分配坐席',
			modal:true
		})
		$("#divSeat").dialog("open");
	}
	function closePlan(){
		$("#divPlan").dialog("close");
	}
	function showPlan(){
		$("#divPlan").dialog({
			title:'加入计划',
			modal:true
		})
		$("#divPlan").dialog("open");
	}
	function exportExcel(){
		doExportCommon("tbl",params,"../../customer/customer/exportCommon","customer/customer");
	}
	function callPlanFor(val){
		return val?val.name:'';
	}
	function voiceTemplateFor(val,row,index){
		return row.callPlan?(row.callPlan.voiceTemplate?row.callPlan.voiceTemplate.name:''):'';
	}
	function clientUserFor(val){
		return val?val.name:'';
	}
	

</script>
<div class="page-body" style="height:100%;">
	<div id="remind" class="ope-bar clearfix">
	<%-- <c:import url="../../header.jsp">
    </c:import> --%>
    <a id="css-1" class="btn2 active" href="${pageContext.request.contextPath}/customer/customer/showList?type=1">公海客户</a>
    <a id="css-2" class="btn" href="${pageContext.request.contextPath}/customer/customer/showList?type=0">私海客户</a>
    </div>
	<!-- <div style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);"> -->
	 <div class="table" style="height: 100%;width: 100%;margin-top:0px;">
    <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" >
        <thead>
        <tr>
        	<th data-options="field:'',checkbox:true"  style="width:30px;"></th>
        	<th data-options="field:'name'" >客户名称</th>
            <th data-options="field:'companyName'" >公司名</th>
            <th data-options="field:'contactUser'" >联系人</th>
            <th data-options="field:'mobile'" >电话号码</th>
            <th data-options="field:'customerType'" style="width:60px;">客户分类</th>
            <th data-options="field:'contactState',formatter:csFormat" style="width:100px;">客户联系状态</th>
            <th data-options="field:'planState',formatter:psFormat" style="width:100px;">计划状态</th>
            <th data-options="field:'receivingState',formatter:rsFormat" style="width:100px;">接听状态</th>
            <th data-options="field:'callPlan',formatter:callPlanFor" style="width:70px;">拨打计划</th>
            <th data-options="field:'voiceTemplate',formatter:voiceTemplateFor" style="width:70px;">语音模板</th>
            <th data-options="field:'clientUser',formatter:clientUserFor" style="width:100px;">所属坐席</th>
            <th data-options="field:'lastCallDate'" >上次拨打</th>
            <th data-options="field:'talkTime'" style="width:60px;">通话时长</th>
            <%-- <c:if test="${params['type']=='0' }">
            	<th data-options="field:'clientUser',formatter(val){return val?val.name:''}" style="width:100px;">所属坐席</th>
            </c:if> --%>
            <!-- <th data-options="field:'type',formatter:carModelFormatter" style="width:70px;">公/私海</th>
            <th data-options="field:'callRecord',formatter:carModelFormatter" style="width:70px;">通话记录</th>
            <th data-options="field:'followRecord',formatter:carModelFormatter" style="width:70px;">跟进记录</th>
            <th data-options="field:'voiceTemplate',formatter:carModelFormatter" style="width:70px;">语音模板</th> -->
        </tr>
        </thead>
    </table>
    </div>
</div>

<c:import url="../customerSearch.jsp"></c:import>
<c:import url="distributionSeat.jsp"></c:import>
<c:import url="addPlan.jsp"></c:import>
</body>
</html>