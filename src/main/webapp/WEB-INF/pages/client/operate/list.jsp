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

<script src="../../content/scripts/client/opList.js"></script>
<script src="../../content/scripts/format/format.js"></script>
<script src="../../content/scripts/common/list.js"></script>

<script type="text/javascript">
	var listjsonUrl = 'operateList';
	var editUrl = 'client/operate/showEdit';
	var deleteUrl = 'deleteOperate';
	
	var addTitle="添加权限";
	var editTitle = "修改权限";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 500;
	var dlgHeight = 600;
</script>

<div class="content">
        <div class="tab">
            <a href="${pageContext.request.contextPath}/client/clientUser/showList?type=0" class="button button-primary button-rounded button-small">用户</a>
            <a href="${pageContext.request.contextPath}/client/userRole/showList?type=0" class="button button-primary button-rounded button-small">角色</a>
            <a href="${pageContext.request.contextPath}/client/operate/showList?type=0" class="button button-primary button-rounded button-small">操作</a>
            <a href="${pageContext.request.contextPath}/client/dept/showList?type=0" class="button button-primary button-rounded button-small">部门</a>
        </div>
        <div class="table">
            <div class="table-function">
                <div class="num">
                    <div class="ConditionTime">
		                <span>角色名称：</span>
	                	<input type="text" class="easyui-textbox" name="roleName" id="roleName">
	               		<a href="#" class="button button-action button-rounded button-small" onclick="searchData()">搜索</a>
		           			<script type="text/javascript">
					            function searchData(){
					               paramsSearch = getFormData('frmCheck',params);
					         	   var roleName = $("#roleName").val();
					         	   
					               if (roleName!="") {
					            	   paramsSearch['roleName'] = roleName;
								   }else {
									   paramsSearch['roleName'] = undefined;
								   }
					               
					               $("#tbl").datagrid("reload",paramsSearch)
					             }
				            </script>   
		            </div>
                </div>
                <div>
                    <!-- <a href="#" class="button button-primary button-rounded button-small">新增用户</a>
                    <a href="#" class="button button-primary button-rounded button-small">一键分配主叫号码</a> -->
                </div>
            </div>
            <div class="table-content">
				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
		        	<thead>
			            <tr>
				          	<th data-options="field:'id',hidden:false" >ID</th>
				          	<th data-options="field:'pid',hidden:false" >PID</th>
				          	<th data-options="field:'text'" >名称</th>
			                <th data-options="field:'grade'" >等级</th>
			                <th data-options="field:'displayOrder'" >排序</th>
			                <th data-options="field:'code'" >代码</th>
			                <th data-options="field:'meauType',hidden:true,formatter:meauFormatter" >所属菜单</th>
			                <th data-options="field:'module',hidden:true,formatter:moduleFormatter" >模块</th>
			                <th data-options="field:'url'" >地址</th>
			            </tr>
		           	</thead>  
		        </table>
		        <script type="text/javascript">
			    	function meauFormatter(value,row,index){
			    		switch (value) {
						case 'Billing':
							return '开单'
							break;
						case 'Manager':
							return '管理'
							break;
						case 'Finance':
							return '财务'
							break;
						case 'WareHouse':
							return '仓库'
							break;
						case 'Store':
							return '商城'
							break;
						case 'Setting':
							return '设置'
							break;
						default:
							break;
						}
			    	}
			    	function moduleFormatter(value,row,index){
			    		switch (value) {
						case 'Client':
							return '用户'
							break;
						case 'Card':
							return '会员卡'
							break;
						case 'Billing':
							return '开单'
							break;
						default:
							break;
						}
			    	}
			    </script>
            </div>
        </div>
    </div>

<%-- <div class="page-body" style="height:100%;">
    <div id="remind" class="ope-bar clearfix">
		<a id="css-1" class="btn" href="${pageContext.request.contextPath}/client/clientUser/showList?type=0">用户</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/userRole/showList?type=0">角色</a>
    	<a id="css-2" class="btn2 active" href="${pageContext.request.contextPath}/client/operate/showList?type=0">操作</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/dept/showList?type=0">部门</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/seatGroup/showList?type=0">坐席组</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/line/showList?type=0">线路</a>
    </div>
       	<div style="height:-moz-calc(100% - 46px);height:-webkit-calc(100% - 46px);height:calc(100% - 46px);">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
        <thead>
            <tr>
	          	<th data-options="field:'id',hidden:false" >ID</th>
	          	<th data-options="field:'pid',hidden:false" >PID</th>
	          	<th data-options="field:'text'" >名称</th>
                <th data-options="field:'grade'" >等级</th>
                <th data-options="field:'displayOrder'" >排序</th>
                <th data-options="field:'code'" >代码</th>
                <th data-options="field:'meauType',hidden:true,formatter:meauFormatter" >所属菜单</th>
                <th data-options="field:'module',hidden:true,formatter:moduleFormatter" >模块</th>
                <th data-options="field:'url'" >地址</th>
            </tr>
           </thead>  
        </table>
    </div>
    
    </div> --%>

</body>
</html>