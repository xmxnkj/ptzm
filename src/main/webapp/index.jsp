<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ include file="/common/common.jsp"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>voip</title>
    <script src="${pageContext.request.contextPath}/content/web/js/jquery-3.1.1.min.js"></script>
    <link href="${pageContext.request.contextPath}/content/styles/login.css" rel="stylesheet" />    
</head>
<script>
var $path = '${pageContext.request.contextPath}';
if(window.top != window){
	window.top.location.href = $path+"/index.jsp"; 
}
	
function login(){
	var tel = $("#account").val();
	var pwd = $("#pwd").val();
	
/* 	if(!FXC.validation.phone(tel,"alert ")){
		return false;
	}
	
	if(!FXC.validation.length(20,6,pwd,"密码","alert")){
		return false;
	} */
	
	$.ajax({
		cache : false,
        type : "POST",
        url : "${pageContext.request.contextPath}/web/clientUser/main",
        data : {
        	"phoneNumber":tel,
        	"pwd":hex_md5(pwd)
        },
        async: false,
        success: function(data){
        	
/*         	if(data.message!=""){
        		alert(data.message);
        	} */
        	
			if(data.success){
				window.location.href="${pageContext.request.contextPath}/web/clientUser/index";
			}else{
				if(data.message!="")
					alert(data.message);
			}
        }
	});
}

</script>

<body>

<div id="lgcontainer" style="text-align:center; margin:auto;position: fixed; left:0px;top:50px">
	<form id="clientForm" id="clientForm" action="${pageContext.request.contextPath}/login/signin" class="logoin-form" >
	<div class="loginbox">
		<div style="width:100%;text-align:left;padding-left:720px; padding-top:290px">
		<table style="width:100%">
			<tr><td style="padding-top:5px"><input type="text" id="account" name ="loginName" class="borderbot" placeholder="请输入手机号" autocomplete="off"></td></tr>
			<tr><td style="padding-top:28px;text-align:left;"><input type="password" id="pwd"  name ="password"  placeholder="请输入密码" autocomplete="off"></td></tr>
			<tr><td style="padding-top:20px" ><input type="submit" value="" class="loginbtn" id="loginAction"/>
			</td></tr>
		</table>
		</div>
	</div>
	</form>
	<div style="padding-top:20px;color:#fff"></div>
	</div>
</body>
</html>