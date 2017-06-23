<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="../../content/css/reset.css">
<link rel="stylesheet" href="../../content/css/common.css">
<link rel="stylesheet" href="../../content/css/style.css">
<link rel="stylesheet" href="../../content/css/Css/seat.css">
 <link rel="stylesheet" href="../../content/css/Css/Pagestyle.css">
 <link rel="stylesheet" href="../../content/css/Css/purge.css">
 <link rel="stylesheet" href="../../content/css/Css/buttons.css">
 <link rel="stylesheet" href="../../content/css/Css/Popup.css">
 <link rel="stylesheet" href="../../content/css/Css/VoiceTemplate.css">
 <link rel="stylesheet" href="../../content/css/Css/userManage.css">
<script src="../../content/js/jquery-3.1.1.min.js"></script>
<script src="../../content/js/common.js"></script>
<script src="../../content/scripts/common/setting.js"></script>
<script src="../../content/scripts/common/utils.js"></script>
<script src="../../content/scripts/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
<script src="../../content/scripts/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
<link href="../../content/scripts/jquery-easyui-1.4.5/themes/icon.css" rel="stylesheet"/>
<link href="../../content/scripts/jquery-easyui-1.4.5/themes/default/easyui.css" rel="stylesheet"/>
<script src="../../content/scripts/common/gridutils.js"></script>
<script src="../../content/scripts/common/formUtils.js"></script>
<script src="../../content/scripts/common/date.js"></script>
<script src="../../content/scripts/format/format.js"></script>
<title>Insert title here</title>
</head>
<body>
<c:if test="${!empty params}">
		<script type="text/javascript">
			var params={};
			<c:forEach var="item" items="${params}">
				<c:if test="${item.key!='id' && item.key!='rnd'}">
					params['${item.key}'] ='${item.value}';
				</c:if>
			</c:forEach>
		</script>
	</c:if>
<script src="../../content/scripts/format/format.js"></script>
<script src="../../content/scripts/common/list.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'list';
	var editUrl = 'customer/callRecordDetail/showEdit';
	var deleteUrl = 'deleteJson';
	var addTitle="添加通话记录";
	var editTitle = "修改通话记录";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	singleSelect = false;
	var dlgWidth = 400;
	var dlgHeight = 500;
	toolbars = undefined;
	
	function edit(){
		var row = $("#tbl").datagrid("getSelected");
		if (row) {
			location.href = "../../customer/callRecordDetail/showList?callRecordId="+row.id;
		}else {
		}
		
	}
	$(function(){
		$(".btn").each(function(){
			$(this).attr("onClick","return noChoose()")
		})
	})
	function noChoose(){
		top.showAlert("请选择一条通话记录！");
		return false;
	}
	function rowClicked(index,row){
		$(".btn").each(function(){
			$(this).removeAttr("onClick")
		})
	}
	function phoneFormat(val){
		return val?val.mobile:'';
	}
	
	function voiceFmt(val,row){
		return '<audio id="'+row.id+'" controls ><source src="../../'+val+'"/></audio>';
		
	}
	
	function showDetails(){
		
		var row = $("#tbl").datagrid("getSelected");
		if (row) {
			location.href = '${pageContext.request.contextPath}/customer/callRecordDetail/showList?callRecordId='+row.id;
		}else {
			alert("请选择一条记录！");
		}
	}
</script>
<div class="page-body" style="height:100%;">
	<div id="remind" class="ope-bar clearfix">
		<%-- <c:import url="../../header.jsp">
	    </c:import> --%>
	    <a id="css-1" class="btn2 active" href="${pageContext.request.contextPath}/customer/callRecord/showList">通话记录</a>
    	<a id="css-2" class="btn" href="javascript:showDetails()">记录详情</a>
    </div> 
    <br>
        <div class="table" style="height: 92%;width: 100%;margin-top:0px;">
            <!-- <div class="contentDataTableBtn">
            		<a href="#" class="button button-primary button-rounded button-small" onclick="add()">新增计划</a>
                    <img src="../../content/images/trash.png" height="200" width="200"/>
                    <span onclick="edit()">修改</span>
                    <img src="../../content/images/trash.png" height="200" width="200"/>
                    <span onclick="del()">删除</span>
            </div> -->
		    <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" >
		        <thead>
		        <tr>
		        	<!-- <th data-options="field:'',checkbox:true"  style="width:30px;"></th> -->
		        	<th data-options="field:'customer',formatter:phoneFormat" >电话号码</th>
		            <th data-options="field:'voiceRecord',formatter:voiceFmt" style="width:330px;">语音记录</th>
		        	<th data-options="field:'textRecord'" >文本记录</th>
		        	<th data-options="field:'callDate'" >时间</th>
		        	<th data-options="field:'talkTime'" >通话时长</th>
		        	<th data-options="field:'delay'" >延迟</th>
		        	<th data-options="field:'isVaild'" >是否有效</th>
		        </tr>
		        </thead>
		    </table>
		    
        </div>
</div>
</body>
</html>