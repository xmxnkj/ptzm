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
	var editUrl = 'client/shop/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加门店";
	var editTitle = "修改门店";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 400;
	var dlgHeight = 520;
</script>

<div class="page-body" style="height:100%;">
	<div class="header2">
	<%-- <c:import url="../../setting/settingHeader.jsp">
		<c:param name="selected" value="${params['dataType']}" />
	</c:import> --%>
	<c:import url="../../header.jsp">
    </c:import>
    </div>
<div style="height:100%;">
    <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb" data-options="fit:true">
        <thead>
        <tr>
            <th data-options="field:'name'" style="width:20%;">门店名称</th>
            <th data-options="field:'address'" style="width:40%;">地址</th>
            <th data-options="field:'tel'" style="width:20%;">电话号码</th>
            <th data-options="field:' '" style="width:10%;">二维码</th>
            <!-- <th data-options="field:' '" style="width:10%;">仓库</th> -->
            <!-- <th data-options="field:'isDefault',formatter:yesNoFormatter">是否默认门店</th>原型没有
            <th data-options="field:'inUse',formatter:yesNoFormatter">是否启用</th>原型没有 -->
        </tr>
        </thead>
    </table>
</div>
</div>
</body>
</html>