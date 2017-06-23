<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body style="background: #f6f6f6">

<script src="../../content/scripts/format/format.js"></script>
<script src="../../content/scripts/common/list.js"></script>
 
<script type="text/javascript">
	var toolbars=[];
	toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'清空', handler:del};

	function reloadData(){
		$("#tbl").datagrid("reload");
	}
	if (typeof params != 'undefined' && params.managerUser == 'true') {
		var listjsonUrl = 'list?isUser=true';
		var editUrl = 'outBound/autodialerNumber/showPage?pageName=managerForm';
		var deleteUrl = 'deleteManager';
		var addTitle="添加号码信息";
		var editTitle = "修改号码信息";
	}else {
		var listjsonUrl = 'list?status=1';
		var editUrl = 'outBound/taskNumber/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle="添加号码信息";
		var editTitle = "修改号码 信息";
	}
	
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 520;

//单击
function rowClicked(index,row){
	columns = [
      	    	{field :'number',title:'电话',width :parseInt($(this).width()*0.10),halign:'center',align:'center'}
      	    ];
	$('#numbersList').datagrid({
		url: "../../outBound/autodialerTask/getNumberList",
		toolbar: [
	                { text: '增加', iconCls: 'icon-add', handler: function () { } },
	                { text: '修改', iconCls: 'icon-edit', handler: function () {  } },
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
		onLoadSuccess:function(data){},
		onDblClickRow :function(rowIndex,rowData){

		}
	}); 
}

$(function(){
	
	$("#numberFile").change(function(){
		upload();
	});
});

function upload(){
	var list = $("#numberFile")[0].files;
	var formData = new FormData();
	
	var outBoundTaskId = $("#outBoundTask").combotree("getValue");
	var file = document.getElementById('numberFile');
	if(!outBoundTaskId){
		alert("请选择拨打任务！");
		file.value = '';
		return;
	}
	
	if(list.length>0){
		formData.append("img",list[0]);
	}else{
		alert("请选择文件！");
		file.value = '';
		return;
	}
	
	//判断文件类型
	var type = list[0].name;
	var arr = type.split(".");
	type = arr[arr.length-1];
	if(type!='xlsx' && type!='xls'){
		alert("请选择.xlsx后缀的文件上传！");
		file.value = '';
		return;	
	}
	formData.append("outBoundTaskId",outBoundTaskId);
	
	$.ajax({  
         url: "inputNumbers",  
         type: 'POST',  
         data: formData,  
         async: false,  
         cache: false,  
         contentType: false,  
         processData: false,  
         success: function (data) {
          alert(data.msg);
          reloadData();
         },  
         error: function (returndata) {  
           alert("error");
             console.log(returndata);  
         }  
    });
}


function showCallPlan(val,row){
	return row.callPlan.name;
}

function showSpeech(val,row){
	return row.callPlan.voiceTemplate.name;
}

function callStateFormat(val,row){
	switch (row.callPlan.callState) {
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

function useFormat(val,row){
	return row.callPlan.inUse?'<font color="green">启用</font>':'<font color="red">停用</font>';
}

</script>
    
    <div id="mediationagencylist_layout" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'" style="height:600px" title="">
			<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
			            <thead>
				            <tr>
				                 <th data-options="field:'name'" >任务名</th>
				                 <th data-options="field:'maximumcall'" >最大并发</th>
				                 <th data-options="field:'callPlan',formatter:showCallPlan" >拨打计划</th>
				                 <th data-options="field:'callPlan.voiceTemplate',formatter:showSpeech" >语音模板</th>
				                 <th data-options="field:'callPlan.callStateFormat',formatter:callStateFormat" >语音模板</th>
				                 <th data-options="field:'callPlan.inUse',formatter:useFormat" style="width:100px;">状态</th>
				            </tr>
			           </thead>
		     </table>
		</div>
	<div data-options="region:'south'" style="height:400px" title="拨号任务号码">
		<table id="numbersList" ></table>
	</div>
</div>
</body>
</html>