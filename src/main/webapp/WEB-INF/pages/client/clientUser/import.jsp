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
<form method="post" action="importClientUser" enctype="multipart/form-data">
	<c:if test="${!empty rst }">
		<div>导入结果：${rst }</div>
	</c:if>
	<c:if test="${!empty error }">
		<div>错误信息：${error }</div>
	</c:if>
	请选择员工资料Excel文件进行导入：<input type="file" name="file" />
	<input type="submit" value="导入" onclick="importData()">
</form>
<script type="text/javascript">
	function importData(){
	   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
	   $("<div class=\"datagrid-mask-msg\"></div>").html("导入中，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	}
</script>
</body>
</html>