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
	var listjsonUrl = 'list?clientUserId=${clientUser.id}';
	var editUrl = 'customer/callPlan/showEdit';
	var deleteUrl = 'deleteJson';
	var changeUrl = 'changeCallState';
	var addTitle="添加计划";
	var editTitle = "修改拨号计划";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	//singleSelect = false;
	var dlgWidth = 600;
	var dlgHeight = 600;
	toolbars = undefined
	function useFormat(val){
		return val?'<font color="green">启用</font>':'<font color="red">停用</font>';
	}
	function lineNumber(val,row){
		return val?val.lingTel:"未设置";
	}
	function pausePlan(jd){
		var row = $("#tbl").datagrid("getSelected");
		var title = jd=='go'?"继续":"暂停";
		if (row!=null) {
			var paramsData = {};
			paramsData['id'] = row.id;
			paramsData['callState'] = "Pause";
			if (jd=='go') {
				if (row['callState']=='Planning') {
					top.showAlert("计划执行中~！")
					return;
				}
				paramsData['goPlan'] = '1';
			}else {
				if (row['callState']=='Pause'){
					top.showAlert("计划暂停中~！")
					return;
				}
			}
			if (row['callState']=='Cancel') {
				top.showAlert("计划停止中，请先重置拨打！")
			}else {
				confirmAjax(title+"计划","是否"+title+"计划："+row.name,changeUrl,paramsData,title);
			}
			
		}else {
			top.showAlert("请选择要暂停的计划！");
		}
	}
	function cancelPlan(){
		var row = $("#tbl").datagrid("getSelected");
		if (row!=null) {
			var paramsData = {};
			paramsData['id'] = row.id;
			paramsData['callState'] = "Cancel";
			confirmAjax("取消计划","是否取消计划："+row.name,changeUrl,paramsData,"取消");
		}else {
			top.showAlert("请选择要取消的计划！");
		}
	}
	function resetPlan(){
		var row = $("#tbl").datagrid("getSelected");
		if (row!=null) {
			var paramsData = {};
			paramsData['id'] = row.id;
			paramsData['callState'] = "Planning";
			confirmAjax("重置计划","是否重置计划："+row.name,changeUrl,paramsData,"重置");
		}else {
			top.showAlert("请选择要重置的计划！");
		}
	}
	function backFun(data,judge){
		if (data.success=='true'||data.success==true) {
			$("#tbl").datagrid("reload");
		}else {
			top.showAlert(judge+"失败！");
		}
	}
	function callStateFormat(val){
		switch (val) {
		case "Pause":
			return "<font color='blue'>计划暂停</font>";
		case "Cancel":
			return "<font color='red'>计划停止</font>";	
			break;
		case "Planning":
			return "<font color='green'>计划执行中</font>";
			break;
		default:
			break;
		}
		return "";
	}
	function voiceTemplateFor(val){
		return val?val.name:'';
	}
	function showDetails(id,number){
		location.href = '/customer/callRecordDetail/showList?callPlanId='+id+"&number="+number;
	}
	
	//单击
	function rowClicked(index,row){
		columns = [
					{field: "ck",checkbox:true},
	      	    	{field :'number',title:'电话',width :parseInt($(this).width()*0.10),halign:'center',align:'center'},
	      	    	{field :'calldate',title:'拨打时间',width :parseInt($(this).width()*0.10),halign:'center',align:'center'},
	      	    	{field :'hangupdate',title:'挂断时间',width :parseInt($(this).width()*0.10),halign:'center',align:'center'},
	      	    	{field :'recordfile',title:'语音文件',width :parseInt($(this).width()*0.25),halign:'center',align:'center',formatter:function(val,row){
	      	    		if(val){
	      	    			return '<audio controls ><source src="../../'+val+'"/></audio>';
	      	    		}
	      	    	}},
	      	    	{field :'detailed',title:'详情',width :parseInt($(this).width()*0.05),halign:'center',align:'center',formatter:function(val,row){
	      	    		var rows = $("#tbl").datagrid("getSelected");
	      	    		if(row){
	      	    			return "<a href='javascript:showDetails(\""+rows.id+"\",\""+row.number+"\")'><font color='blue'>详情</font></a>";
	      	    		}
	      	    		
	      	    	}}
	      	    ];
		$('#numbersList').datagrid({
			url: "../../outBound/autodialerTask/getNumberList",
			toolbar: [
		                
		                
		                /* { text: '导入客户', iconCls: 'icon-add', handler: 
		                	function(){
		                		var row = $("#tbl").datagrid("getSelected");
		                		if(row){
		                			top.father = window;
		                			top.openDialog2("customer/callPlan/addCustomer?type=0&callPlanId="+row.id,
				        					addTitle,
				        					dlgWidth, dlgHeight
				        			);
		                		}else{
		                			alert("请选中拨号计划！");
		                		}
		                		
		                	} 
		                }, */
		                
		                { text: '删除', iconCls: 'icon-remove', handler: 
		                	function(){
		                		var checkedItems = $('#numbersList').datagrid('getChecked')
			                	if(checkedItems.length<1){
			                		alert("请选择一行进行删除！");
			                		return ;
			                	}
		                		del(checkedItems);
		                	} 
		                },
		                
		               ],
			queryParams: {"id":row.id},
			fit:true,
			striped:true,
			singleSelect:true,
			checkOnSelect:false,
			selectOnCheck:false,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:50,
			columns:[columns],
			onLoadSuccess:function(data){
			},
			onDblClickRow :function(rowIndex,rowData){

			}
		}); 
	}
	
	function del(checkedItems){
		var row = getRow();
		var arr = [];
		for(var i=0;i<checkedItems.length;i++){
			arr.push(checkedItems[i].number);
		}
		if(row){
			$.ajax({
			       type: "POST", //GET或POST,
			       async:true, //默认设置为true，所有请求均为异步请求。
			       url: "../../outBound/autodialerTask/delNumber",
			       data: {"id":row.id,"numbers":arr},
			       success: function(data) {
			    	   $('#numbersList').datagrid("reload");
			       }
			   });
		}else{
			alert("请选择任务！");
		}
		
	}
