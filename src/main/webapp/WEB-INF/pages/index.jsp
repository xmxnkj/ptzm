<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<link rel="stylesheet" href="content/css/Css/buttons.css">
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
		var seatCustomer = [0,0,0,0,0,0];
		var commonCustomer = [0,0,0,0,0,0];
		var seatMax = 500;
		var comMax = 500;
		function changeYmax(type,val){
			if (type=="seatMax") {
				myChart.showLoading();
				seatMax = parseInt(val);
				loadDtaSeat();
				reloadDataSeat();
				myChart.hideLoading();
			}else if (type=="comMax") {
				 myChart2.showLoading();
				 comMax = parseInt(val);
				 loadDtaCom();
				 reloadDataCom();
				 myChart2.hideLoading();
			}
		}
		var compare = function (x, y) {//降序
		    if (x < y) {
		        return 1;
		    } else if (x > y) {
		        return -1;
		    } else {
		        return 0;
		    }
		}
		function changeActive(jDate,jName){
			if (jDate=="d") {
				$("#"+jName+"Day").attr("class","active pitch")//active3
				$("#"+jName+"Week").attr("class","")
				$("#"+jName+"Month").attr("class","")
			}else if (jDate=="w") {
				$("#"+jName+"Week").attr("class","active pitch")
				$("#"+jName+"Day").attr("class","")
				$("#"+jName+"Month").attr("class","")
			}else {
				$("#"+jName+"Month").attr("class","active pitch")
				$("#"+jName+"Week").attr("class","")
				$("#"+jName+"Day").attr("class","")
			}
		}
		function countSeatCustomer(jDate){
			myChart.showLoading();
			changeActive(jDate,"seat");
			$.ajax({
				type:'post',
				url:'customer/customer/countCustomerType?notClientUser=1&type=0&jDate='+jDate,
				success:function(dataA){
					seatCustomer =[];
					seatCustomer.push(dataA.entity.countA);
					seatCustomer.push(dataA.entity.countB);
					seatCustomer.push(dataA.entity.countC);
					seatCustomer.push(dataA.entity.countD);
					seatCustomer.push(dataA.entity.countE);
					seatCustomer.push(dataA.entity.countF);
					loadChartData(dataA.entity,"seat");
				}
			})
		}
		
		function countComCustomer(jDate){
			myChart2.showLoading();
			changeActive(jDate,"com");
			$.ajax({
				type:'post',
				url:'customer/customer/countCustomerType?notClientUser=1&type=1&jDate='+jDate,
				success:function(dataB){
					commonCustomer =[];
					commonCustomer.push(dataB.entity.countA);
					commonCustomer.push(dataB.entity.countB);
					commonCustomer.push(dataB.entity.countC);
					commonCustomer.push(dataB.entity.countD);
					commonCustomer.push(dataB.entity.countE);
					commonCustomer.push(dataB.entity.countF);
					loadChartData(dataB.entity,"com");
				}
			})
		}
		
		function loadChartData(data,type){
			var arr = [data.countA,data.countB,data.countC,data.countD,data.countE,data.countF];
			arr.sort(compare)
			var maxVal = arr[0]>0?"1":"500";
			if (arr[0]>0) {
				var len = (arr[0]+"").length;
				for (var i = 0; i < len; i++) {
					maxVal += "0";
				}
			}
			//myChart.showLoading();
			if (type=="com") {
				comMax = parseInt(maxVal);
				loadDtaCom();
				reloadDataCom();
				myChart2.hideLoading();
			}else {
				seatMax = parseInt(maxVal);
				loadDtaSeat();
				reloadDataSeat();
				myChart.hideLoading();
			}
			
		}
		
		function go(url){
			location.href="main?url=" + encodeURIComponent(url);
		}
		//计算客户联系类型
		function countCustomer(){
			paramsData = {};
			paramsData['notClientUser'] = '1';
			$.ajax({
				type:'post',
				url:"customer/customer/countNum",
				data:paramsData,
				timeout:10000,
				success:function(data){
					$("#allCust").prop("innerHTML",data.entity.countAll);
					$("#unContactCust").prop("innerHTML",data.entity.countUnCon);
					$("#contactedCust").prop("innerHTML",data.entity.countConed);
					$("#contactingCust").prop("innerHTML",data.entity.countConing);
				}
			})
		}
		function stpTask(){
			top.showConfirm("<font color='red'>警告：确定要停止所有任务吗？</font>", function(r){
				if (r) {
					$.ajax({
						type:'post',
						url:'customer/callPlan/stpTask',
						success:function(data){
							if (data.success) {
								$.messager.alert("停止成功", "已停止所有任务！")
							}else {
								$.messager.alert("停止失败", "停止失败，请重试！")
							}
						}
					})
				}
			})
		}
		$(function(){
			countCustomer();
			countSeatCustomer("d");
			countComCustomer("d");
		})
	</script>
    <div class="content">
            <p class="welcome">Hi~欢迎回来！</p>
            <div class="alarm">电话卡余额预警：<p class="active2" id="H">高</p><p id="M">中</p><p id="L">低</p></div>
            <div class="note">
           		<span>注：首页统计数据30分钟更新一次。</span> 
           		<a href="#" class="button button-caution button-rounded button-small" onclick="stpTask()">一键停止所有任务</a>
            </div>
            <div class="user-connect">
                <div class="user-box">
                    <img src="content/images/user2.png">
                    <div class="user-box-num">
                        <p>所有客户</p>
                        <p id="allCust">0</p>
                    </div>
                </div>
                <div class="user-box">
                    <img src="content/images/no.png">
                    <div class="user-box-num">
                        <p>未联系</p>
                        <p id="unContactCust">0</p>
                    </div>
                </div>
                <div class="user-box">
                    <img src="content/images/connect.png">
                    <div class="user-box-num">
                        <p>联系中</p>
                        <p id="contactingCust">0</p>
                    </div>
                </div>
                <div class="user-box">
                    <img src="content/images/already.png">
                    <div class="user-box-num">
                        <p>已联系</p>
                        <p id="contactedCust">0</p>
                    </div>
                </div>
            </div>
            <div class="chart">
                <div class="chart-box">
                    <div class="chart-box-increase">
                        <p class="chart-box-txt">坐席库</p>
                         <div style="width: 1000px">
                            <p class="active3" onclick="countSeatCustomer('d')" id="seatDay">今日新增</p>
                            <p onclick="countSeatCustomer('w')" id="seatWeek">本周新增</p>
                            <p onclick="countSeatCustomer('m')" id="seatMonth">本月新增</p>
                             <p>
                            	<a href="#" onclick="changeYmax('seatMax','100')">100</a>|
                            	<a href="#" onclick="changeYmax('seatMax','500')">500</a>
                            </p>
                        </div>
                    </div>
                    <div class="private-chart" id="private-chart">

                    </div>
                </div>
                <div class="chart-box">
                    <div class="chart-box-increase">
                        <p class="chart-box-txt">客户库</p>
                        <div style="width: 1000px">
                            <p class="active3" onclick="countComCustomer('d')" id="comDay">今日新增</p>
                            <p onclick="countComCustomer('w')" id="comWeek">本周新增</p>
                            <p onclick="countComCustomer('m')" id="comMonth">本月新增</p>
                             <p>
                            	<a href="#" onclick="changeYmax('comMax','100')">100</a>|
                            	<a href="#" onclick="changeYmax('comMax','500')">500</a>
                            </p>
                        </div>
                    </div>
                    <div class="private-chart" id="common-chart">
                    </div>
                </div>
            </div>
        </div>
    <script type="text/javascript" src="content/scripts/common/echarts.min.js"></script>
    <script type="text/javascript" src="content/scripts/common/chart.js"></script>
</body>
</html>