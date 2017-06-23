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
<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'user/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加管理员";
	var editTitle = "修改管理员资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	function stateFormat(val,row){
		return val=='Normal'?'启用':'停用';
	}
</script>
<script src="../../content/scripts/common/list.js"></script>
    <div class="page-body">
    	<!-- <div class="ope-bar clearfix">
        	<div class="fl">
               <a class="btn2 active" href="showList">管理员列表</a>
            </div>
        </div> -->
        <div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
        </div>
        <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
                <th data-options="field:'loginAccount'">管理员账号</th>
                <th data-options="field:'userState',formatter:stateFormat">账号状态</th>
                <th data-options="field:'bornDate'">最后登录时间</th>
                <th data-options="field:'defaultLoginIp'">最后登录ip</th>
                <th data-options="field:'displayOrder'">登录次数</th>
                <th data-options="field:'description'">账户权限</th>
                <th data-options="field:'pinYin'">生效时间</th>
            </tr>
           </thead>
        </table>
        </div>
    </div>
 


</body>
</html>