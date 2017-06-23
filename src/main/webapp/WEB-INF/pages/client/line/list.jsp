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
		var listjsonUrl = 'managerRoleList?isUser=true';
		var editUrl = 'client/line/showPage?pageName=managerForm';
		var deleteUrl = 'deleteManager';
		var addTitle="添加线路信息";
		var editTitle = "修改线路信息";
	}else {
		var listjsonUrl = 'list?status=1&clientUserId=${clientUser.id}';
		var editUrl = 'client/line/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle="添加线路信息";
		var editTitle = "修改线路信息";
	}
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 520;
	
	function roleformatter(value,row,index){
			return row.remark;
		}
	
function getDept(val,row){
	if(row.dept){
		return row.dept.name;
	}
	return "未分配";
}

function getClientUser(val,row){
	if(row.clientUser){
		return row.clientUser.name;
	}
	return "未分配";
}

$(function(){
	loadDeptTree();	
});

//加载部门的所有坐席
function loadClientUserList(){
	$("#UserRole").combobox({
		url:'../../client/userRole/list',
		valueField:'id',  
	    textField:'roleName',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return data.rows;
        },
        onSelect: function (node) {
        	
        }
	});
}

</script>

<div class="content">
        <div class="tab">
            <%-- <a href="${pageContext.request.contextPath}/client/clientUser/showList?type=0" class="button button-primary button-rounded button-small">用户</a>
            <a href="${pageContext.request.contextPath}/client/userRole/showList?type=0" class="button button-primary button-rounded button-small">角色</a>
            <a href="${pageContext.request.contextPath}/client/operate/showList?type=0" class="button button-primary button-rounded button-small">操作</a>
            <a href="${pageContext.request.contextPath}/client/dept/showList?type=0" class="button button-primary button-rounded button-small">部门</a> --%>
        </div>
        <div class="table">
            <div class="table-function">
                <div class="num">
                    <div class="ConditionTime">
		               <!--  <span>部门：</span>
		                <input type="text" name="owndept" id="owndept">
		                <span>坐席：</span>
		                <input type="text" name="clientUsers" id="clientUsers">
		                <a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
		            	<script type="text/javascript">
				            function searchData(){
				               paramsSearch = getFormData('frmCheck',params);
				         	   var deptId = $("#owndept").combotree("getValue");
				               if (deptId!="") {
				            	   paramsSearch['deptId'] = deptId;
							   }else {
								   paramsSearch['deptId'] = undefined;
							   }
				               $("#tbl").datagrid("reload",paramsSearch);
				             }
			            </script> -->
		            </div>
                </div>
                <div>
                </div>
            </div>
            <div class="table-content">
				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
		            <thead>
			            <tr>
			                <th  data-options="field:'lingTel'" >线路号码</th>
			                <th  data-options="field:'serialNumber'" >线路编号</th>
			            	<th  data-options="field:'dept',formatter:getDept" >所属部门</th>
			            	<th  data-options="field:'clientUser',formatter:getClientUser" >所属坐席</th>
			            </tr>
		           </thead>
	           </table>
            </div>
        </div>
    </div>
</body>
</html>