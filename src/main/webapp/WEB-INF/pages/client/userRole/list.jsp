<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	    <link rel="stylesheet" href="../../content/css/reset.css">
		<link rel="stylesheet" href="../../content/css/common.css">
		<link rel="stylesheet" href="../../content/css/style.css">
		<script src="../../content/js/jquery-3.1.1.min.js"></script>
		<script src="../../content/js/common.js"></script>
		<script src="../../content/scripts/common/setting.js"></script>
		<script src="../../content/scripts/common/utils.js"></script>
		<script src="../../content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
		<script src="../../content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
		<link href="../../content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
		<link href="../../content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
		<script src="../../content/scripts/common/gridutils.js"></script>
	 
	
</head>
<body>

<script src="../../content/scripts/common/list.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'rolelist';
	var editUrl = 'client/userRole/showEdit';
	//var deleteUrl = 'deleteUserRole';
	
	var addTitle="添加角色";
	var editTitle = "修改角色";
	var confirmDeleteTitle = "提示信息";
	//var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 500;
</script>

<div class="content">
        <div class="tab">
            <a href="${pageContext.request.contextPath}/client/clientUser/showList?type=0" class="button button-primary button-rounded button-small">用户</a>
            <a href="${pageContext.request.contextPath}/client/userRole/showList?type=0" class="button button-primary button-rounded button-small">角色</a>
            <a href="${pageContext.request.contextPath}/client/operate/showList?type=0" class="button button-primary button-rounded button-small">操作</a>
            <a href="${pageContext.request.contextPath}/client/dept/showList?type=0" class="button button-primary button-rounded button-small">部门</a>
        </div>
        <div class="table">
            <div class="table-function">
                <div class="num">
                    <div class="ConditionTime">
		                <span>角色名称：</span>
	                	<input type="text" class="easyui-textbox" name="roleName" id="roleName">
	               		<a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
		           			<script type="text/javascript">
					            function searchData(){
					               paramsSearch = getFormData('frmCheck',params);
					         	   var roleName = $("#roleName").val();
					         	   
					               if (roleName!="") {
					            	   paramsSearch['roleName'] = roleName;
								   }else {
									   paramsSearch['roleName'] = undefined;
								   }
					               
					               $("#tbl").datagrid("reload",paramsSearch)
					             }
				            </script>   
		            </div>
                </div>
                <div>
                    <a href="#" class="button button-primary button-rounded button-small">新增用户</a>
                    <a href="#" class="button button-primary button-rounded button-small">一键分配主叫号码</a>
                </div>
            </div>
            <div class="table-content">
				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
			        <thead>
			            <tr>
			                <th data-options="field:'roleName'" style="width:70px;">角色名</th>
			                <th data-options="field:'remark',width:100" style="width:300px;">备注</th>
			                <th data-options="field:'show',width:600" style="width:600px;">权限</th>
			            </tr>
			        </thead>  
			    </table>
            </div>
        </div>
    </div>

<%-- <div class="page-body" style="height:100%;">

    <div id="remind" class="ope-bar clearfix">
    	<a id="css-1" class="btn" href="${pageContext.request.contextPath}/client/clientUser/showList?type=0">用户</a>
    	<a id="css-2" class="btn2 active" href="${pageContext.request.contextPath}/client/userRole/showList?type=0">角色</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/operate/showList?type=0">操作</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/dept/showList?type=0">部门</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/seatGroup/showList?type=0">坐席组</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/line/showList?type=0">线路</a>
    </div>
		
			<form id="frmCheck">
				<div class="ConditionTime">
	                <span>角色名称：</span>
	                <input type="text" class="easyui-textbox" name="roleName" id="roleName">
	                <a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
            	</div>
            </form>
            <script type="text/javascript">
	            function searchData(){
	               paramsSearch = getFormData('frmCheck',params);
	         	   var roleName = $("#roleName").val();
	         	   
	               if (roleName!="") {
	            	   paramsSearch['roleName'] = roleName;
				   }else {
					   paramsSearch['roleName'] = undefined;
				   }
	               
	               $("#tbl").datagrid("reload",paramsSearch)
	             }
            </script>    
       	<div style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <thead>
            <tr>
                <th data-options="field:'roleName'" style="width:70px;">角色名</th>
                <th data-options="field:'remark',width:100" style="width:300px;">备注</th>
                <th data-options="field:'show',width:600" style="width:600px;">权限</th>
            </tr>
           </thead>  
        </table>
        </div> --%>

</body>
</html>