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
	var listjsonUrl = "/spma/news/news/list";
	var editUrl = 'spmClient/spmNewsEdit';//system/spmClient/
	var deleteUrl = '/spma/news/news/deleteJson';
	
	var addTitle="添加新闻资讯";
	var editTitle = "修改新闻资讯";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 1000;
	var dlgHeight = 800;
	toolbars = undefined;
	//function edit(){};
	function belongTypeFormat(val){
		switch (val) {
		case "General":
			return "普通图文";
			break;
		case "ImgText":
			return "<font color='blue'>首页图文</font>";	
					break;
		case "Top":
			return "<font color='red'>首页顶部</font>";
			break;
		case "Video":
			return "<font color='green'>首页视频</font>";
			break;

		default:
			break;
		}
		
	}
	function imgFormat(val){
		return '<img alt="封面图片" src="'+val+'" width="150px" height="100px">';
	}
</script>
    <div class="page-body">
    	<!-- <div class="ope-bar clearfix">
        	<div class="fl">
               <a class="btn2 active" href="showList">管理员列表</a>
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
                <th data-options="field:'title'">标题</th>
                <th data-options="field:'image',formatter:imgFormat">封面图</th>
               <!--  <th data-options="field:'message',width:500">正文</th> -->
                <th data-options="field:'belongType',formatter:belongTypeFormat">版面</th>
                <th data-options="field:'creater'">创建人</th>
                <th data-options="field:'creTime'">创建时间</th>
                <th data-options="field:'modifier'">修改人</th>
                <th data-options="field:'modifyTime'">修改时间</th>
            </tr>
           </thead>
        </table>
        </div>
    </div>
</body>
</html>