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
	function formLoaded(data){
		/* if (typeof eid != 'undefined') {
			$("#clientPayRecord_giftMoney").numberbox({
				disabled:true
			})
			$("#clientPayRecord_payAmount").numberbox({
				disabled:true
			})
			$("#clientPayRecord_effectiveDate").datebox("setValue",data.lastExpireDate);
			$("#bePayMoney").numberbox("setValue",changeNumber(data.money)+changeNumber(data.giftMoney));
			$("#clientPayRecord_description").val("");
		} */
	}
	function check(){
		var paramsForm = {};
		//paramsForm['id'] = $("#clientPayRecord_id").val(); 
		paramsForm['client.id'] = $("#clientPayRecord_id").val();
		paramsForm['payAmount'] = $("#clientPayRecord_payAmount").numberbox("getValue");
		paramsForm['nextExpireDate'] = $("#clientPayRecord_nextExpireDate").datebox("getValue");
		paramsForm['lastExpireDate'] = $("#clientPayRecord_effectiveDate").datebox("getValue");
		paramsForm['giftMoney'] = $("#clientPayRecord_giftMoney").numberbox("getValue");
		paramsForm['money'] = $("#clientPayRecord_money").numberbox("getValue");
		paramsForm['description'] = $("#clientPayRecord_description").val();
		paramsForm['cancel'] = false;
		ajax({
			type:'post',
			url:saveUrl,
			data:paramsForm,
			success:function(data){
				if (data.success==true||data.success=='true') {
					top.openDialog("client/client/showPage?pageName=codeForm&code="+encodeURIComponent(data.entity),"缴费付款",250, 235);
					//$.messager.alert('缴费','缴费成功!','缴费成功',fn);
				}else {
					top.showAlert(data.message);
				}
			}, 
			error:function(){
				top.showAlert("网络出现异常，请重试！")
			}
		})
		/* doSubmit(); */
	}
	function fn(r){
		if (r) {
			location.replace(location.href);
		}else {
			location.replace(location.href);
		}
		
	}
	function payAmountChange(val){
		var  effectiveDate = $("#clientPayRecord_effectiveDate").datebox("getValue");
		var amount = $("#clientPayRecord_payAmount").numberbox("getValue");
		var price = $("#clientPayRecord_clientMeal_yearPrice").val();
		price = price!=''?price:0.00;
		if (effectiveDate!="") {
			var nowDate = new Date().Format("yyyy-MM-dd");
			var rst = CompareDate(nowDate,effectiveDate);
			//计算下次到期时间
			if (rst) {//有效期小于当前日期
				if (amount!='') {
					countNextDate(nowDate,amount,price);
				}
			}else {//有效期大于当前日期
				if (amount!='') {
					countNextDate(effectiveDate,amount,price);
				}
			}
		}else {
			top.showAlert("请先设置账户的有效期限！")
		}
	}
	function countNextDate(date,amount,price){
		var year = parseInt(date.substring(0,4))+parseInt(amount);
		var time = date.substring(5,10);
		if (time == '02-29') {
			time = '03-01';
		}
		$("#clientPayRecord_nextExpireDate").datebox("setValue",year+"-"+time);
		$("#bePayMoney").numberbox("setValue",amount*price);
	}
	function payMoney(){
		var bePay = $("#bePayMoney").numberbox("getValue");
		var giftMoney = $("#clientPayRecord_giftMoney").numberbox("getValue")!=''?$("#clientPayRecord_giftMoney").numberbox("getValue"):0.00;
		if (bePay=='') {
			return;
		}
		if (parseFloat(bePay) < parseFloat(giftMoney)) {
			top.showAlert("优惠金额不应大于应付金额");
			$("#clientPayRecord_giftMoney").numberbox("setValue","0.00");
			return;
		}
		$("#clientPayRecord_money").numberbox("setValue",bePay-giftMoney);
	}
	/* function setNull(){
		
	} */
