<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/site.css">
    <script src="${pageContext.request.contextPath}/content/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/content/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/default/easyui.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/content/scripts/main/main.js"></script>



</head>
<body>
 
 <script src="../../content/scripts/common/form.js"></script>
                
<script type="text/javascript">
	var saveUrl = "saveJson";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	var area = true;
	function check(){
		var pw = top.trim($("#passWord").textbox("getValue"));
		var cpw = top.trim($("#comPassWord").textbox("getValue"));
		var loginAcount = top.trim($("#loginAccount").textbox("getValue"));
		rst = true;
		var info = "";
		if (loginAcount=="") {
			info += "请填写登录账号！"
			rst = false;
		}
		if(pw!="" || cpw!="") {
			if (pw=="") {
				info += "请输入登录密码！"
				rst = false;
			}
			if (cpw=="") {
				info += "请输入登录确认密码！"
					rst = false;
			}else {
				if (pw != cpw) {
					info += "两次密码不一致！"
						rst = false;
				}
			}
		}
		
		if (!rst) {
			top.showAlert(info);
		}
		if (rst) {
			paramsForm = genFormData("frm",null,null);
			
			if (pw!='') {
				paramsForm['loginPasswd'] = pw;
			}
			var nodes = $('#operation').combotree('tree').tree('getChecked', ['checked','indeterminate']);
			for (var i = 0; i < nodes.length; i++) {
	     		 var checkState = nodes[i].checkState;
	     		 if (checkState == 'indeterminate') {
	     			checkState = 0;
				 }else {
					checkState = 1;
				}
	     		 var operateId = nodes[i].id; 
	     		paramsForm['items['+i+'].actorId'] = checkState;
	     		paramsForm['items['+i+'].operationId'] = operateId;
			}
			/* 
			var operation = $("#operation").combobox("getValues");
			for (var i = 0; i < operation.length; i++) {
				paramsForm['items['+i+'].operationId'] = operation[i];
			} */
			ajax({
				type:'post',
				url:'saveUser',
				data:paramsForm,
				success:function(data){
					if (data.success=='true'||data.success==true) {
						top.closeDialog();
						top.refreshContent();
					}else {
						top.showAlert(data.message);
					}
					
				},
				error:function(){
					top.showAlert("网络延迟，请重试！")
				}
			})
			//doSubmit();
		}
	}
	
	$(function(){
		if (typeof eid != 'undefined' && eid!=null && eid != '') {
			$.ajax({
				type:'post',
				url:'getOperation',
				data:{'userId':eid},
				success:function(data){
					var operations = [];
	                var rows = data.rows;
	               	for (var i = 0; i < rows.length; i++) {
	               		operations.push(rows[i].operationId);
					}
	               	if (operations.length > 0) {
	               		$("#operation").combotree("setValues",operations)
					}
					
				}
			})
		}
		/* $("#contact").combobox({
			onSelect:function(value){
				$("#departmentName").combobox("setValue","");
				$("#description").combobox("setValue","");
				$("#departmentName").combobox({
					url:'../../system/baseArea/list?parentId='+value.id,
				})
			},
			onChange:function(value){
				if (area) {
					var description = $("#description").combobox("getValue");
					var departmentName = $("#departmentName").combobox("getValue");
					$("#departmentName").combobox({
						url:'../../system/baseArea/list?parentId='+value,
						onChange:function(value_s){
							if (value_s=='') {
								$("#description").combobox({
									url:'../../system/baseArea/list?parentId=000000',
								})
							}else {
								$("#description").combobox({
									url:'../../system/baseArea/list?parentId='+value_s,
								})
							}
							if (description!='') {
								$("#description").combobox("setValue",description)
								description='';
							}
						}
					})
					if (departmentName!='') {
						$("#departmentName").combobox("setValue",description)
						departmentName='';
					}
					area =false
				}
			}
		}) */
	})
