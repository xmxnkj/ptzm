<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>e管店汽车服务生态应用系统</title>
</head>

<body class="index">
	<script type="text/javascript">
	function validate(){
		if($("#newPasswd").val()!=$("#passwd").val()){
			top.Alert("密码确认错误！");
			return;
		}
	}
	
	function changePwd(){
		$.ajax({
			url:'changePasswd',
			type:'post',
			data:{oldPasswd:$("#oldPasswd").val(), newPasswd:$("#newPasswd").val()},
			success:function(data){
				if(dealJsonResult(data, '密码修改成功！')){
					$("#oldPasswd").val("");
					$("#newPasswd").val("");
					$("#passwd").val("");
                }
			},
			error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导密码修改失败！', 'error');
            }
		})
	}
</script>
	<!-- <div id="page-container">
		 <form id="frm" method="post" onsubmit="changePwd();return false;" class="login">
		 	<div class="cboxinput"><span>旧密码</span><input type="password" name="oldPasswd" id="oldPasswd" class="username" placeholder="请输入旧密码"></div>
			<div class="cboxinput"><span>新密码</span><input type="password" name="newPasswd" id="newPasswd" placeholder="请输入新密码"></div>
			<div class="cboxinput"><span>密码确认</span><input type="password" name="passwd" id="passwd" placeholder="请输入密码确认"></div>
			<div class="cboxinput"><span></span><input type="submit" value="确认修改" class="cbtn"></div>
		</form> 
	</div> -->
	<div class="wrap">
    <div class="main">
         <form method="post" id="frm" style="width:50%; ">
        <div class="Input">
            <div class="InputContent">
                <p>旧密码</p>
                <input  type="password" name="oldPasswd" id="oldPasswd" data-options="required:true,missingMessage:'请输入旧密码！'" style="width:50%;">
            </div>
            <div class="InputContent">
                <p>新密码</p>
                <input type="password" name="newPasswd" id="newPasswd" data-options="required:true,missingMessage:'请输入新密码！'" style="width:50%;">
            </div>
            <div class="InputContent">
                <p>密码确认</p>
                <input type="password" name="passwd" id="passwd" data-options="required:true,missingMessage:'请输入确认密码！'" style="width:50%;">
            </div>
        </div>
        </form>
        <div class="Button">
            <a href="#" class="button button-action button-pill" onclick="changePwd()">确认修改</a>
        </div>
    </div>
</div>
</body>
</html>