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
<script src="../../content/scripts/common/list.js"></script>
<script type="text/javascript">
	var listjsonUrl = '../../wechat/wxuser/list?is'+params['type']+'=true';
	var deleteUrl = '../../wechat/wxuser/delNotice';
	
	var addTitle="添加管理员";
	var editTitle = "修改管理员资料";
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 700;
	var dlgHeight = 600;
	toolbars = [];
	toolbars[0] = {iconCls:'icon-remove', text:'删除', handler:del};
	function del(){
		var row = $('#tbl').datagrid('getSelected');
		if(row != null){
			$.messager.confirm(confirmDeleteTitle, confirmDeleteInfo, function (data) {
	            if (data) {
	                $.ajax({
	                    url: deleteUrl + '?id=' + row.id+ "&is"+params['type']+'=false',
	                    type: 'post',
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
	function sexFormat(val,row){
		if (val==1) {
			return "女";
		}else {
			return "男";
		}
	}
</script>
    <div class="page-body">
        <table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
            <thead>
            <tr>    
                <th data-options="field:'nickName'">微信名</th>
                <th data-options="field:'sex',formatter:sexFormat">性别</th>
            </tr>
           </thead>
        </table>
    </div>
 


</body>
</html>