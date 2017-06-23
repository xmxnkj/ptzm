<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script type="text/javascript">
	var listjsonUrl = 'getTreeDegrid';
	var editUrl = 'client/dept/showEdit';
	var deleteUrl = 'deleteDept';
	
	var addTitle="添加部门";
	var editTitle = "修改部门资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	
	function showAmaldar(val,row){
		return row.clientUserId;
	}
</script>
<script src="../../content/scripts/common/list.js"></script>
<script src="../../content/scripts/client/deList.js"></script>
<script src="../../content/scripts/format/format.js"></script>
    
    
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
			            	<th data-options="field:'id'">id</th>
			                <th data-options="field:'name'">名称</th>
			                <th data-options="field:'code'">编码</th>
			                <th data-options="field:'clientUserNames'">经理</th>
			                <th data-options="field:'aiCount'">AI数</th>
			                <th data-options="field:'privateSea'">私海容量</th>
			                <th data-options="field:'dpid'">pid</th>
			                <th data-options="field:'level'">等级</th>
			                <th data-options="field:'remarks'">备注</th>
			            </tr>
		           </thead>
		        </table>
            </div>
        </div>
    </div>
  
</body>
</html>