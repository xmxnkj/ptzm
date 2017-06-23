<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
   <%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/reset.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/common.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/style.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/site.css">
     <script src="${pageContext.request.contextPath}/content/js/jquery-3.1.1.min.js"></script>
     <script src="${pageContext.request.contextPath}/content/js/common.js"></script>
     <script src="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
	 <link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet"/>
	 <link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/default/easyui.css" rel="stylesheet"/>
	 <link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	 <script src="${pageContext.request.contextPath}/content/scripts/main/main.js"></script>  --%>
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
<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'client/client/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加管理员";
	var editTitle = "修改管理员资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	
</script>
<script src="../../content/scripts/common/list.js"></script>
    
    
    <div class="page-body">
    <div class="ope-bar">
        <a class="btn" href="../../client/userRole/showList">角色设置</a>
        <a class="btn" href="../../client/clientUser/showList">员工管理</a>
        <a class="btn2 active" href="../../client/client/showList">管理员</a>
    </div>

     <!--<div class="tab-cont">
        <div class="ope-bar2 clearfix">
            <div class="fl" >
                <a class="a-btn" href="" id=""><i class="icon-edit"></i>增加</a>
            </div>
        </div> -->
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
                <th data-options="field:'name'">管理员</th>
                <th data-options="field:'loginName'">账户</th>
                <th data-options="field:'userRole'">角色</th>
                <th data-options="field:'powerContent'">描述</th>
                <th data-options="field:'loginTime'">最后登录时间</th>
                <th data-options="field:'loginOutTime'">最后登出时间</th>
                <th data-options="field:'op'">操作</th>
            </tr>
           </thead>
        </table>
    </div>
 


</body>
</html>