</script>
	
    <div class="content">
        <div class="table" style="margin-top:0px;height:100%">
            <div class="contentDataTableBtn" style="justify-content: space-between;height: 46px;padding: 0px 10px;box-sizing: border-box;">
                    <div class="function">
	                    <a href="#" class="button button-primary button-rounded button-small" onclick="add()">添加</a>
	                    <a href="#" class="button button-primary button-rounded button-small" onclick="edit()">修改</a>
	                    <a href="#" class="button button-caution button-rounded button-small" onclick="del()">删除</a>
	                </div>
                    <div class="function">
	                    <a href="#" class="button button-primary button-rounded button-small" onclick="pausePlan('pause')" id="pauseCall">暂停拨打</a>
	                    <a href="#" class="button button-primary button-rounded button-small" onclick="pausePlan('go')">继续拨打</a>
	                    <a href="#" class="button button-primary button-rounded button-small" onclick="cancelPlan()">取消拨打</a>
	                    <a href="#" class="button button-action button-rounded button-small" onclick="resetPlan()">重置拨打</a>
	                </div>
            </div>
         
		    <!--  -->
		     <div id="mediationagencylist_layout" class="easyui-layout" data-options="fit:true">
				<div data-options="region:'center'" style="width:35%" title="">
						<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0">
					        <thead>
					        <tr>
					        	<!-- <th data-options="field:'',checkbox:true"  style="width:30px;"></th> -->
					        	<th data-options="field:'name'" style="width:100px;">计划名称</th>
					            <th data-options="field:'voiceTemplate',formatter:voiceTemplateFor" style="width:70px;">语音模板</th>
					        	<th data-options="field:'callState',formatter:callStateFormat" style="width:70px;">计划状态</th>
					        	<th data-options="field:'inUse',formatter:useFormat" style="width:70px;">状态</th>
					        	<th data-options="field:'line',formatter:lineNumber" style="width:100px;">线路号码</th>
					        </tr>
					        </thead>
					    </table>
				</div>
				<div data-options="region:'east'" style="width:65%" title="号码表">
					<table id="numbersList" ></table>
				</div>
	</div>
        </div>
    </div>
	 

</body>
</html>