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
	var count = 0;
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
		
		
		
		$('#MembershipPackage').combobox({
			formatter:function(row){
				var imageFile = 'images/' + row.icon;
				return '<img class="item-img" src="'+imageFile+'"/><span class="item-text">'+row.text+'</span>';
			}
		});
		
		$('#insurance').combobox({
			formatter:function(row){
				var imageFile = 'images/' + row.icon;
				return '<img class="item-img" src="'+imageFile+'"/><span class="item-text">'+row.text+'</span>';
			}
		});
		
		$('#annualsurvey').combobox({
			formatter:function(row){
				var imageFile = 'images/' + row.icon;
				return '<img class="item-img" src="'+imageFile+'"/><span class="item-text">'+row.text+'</span>';
			}
		});
		
		$('#maintain').combobox({
			formatter:function(row){
				var imageFile = 'images/' + row.icon;
				return '<img class="item-img" src="'+imageFile+'"/><span class="item-text">'+row.text+'</span>';
			}
		});
		
		$('#birthday').combobox({
			formatter:function(row){
				var imageFile = 'images/' + row.icon;
				return '<img class="item-img" src="'+imageFile+'"/><span class="item-text">'+row.text+'</span>';
			}
		});
		
		$('#customerservice').combobox({
			formatter:function(row){
				var imageFile = 'images/' + row.icon;
				return '<img class="item-img" src="'+imageFile+'"/><span class="item-text">'+row.text+'</span>';
			}
		});
		
	}
	
	function setCheck(id, checked){
		$("#"+id).prop("checked", checked);
	}

	function setPointGet(){
		saveSetting('setPointGet', {salePoint:$("#salePoint").prop("checked"), cardPoint:$("#cardPoint").prop("checked")});
	}
	
	function setPointCustomer(){
		saveSetting('setPointCustomer', {allCustomerPoint:$("#allCustomerPoint").prop("checked"), cardCustomerPoint:$("#cardCustomerPoint").prop("checked")});
	}
	
	function setPointMoneyRate(){
		saveSetting("setPointMoneyRate", {pointMoneyRate:$("#pointMoneyRate").numberbox("getValue")});
	}
	
	function setPointMoney(){
		saveSetting("setPointMoney",{pointMoney:$('input[type="radio"][name="pointMoney"]:checked').val()});
	}
	
	function saveProduct(){
		saveSetting("setProduct",{productHeader:$("#productHeader").val(), productLen:$("#productLen").numberbox("getValue")});
	}
	
	function saveService(){
		saveSetting("setService",{serviceHeader:$("#serviceHeader").val(), serviceLen:$("#serviceLen").numberbox("getValue")});
	}
	
	function saveCard(){
		saveSetting("setCard",{cardHeader:$("#cardHeader").val(), cardLen:$("#cardLen").numberbox("getValue")});
	}
	
	function saveCardRemind(){
		saveSetting("setCardRemind",{days:$("#cardRemindDays").numberbox("getValue"), 
			times:$("#cardRemindTimes").numberbox("getValue"), 
			interval:$("#cardRemindInterval").numberbox("getValue")});
	}
	
	function saveInsuranceRemind(){
		saveSetting("setInsuranceRemind",{days:$("#insuranceRemindDays").numberbox("getValue"), 
			times:$("#insuranceRemindTimes").numberbox("getValue"), 
			interval:$("#insuranceRemindInterval").numberbox("getValue")});
	}
	
	function saveYearCheckRemind(){
		saveSetting("setYearCheckRemind",{days:$("#yearCheckRemindDays").numberbox("getValue"), 
			times:$("#yearCheckRemindTimes").numberbox("getValue"), 
			interval:$("#yearCheckRemindInterval").numberbox("getValue")});
	}
	
	function saveCareRemind(){
		saveSetting("setCareRemind",{days:$("#careRemindDays").numberbox("getValue"), 
			times:$("#careRemindTimes").numberbox("getValue"), 
			interval:$("#careRemindInterval").numberbox("getValue")});
	}
	
	function saveSaleRemind(){
		saveSetting("setSaleRemind",{days:$("#saleRemindDays").numberbox("getValue"), 
			times:$("#saleRemindTimes").numberbox("getValue"), 
			interval:$("#saleRemindInterval").numberbox("getValue")});
	}
	
	function saveBornRemind(){
		saveSetting("setBornRemind",{days:$("#bornRemindDays").numberbox("getValue"),interval:$("#bornRemindInterval").numberbox("getValue")});
	}
	
	function saveSetting(url, data){
		$.ajax({
			url:url,
			type:'post',
			data:data,
			timeout:TIMEOUT,
			success:function(data){
				count++
				if (count==13) {
					var rst = "保存成功！";
					dealJsonResult(data, rst);
				}
			},
			error:function(){
				$.messager.alert(WINDOW_CAPTION, '网络原因导致保存失败，请重试！', 'error');
			}
		})
	}
	
	function salePointClicked(){
		$("#cardPoint").prop("checked", false);
	}
	
	function cardPointClicked(){
		$("#salePoint").prop("checked", false);
	}
	
	function cardCustomerPointClick(){
		$("#allCustomerPoint").prop("checked", false);
	}
	function allCustomerPointClick(){
		$("#cardCustomerPoint").prop("checked", false);
	}
	
	function submitForm(){
		count = 0;
		saveBornRemind();
		saveSaleRemind();
		saveCareRemind();
		saveYearCheckRemind();
		saveInsuranceRemind();
		saveCardRemind();
		saveCard();
		saveService();
		saveProduct();
		setPointMoney();
		setPointMoneyRate();
		setPointCustomer();
		setPointGet();
	}
	function addNoticeMan(type){
		var id = $("#"+type).combobox("getValue");
		if(id != ''){
			$.messager.confirm("添加", "是否确认添加通知人员", function (data) {
	            if (data) {
	                $.ajax({
	                    url: '../../wechat/wxuser/delNotice' + '?id=' + id + "&is"+type+'=true',
	                    type: 'post',
	                    timeout: TIMEOUT,
	                    success: function (data) {
	                        $.messager.alert("添加成功","添加成功！")
	                    },
	                    error: function () {
	                        $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
	                    }
	                });
	            }
	        });
		}else {
			top.showAlert("请选择要通知的人员！")
		}
	}
	function noticeManList(type){
		urlEdit = 'client/client/showPage?pageName=wxUserList&type='+type;
		var title = "";
		switch (type) {
		case "Card":
			title = "会员套餐到期通知人员";
			break;
		case "Insurance":
			title = "保险到期通知人员";
			break;
		case "YearCheck":
			title = "年检到期通知人员";
			break;
		case "Care":
			title = "保养到期通知人员";
			break;
		case "Born":
			title = "生日到期通知人员";
			break;
		case "Sale":
			title = "售后到期通知人员";
			break;
		default:
			break;
		}
		top.openDialog(urlEdit,title,400, 400);
	}
