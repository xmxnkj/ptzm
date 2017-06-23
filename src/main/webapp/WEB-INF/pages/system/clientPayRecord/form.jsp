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
<script type="text/javascript">
	var saveUrl = "savePay";
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
	function formLoaded(data){
		if (typeof eid != 'undefined') {
			$("#clientPayRecord_giftMoney").numberbox({
				disabled:true
			})
			$("#clientPayRecord_payAmount").numberbox({
				disabled:true
			})
			$("#clientPayRecord_client_effectiveDate").datebox("setValue",data.lastExpireDate);
			$("#bePayMoney").numberbox("setValue",changeNumber(data.money)+changeNumber(data.giftMoney));
			$("#clientPayRecord_description").val("");
		}
	}
	$(function(){
		$("#key").combobox({
			valueField:'id',
			textField:'name',
			formatter:function(row){
				return row.name;
			},
			onChange:function(newValue){
				if(isFilling || newValue=='' || newValue.length<=1){
	                return;
				}
	             $.ajax({
	                 url:'../../system/client/list',
	                 data:{'key':newValue},
	                 type:'post',
	                 error:function(){
	                     //alert('error');
	                 }
	                 }).done(function(data){
	                     if(data!=null && data.rows!=null && data.rows.length>0){
	                        $("#key").combobox('loadData', data.rows);
	                     }
	                     isFilling=false;
	                 });
			},
			onSelect:function(row){
				fillForm(row,"clientPayRecord_client");
				$("#clientPayRecord_payAmount").numberbox("setValue","");
			}
		})
	})
	function check(){
		var paramsForm = {};
		if (typeof eid != 'undefined') {
			paramsForm['id'] = $("#clientPayRecord_id").val(); 
			paramsForm['client.id'] = $("#clientPayRecord_client_id").val();
			paramsForm['lastExpireDate'] = $("#clientPayRecord_client_effectiveDate").datebox("getValue");
			paramsForm['description'] = $("#clientPayRecord_description").val();
			paramsForm['payAmount'] = $("#clientPayRecord_payAmount").numberbox("getValue");
			paramsForm['managerPw'] = $("#managerPw").val();
			paramsForm['cancel'] = true;
		}else {
			paramsForm['id'] = $("#clientPayRecord_id").val(); 
			paramsForm['client.id'] = $("#clientPayRecord_client_id").val();
			paramsForm['payAmount'] = $("#clientPayRecord_payAmount").numberbox("getValue");
			paramsForm['nextExpireDate'] = $("#clientPayRecord_nextExpireDate").datebox("getValue");
			paramsForm['lastExpireDate'] = $("#clientPayRecord_client_effectiveDate").datebox("getValue");
			paramsForm['giftMoney'] = $("#clientPayRecord_giftMoney").numberbox("getValue");
			paramsForm['money'] = $("#clientPayRecord_money").numberbox("getValue");
			paramsForm['description'] = $("#clientPayRecord_description").val();
			paramsForm['cancel'] = false;
		}
		ajax({
			type:'post',
			url:saveUrl,
			data:paramsForm,
			success:function(data){
				if (typeof eid != 'undefined') {
					if (data.success==true||data.success=='true') {
						top.closeDialog();
						top.closeDialog2();
						top.refreshContent();
					}else {
						top.showAlert(data.message);
					}
				}else {
					if (data.success==true||data.success=='true') {
						//setNull("frm");
						$.messager.alert('缴费','缴费成功!','缴费成功',fn);
					}else {
						top.showAlert(data.message);
					}
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
		if (typeof eid == 'undefined') {
			var  effectiveDate = $("#clientPayRecord_client_effectiveDate").datebox("getValue");
			var amount = $("#clientPayRecord_payAmount").numberbox("getValue");
			var price = $("#clientPayRecord_client_clientMeal_yearPrice").val();
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
 	<c:if test="${empty params['cancel'] }">
 		<!-- <div class="ope-bar clearfix" >
	   	    <div class="fl">
	           <a class="btn2 active" href="showEdit">缴费</a>
	           <a id="btn1" class="btn" href="../../system/clientPayRecord/showList?cancel=false">缴费记录</a>
	           <a id="btn2" class="btn" href="../../system/clientPayRecord/showList?cancel=true">作废列表</a>
	       </div>
	   </div> -->
	   <div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
        </div>
 	</c:if>
   
   
    <div class="pop" id="PerfectData" style="top:12%;width:600px; margin-left:-280px;height: auto">
      <div class="cont" style="max-height: 100%">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <c:if test="${empty params['cancel'] }">
            	 <tr>
	            	<th width="30%">用户搜索</th>
	            	<td><select  class="easyui-combobox" id="key" name="key" data-options="editable:true,valueField:'id',textField:'name',width:350">
	            	</select></td>
	             </tr>
            </c:if>
            <tr>
            	<th width="30%">区域</th>
            	<td><select class="easyui-combobox" id="clientPayRecord_client_area_id" name="clientPayRecord.client.area.id"  data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:350,url:'../../system/baseArea/list?parentId=00',loadFilter:loadFilter">
            	</select></td>
            </tr>
            <tr>
                <th width="30%">客户名称</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.client.loginName" id="clientPayRecord_client_loginName" data-options="disabled:true,width:350"></td>
            </tr>
            <tr>
                <th width="30%">客户地址</th>
                <td width="70%">
                <select class="easyui-combobox" id="clientPayRecord_client_province" name="clientPayRecord.client.province" data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:85,url:'../../system/baseArea/list?parentId=0',loadFilter:loadFilter"></select>
		                省<select class="easyui-combobox" id="clientPayRecord_client_city" name="clientPayRecord.client.city" data-options="disabled:true,editable:false,valueField:'id',textField:'name',url:'../../system/baseArea/list',width:85,loadFilter:loadFilter"></select>
		                市<select class="easyui-combobox" id="clientPayRecord_client_county" name="clientPayRecord.client.county" data-options="disabled:true,editable:false,valueField:'id',textField:'name',url:'../../system/baseArea/list',width:85,loadFilter:loadFilter"></select>
		                县</td>
            </tr>
            <tr>
            	<th width="30%">负责人</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.client.responsibleUser" id="clientPayRecord_client_responsibleUser" data-options="disabled:true,width:350" ></td>
            </tr>
            <tr>
                <th width="30%">电话</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.client.cellPhone" id="clientPayRecord_client_cellPhone" data-options="disabled:true,width:350"></td>
            </tr>
            <tr>
                <th width="30%">账号名称</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="clientPayRecord.client.name" id="clientPayRecord_client_name"  data-options="disabled:true,width:350"></td>
            </tr>
            <tr>
                <th width="30%">选择套餐</th>
                <td><select class="easyui-combobox" id="clientPayRecord_client_clientMeal_id" name="clientPayRecord.client.clientMeal.id" data-options="disabled:true,editable:true,valueField:'id',textField:'name',width:350,url:'../../system/clientMeal/list',loadFilter:loadFilter">
            	</select></td>
            	<input type="hidden" id="clientPayRecord_client_clientMeal_yearPrice" name="clientPayRecord.client.clientMeal.yearPrice">
            </tr>
            <tr>
                <th width="30%">账号有效期</th>
            	<td width="70%"><input class="easyui-datebox"  name="clientPayRecord.client.effectiveDate" id="clientPayRecord_client_effectiveDate" data-options="disabled:true,width:350"/></td>
            </tr>
           <tr>
                <th width="30%">缴费数量</th>
                <td width="70%"><input class="easyui-numberbox" name="clientPayRecord.payAmount" id="clientPayRecord_payAmount" data-options="disabled:false,precision:0,min:1,width:350,onChange:payAmountChange"></td>
            </tr>
             <tr>
                <th width="30%">下次到期时间</th>
            	<td width="70%"><input class="easyui-datebox"  name="clientPayRecord.nextExpireDate" id="clientPayRecord_nextExpireDate"  data-options="disabled:true,width:350"/></td>
            </tr>
            <tr>
                <th width="30%">应收金额</th>
                <td width="70%"><input class="easyui-numberbox" name="bePayMoney" id="bePayMoney" data-options="disabled:true,width:350,precision:2,onChange:payMoney"></td>
            </tr>
            <tr>
                <th width="30%">优惠</th>
                <td width="70%"><input class="easyui-numberbox" name="clientPayRecord.giftMoney" id="clientPayRecord_giftMoney" data-options="width:350,precision:2,onChange:payMoney"></td>
            </tr>
            <tr>
                <th width="30%">实收</th>
                <td width="70%"><input class="easyui-numberbox" name="clientPayRecord.money" id="clientPayRecord_money" data-options="disabled:true,width:350,precision:2,onChange:payMoney"></td>
            </tr>
            <c:if test="${!empty params['cancel'] }">
            	<tr>
	                <th width="30%">总账号密码</th>
	                <td width="70%"><input class="inp2" type="password" name="managerPw" id="managerPw" ></td>
	            </tr>
            </c:if>
            <tr>
                <th width="30%">备注</th>
                <td width="70%"><textarea class="inp2" name="clientPayRecord.description" id="clientPayRecord_description" style="width: 350px;height: 100px"></textarea></td>
            </tr>
            </tbody>
        </table>
		 <input type="hidden" name="clientPayRecord.id" id="clientPayRecord_id">
         <input type="hidden" name="clientPayRecord.client.id" id="clientPayRecord_client_id">
        </form>
      </div>
      <div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">
          		<c:if test="${empty params['cancel'] }">
          			缴费
          		</c:if>
          		<c:if test="${!empty params['cancel'] }">
          			撤销
          		</c:if>
          	</a>         
    	</div>
    </div>
</body>
</html>