</script>
    <div class="pop" id="PerfectData" style="top:10%; width:600px; margin-left:-280px;">
      <div class="cont" style="max-height: 100%">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <%-- <c:if test="${empty params['cancel'] }">
            	 <tr>
	            	<th width="30%">用户搜索</th>
	            	<td><select  class="easyui-combobox" id="key" name="key" data-options="editable:true,valueField:'id',textField:'name',width:350">
	            	</select></td>
	             </tr>
            </c:if> --%>
            <tr>
            	<th width="30%">区域</th>
            	<td><select class="easyui-combobox" id="clientPayRecord_area_id" name="clientPayRecord.area.id"  data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:350,url:'../../client/client/baseAreaList?parentId=0',loadFilter:loadFilter">
            	</select></td>
            </tr>
            <tr>
                <th width="30%">客户名称</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.loginName" id="clientPayRecord_loginName" data-options="disabled:true,width:350"></td>
            </tr>
            <tr>
                <th width="30%">客户地址</th>
                <td width="70%">
                <select class="easyui-combobox" id="clientPayRecord_province" name="clientPayRecord.province" data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:85,url:'../../client/client/baseAreaList?parentId=0',loadFilter:loadFilter"></select>
		                省<select class="easyui-combobox" id="clientPayRecord_city" name="clientPayRecord.city" data-options="disabled:true,editable:false,valueField:'id',textField:'name',url:'../../client/client/baseAreaList',width:85,loadFilter:loadFilter"></select>
		                市<select class="easyui-combobox" id="clientPayRecord_county" name="clientPayRecord.county" data-options="disabled:true,editable:false,valueField:'id',textField:'name',url:'../../client/client/baseAreaList',width:85,loadFilter:loadFilter"></select>
		                县</td>
            </tr>
            <tr>
            	<th width="30%">负责人</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.responsibleUser" id="clientPayRecord_responsibleUser" data-options="disabled:true,width:350" ></td>
            </tr>
            <tr>
                <th width="30%">电话</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.cellPhone" id="clientPayRecord_cellPhone" data-options="disabled:true,width:350"></td>
            </tr>
            <tr>
                <th width="30%">账号名称</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.name" id="clientPayRecord_name"  data-options="disabled:true,width:350"></td>
            </tr>
            <tr>
                <th width="30%">选择套餐</th>
                <td><select class="easyui-combobox" id="clientPayRecord_clientMeal_id" name="clientPayRecord.clientMeal.id" data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:350,url:'../../client/client/clientMealList',loadFilter:loadFilter">
            	</select></td>
            	<input type="hidden" id="clientPayRecord_clientMeal_yearPrice" name="clientPayRecord.clientMeal.yearPrice">
            </tr>
            <tr>
                <th width="30%">账号有效期</th>
            	<td width="70%"><input class="easyui-datebox"  name="clientPayRecord.effectiveDate" id="clientPayRecord_effectiveDate" data-options="disabled:true,width:350"/></td>
            </tr>
           <tr>
                <th width="30%">缴费数量</th>
                <td width="70%"><input class="easyui-numberbox" name="clientPayRecord.payAmount" id="clientPayRecord_payAmount" data-options="precision:0,min:1,width:350,onChange:payAmountChange"></td>
            </tr>
             <tr>
                <th width="30%">下次到期时间</th>
            	<td width="70%"><input class="easyui-datebox"  name="clientPayRecord.nextExpireDate" id="clientPayRecord_nextExpireDate"  data-options="disabled:true,width:350"/></td>
            </tr>
            <tr>
                <th width="30%">应收金额</th>
                <td width="70%"><input class="easyui-numberbox" name="bePayMoney" id="bePayMoney" data-options="disabled:true,width:350,precision:2,onChange:payMoney"></td>
            </tr>
            <tr style="display: none">
                <th width="30%">优惠</th>
                <td width="70%"><input class="easyui-numberbox" name="clientPayRecord.giftMoney" id="clientPayRecord_giftMoney" data-options="width:350,precision:2,onChange:payMoney"></td>
            </tr>
            <tr>
                <th width="30%">实收</th>
                <td width="70%"><input class="easyui-numberbox" name="clientPayRecord.money" id="clientPayRecord_money" data-options="disabled:true,width:350,precision:2,onChange:payMoney"></td>
            </tr>
            <tr>
                <th width="30%">备注</th>
                <td width="70%"><textarea class="inp2" name="clientPayRecord.description" id="clientPayRecord_description" style="width: 350px;height: 100px"></textarea></td>
            </tr>
            </tbody>
        </table>
		 <input type="hidden" name="clientPayRecord.id" id="clientPayRecord_id">
         <!-- <input type="hidden" name="clientPayRecord.client.id" id="clientPayRecord_client_id"> -->
        </form>
      </div>
      
    </div>
     	<div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">缴费</a>         
    	</div>
		<div id="qrcode" class="cont" style="display: none;position: absolute;left: 50%;top: 48%;z-index: 99999;text-align: center;" ></div>
</body>
</html>