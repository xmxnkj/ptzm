<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet" href="../../content/css/indexcss/style.css">
<link rel="stylesheet" href="../../content/css/indexcss/reset.css">
<link rel="stylesheet" href="../../content/css/indexcss/common.css"> -->
<link rel="stylesheet" href="../../content/css/Css/login.css">
    <link rel="stylesheet" href="../../content/css/Css/purge.css">
    <link rel="stylesheet" href="../../content/css/Css/Pagestyle.css">
<script src="../../content/js/jquery-3.1.1.min.js"></script>
<script src="../../content/js/common.js"></script>
<script src="../../content/scripts/common/setting.js"></script>
<script src="../../content/scripts/common/utils.js"></script>
<script src="../../content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script src="../../content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<link href="../../content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
<link href="../../content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
<title>通讯管理系统</title>
<script type="text/javascript">
	if(top.location.href!=location.href){
		top.location.href=location.href;
	}  
</script>
</head>
<body class="login-body" style="background: url('../../content/images/login1.png')"><!--  -->
<div class="login-box">
    <div class="login-left">
        <img src="../../content/images/logo.png">
    </div>
    <div class="login-right">
        <p class="login-right-text">用户登录</p>
        <div>
        	<div class="login-right-box">
            	<img src="../../content/images/user.png">
            	<input type="text" id="clientName" name="clientName" placeholder="请输入主账号">
        	</div>
            <div class="login-right-box">
            	<img src="../../content/images/user.png">
            	<input type="text" id="account" name="account" placeholder="请输入用户名/手机号">
        	</div>
            <div class="login-right-box">
                <img src="../../content/images/password.png">
                <input type="password" id="passwd" name="passwd" placeholder="请输入密码">
            </div>
            <div class="login-right-box" style="display: none">
                <img src="../../content/images/test.png">
                <input type="text" placeholder="请输入验证码">
                <img src="">
            </div>
            <div class="login-right-password">
            <label>
                <input type="checkbox" id="remFlagCheck">记住密码
            </label>
                <span style="display: none">忘记密码？</span>
            </div>
        </div>

        <p class="login-btn" onclick="login()" id="btn">立即登录</p>
    </div>
</div>
	<!-- <div id="page-container" class="page-container">
		 <form method="post" onsubmit="login();return false;" class="login">
		 <div class="cboxinput"><span></span><span style="width: 290px;font-size: 27px;font-weight: bold;margin-bottom: 20px;">厦门西牛科技有限公司</span></div>
		 	<div class="cboxinput"><span>账号</span><input type="text" name="clientName" id="clientName" class="username" placeholder="请输入账号"></div>
		 	<div class="cboxinput"><span>员工</span><input type="text" name="account" id="account" placeholder="请输员工姓名"></div>
			<div class="cboxinput"><span>密码</span><input type="password" name="passwd" id="passwd" placeholder="请输入密码"></div>
			<div class="cboxinput"><span></span><input type="submit" value="登录" class="cbtn"></div>
		</form> 
		<div class="connect">
			<p>厦门西牛科技有限公司</p>
			<p style="margin-top:20px;">服务热线：110-6666-120</p>
		</div>
	</div> -->

<script type="text/javascript">
	$(document).keydown(function(event){
	　　　if(event.keyCode == 13){
	　　　　　if(typeof login == "function"){
				login();
	　　　　　}
	　　　　}
	　}); 
	$(function(){
		//记住密码功能
        var str = getCookie("loginInfo");
        str = str.substring(0,str.length);
        ///setTimeout(() => {
        	 if (str!="") {
             	var clientName = str.split("#")[0];
                 var account = str.split("#")[1];
                 var password = str.split("#")[2];
                 var checkState = str.split("#")[3];
                 //自动填充用户名和密码
                 $("#clientName").val(clientName);
                 $("#account").val(account);
                 $("#passwd").val(password);
                 $("#remFlagCheck").attr("checked",checkState);
     		}else {
     			$("#clientName").val("");
                $("#account").val("");
                $("#passwd").val("");
			}
		//}, 10);
       
        
	})

	//获取cookie
	function getCookie(cname) {
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0; i<ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1);
	        if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
	    }
	    return "";
	}

	function login(){
		var remFlag = $("#remFlagCheck").is(':checked');
		var clientName = $("#clientName").val();
		var account = $("#account").val();
		var passwd = $("#passwd").val();
		var params={
			clientName:clientName,
			account:account,
			passwd:passwd,
			remFlag:remFlag
		}
		$.ajax({
            url: "loginJson",
            data:params,
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
                if(data.success){
                	location.href='../../main';
                }else{
                	$.messager.alert(WINDOW_CAPTION, data.message, 'error');	
                }
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '登录失败，请检查您的网络！', 'error');
            }
        });
		
		return false;
	}
	
	window.onload = function () {
        $(".connect p").eq(0).animate({"left": "0%"}, 600);
        $(".connect p").eq(1).animate({"left": "0%"}, 400);
    }
 
    function is_hide() {
        $(".alert").animate({"top": "-40%"}, 300)
    }

    function is_show() {
        $(".alert").show().animate({"top": "45%"}, 300)
    }
</script>

</body>
</html>