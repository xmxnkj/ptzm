<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.xmxnkj.voip.system.entity.PublishNotice"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ include file="/common/common.jsp"%> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>首页</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="keywords" content="">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=11;IE=10;IE=9;IE=8;IE=7;IE=EDGE">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<!-- <link rel="stylesheet" href="content/css/indexcss/reset.css">
<link rel="stylesheet" href="content/css/indexcss/common.css">
<link rel="stylesheet" href="content/css/indexcss/style.css"> -->
<link rel="stylesheet" href="content/css/Css/index.css">
<link rel="stylesheet" href="content/css/Css/Pagestyle.css">
<link rel="stylesheet" href="content/css/Css/purge.css">
<script src="content/js/jquery.js"></script>
<script src="content/js/jquery-3.1.1.min.js"></script>
<script src="content/js/common.js"></script>
<script src="content/scripts/common/setting.js"></script>
<script src="content/scripts/common/utils.js"></script>
<script src="content/scripts/main/main.js"></script>
<script src="content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<link href="content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
<link href="content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
<link href="content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
<style type="text/css">
	html,body{
		overflow: hidden;
	}
</style>
</head>

<body style="background: #f6f6f6">
	<script type="text/javascript">
		var clientId='${client.id}';
		var clientName='${client.name}';
		var clientUserId='${clientUser.id}';
		var clientUserName='${clientUser.name}';
		var meauLength = parseInt('${topMenusLength}');
		function go(url){
			location.href="main?url=" + encodeURIComponent(url);
		}
		function showLoading(){
			$("#loading").css("display","flex");
		}
		function hideLoading(){
			$("#loading").hide();
		}
		
		function czxShow(){
			$(".czxShow").click(function(){
	            $('.czxShow').removeClass("zx");
				$(this).addClass("zx");	
				});
			$(".czxShow").mouseleave(function(){
	            $('.czxShow').removeClass("zx");
				});
		}
		function notice(url){
			/* location.href="main?url=" + encodeURIComponent(url+clientId); */
			$("#ifrContent").attr("src",url+clientId);
			var srcBefo = "content/images/";
			var srcAfte_gray = "_gray.png";
			var unActive = "content/images/more3.png";
			var icon = "";
			for (var i = 0; i < meauLength; i++) {
				icon = $("#iconVal_"+i).val();
				$("#img1_"+i).attr("src",srcBefo+icon+srcAfte_gray);
				$("#img2_"+i).attr("src",unActive);
				$("#ac_"+i).attr("class"," ");
			}
		}
		function changePage(displayOrder,iconNow){
			var srcBefo = "content/images/";
			var srcAfte_gray = "_gray.png";
			var srcAfte_green = "_green.png";
			var active = "content/images/more2.png";
			var unActive = "content/images/more3.png";
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
		function logout(){
			top.showConfirm("确定要退出系统吗？", function(r){
				if (r) {
					location.href="client/clientUser/logout";
				}
			})
		}
		function changePasswd(){
			openDialog("client/clientUser/showPage?pageName=changePwd","修改密码", 800, 400);
		}
		$(function(){
			if ($("#ac_0").length > 0) {
				var indexSrc =  $("#ac_0").attr("href");
				$("#ifrContent").attr("src",indexSrc);
			}
		})
	</script>
	<!--  <p class="top">E管店汽车服务生态应用系统正式发布！</p> -->
   <%--  <h2 class="welcome"></h2>
    <h3 class="title"></h3>
    <ul class="index-nav clearfix">
    	<c:forEach var="btn" items="${buttons}">
    		<c:if test="${btn.isShow }">
    		<li class="${btn.css }"><a href="#" onclick="go('${btn.url }');return false;"><i></i><em>${btn.text }</em></a></li>
    		</c:if>	
    	</c:forEach>
    </ul> --%>
    <%-- <%List<PublishNotice> notices = (List<PublishNotice>)request.getSession().getAttribute("notices"); 
    	%>
    	<marquee id="mar" onmouseout="this.start();" onmouseover="this.stop();"  bgcolor="#fffeef"  scrollamount="7" height="20px" onclick="notice('client/client/showPage?pageName=noticeList&clientId=')">
    	<%if(notices!=null&&notices.size()>0){
    		for(int i=0; i<notices.size(); i++){
    			PublishNotice opt = notices.get(i);
    			String content = opt.getContent();
    	%>
    		  <span style="margin-left: 100px"><font color="green"><%=content%></font></span>
    	<%
    		}
    	}else{
    		%>
    		敬请期待！……
    		<%
    	}
    	%>
    	</marquee> --%>
    <div class="nav">
        <img src="content/images/system.png">
        <div class="nav-content" >
            <div class="nav-user-help">
                <div class="nav-user">
                    <img src="content/images/user3.png">
                    <p>你好，<span style="overflow:auto;">${clientUser.name}</span>
                    </p>
                    <img src="content/images/more.png">
                    <div class="nav-user-ftn">
                    	<ul>
                    		<li>
                    			<img src="content/images/exit.png">
                    			<span onclick="logout()">退出登陆</span>
                    		</li>
                    		<li>
                    			<img src="content/images/exit.png">
                    			<span onclick="changePasswd()">修改密码</span>
                    		</li>
                    	</ul>
                    </div>
                </div>
                <div class="line"></div>
                <div class="nav-help">
                    <img src="content/images/help.png">
                    <!-- <span onclick="logout()">退出系统</span>
                 	<span onclick="changePasswd()">修改密码</span> -->
                    <span>帮助</span>
                </div>
            </div>
        </div>
    </div>
    <div class="home">
    	<%-- 访问地址：<%=request.getRequestURI() %> --%>
        <div class="aside" style="overflow: auto;">
	        <c:forEach var="topMenu" items="${topMenus}">
	        	<c:if test="${topMenu.isShow }">
		        	<div class="aside-box">
		        		<c:if test="${topMenu.displayOrder=='0' }">
		        			 <div class="aside-box-text">
			                    <img src="content/images/${topMenu.css }_green.png" id="img1_${topMenu.displayOrder }">
			                    <input type="hidden" value="${topMenu.css }" id="iconVal_${topMenu.displayOrder }">
			                    <span><a href="${topMenu.url }" class="active" id="ac_${topMenu.displayOrder }" target="ifrContent" onclick="changePage('${topMenu.displayOrder }','${topMenu.css }')">${topMenu.text }</a></span>
			                </div>
			                <img src="content/images/more2.png" id="img2_${topMenu.displayOrder }">
		        		</c:if>
		        		<c:if test="${topMenu.displayOrder!='0' }">
		        			<div class="aside-box-text">
			                    <img src="content/images/${topMenu.css }_gray.png" id="img1_${topMenu.displayOrder }" >
			                    <input type="hidden" value="${topMenu.css }" id="iconVal_${topMenu.displayOrder }">
			                    <span><a href="${topMenu.url }" class="" id="ac_${topMenu.displayOrder }" target="ifrContent" onclick="changePage('${topMenu.displayOrder }','${topMenu.css }')">${topMenu.text }</a></span>
			                </div>
			                <img src="content/images/more3.png" id="img2_${topMenu.displayOrder }">
		        		</c:if>
		            </div>
	        	</c:if>		
	        </c:forEach>
            <!-- <div class="aside-box" id="indexPage">
                <div class="aside-box-text">
                    <img src="content/images/home_icon_green.png">
                    <a href="index" target="ifrContent" class="active">首页</a>
                </div>
                <img src="content/images/more2.png">
            </div>
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/user_icon_gray.png">
                    <span><a href="customer/customer/showPage?pageName=seatList&type=0" target="ifrContent">坐席库</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/ccUsers_icon_gray.png">
                    <span><a href="customer/customer/showPage?pageName=customerList&type=1" target="ifrContent">客户库</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/userManage_icon_gray.png">
                    <span><a href="client/clientUser/showList?type=1" target="ifrContent">用户管理</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/time_icon_gray.png">
                    <span><a href="voice/calltime/showList" target="ifrContent">拨号时间</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/vocie_icon_gray.png">
                    <span><a href="voice/template/showList" target="ifrContent">语音模板</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/balance_icon_gray.png">
                    <span><a href="customer/callPlan/showList" target="ifrContent">拨号计划</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/balance_icon_gray.png">
                    <span><a href="customer/callRecord/showList" target="ifrContent">通话记录</a></span>
                </div>
                <img src="content/images/more3.png">
            </div>
            <div class="aside-box">
                <div class="aside-box-text">
                    <img src="content/images/balance_icon_gray.png">
                    <span>电话卡余额</span>
                </div>
                <img src="content/images/more3.png">
            </div> -->
        </div>
        	<iframe id="ifrContent" src="" name="ifrContent" frameborder="0" width="100%" height="100%"></iframe>
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
	</div></div>
    <script type="text/javascript" src="content/scripts/common/echarts.min.js"></script>
    <script type="text/javascript" src="content/scripts/common/chart.js"></script>
</body>
</html>