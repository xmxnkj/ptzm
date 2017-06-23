<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hsit.common.uac.entity.Operation"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
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
	var editUrl = 'client/showEdit';
	var deleteUrl = 'deleteJson';
	var addTitle="添加管理员";
	var editTitle = "修改管理员资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	var toolbars = [];
	/* toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del}; */
	function del(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			if(editUrl.lastIndexOf('?')>=0){
				url = editUrl + '&id=' + row.id + '&delete=1';
			}else{
				url = editUrl + '?id=' + row.id + '&delete=1';
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
					top.openDialog2(url,editTitle,dlgWidth, dlgHeight);
				}else{
					top.openDialog(url,editTitle,dlgWidth, dlgHeight);
				}
			}else{
				location.href=url;
			}
		}else{
			$.messager.alert('提示', '请选择一行进行删除！');
		}
	}
	function stateFormat(val,row){
		return val?'开启':'停用'
	}
</script>
<%-- <%List<Operation> operations = (List<Operation>)request.getSession().getAttribute("operations"); 
	if(operations!=null&&operations.size()>0){
		for(int i =0;i < operations.size();i++){
			Operation operation =  operations.get(i);
			if(operation.getName().equals("添加前台账户")){
	%>
			<p><%=operation.getName() %></p>
			
			
	<% 
			}
		}
	}
%> --%>

    <div class="page-body">
    	<div class="ope-bar clearfix">
	    	<c:import url="../header.jsp">
	    	</c:import>
	    	<!-- <div class="fl">
               <a class="btn2 active" href="showList">账户列表</a>
                <a id="btn1" class="btn" href="../../system/modifyClientRecord/showList?modifyRecord=true">修改记录</a>
                <a id="btn2" class="btn" href="../../system/modifyClientRecord/showList?delRecord=true">删除列表</a>
            </div> -->
        </div>
        <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
                <th data-options="field:'loginName'">客户名称</th>
                <th data-options="field:'address'">客户地址</th>
                <th data-options="field:'responsibleUser'">负责人</th>
                <th data-options="field:'cellPhone'">电话</th>
                <th data-options="field:'name'">账户名称</th>
                <th data-options="field:'clientMeal',formatter(val){return val?val.name:''}">套餐</th>
                <th data-options="field:'useDate'">启用日期</th>
                <th data-options="field:'state',formatter:stateFormat">账户状态</th>
                <!-- <th data-options="field:'namess'">最后登录时间</th>
                <th data-options="field:'cellPhoness'">最后登录IP</th> -->
                <th data-options="field:'effectiveDate'">账户有效期</th>
                <th data-options="field:'description'">备注</th>
                <th data-options="field:'addUser',formatter(val){return val?val.loginAccount:''}">添加人</th>
                <th data-options="field:'area',formatter(val){return val?val.name:''}">区域</th>
            </tr>
           </thead>
        </table>
        </div>
    </div>
 


</body>
</html>