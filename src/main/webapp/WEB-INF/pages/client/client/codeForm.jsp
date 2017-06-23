<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/site.css">
    <script src="${pageContext.request.contextPath}/content/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/content/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/default/easyui.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/content/scripts/main/main.js"></script>



</head>
<body>
 
 <script src="../../content/scripts/common/form.js"></script>
 <script src="../../content/scripts/format/format.js"></script>
 <script src="../../content/scripts/common/qrcode.js"></script>
<script type="text/javascript">
	var saveUrl = "savePayClient";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	var isFilling = false;
	var formPrex = "clientPayRecord";
	var websocket = "wss://e.aoyexm.com/websocket"; 
	$(function(){
	     var qrcode = new QRCode(document.getElementById("qrcode"), {
	         width : 150,//设置宽高
	         height : 150
	     });
	     qrcode.makeCode(params['code']);
	     if ("WebSocket" in window)
	     {
	        // 打开一个 web socket
	        var ws = new WebSocket(websocket);
	        var send = '{"fcode":"sid", "sid":"<%=request.getSession().getId() %>"}';
	        ws.onopen = function()
	        {
	           // Web Socket 已连接上，使用 send() 方法发送数据
	           ws.send(send);
	        };
	         
	        ws.onmessage = function (evt) 
	        { 
	           var received_msg = evt.data;
	           $.messager.alert('缴费','缴费成功!','缴费成功',fn);
	           //alert("数据已接收...");
	        };
	        ws.onclose = function()
	        { 
	           // 关闭 websocket
	           // $.messager.alert('缴费','支付超时!','支付超时!',fn);
	           //top.showAlert("支付已过时！");
	        };
	        ws.onerror = function()
	        {
	     	  // $.messager.alert('缴费','支付出现错误！','支付出现错误！',fn);
	        }
	     }
	     else
	     {
	        alert("您的浏览器不支持扫码支付!");
	     }
	})
	function fn(r){
		if (r) {
			top.closeDialog();
			top.$("#ifrContent")[0].contentWindow.fn(r);
		}else {
			top.closeDialog();
			top.$("#ifrContent")[0].contentWindow.fn(r);
		}
	}


</script>
    <div class="pop" id="PerfectData" style="top:10%; width:600px; margin-left:-280px;">
      <div class="cont" style="max-height: 100%">
        <form id="frm" method="post" >  
        	<div id="qrcode" class="cont" style="margin-left: 145px" ></div>
        </form>
      </div>
    </div>
</body>
</html>