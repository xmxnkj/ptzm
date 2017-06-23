<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hsit.common.uac.entity.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通讯后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9;IE=8;IE=7;IE=EDGE">
    <meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <!-- <link rel="stylesheet" href="../content/css/reset.css">
    <link rel="stylesheet" href="../content/css/common.css">
    <link rel="stylesheet" href="../content/css/style.css">
    <link rel="stylesheet" href="../content/css/site.css"> -->
    <link rel="stylesheet" href="../content/css/Css/jurisdiction.css">
    <link rel="stylesheet" href="../content/css/Css/Pagestyle.css">
    <link rel="stylesheet" href="../content/css/Css/purge.css">
    <link rel="stylesheet" href="../content/css/Css/buttons.css">
    <script src="../content/js/jquery-3.1.1.min.js"></script>
    <script src="../content/js/common.js"></script>
    <script src="../content/scripts/common/setting.js"></script>
    <script src="../content/scripts/common/utils.js"></script>
    <script src="../content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
	<link href="../content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
	<link href="../content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
	<link href="../content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	 <script src="../content/scripts/main/main.js"></script>
</head>
<body style="background: #f6f6f6">
<script type="text/javascript">
	var user = "${user.loginAccount}";
	var meauLength = parseInt('${topMenusLength}');
	function showLoading(){
		$("#loading").show();
	}
	function hideLoading(){
		$("#loading").hide();
	}
	function logoutUser(){
		top.showConfirm("确定要退出系统吗？", function(r){
			if (r) {
				location.href="user/logoutUser";
			}
		})
	}
	function changePage(displayOrder,iconNow){
		var srcBefo = "../content/images/";
		var srcAfte_gray = "_gray.png";
		var srcAfte_green = "_green.png";
		var active = "../content/images/more2.png";
		var unActive = "../content/images/more3.png";
		var icon = "";
		for (var i = 0; i < meauLength; i++) {
			icon = $("#iconVal_"+i).val();
			$("#img1_"+i).attr("src",srcBefo+icon+srcAfte_gray);
			$("#img2_"+i).attr("src",unActive);
			$("#ac_"+i).attr("class"," ");
		}
		$("#img1_"+displayOrder).attr("src",srcBefo+iconNow+srcAfte_green);
		$("#img2_"+displayOrder).attr("src",active);
		$("#ac_"+displayOrder).attr("class","active");
	}
	
</script>
<%-- 
<div class="header clearfix">
    <ul class="fl clearfix">
        <li><a class="top-home" href="main" ><i class="icon-home"></i></a></li>
        <c:forEach var="topOperation" items="${topOperations}">
        	<li class="czxShow">
        	<a href="../${topOperation.urlPath }" target="ifrContent">${topOperation.name }</i></a>
        		<ul>
        			<c:forEach items="${menus[topMenu.id] }" var="menu">
        				<li><a href="${menu.url }" target="ifrContent">${menu.text }</a></li>
        			</c:forEach>
        		</ul>
        	</li>
        </c:forEach>
    </ul>
    <div class="fr">
        <em class="sel-box">
        </em>
        <%User user = (User)request.getSession().getAttribute("user"); %>
       <!--  <a href="message/inform/showList" target="ifrContent"><img src="content/img/icon-xx.png" style="vertical-align: middle;width: 25px;"></a> -->
        <span class="top-user" href=""><font color="white"><%=user.getLoginAccount() %></font>
                <ul>
                    <!-- <li><a href="client/clientUser/showPage?pageName=changePwd" target="ifrContent">修改密码</a></li> -->
                    <li style="display:none"><a href="">系统消息</a></li>
                    <li style="display:none"><a href="">在线充值</a></li>
                    <li><a href="#" onclick="logoutUser();">退出</a></li>
                </ul>
             </span>
    </div>
</div>
 <div class="mainContent">
	<iframe id="ifrContent" src="<%=request.getContextPath() %>${params['url'] }" name="ifrContent" frameborder="0" class="fraContent"></iframe>
