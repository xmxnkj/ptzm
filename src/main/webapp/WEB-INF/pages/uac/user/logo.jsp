<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
	html,body, form{
		margin:0xp;
		padding:0px;
	}
</style>
</head>
<body>
	<form action="saveLogo" method="post" enctype="multipart/form-data">
		<input type="file" name="logoFile"><input type="submit" value="上传">
	</form>
	<c:if test="${path!=NULL }">
		<script type="text/javascript">
			var path = '${path}';
			window.parent.user.setLogo(path);
		</script>
	</c:if>
</body>
</html>
