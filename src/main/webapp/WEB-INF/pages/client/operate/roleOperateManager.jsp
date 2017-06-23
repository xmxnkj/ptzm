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

<script src="../../content/scripts/common/list.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'operateList';
	var editUrl = 'client/operate/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加权限";
	var editTitle = "修改权限";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 400;
	var dlgHeight = 400;
	

	
</script>


<div class="page-body">
    <div class="ope-bar">
        <a class="btn" href="../../client/userRole/showList">角色设置</a>
        <a class="btn" href="../../client/clientUser/showList" >员工管理</a>
       <a class="btn" href="../../client/clientUser/showList?managerUser=true" >用户</a>
       <a class="btn" href="../../client/operate/showList" >权限设置</a>
       <a class="btn2 active" href="../../client/operate/showPage?pageName=roleOperateManager" >角色权限管理</a>
    </div>
	<div style="height: 525px">
        选择角色：<input class="easyui-combobox" name="userRole" id="userRole" data-options="url:'../../client/userRole/rolelist',editable:false, valueField:'id', textField:'roleName',width:321,loadFilter:loadFilter">
    
    </div>
    </div>

</body>
</html>