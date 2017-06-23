<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script src="../../content/scripts/common/list.js"></script>
<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'clientMeal/showEdit';
	var deleteUrl = 'deleteJson';
	
	var addTitle="添加套餐";
	var editTitle = "修改套餐信息";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	/* toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
	toolbars[3] = {iconCls:'icon-search', text:'查看', handler:look}; */
	function edit(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			if (row.useAmount!=null&&row.useAmount!=''&&row.useAmount>0) {
				top.showAlert("该套餐已使用！");
				return;
			}
			if(editUrl.lastIndexOf('?')>=0){
				url = editUrl + '&id=' + row.id;
			}else{
				url = editUrl + '?id=' + row.id;
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
			$.messager.alert('提示', '请选择一行进行修改！');
		}
	}
	function look(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			if(editUrl.lastIndexOf('?')>=0){
				url = editUrl + '&id=' + row.id + "&look=1";
			}else{
				url = editUrl + '?id=' + row.id + "&look=1";
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
			$.messager.alert('提示', '请选择一行进行查看！');
		}
	}
	function del(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			if (row.useAmount!=null&&row.useAmount!=''&&row.useAmount>0) {
				top.showAlert("该套餐已使用！");
				return;
			}
			$.messager.confirm(confirmDeleteTitle, confirmDeleteInfo, function (data) {
	            if (data) {
	                $.ajax({
	                    url: deleteUrl + '?id=' + row.id+ "&rnd="+ Math.random(),
	                    type: 'get',
	                    timeout: TIMEOUT,
	                    success: function (data) {
	                        if(dealJsonResult(data, deleteSuccess)){
	                        	$('#tbl').datagrid('reload');	
	                        }
	                    },
	                    error: function () {
	                        $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
	                    }
	                });
	            }
	        });
		}
	}
	function stopUse(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			if (!row.mealState) {
				top.showAlert("该套餐已停用！");
				return;
			}
			$.messager.confirm("套餐停用", "确定要停用该套餐吗？", function (data) {
	            if (data) {
	            	saveParams = {};
	            	saveParams['id'] = row.id;
	            	saveParams['mealState'] = false;
	                $.ajax({
	                    url: "saveJson",
	                    type: 'post',
	                    data:saveParams,
	                    timeout: TIMEOUT,
	                    success: function (data) {
	                        if(dealJsonResult(data, "套餐停用成功！")){
	                        	$('#tbl').datagrid('reload');	
	                        }
	                    },
	                    error: function () {
	                        $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
	                    }
	                });
	            }
	        });
		}else {
			top.showAlert("请选择套餐！")
		}
	}
</script>
    <div class="page-body">
    	<div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
        </div>
        <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
	        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
	            <thead>
	            <tr>    
	                <th data-options="field:'name'">套餐名称</th>
	                <th data-options="field:'useAmount'">使用数量</th>
	                <th data-options="field:'shopAmount'">门店限制</th>
	                <th data-options="field:'yearPrice'">年费价格</th>
	                <th data-options="field:'mealState',formatter(val){return val?'开启':'停用'}">套餐状态</th>
	                <th data-options="field:'addDate'">添加时间</th>
	                <th data-options="field:'mealContent',width:300">套餐内容</th>
	                <th data-options="field:'addUser',formatter(val){return val?val.loginAccount:''}">添加人</th>
	            </tr>
	           </thead>
	        </table>
        </div>
    </div>
</body>
</html>