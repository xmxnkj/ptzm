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
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};

	function reloadData(){
		$("#tbl").datagrid("reload");
	}
	if (typeof params != 'undefined' && params.managerUser == 'true') {
		var listjsonUrl = 'list?isUser=true';
		var editUrl = 'ivr/aiResponse/showPage?pageName=managerForm';
		var deleteUrl = 'deleteManager';
		var addTitle="添加答复信息";
		var editTitle = "修改答复信息";
	}else {
		var listjsonUrl = 'list?status=1';
		var editUrl = 'ivr/aiResponse/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle="添加答复信息";
		var editTitle = "修改答复信息";
	}
	
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 520;

$(function(){
	loadFlowTemplate();
});

//模板
function loadFlowTemplate(){
	$("#flowTemplateId").combobox({
		url:'../../ivr/flowTemplate/list',
		valueField:'id',  
	    textField:'name',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return data.rows;
        },
        onSelect: function (node) {
        	
        }
	});
}
function template(val,row){
	return row.flowTemplate.name;
}
</script>

<div class="content">
        <div class="tab">
            <a href="${pageContext.request.contextPath}/ivr/flow/showList?type=0" class="button button-primary button-rounded button-small">流程管理</a>
        	<a href="${pageContext.request.contextPath}/ivr/aiResponse/showList?type=0" class="button button-primary button-rounded button-small">应答</a>
        </div>
        <div class="table">
            <div class="table-function">
                <div class="num">
                    <div class="ConditionTime">
		                <span>模板名称：</span>
		                <input type="text" id="flowTemplateId" name="flowTemplateId"/>
		                <span>关键字：</span>
		                <input type="text" id="keywordsString" name="keywordsString"/>
		                <a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
							                <script type="text/javascript">
									            function searchData(){
									               paramsSearch = getFormData('frmCheck',params);
									         	   var flowTemplateId = $("#flowTemplateId").combotree("getValue");
									         	   var keywordsString = $("#keywordsString").val();
									               if (flowTemplateId!="") {
									            	   paramsSearch['flowTemplateId'] = flowTemplateId;
												   }else {
													   paramsSearch['flowTemplateId'] = undefined;
												   }
									               paramsSearch["keywordsString"] = keywordsString;
									               $("#tbl").datagrid("reload",paramsSearch)
									             }
								            </script>
		            </div>
                </div>
                <div>
                </div>
            </div>
            <div class="table-content">
				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
		            <thead>
			            <tr>
			            	 <th  data-options="field:'flowCode'" >流程编号</th>
			                 <th  data-options="field:'nextStepCode'" >下一步代码</th>
			                 <th  data-options="field:'code'" >流程代码</th>
			                 <th  data-options="field:'answer'" >应答</th>
			                 <th  data-options="field:'remainOriginal'" >是否为原</th>
			                 <th  data-options="field:'remainOriginalAnswer'" >原文</th>			                 
			                 <th  data-options="field:'remainOriginalSound'" >原语音</th>
			                 <th  data-options="field:'code'" >代码</th>
			                 <th  data-options="field:'repeat'" >是否重复</th>
			                 <th  data-options="field:'keywordsString'" >关键字</th>
			            </tr>
		           </thead>
	           </table>
            </div>
        </div>
    </div>
</body>
</html>