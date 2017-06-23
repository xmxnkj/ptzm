<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=session.getMaxInactiveInterval() %></title>
</head>
<body>
<script src="../../content/scripts/format/format.js"></script>
<script src="../../content/scripts/common/list.js"></script>
<script src="../../content/scripts/customer/customer.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'customer/customer/showEdit?type='+params['type'];
	var deleteUrl = 'deleteJson';
	var showName = params['type']!='1'?'坐席库':'客户库';
	var addTitle="添加"+showName+"客户";
	var editTitle = "修改"+showName+"客户";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "删除客户的同时将删除之前的通话记录！确认要删除吗？";
	var deleteSuccess = "删除成功！";
	singleSelect = false;
	var dlgWidth = 800;
	var dlgHeight = 500;
	/* toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
	toolbars[3] = {iconCls:'icon-redo', text:'导入'+showName+'客户', handler:importCustomer};
	toolbars[4] = {iconCls:'icon-search', text:'搜索'+showName+'客户', handler:showSearch};
	toolbars[5] = {iconCls:'icon-undo', text:'分配坐席', handler:showSeat}; */
	toolbars = undefined;
	var paramsSearch = {};
	var privateSeaCountNum = "${privateSeaCount}";
	var privateSeaNum = "${clientUserPrivateSea}";	
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
<div class="content">
    <div class="contentData" style="height:100%;margin-top:0px">
    	
        <div class="contentDataFunction" >
            <div class="num">
                <span>我的坐席：<span id="allAmount">${privateSeaCount}</span>/${clientUserPrivateSea}（当前/数量）</span>
                <div class="num-box">
                    <div class="num-img" id="numImg"></div>
                </div>
            </div>
            <div class="function">
                <!-- <a href="#" class="button button-primary button-rounded button-small">加入至私海客户</a> -->
				<!-- <a href="#" class="button button-primary button-rounded button-small" onclick="importCustomer()">导入单个客户</a> -->
                <a href="#" class="button button-primary button-rounded button-small" onclick="importCustomer()">批量导入客户</a>
            </div>
        </div>
        <div class="contentDataCondition" style="height: 130px;">
            <div class="ConditionTime">
                <span>搜索时间：</span>
                <input class="easyui-datebox" id="createDateLower" name="createDateLower"/>
                <span>--</span>
                <input class="easyui-datebox" id="createDateUpper" name="createDateUpper"/>
            </div>
            <form id="frmCheck">
	            <div class="ConditionCompany">
	                <input  type="text" id="key" name="key" placeholder="请输入公司名称或电话号码">
	                <a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
	            </div>
            </form>
            <script type="text/javascript">
	            function searchData(){
	               paramsSearch = getFormData('frmCheck',params);
	         	   /* 默认参数 */
	         	   var createDateLower = $("#createDateLower").datebox("getValue");
	               var createDateUpper = $("#createDateUpper").datebox("getValue");
	               if (createDateLower!=""&&createDateUpper!=""&&createDateLower > createDateUpper) {
	       			top.showAlert("前后时间差错误！");
	       			return;
	       		   }
	               if (createDateLower!="") {
	            	   paramsSearch['createDateLower'] = createDateLower;
				   }else {
					   paramsSearch['createDateLower'] = undefined;
				   }
	               if (createDateUpper!="") {
	            	   paramsSearch['createDateUpper'] = createDateUpper;
				   }else {
					   paramsSearch['createDateUpper'] = undefined;
					}
	               $("#tbl").datagrid("reload",paramsSearch)
	         	   closeSearch();
	             }
            </script>
        </div>
        <div class="contentDataTable" style="    height: calc(100% - 180px);">
            <div class="contentDataTableFuntion">
	                <a href="#" class="button button-primary button-rounded button-small" onclick="add()">添加</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="edit()">修改</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="del()">删除</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="showSeat()">分配坐席</a>
	            </div>
            <!-- <div class="contentDataTableTitle"> -->
<!--             <div style="height:-moz-calc(100% - 50px);height:-webkit-calc(100% - 50px);height:calc(100%-50px);">
 -->             <div class="table" style="height: 100%;width: 100%;margin-top:0px;">	
 				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0">
			        <thead >
				        <tr>
				        	<th data-options="field:'',checkbox:true"  style="width:30px;"></th>
				        	<th data-options="field:'createDate'" style="width:100px;">创建时间</th>
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
    </div>
</div>
<c:import url="distributionSeat.jsp"></c:import>
</body>
</html>