</script>
 
   
   
    <div class="pop" id="PerfectData" style="top:10%; width:400px; margin-left:-280px;">
   <!--  <h2 class="title">新增</h2> -->
      <div class="cont" style="max-height: 500px">
        
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="30%">管理员账号</th>
                <td width="70%"><input class="easyui-textbox" type="text" name="loginAccount" id="loginAccount"></td>
            </tr>
            <tr>
                <th width="30%">账号密码</th>
                <td width="70%"><input class="easyui-textbox" type="password" name="passWord" id="passWord"></td>
            </tr>
            <tr>
                <th width="30%">确认密码</th>
                <td width="70%"><input class="easyui-textbox" type="password" name="comPassWord" id="comPassWord"></td>
            </tr>
            <tr>
                <th width="30%">区域</th>
                <td width="70%">
                <select class="easyui-combobox" id="contact" name="contact" data-options="editable:true,valueField:'id',textField:'name',width:180,url:'../../system/baseArea/list?parentId=00',loadFilter:loadFilter"></select>
                <!-- <select class="easyui-combobox" id="departmentName" name="departmentName" data-options="editable:false,valueField:'id',textField:'name',width:60,loadFilter:loadFilter"></select>
                <select class="easyui-combobox" id="description" name="description" data-options="editable:false,valueField:'id',textField:'name',width:60,loadFilter:loadFilter"></select> -->
                </td>
            </tr>
            <tr>
                <th width="30%">状态</th>
                <td width="70%"><select class="easyui-combobox" type="text"  name="userState" id="userState">
                	<option value=""></option>
                	<option value="Normal">启用</option>
                	<option value="Disabled">停用</option>
                </select></td>
            </tr>
            <tr>
                <th width="18%">账号权限</th>
                <td width="32%"><select class="easyui-combotree"  name="operation" id="operation" data-options="url:'../../system/operations/operationTreeList?adminId=${id }',valueField:'id', textField:'name',width:180,loadFilter:loadFilter" ></select></td>
            </tr>
            </tbody>
        </table>
		<script type="text/javascript">
			$(function(){
				$("#operation").combotree({
					animate:true,
					checkbox:true,
					multiple:true,
					lines:true, 
					cascadeCheck:false,
					onLoadSuccess:function(node, data){
						$("#operation").combotree('tree').tree("collapseAll");
					},
					onBeforeExpand:function(node){
						var childrens = node.children
					},
					onCheck:function(node,checked){
						var nodeChildren = node.children;
    					isChildren(nodeChildren,checked);
    					if (checked) {
    						var parent = $('#operation').combotree('tree').tree('getParent', node.target);
    						isParent(parent);
						}
    					var checkedNodes = $('#operation').combotree('tree').tree('getChecked', ['checked']);
    					var lists = [];
    					for (var i = 0; i < checkedNodes.length; i++) {
    						lists.push(checkedNodes[i].id);
						}
    					$("#operation").combotree("setValues",lists);
    					$('#operation').combotree('tree').tree('scrollTo', node.target);
					}
				})
				
				var nodeId = null;
        		var spanList = null;
        		function isChildren(nodeChildren,checked){
        			if (nodeChildren!=null&&nodeChildren.length>0) {
						if (checked) {
							for (var i = 0; i < nodeChildren.length; i++) {
								nodeChildren[i].checkState = "checked";
								isChildren(nodeChildren[i].children,true);
							}
						}else {
							for (var i = 0; i < nodeChildren.length; i++) {
								nodeChildren[i].checkState = "unchecked";
								nodeId = nodeChildren[i].domId;
								spanList = $("#"+nodeId).find("span");
								spanList.eq((spanList.length)-2).attr("class","tree-checkbox tree-checkbox0");
								isChildren(nodeChildren[i].children,false);
							}
						}
					}
        		}
        		function isParent(parent){
   					if (parent!=null&&parent.checkState=='unchecked') {
   						parent.checkState = "checked";
   						parent = $('#operation').combotree('tree').tree('getParent', parent.target);
   						isParent(parent);
   					}
        		}
				
				if (typeof eid != 'undefined') {
					$.ajax({
			            url: '../../system/operations/operationAceList',
			            data:{'objectId':eid},
			            type: 'post',
			            timeout: TIMEOUT,
			            success: function (data) {
			            	var operates = [];
			                var rows = data.rows;
			               	for (var i = 0; i < rows.length; i++) {
			               		if (rows[i].actorId == '1') {
			               			operates.push(rows[i].operationId);
								}
							}
			               	if (operates.length > 0) {
			               		$("#operation").combotree("setValues",operates)
							}
			            },
			            error: function () {
			                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
			            }
			        });
				}
			})
		</script>
		 <input type="hidden" name="id" id="id">
          
        </form>
      </div>
    </div>
	<div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>         
    </div>
	
</body>
</html>