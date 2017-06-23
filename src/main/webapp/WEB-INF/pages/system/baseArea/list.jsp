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
	var listjsonUrl = 'list';
	var editUrl = 'baseArea/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加套餐";
	var editTitle = "修改套餐信息";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 300;
	/* toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
	toolbars[3] = {iconCls:'icon-search', text:'查看', handler:look}; */
	
</script>
    <div class="page-body">
    	<div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
        </div>
        <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
                <th data-options="field:'name',width:100">区域名称</th>
                <th data-options="field:'areaContent',width:180">区域内容</th>
            </tr>
           </thead>
        </table>
        </div>
    </div>
</body>
</html>