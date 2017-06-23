<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript">
	$(function(){
		getClientInfo();
	});
	
	function getClientInfo(){
		$.ajax({
			url:'clientInfo',
			type:'post',
			timeout:TIMEOUT,
			success:function(data){
				if(data){
					fillForm(data);
				}
			},
			error:function(){
				$.messager.alert(WINDOW_CAPTION, '网络原因导致保存失败，请重试！', 'error');
			}
		})
	}
	
	function saveWeChat(){
		var data=genFormData("frm");
		saveSetting("setWeChat",data);
	}
	
	function saveSetting(url, data){
		$.ajax({
			url:url,
			type:'post',
			data:data,
			timeout:TIMEOUT,
			success:function(data){
				var rst = "保存成功！";
				dealJsonResult(data, rst);
			},
			error:function(){
				$.messager.alert(WINDOW_CAPTION, '网络原因导致保存失败，请重试！', 'error');
			}
		})
	}
	
</script>
<div class="page-body">
		<div class="ope-bar">
    		<c:import url="../../header.jsp">
   		 	</c:import>
    	</div>
    	<!-- <ul class="tab clearfix">
        	<li class="active">微信设置</li>
        </ul> -->
        
        <div class="set-rule">
			<form id="frm">
			<ul class="expire">
				<li>
					<div class="clearfix">
						<b style="width:30%;">公众号AppID</b>
						<div style="width:50%;height:36px;float:left;">
						<input style="width:300px;" id="wxAppId" name="wxAppId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">公众号Secert</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="wxSecret" name="wxSecret" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">小程序AppID</b>
						<div style="width:50%;height:36px;float:left;">
						<input style="width:300px;" id="wxMiniAppId" name="wxMiniAppId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">小程序Secert</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="wxMiniSecret" name="wxMiniSecret" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">服务器Token</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="wxToken" name="wxToken" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">支付商户ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="merchantId" name="merchantId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">支付Key</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="payKey" name="payKey" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">信用卡支付</b>
						<div style="width:50%;height:36px;float:left;">
						<!-- <input  style="width:300px;" id="forbitCredit" name="forbitCredit" type="text"> -->
						<p>
							<label style="margin-right:50px;margin-left:-50px;"><input type="radio" name="forbitCredit" value="true">允许</label>
							<label><input type="radio" name="forbitCredit" value="false" id="forbitCredit">禁止</label>
						</p>
						
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">开单结算模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="saleMsgTplId" name="saleMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">开单施工模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="constructCustMsgTplId" name="constructCustMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">预约开单模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="preOrderMsgTplId" name="preOrderMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">办卡结算模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="cardMsgTplId" name="cardMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">车检提交模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="checkMsgTplId" name="checkMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">验车报告模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="finishCheckMsgTplId" name="finishCheckMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">                <!-- 字段未定义 -->
						<b style="width:30%;">施工派单模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="constructMsgTplId" name="constructMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">施工进度模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="dealConstructMsgTplId" name="dealConstructMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">开单报价模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="priceMsgTplId" name="priceMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">售后提醒模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="saleServiceMsgTplId" name="saleServiceMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">套餐到期模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="customerCardExpiredMsgTplId" name="customerCardExpiredMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">年检到期模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="yearCheckMsgTplId" name="yearCheckMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">保险到期模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="insuranceMsgTplId" name="insuranceMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">保养到期模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="careMsgTplId" name="careMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">
						<b style="width:30%;">商城订单受理模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="mallOrderMsgTplId" name="mallOrderMsgTplId" type="text">
					</div>
				</li>
				<li>
					<div class="clearfix">                <!-- 字段未定义 -->
						<b style="width:30%;">商城预约通知模块ID</b>
						<div style="width:50%;height:36px;float:left;">
						<input  style="width:300px;" id="mallPreOrderMsgTplId" name="mallPreOrderMsgTplId" type="text">
					</div>
				</li>
			</ul>
			</form>
			<div><a class="btn2 blue" href="#" onclick="saveWeChat();return false;" style="float: right;margin-right: 155px;margin-top:15px;">保存</a></div>
        </div>
    </div>
</body>
</html>