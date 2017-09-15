<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>登录页</title>
<link rel="stylesheet" type="text/css" href="../content/css/reset.css">
<link rel="stylesheet" type="text/css" href="../content/css/style.css">
</head>
<body class="main">
<div class="logoin">
	<div class="logo">
    <img src="../content/images/top.png">
    </div>
    <div class="login-box">
    	<div class="login-box-background"> 
        	<div class="login-box-sculpture">
            	<img src="../content/images/user.png">
            </div>
        </div>
        <div class="login-box-input-field">
        	<form id="frm" class="logoin-form" action="../user/login" method="post">
            <div class="login-name">
            <span>用户名</span>
            <input name="account" type="text">
            </div>
            <div class="login-password">
            <span>密码</span>
            <input type="password" name="passwd">
            </div>
            <div class="submit-button">
            <p onclick="frm.submit();">立即登录</p>
            </div>
            </form>
		</div>
    </div>
</div>
</body>
</html>
