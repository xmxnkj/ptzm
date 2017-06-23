<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	    <link rel="stylesheet" href="../../content/css/reset.css">
		<link rel="stylesheet" href="../../content/css/common.css">
		<link rel="stylesheet" href="../../content/css/style.css">
		<script src="../../content/js/jquery-3.1.1.min.js"></script>
		<script src="../../content/js/common.js"></script>
		<script src="../../content/scripts/common/setting.js"></script>
		<script src="../../content/scripts/common/utils.js"></script>
		<script src="../../content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
		<script src="../../content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
		<link href="../../content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
		<link href="../../content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
		<script src="../../content/scripts/common/gridutils.js"></script>

</head>
<body>
<script src="../../content/scripts/common/list.js"></script>
<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'clientPayRecord/showEdit';
	var deleteUrl = 'deleteJson';
	var addTitle="添加管理员";
	var editTitle = "修改管理员资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 900;
	var dlgHeight = 850;
	if (params['cancel'] != 'true') {
		/* toolbars = [];
		toolbars[0] = {iconCls:'icon-remove', text:'废除', handler:del}; */
	}else {
		toolbars = undefined;
	}
	function edit(){};
	function del(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			if(editUrl.lastIndexOf('?')>=0){
				url = editUrl + '&id=' + row.id + "&cancel=false";
			}else{
				url = editUrl + '?id=' + row.id + "&cancel=false";
			}
			if(typeof(formParams)!='undefined'){
				for(var key in formParams){
					if(url.lastIndexOf("?")>=0){
						url+="&";
					}else{
						url+="?";
					}
					url+=key+"="+formParams[key];
				}
				
			}
			if(typeof(isPage)=='undefined' || !isPage){
				if(isOpenDlg2()){
					top.openDialog2(url,
							editTitle,
							dlgWidth, dlgHeight
							);
				}else{
					top.openDialog(url,
							editTitle,
							dlgWidth, dlgHeight
							);
				}
			}else{
				location.href=url;
			}
		}else{
			$.messager.alert('提示', '请选择一行进行废除！');
		}
	}
	$(function(){
		if (params['cancel'] != 'true') {
			$("#btn2").attr("class","btn")
			$("#btn1").attr("class","btn2 active")
		}else {
			$("#btn1").attr("class","btn")
			$("#btn2").attr("class","btn2 active")
		}
	})
</script>
    <div class="page-body">
    	<!-- <div class="ope-bar clearfix">
        	<div class="fl">
                <a class="btn" href="../../system/clientPayRecord/showEdit">缴费</a>
                <a id="btn1" class="btn" href="../../system/clientPayRecord/showList?cancel=false">缴费记录</a>
                <a id="btn2" class="btn" href="../../system/clientPayRecord/showList?cancel=true">作废列表</a>
            </div>
        </div> -->
        <div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
        </div>
        <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
            	<c:if test="${params['cancel']=='true' }">
                	 <th data-options="field:'cancelDate'">操作时间</th>
                </c:if>
                <th data-options="field:'payDate'">缴费时间</th>
                <th data-options="field:'client.area',formatter(val,row){return row.client.area?row.client.area.name:''},width:100">区域</th>
                <th data-options="field:'client',formatter(val){return val?val.loginName:''},width:100">客户</th>
                <th data-options="field:'client.address',formatter(val,row){return row.client?row.client.address:''}">客户地址</th>
                <th data-options="field:'client.name',formatter(val,row){return row.client?row.client.name:''},width:100">账户名称</th>
                <th data-options="field:'clientMeal',formatter(val,row){return row.client.clientMeal?row.client.clientMeal.name:''},width:100">套餐</th>
                <th data-options="field:'client.useDate',formatter(val,row){return row.client?row.client.useDate:''}">启用日期</th>
                <th data-options="field:'nextExpireDate'">下次到期时间</th>
                <th data-options="field:'payAmount'">缴费数量</th>
                <th data-options="field:'money'">实收金额</th>
                <th data-options="field:'giftMoney'">优惠金额</th>
                <th data-options="field:'payType',formatter(val){return val!='Cash'?'自助缴费':'后台缴费'}">缴费方式</th>
                <th data-options="field:'description'">备注</th>
                <c:if test="${params['cancel']=='false' }">
                	 <th data-options="field:'user',formatter(val,row){return val?val.loginAccount:''},width:100">收款员</th>
                </c:if>
               <c:if test="${params['cancel']=='true' }">
                	 <th data-options="field:'cancelUser',formatter(val,row){return val?val.loginAccount:''},width:100">操作员</th>
                </c:if>
            </tr>
           </thead>
        </table>
        </div>
    </div>
 


</body>
</html>