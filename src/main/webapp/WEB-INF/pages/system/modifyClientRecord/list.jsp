<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src="../../content/scripts/common/list.js"></script>
<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'client/showEdit';
	var deleteUrl = 'deleteJson';
	var addTitle="添加管理员";
	var editTitle = "修改管理员资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	var toolbars = undefined;
	$(function(){
		if (typeof params['delRecord'] != 'undefined') {
			$("#btn0").attr("class","btn")
			$("#btn1").attr("class","btn")
			$("#btn2").attr("class","btn2 active")
		}else {
			$("#btn0").attr("class","btn")
			$("#btn2").attr("class","btn")
			$("#btn1").attr("class","btn2 active")
		}
	})
	function edit(){}
</script>
    <div class="page-body">
    	<!-- <div class="ope-bar clearfix">
        	<div class="fl">
                <a id="btn0" class="btn" href="../../system/client/showList">账户列表</a>
                <a id="btn1" class="btn" href="../../system/modifyClientRecord/showList?modifyRecord=true">修改记录</a>
                <a id="btn2" class="btn" href="../../system/modifyClientRecord/showList?delRecord=true">删除列表</a>
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
                <th data-options="field:'area'">区域</th>
                <th data-options="field:'loginAccount'">客户名称</th>
                <th data-options="field:'address'">客户地址</th>
                <th data-options="field:'responsibleUser'">负责人</th>
                <th data-options="field:'mobile'">电话</th>
                <th data-options="field:'name'">账户名称</th>
                <th data-options="field:'clientMeal'">套餐</th>
                <c:if test="${!empty params['modifyRecord'] }">
                	<th data-options="field:'accountState'">账户状态</th>
	                <th data-options="field:'effectiveDate'">账户有效期</th>
	                <th data-options="field:'addUser'">添加人</th>
	                <th data-options="field:'remark'">修改备注</th>
	                <th data-options="field:'modifyDate'">修改时间</th>
	                <th data-options="field:'modifyUser'">修改人</th>
                </c:if>
                <c:if test="${!empty params['delRecord'] }">
	                <th data-options="field:'useDate'">启用时间</th>
	                <th data-options="field:'effectiveDate'">账户有效期</th>
	                <th data-options="field:'remark'">删除说明</th>
	                <th data-options="field:'modifyDate'">删除时间</th>
	                <th data-options="field:'delUser'">删除人</th>
                </c:if>
            </tr>
           </thead>
        </table>
        </div>
    </div>
 


</body>
</html>