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
	var listjsonUrl = 'noticeList';
	var editUrl = 'client/client/showPage?pageName=noticeForm';
	var deleteUrl = 'deleteJson';
	var addTitle="添加管理员";
	var editTitle = "系统消息";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 550;
	var dlgHeight = 400;
	var toolbars = undefined;
	var gridOpt={
			rowStyler:function(index,row){
				if(row.isRead!=null&&row.isRead){
				}else {
					return 'color:red;';  
				}
			}
	}
	
	//toolbars[0] = {iconCls:"icon-remove",text:"废除",handler:del};
</script>

	<div class="content">
       <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100%);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
            	 <th data-options="field:'noticeDate'">发布时间</th>
                <th data-options="field:'theme'">提醒主题</th>
                <th data-options="field:'user',formatter(val){return val?val.loginAccount:''}">发布人</th>
                <th data-options="field:'noticeArea',formatter(val){return val?val.name:''}">发布区域</th>
                <th data-options="field:'content'">通知内容</th>
            </tr>
           </thead>
        </table>
        </div>
    </div>
    <!-- <div class="page-body">
        <div id="hei" style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100%);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
            	 <th data-options="field:'noticeDate'">发布时间</th>
                <th data-options="field:'theme'">提醒主题</th>
                <th data-options="field:'user',formatter(val){return val?val.loginAccount:''}">发布人</th>
                <th data-options="field:'noticeArea',formatter(val){return val?val.name:''}">发布区域</th>
                <th data-options="field:'content'">通知内容</th>
            </tr>
           </thead>
        </table>
        </div>
        
    </div> -->
 


</body>
</html>