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

<script src="../../content/scripts/common/list.js"></script>
<script src="../../content/scripts/format/format.js"></script>

<script type="text/javascript">
	var toolbars=[];
	toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
	 if(typeof params['managerUser'] == 'undefined') {
		toolbars[3] = {iconCls:'icon-search', text:'搜索', handler:showSearch};
	}
	function reloadData(){
		$("#tbl").datagrid("reload");
	}
	if (typeof params != 'undefined' && params.managerUser == 'true') {
		var listjsonUrl = 'managerRoleList?isUser=true';
		var editUrl = 'client/seatGroup/showPage?pageName=managerForm';
		var deleteUrl = 'deleteManager';
		var addTitle="添加员工信息";
		var editTitle = "修改员工信息";
	}else {
		var listjsonUrl = 'list?status=1';
		var editUrl = 'client/seatGroup/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle="添加用户信息";
		var editTitle = "修改用户信息";
	}
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 520;
	
	$(function(){
		initOperateTree();
	});
	
	function update(){
		var row = $("#deptTree").tree('getSelected');
		if(row){
			editUrl = 'client/seatGroup/showEdit?deptId='+row.id+"&deptName="+row.name;
			add();
		}else{
			alert("请选择一个部门！");	
		}
	}
	
	function initOperateTree(){
		$("#deptTree").tree({
	        url: "../../client/dept/list",
	        loadFilter: function (data) {
	        	return deptTree(data.rows);
	        },
	        onClick:function(node){
	        	loadSeatGroup(node.id);		//加载本部门的坐席位
	        	
	        	var query = [];
	        	query["deptId"]=node.id;
	        	query["seatId"]="0000";
	        	loadUnabsorbed(query,"UnabsorbedTable");	//加载本部门的未分配人员
	        }
	    });
	}
	//树
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
	
	//移除坐席组
	function drop(){
		var row = $('#tbl').datagrid("getSelected");
		if(!row){
			alert("请选中一行数据！");
			return;
		}
		
		$.post('drop',{"id":row.id},function(data){
			if(data.success){
				$('#tbl').datagrid("reload");
			}else{
				alert(data.message);
			}
		});
	}
	
	//坐席列表
	function loadSeatGroup(deptId){
		columns = [
	       	    	{field :'name',title:'坐席组名',width :parseInt($(this).width()*0.10),halign:'center',align:'left'},
	       	    	
	       	    ];
		$('#tbl').datagrid({
			url: "../../client/seatGroup/list",
			toolbar:[
			         {text:"增加",iconCls:"icon-add",handler:function(){
			        	 top.fwin = window;
			        	 update();
			         }},
			         {text:"修改",iconCls:"icon-remove",handler:function(){
			        	 top.fwin = window;
			        	 var row = $('#tbl').datagrid("getSelected");
			        	 if(!row){
			        		 alert("请选择一行数据！");
			        		 return;
			        	 }
			        	 var dept = $("#deptTree").tree('getSelected');
			        	 if(!dept){
			        		 alert("请选择部门！");
			        		 return;
			        	 }
			        	 editUrl = 'client/seatGroup/showPage?pageName=form&deptName='+dept.name;
			        	 edit();
			         }},
			         {text:"删除",iconCls:"icon-remove",handler:function(){drop();}}
			      ],
			queryParams: {"deptId":deptId},
			fit:true,
			striped:true,
			singleSelect:true,
			checkOnSelect:false,
			selectOnCheck:false,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:50,
			columns:[columns],
			onLoadSuccess:function(data){},
			onClickRow: function (index, row) {
				 //加载已分配的人
				 var dept = $("#deptTree").tree('getSelected');
				 if(!dept){
					alert("请选中部门！");
					return;
				 }
				 if(!row){
						alert("请选中坐席！");
						return;
				 }
				 var query = [];
				 query["deptId"]=dept.id;
				 query["seatId"]=row.id;
				 loadUnabsorbed(query,"absorbedTable");
			}
		}); 
	}
	
	//坐席内/外的成员
	function loadUnabsorbed(query,table){
		columns = [
	       	    	{field :'name',title:'成员名称',width :parseInt($(this).width()*0.10),halign:'center',align:'center'},
	       	    	{field :'dept',title:'部门',width :parseInt($(this).width()*0.10),halign:'center',align:'center',formatter:function(val,row){
	       	    		if(row.dept){
	       	    			return row.dept.name;
	       	    		}
	       	    		return "未分配";
	       	    	}},
	       	    	{field :'roleNames',title:'角色',width :parseInt($(this).width()*0.10),halign:'center',align:'center'},
	       	    ];
		$('#'+table).datagrid({
			url: "../../client/clientUser/list",
			queryParams: query,
			fit:true,
			striped:true,
			singleSelect:true,
			checkOnSelect:false,
			selectOnCheck:false,
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			pageSize:50,
			columns:[columns],
			onLoadSuccess:function(data){},
			onDblClickRow :function(rowIndex,rowData){
			   //执行移入和移出
			   var dept = $("#deptTree").tree('getSelected');
			   if(!dept){
				   alert("请选中部门！");
				   return;
			   }
			   
			  var seat = $('#tbl').datagrid('getSelected');
			   
			  if(!seat){
				   alert("请选中坐席！");
				   return;
			   }
			  
			   var row = $(this).datagrid('getSelected');
			   if(row){
				  moveOrAdd(dept.id,seat.id,row.id); 
			   }
			}
		}); 
	}

	//移出或者移入
	function moveOrAdd(deptId,seatId,clientUserIds){
		$.post("../../client/seatGroup/moveOrAdd",{"deptId":deptId,"seatId":seatId,"clientUserIds":clientUserIds},function(data){
			if(data.success){
				$('#absorbedTable').datagrid("reload");
				$('#UnabsorbedTable').datagrid("reload");	
			}else{
				alert(data.message);
			}
			
		});
	}
//本部门还未分配的人
</script>

<div class="page-body" >

   <div id="remind" class="ope-bar clearfix">
    	<%-- <a id="css-1" class="btn" href="${pageContext.request.contextPath}/client/clientUser/showList?type=0">用户</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/userRole/showList?type=0">角色</a>
    	<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/operate/showList?type=0">操作</a>
   		<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/dept/showList?type=0">部门</a>
   		<a id="css-2" class="btn2 active" href="${pageContext.request.contextPath}/client/seatGroup/showList?type=0">坐席组</a>
   		<a id="css-2" class="btn" href="${pageContext.request.contextPath}/client/line/showList?type=0">线路</a> --%>
   </div>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'west'" style="height:100%;width:20%" title="部门信息">
		<ul id="deptTree"></ul>
	</div>
	
	<div data-options="region:'center'" style="height:100%;width:30%" title="坐席组">
		
		<div id="mediationagencylist_layout" class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west'" style="width:30%" title="坐席列表">
				<table id="tbl"></table>
			</div>
			
			<div data-options="region:'center'" style="width:50%" title="人员分配">
				
				<div id="mediationagencylist_layout" class="easyui-layout" data-options="fit:true">
					<div data-options="region:'north'" style="height:50%" title="未分配人员">
						<table id="UnabsorbedTable"></table>	<!-- 未分配 -->
					</div>
				
					<div data-options="region:'south'" style="height:50%" title="已分配人员">
						<table id="absorbedTable"></table>		<!-- 已分配 -->
					</div>
				</div>	
				
			</div>
		</div>	
		
	</div>
</div>	

 </div>
</body>
</html>