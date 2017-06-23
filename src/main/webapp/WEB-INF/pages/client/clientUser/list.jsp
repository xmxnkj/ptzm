<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
       
</head>
<body style="background: #f6f6f6">
<script src="../../content/scripts/format/format.js"></script>
<script src="../../content/scripts/common/list.js"></script>
 
<script type="text/javascript">
	var toolbars=[];
	toolbars[0] = {iconCls:'icon-add', text:'添加', handler:function(){
		add();
	}};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:function(){
		edit();
	}};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
	function reloadData(){
		$("#tbl").datagrid("reload");
	}
	if (typeof params != 'undefined' && params.managerUser == 'true') {
		var listjsonUrl = 'managerRoleList?isUser=true';
		var editUrl = 'client/clientUser/showPage?pageName=managerForm';
		var deleteUrl = 'deleteManager';
		var addTitle="添加员工信息";
		var editTitle = "修改员工信息";
	}else {
		var listjsonUrl = 'list?status=1&isNotLimit=true';
		var editUrl = 'client/clientUser/showEdit';
		var deleteUrl = 'deleteClientUser';
		var addTitle="添加用户信息";
		var editTitle = "修改用户信息";
	}
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 520;
	function dateFormatter(value,row,index){
		if (typeof value != 'undefined' && value != '' && value != null) {
			var unixTimestamp = new Date( value )
			return unixTimestamp.Format("yyyy-MM-dd");
		}
		return value;
	}
	
	function roleformatter(value,row,index){
			return row.remark;
		}
	
	function gender(value,row,index){
		switch(row.sex){
			case 0:
				return "男";
				break;
			case 1:
				return "女";
				break;
			default:
				return "未设置";
				break;		
		}
	}
function getDept(val,row){
	if(row.dept){
		return row.dept.name;
	}
	return "未分配";
}

function refreshData(){
	$("#tbl").datagrid("reload");
}

$(function(){
	loadDeptTree();	
	loadUserRole();
});

//角色
function loadUserRole(){
	$("#UserRole").combobox({
		url:'../../client/userRole/list',
		valueField:'id',  
	    textField:'roleName',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return data.rows;
        },
        onSelect: function (node) {
        	
        }
	});
}

//搜索部门树
function loadDeptTree(){
	$("#owndept").combotree({
		url:'../../client/dept/list',
		valueField:'id',  
	    textField:'name',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return deptTree(data.rows);
        },
        onSelect: function (node) {
        	$("#deptId").val(node.id);
        },
        onLoadSuccess: function (node, data) {
        }
	});
}

//部门树
function deptTree(list){
	var maxLevel = 0;
	var arr = []; 
	
	for(var i=0;i<list.length;i++){
		//level为1的记录
		list[i].text = list[i].name;
		if(list[i].level>maxLevel){
			maxLevel = list[i].level;
		}
		var currentLevel = list[i].level;
		var currentId = list[i].id;
		if(arr.hasOwnProperty(currentLevel)){
			arr[currentLevel][currentId]=list[i];
		}else{
			arr[currentLevel] = [];
			arr[currentLevel][currentId]=list[i];
		}
	}
	
	var result = [];
	for(var i=maxLevel;i>1;i--){
			var arrSon = arr[i];
			for(var key in arrSon){
				var obj = arrSon[key];
				if(arr[obj.level-1][obj.dpid].children){
					arr[obj.level-1][obj.dpid].children.push(obj);
				}else{
					arr[obj.level-1][obj.dpid]["children"]=new Array();
					arr[obj.level-1][obj.dpid].children.push(obj);
				}
			}
	}
	for(var key in arr[1]){
		result.push(arr[1][key]);
	}
	return result;
}
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
		                <span>搜索部门：</span>
		                <input type="text" name="owndept" id="owndept">
		                <span>搜索角色:</span>
		                <input type="text" id="UserRole" name="UserRole"/>
		                <a href="#" class="button button-action button-rounded button-small" target="ifrContent" onclick="searchData()">搜索</a>
		            	<script type="text/javascript">
				            function searchData(){
				               paramsSearch = getFormData('frmCheck',params);
				         	   var deptId = $("#owndept").combotree("getValue");
				         	   var roleIds = $("#UserRole").combotree("getValue");
				         	   
				               if (deptId!="") {
				            	   paramsSearch['deptId'] = deptId;
							   }else {
								   paramsSearch['deptId'] = undefined;
							   }
				               
				               if (roleIds!="") {
				            	   paramsSearch['roleIds'] = roleIds;
							   }else {
								   paramsSearch['roleIds'] = undefined;
							   }
				               
				               $("#tbl").datagrid("reload",paramsSearch)
				             }
			            </script>
		            </div>
                </div>
                <div>
                    <a href="#" class="button button-primary button-rounded button-small">新增用户</a>
                    <a href="#" class="button button-primary button-rounded button-small">一键分配主叫号码</a>
                </div>
            </div>
            <div class="table-content">
				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
		            <thead>
			            <tr>
			                <th  data-options="field:'name'" >名称</th>
			                <th  data-options="field:'account'" >账号</th>
			                <th  data-options="field:'roleNames'" >角色</th>
			                <th  data-options="field:'birthday'" >生日</th>
			                <th  data-options="field:'idCard'">身份证</th>
			                <th  data-options="field:'sex',formatter:gender">性别</th>
			                <th  data-options="field:'address'">地址</th>
			                <th  data-options="field:'phone'" >手机</th>
			                <th  data-options="field:'email'" >邮件</th>
			                <th  data-options="field:'privateSea'" >私海容量</th>
			                <th  data-options="field:'aiCount'" >AI数</th>
			            	<th  data-options="field:'remark'" >备注</th>
			            	<th  data-options="field:'dept',formatter:getDept" >部门</th>
			            </tr>
		           	</thead>
           		</table>
            </div>
        </div>
    </div>
</body>
</html>