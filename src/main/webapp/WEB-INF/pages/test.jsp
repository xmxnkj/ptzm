<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智能机器人</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9;IE=8;IE=7;IE=EDGE">
    <meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <link rel="stylesheet" href="content/css/reset.css">
    <link rel="stylesheet" href="content/css/common.css">
    <link rel="stylesheet" href="content/css/style.css">
    <link rel="stylesheet" href="content/css/site.css">
    <script src="../content/js/jquery-3.1.1.min.js"></script>
    <script src="content/js/common.js"></script>
    <script src="content/scripts/common/setting.js"></script>
    <script src="content/scripts/common/utils.js"></script>
    <script src="content/scripts/main/main.js"></script>
    
    <script src="content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
	<link href="content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	<link href="content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
	<link href="content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
	
	 
</head>
<body>
<div>
<form action="" onsubmit="send();return false;">
客户：<input type="text" id="ct"><input type="button" onClick="send()" value="回复" onkeyup="keyup">
</form>
</div>
<div id="divRst">
</div>
<script type="text/javascript">
	
	function keyup(val){
		alert(val);
	}
		
	function send(){
		var rpl = $('#ct').val();
		$('#divRst').append('<div>客户：' + rpl + '</div>');
		$.ajax({
			url:'test',
			type:'post',
			data:"message=" + rpl,
			timeout:10000,
			success:function(rst){
				if(rpl=='再见' || rpl=='reload'){
					$('#divRst').prop('innerHTML', '');
				}else{
					$('#divRst').append('<div>AI:' + rst.params.prompt + '</div>');
				}
			},
			error:function(){
				
			}
		})
	}
	
	
</script>

</body>
</html>