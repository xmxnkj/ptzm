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
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	singleSelect = false;
	var privateSeaCountNum = "${privateSeaCount}";
	var privateSeaNum = "${clientUserPrivateSea}";
	var dlgWidth = 800;
	var dlgHeight = 500;
	/* toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-redo', text:'导入'+showName+'客户', handler:importCustomer};
	toolbars[3] = {iconCls:'icon-search', text:'搜索'+showName+'客户', handler:showSearch};
	toolbars[4] = {iconCls:'icon-search', text:'导出'+showName+'客户', handler:exportExcel};
	toolbars[5] = {iconCls:'icon-undo', text:'开放至公海', handler:open};
	toolbars[6] = {iconCls:'icon-undo', text:'加入计划', handler:showPlan}; */
	toolbars =undefined;
	$(function(){
		countSeatCustomer('d');
	})
	function countSeatCustomer(jDate){
		if (jDate=="d") {
			$("#dayD").attr("class","active pitch")//pitch
			$("#dayW").attr("class","")
			$("#dayM").attr("class","")
		}else if (jDate=="w") {
			$("#dayW").attr("class","active pitch")
			$("#dayM").attr("class","")
			$("#dayD").attr("class","")
		}else {
			$("#dayM").attr("class","active pitch")
			$("#dayW").attr("class","")
			$("#dayD").attr("class","")
		}
		$.ajax({
			type:'post',
			url:'countCustomerType?notClientUser=1&type=Contacted&jDate='+jDate,
			success:function(data){
				$("#CA").prop("innerHTML",data.entity.countA);
				$("#CB").prop("innerHTML",data.entity.countB);
				$("#CC").prop("innerHTML",data.entity.countC);
				$("#CD").prop("innerHTML",data.entity.countD);
				$("#CE").prop("innerHTML",data.entity.countE);
				$("#CF").prop("innerHTML",data.entity.countF);
			}
		})
	}
	var searchParams = null;
	function searchData(){
		//unContact contacting contacted 联系状态
		//customerA customerB customerC customerD customerE customerF *customerG
		var createDateLower = $("#createDateLower").datebox("getValue");
        var createDateUpper = $("#createDateUpper").datebox("getValue");
        if (createDateLower!=""&&createDateUpper!=""&&createDateLower > createDateUpper) {
			top.showAlert("前后时间差错误！");
			return;
		}
		searchParams = {};
		searchParams = getFormData('frmCheck',params);
		var checkValue = "";
		if ( $("#unContact").is(":checked")) {
			checkValue += "0,"
		}
		if ( $("#contacting").is(":checked")) {
			checkValue += "2,"
		}
		if ( $("#contacted").is(":checked")) {
			checkValue += "1,"
		}
		if (checkValue.length > 0) {
			checkValue = checkValue.substring(0,checkValue.length-1);
			searchParams['contactState_s'] = checkValue;
		}else {
			searchParams['contactState_s'] = undefined
		}
		checkValue = "";
		if ( $("#customerA").is(":checked")) {
			checkValue += "0,"
		}
		if ( $("#customerB").is(":checked")) {
			checkValue += "1,"
		}
		if ( $("#customerC").is(":checked")) {
			checkValue += "2,"
		}
		if ( $("#customerD").is(":checked")) {
			checkValue += "3,"
		}
		if ( $("#customerE").is(":checked")) {
			checkValue += "4,"
		}
		if ( $("#customerF").is(":checked")) {
			checkValue += "5,"
		}
		if (checkValue.length > 0) {
			checkValue = checkValue.substring(0,checkValue.length-1);
			searchParams['customerType_s'] = checkValue;
		}else {
			searchParams['customerType_s'] = undefined
		}
        if (createDateLower!="") {
        	   searchParams['createDateLower'] = createDateLower;
		   }else {
			   searchParams['createDateLower'] = undefined;
		   }
        if (createDateUpper!="") {
        	searchParams['createDateUpper'] = createDateUpper;
	    }else {
	    	searchParams['createDateUpper'] = undefined;
		}
		$("#tbl").datagrid("reload",searchParams);
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
	function isPrivate(val){
		if(val && val==1){
			return "否";
		}
		return "是";
	}
</script>
    <div class="content">
        <div class="DataShow">
            <div class="TimeData">
                <a class="pitch" onclick="countSeatCustomer('d')" id="dayD">今日新增</a>
                <span class="segmentation">|</span>
                <a onclick="countSeatCustomer('w')" id="dayW">本周新增</a>
                <span class="segmentation">|</span>
                <a onclick="countSeatCustomer('m')" id="dayM">本月新增</a>
                <span>（下列数据为已拨打客户分类）</span>
            </div>
            <div class="currentData">
                <div class="currentDataFrame">
                    <span>A</span>
                    <span id="CA">0</span>
                </div>
                <div class="currentDataFrame">
                    <span>B</span>
                    <span id="CB">0</span>
                </div>
                <div class="currentDataFrame">
                    <span>C</span>
                    <span id="CC">0</span>
                </div>
                <div class="currentDataFrame">
                    <span>D</span>
                    <span id="CD">0</span>
                </div>
                <div class="currentDataFrame">
                    <span>E</span>
                    <span id="CE">0</span>
                </div>
                <div class="currentDataFrame">
                    <span>F</span>
                    <span id="CF">0</span>
                </div>
            </div>
        </div>
        <div class="contentData">
            <div class="contentDataFunction">
                <div class="num">
                    <span>我的坐席：<span id="allAmount">${privateSeaCount}</span>/<span id="amount">${clientUserPrivateSea}</span>（当前/数量）</span>
                    <div class="num-box">
                        <div class="num-img" id="numImg"></div>
                    </div>
                </div>
                <div class="function">
                    <!-- <a href="#" class="button button-primary button-rounded button-small" onclick="importCustomer()">导入单个客户</a> -->
                    <a href="#" class="button button-primary button-rounded button-small" onclick="importCustomer()">批量导入客户</a>
                    <a href="#" class="button button-action button-rounded button-small" onclick="exportExcel()">导出资料</a>
                </div>
            </div>
            <div class="contentDataCondition">
                <div class="ConditionTime">
                    <span>搜索时间：</span>
	                <input class="easyui-datebox" id="createDateLower" name="createDateLower" >
	                <span>--</span>
	                <input class="easyui-datebox" id="createDateUpper" name="createDateUpper" >
                </div>
                <div class="ConditionState">
                    <label><input type="checkbox"  id="unContact" onchange="searchData()" ><span>未联系</span></label>
                    <label><input type="checkbox"  id="contacting" onchange="searchData()"><span>联系中</span></label>
                    <label><input type="checkbox"  id="contacted" onchange="searchData()"><span>已联系</span></label>
                </div>
                <div class="ConditionType">
                    <label><input type="checkbox"  id="customerA" onchange="searchData()"><span>A类</span></label>
                    <label><input type="checkbox"  id="customerB" onchange="searchData()"><span>B类</span></label>
                    <label><input type="checkbox"  id="customerC" onchange="searchData()"><span>C类</span></label>
                    <label><input type="checkbox"  id="customerD" onchange="searchData()"><span>D类</span></label>
                    <label><input type="checkbox"  id="customerE" onchange="searchData()"><span>E类</span></label>
                    <label><input type="checkbox"  id="customerF" onchange="searchData()"><span>F类</span></label>
                </div>
                <form id="frmCheck">
	                <div class="ConditionCompany">
	                    <label><input type="checkbox"  id="customerG" onchange="searchData()"><span>拜访</span></label>
	                    <input type="text" id="key" name="key" placeholder="请输入公司名称或电话号码">
	                    <a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
	                </div>
                </form>
            </div>
            <div class="contentDataTable">
                <div class="contentDataTableFuntion">
	                <a href="#" class="button button-primary button-rounded button-small" onclick="add()">添加</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="edit()">修改</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="openCustomer()">开放至客户库</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="showPlan()">加入计划</a>
	                <span>|</span>
	                <a href="#" class="button button-primary button-rounded button-small" onclick="del()">删除客户</a>
	            </div>
	            <!-- <div class="contentDataTableTitle"> -->
	            <div class="table" style="height: 100%;width: 100%;margin-top:0px;">
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
					            <th data-options="field:'isPrivate',formatter:isPrivate" style="width:100px;">是否可移入公海</th>
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
<c:import url="addPlan.jsp"></c:import>
</body>
</html>