</div>  src="${params['url'] }"
<div class="footer">Copyright@2015-2020.All Rights Reserved 闽ICP备15026902号</div> --%>
<div class="nav">
    <img src="../content/images/system_jurisdiction.png">
    <div class="nav-content">
        <div class="nav-user-help">
            <div class="nav-user">
                <img src="../content/images/user3.png">
                <p>你好，<span>${user.loginAccount}</span></p>
                <img src="../content/images/more.png">
                <div class="nav-user-ftn">
                   	<ul>
                   		<li>
                   			<img src="../content/images/exit.png">
                   			<span onclick="logoutUser()">退出登陆</span>
                   		</li>
                   	</ul>
                 </div>
            </div>
            <div class="line"></div>
            <div class="nav-help">
                <img src="../content/images/help.png">
                <span>帮助</span>
            </div>
        </div>
    </div>
</div>
 <div class="home">
    	<%-- 访问地址：<%=request.getRequestURI() %> --%>
        <div class="aside">
	        <c:forEach var="topMenu" items="${topOperations}">
	        	<%-- <c:if test="${topMenu.isShow }"> --%>
		        	<div class="aside-box">
		        		<c:if test="${topMenu.displayOrder=='0' }">
		        			 <div class="aside-box-text">
			                    <img src="../content/images/${topMenu.actionClassName }_green.png" id="img1_${topMenu.displayOrder }" >
			                    <input type="hidden" value="${topMenu.actionClassName }" id="iconVal_${topMenu.displayOrder }">
			                    <span><a href="../${topMenu.urlPath }" class="active" id="ac_${topMenu.displayOrder }" target="ifrContent" onclick="changePage('${topMenu.displayOrder }','${topMenu.actionClassName }')">${topMenu.name }</a></span>
			                </div>
			                <img src="../content/images/more2.png" id="img2_${topMenu.displayOrder }">
		        		</c:if>
		        		<c:if test="${topMenu.displayOrder!='0' }">
		        			<div class="aside-box-text">
			                    <img src="../content/images/${topMenu.actionClassName }_gray.png" id="img1_${topMenu.displayOrder }" >
			                    <input type="hidden" value="${topMenu.actionClassName }" id="iconVal_${topMenu.displayOrder }">
			                    <span><a href="../${topMenu.urlPath }" class="" id="ac_${topMenu.displayOrder }" target="ifrContent" onclick="changePage('${topMenu.displayOrder }','${topMenu.actionClassName }')">${topMenu.name }</a></span>
			                </div>
			                <img src="../content/images/more3.png" id="img2_${topMenu.displayOrder }">
		        		</c:if>
		            </div>
	        <%-- 	</c:if>		 --%>
	        </c:forEach>
        </div>
        	<iframe id="ifrContent" src="<%=request.getContextPath() %>/${firstUrl }" name="ifrContent" frameborder="0" width="100%" height="100%"></iframe>
    </div>


<div id="dlg" class="easyui-dialog" closed="true" modal="true" title="窗口" style="width: 880px; height: 600px; overflow: visible">
	<iframe id="ifrDlg" style="width: 100%; height: 100%; border: 0;" frameborder="0"></iframe>
</div>
<div id="dlg2" class="easyui-dialog" closed="true" modal="true" title="窗口" style="width: 880px; height: 600px; overflow: visible">
	<iframe id="ifrDlg2" style="width: 100%; height: 100%; border: 0;" frameborder="0"></iframe>
</div>
<div id="loading" style="display:none; margin:auto;text-align:center; position:fixed;left:0px;top:0px;height:100%;width:100%;background-color: rgba(0,0,0,0.1);z-index: 99999;" >
<div style="position:fixed; left:50%; top:50%; transform: translateY(-50%);">
<img alt="" src="content/images/loading.gif" />
</div>
</div>
</body>
</html>