</script>
<div class="page-body">
    	<ul class="tab clearfix">
        	<li class="active">规则设置</li>
        </ul>
        <div style="text-align: center;"><a class="btn2 blue" href="#" onclick="submitForm();return false;">保存</a></div>
        <div class="set-rule">
        	<ul class="col4 clearfix">
				<li style="display:none">
					<h4>积分获得</h4>
					<p>
					<label><input type="checkbox" id="salePoint" >消费获得</label>
					<label><input type="checkbox" id="cardPoint" >办卡获得</label>
					</p>
					<a style="display: none;" class="btn2 blue" href="#" onclick="setPointGet();return false;">保存</a>
				</li>
				<li>
					<h4>获得资格</h4>
					<p><label><input type="checkbox" id="allCustomerPoint" onclick="allCustomerPointClick()">所有客户</label>
					<label><input type="checkbox" id="cardCustomerPoint" onclick="cardCustomerPointClick()">会员客户</label></p>
					<a style="display: none;" class="btn2 blue" href="#" onclick="setPointCustomer();return false;">保存</a>
				</li>
				<li>
					<h4>兑换比例</h4>
					<p><input style="width:80px" class="easyui-numberbox"  id="pointMoneyRate">积分＝1元</p>
					<a style="display: none;" class="btn2 blue" href="#" onclick="setPointMoneyRate();return false;">保存</a>
				</li>
				<li>
					<h4>积分兑换</h4>
					<p>
					<label><input type="radio" name="pointMoney" value="true" id="pointMoney">允许兑换</label>
					<label><input type="radio" name="pointMoney" value="false">禁止兑换</label>
					</p>
					<a style="display: none;" class="btn2 blue" href="#" onclick="setPointMoney();return false;">保存</a>
				</li>
			</ul>
			
			<ul class="number">
				<li>
					<div class="clearfix">
						<b>商品条码组成</b>
						<span>
							<input class="inp2" type="text" id="productHeader" placeholder="请输入前缀" >
							<input class="easyui-numberbox" style="width:80px;height:26px;"  id="productLen" prompt="请输入位数">
						</span>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveProduct();return false;">保存</a>
					</div>
					<p>商品条码由前缀和自动增长的编码组成</p>
				</li>
				<li>
					<div class="clearfix">
						<b>服务编码组成</b>
						<span>
							<input class="inp2" id="serviceHeader" type="text" placeholder="请输入前缀">
							<input class="easyui-numberbox" style="width:80px;height:26px;" id="serviceLen"  prompt="请输入位数">
						</span>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveService();return false;">保存</a>
					</div>
					<p>服务编码由前缀和自动增长的编码组成</p>
				</li>
				<li>
					<div class="clearfix">
						<b>会员卡号组成</b>
						<span>
						<input class="inp2" id="cardHeader" type="text" placeholder="请输入前缀">
						<input class="easyui-numberbox" style="width:80px;height:26px;" id="cardLen" prompt="请输入位数"></span>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveCard();return false;">保存</a>
					</div>
					<p>会员卡号由前缀和自动增长的编码组成</p>
				</li>
			</ul>
			
			<ul class="expire">
				<li>
					<div class="clearfix">
						<div style="width: 20%;float: left;    margin-right: 110px;">
						<b style="width:100%;">会员套餐到期</b>
						<b style="width:100%;">通知人员</b>
						</div>
						<div style="width:55%;float:left;">
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;">
								<span>到期前</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" data-options="precision:0,min:0" style="width:50px;" id="cardRemindDays" ></span>
								<span >日提醒，提醒</span>
								<span style="margin-top: 8px;display: none;"><input class="easyui-numberbox" style="width:50px;" id="cardRemindTimes" ></span>
								<span style="display: none;">次，每次间隔</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" style="width:50px;" data-options="precision:0,min:0" id="cardRemindInterval" value=""></span>
								<span>天</span>				
							</div>
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;height: 36px;
    align-content: center;
    align-items: center;">
								<select class="easyui-combobox" style="width:300px;" data-options="editable:true,valueField:'id',textField:'nickName',url:'../../wechat/wxuser/list?notCard=false',loadFilter:loadFilter" id="Card"></select>
								<input style="width:50px;"  type="button" onclick="addNoticeMan('Card')" value="添加">
								|
								<input style="width:90px;"  type="button" onclick="noticeManList('Card')" value="通知人员列表">
								<!-- <input style="width:50px;"  type="button" value="删除">
								<input id="MembershipPackage" style="width:110px" url="data/combobox_data.json" valueField="id" textField="text"></input> -->
							</div>
						</div>
						
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveCardRemind();return false;">保存</a>
						
					</div>
				</li>
				<li>
					<div class="clearfix">
						<div style="width: 20%;float: left;    margin-right: 110px;">
						<b style="width:100%;">保险到期</b>
						<b style="width:100%;">通知人员</b>
						</div>						
						<div style="width:55%;float:left;">
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;">
							<span>到期前</span>
							<span style="margin-top: 8px;"><input class="easyui-numberbox" id="insuranceRemindDays" style="width:50px"  value=""></span>
							<span>日提醒，提醒</span>
							<span style="margin-top: 8px;display: none;"><input class="easyui-numberbox" id="insuranceRemindTimes" style="width:50px" value=""></span>
							<span style="display: none;">次，每次间隔</span>
							<span style="margin-top: 8px;"><input class="easyui-numberbox" id="insuranceRemindInterval" style="width:50px"  value=""></span>
							<span>天</span>
						</div>
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;height: 36px;
    align-content: center;
    align-items: center;">
								<select class="easyui-combobox" id="Insurance" style="width:300px;" data-options="editable:true,valueField:'id',textField:'nickName',url:'../../wechat/wxuser/list?notInsurance=false',loadFilter:loadFilter"></select>
								<input style="width:50px;"  type="button" onclick="addNoticeMan('Insurance')" value="添加">
								|
								<input style="width:90px;"  type="button" onclick="noticeManList('Insurance')" value="通知人员列表">
								<!-- <input style="width:50px;"  type="button" value="删除">
								<input id="insurance" style="width:110px" url="data/combobox_data.json" valueField="id" textField="text"></input> -->
							</div>
						</div>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveInsuranceRemind();return false;">保存</a>
					</div>
				</li>
				<li>
					<div class="clearfix">
						<div style="width: 20%;float: left;    margin-right: 110px;">
						<b style="width:100%;">年检到期</b>
						<b style="width:100%;">通知人员</b>
						</div>
						<div style="width:55%;float:left;">
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;">
								<span>到期前</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="yearCheckRemindDays" style="width:50px" value=""></span>
								<span>日提醒，提醒</span>
								<span style="margin-top: 8px;display: none;"><input class="easyui-numberbox" id="yearCheckRemindTimes" style="width:50px"  value=""></span>
								<span style="display: none;">次，每次间隔</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="yearCheckRemindInterval" style="width:50px"  value=""></span>
								<span>天</span>
							</div>
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;height: 36px;
    align-content: center;
    align-items: center;">
								<select class="easyui-combobox" id="YearCheck" style="width:300px;" data-options="editable:true,valueField:'id',textField:'nickName',url:'../../wechat/wxuser/list?notYearCheck=false',loadFilter:loadFilter"></select>
								<input style="width:50px;"  type="button" onclick="addNoticeMan('YearCheck')" value="添加">
								|
								<input style="width:90px;"  type="button" onclick="noticeManList('YearCheck')" value="通知人员列表">
								<!-- <input style="width:50px;"  type="button" value="删除">
								<input id="annualsurvey" style="width:110px" url="data/combobox_data.json" valueField="id" textField="text"></input> -->
							</div>
						</div>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveYearCheckRemind();return false;">保存</a>
					</div>
					
				</li>
				<li>
					<div class="clearfix">
						<div style="width: 20%;float: left;    margin-right: 110px;">
						<b style="width:100%;">保养到期</b>
						<b style="width:100%;">通知人员</b>
						</div>
						<div style="width:55%;float:left;">
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;">
								<span>到期前</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="careRemindDays" style="width:50px"  value=""></span>
								<span>日提醒，提醒</span>
								<span style="margin-top: 8px;display: none;"><input class="easyui-numberbox" id="careRemindTimes" style="width:50px"  value=""></span>
								<span style="display: none;">次，每次间隔</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="careRemindInterval" style="width:50px"  value=""></span>
								<span>天</span>
							</div>
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;height: 36px;
    align-content: center;
    align-items: center;">
								<select class="easyui-combobox" id="Care" style="width:300px;" data-options="editable:true,valueField:'id',textField:'nickName',url:'../../wechat/wxuser/list?notCare=false',loadFilter:loadFilter"></select>
								<input style="width:50px;"  type="button" onclick="addNoticeMan('Care')" value="添加">
								|
								<input style="width:90px;"  type="button" onclick="noticeManList('Care')" value="通知人员列表">
								<!-- <input style="width:50px;"  type="button" value="删除">
								<input id="maintain" style="width:110px" url="data/combobox_data.json" valueField="id" textField="text"></input> -->
							</div>
						</div>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveCareRemind();return false;">保存</a>
					</div>
				</li>
				<li>
					<div class="clearfix">
						<div style="width: 20%;float: left;    margin-right: 110px;">
						<b style="width:100%;">生日提醒</b>
						<!-- <b style="width:100%;">通知人员</b> -->
						</div>
						<div style="width:55%;float:left;">
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;">
								<span>到期前</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="bornRemindDays" style="width:50px"  value=""></span>
								<span>日提醒，提醒</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="bornRemindInterval" style="width:50px"  value=""></span>
								<span>天</span>
							</div>
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;height: 36px;
    align-content: center;
    align-items: center;">
								<!-- <select class="easyui-combobox" id="Born" style="width:300px;" data-options="editable:true,valueField:'id',textField:'nickName',url:'../../wechat/wxuser/list?notBorn=false',loadFilter:loadFilter"></select>
								<input style="width:50px;"  type="button" onclick="addNoticeMan('Born')"  value="添加">
								|
								<input style="width:90px;"  type="button" onclick="noticeManList('Born')" value="通知人员列表"> -->
								<!-- <input style="width:50px;"  type="button" value="删除">
								<input id="birthday" style="width:110px" url="data/combobox_data.json" valueField="id" textField="text"></input> -->
							</div>
						</div>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveBornRemind();return false;">保存</a>
					</div>
				</li>
				<li>
					<div class="clearfix">
						<div style="width: 20%;float: left;    margin-right: 110px;">
						<b style="width:100%;">售后提醒</b>
						<b style="width:100%;">通知人员</b>
						</div>
						<div style="width:55%;float:left;">
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;">
								<span>到期前</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="saleRemindDays" style="width:50px"  value=""></span>
								<span>日提醒，提醒</span>
								<span style="margin-top: 8px;display: none;"><input class="easyui-numberbox" id="saleRemindTimes" style="width:50px"  value=""></span>
								<span style="display: none;">次，每次间隔</span>
								<span style="margin-top: 8px;"><input class="easyui-numberbox" id="saleRemindInterval" style="width:50px"  value=""></span>
								<span>天</span>
							</div>
							<div style="overflow: auto;width: 380px;display: flex;justify-content: space-between;height: 36px;
    align-content: center;
    align-items: center;">
								<select class="easyui-combobox" style="width:300px;" id="Sale" data-options="editable:true,valueField:'id',textField:'nickName',url:'../../wechat/wxuser/list?notSale=false',loadFilter:loadFilter"></select>
								<input style="width:50px;"  type="button" onclick="addNoticeMan('Sale')" value="添加">
								|
								<input style="width:90px;"  type="button" onclick="noticeManList('Sale')" value="通知人员列表">
								<!-- <input style="width:50px;"  type="button" value="删除">
								<input id="customerservice" style="width:110px" url="data/combobox_data.json" valueField="id" textField="text"></input> -->
							</div>
						</div>
						<a style="display: none;" class="btn2 blue" href="#" onclick="saveSaleRemind();return false;">保存</a>
					</div>
				</li>
			</ul>
        </div>
    </div>
</body>
</html>