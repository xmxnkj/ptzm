<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/sitemesh-decorator.tld" prefix="decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9;IE=8;IE=EDGE" />
<title><%=request.getRequestURL()%></title>
<!-- <link rel="stylesheet" href="../../content/css/reset.css">
<link rel="stylesheet" href="../../content/css/common.css">
<link rel="stylesheet" href="../../content/css/style.css"> -->
<link rel="stylesheet" href="../../content/css/Css/seat.css">
 <link rel="stylesheet" href="../../content/css/Css/Pagestyle.css">
 <link rel="stylesheet" href="../../content/css/Css/purge.css">
 <link rel="stylesheet" href="../../content/css/Css/buttons.css">
 <link rel="stylesheet" href="../../content/css/Css/Popup.css">
 <link rel="stylesheet" href="../../content/css/Css/VoiceTemplate.css">
 <link rel="stylesheet" href="../../content/css/Css/userManage.css">
 
 
<script src="../../content/js/jquery-3.1.1.min.js"></script>
<script src="../../content/js/common.js"></script>
<script src="../../content/scripts/common/setting.js"></script>
<script src="../../content/scripts/common/utils.js"></script>
<script src="../../content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script src="../../content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<link href="../../content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
<link href="../../content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
<script src="../../content/scripts/common/gridutils.js"></script>
<script src="../../content/scripts/common/formUtils.js"></script>
<script src="../../content/scripts/common/date.js"></script>
<script src="../../content/scripts/format/format.js"></script>
</head>
<body style="background: #f6f6f6">	
	<script type="text/javascript">
	var _contextPath = "${pageContext.request.contextPath}";
	</script>
	<c:if test="${!empty id}">
		<script type="text/javascript">
			var eid='${id}';			
		</script>
	</c:if>
	<c:if test="${!empty ViewCost}">
		<script type="text/javascript">
			var ViewCost='${ViewCost}';
		</script>
	</c:if>
	<c:if test="${!empty params}">
		<script type="text/javascript">
			var params={};
			<c:forEach var="item" items="${params}">
				<c:if test="${item.key!='id' && item.key!='rnd'}">
					params['${item.key}'] ='${item.value}';
				</c:if>
			</c:forEach>
		</script>
	</c:if>
		<decorator:body />
</body>
</html>