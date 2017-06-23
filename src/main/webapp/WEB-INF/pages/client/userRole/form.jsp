<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
 
</head>
<body class="wrap">

<!-- <script src="../../content/scripts/common/opForm.js"></script> -->
<script src="../../content/scripts/common/form.js"></script>
<script src="../../content/scripts/format/format.js"></script> 
<!-- <script src="../../content/scripts/common/formUtils.js"></script> -->
<script type="text/javascript">
	var saveUrl = "saveRole";
	var entityUrl = "entity";
	// var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	$(function(){
		if (typeof eid != 'undefined') {
			$.ajax({
	            url: '../../client/operateRole/operateRoleList',
	            data:{'userRoleId':eid},
	            type: 'post',
	            timeout: TIMEOUT,
	            success: function (data) {
	            	var operates = [];
	                var rows = data.rows;
	               	for (var i = 0; i < rows.length; i++) {
	               		/* if (rows[i].checkState == '1') {
	               			operates.push(rows[i].operate.id);
						} */
	               		operates.push(rows[i].operate.id);
					}
	               	if (operates.length > 0) {
	               		$("#operate").combotree("setValues",operates)
					}
	            },
	            error: function () {
	                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
	            }
	        });
		}
	})
	
	
	function check(){
		var form = {};
		if ($("#id").val()!='') {
			form['id'] = $("#id").val();
		}
		form ['roleName'] = $("#roleName").val();
		form ['remark'] = $("#remark").val();
		var nodes = $('#operate').combotree('tree').tree('getChecked', ['checked','indeterminate']);
 		for (var i = 0; i < nodes.length; i++) {
     		 var checkState = nodes[i].checkState;
     		 if (checkState == 'indeterminate') {
     			checkState = 0;
			 }else {
				checkState = 1;
			}
     		 var operateId = nodes[i].id; 
     		 form['operateRoles['+i+'].checkState'] = checkState;
     		 form['operateRoles['+i+'].operate.id'] = operateId;
		}
 		ajax({
            url: '/client/userRole/saveRole',
            data:form,
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
            	dealJsonResult(data, "保存成功！", null, closeWin);
            	closeWin();
              // $.messager.alert("success","保存成功!")
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
            }
        });
	}

</script>

	<nav>
        <div class="decorate"></div>
        <span>添加成员</span>
        <img src="../HTML/Images/popup-close.png"/>
    </nav>
    
   
    <div class="main">
    	 <form method="post" id="frm">
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
            <div class="InputContent">
                <p>权限名</p>
                <input type="text" name="roleName" id="roleName">
                <input style="display: none;" type="text" name="id" id="id">
            </div>
            <div class="InputContent">
                <p>权限设置</p>
                <input class="easyui-combotree" name="operate" id="operate" data-options="editable:false,width:180,loadFilter:loadFilter">
            </div>
			<script type="text/javascript">
		               	function closeWin(){
		               		//top.refreshContent();
		       	        	if (top.$("#dlg").window('close')) {}
		       	        	if (top.$("#dlg2").window('close')) {}
		       	        }
                		$(function(){
                			$("#operate").combotree({
                				url:'../../client/operate/operateTreeList',  
                				animate:true,
                				checkbox:true,
                				multiple:true,
                				lines:true, 
                				cascadeCheck:false,
                				onLoadSuccess:function(node, data){
                					$("#operate").combotree('tree').tree("collapseAll");
                				},
                				onClick:function(row){
                					
                				},
                				onBeforeExpand:function(node){
                					var childrens = node.children
                					
                				},
                				onCheck:function(node,checked){
                					var nodeChildren = node.children;
                					isChildren(nodeChildren,checked);
                					if (checked) {
                						var parent = $('#operate').combotree('tree').tree('getParent', node.target);
                						isParent(parent);
									}
                					var checkedNodes = $('#operate').combotree('tree').tree('getChecked', ['checked']);
                					debugger
                					var lists = [];
                					for (var i = 0; i < checkedNodes.length; i++) {
                						lists.push(checkedNodes[i].id);
									}
                					$("#operate").combotree("setValues",lists)
                				}
                			})
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
           						parent = $('#operate').combotree('tree').tree('getParent', parent.target);
           						isParent(parent);
           					}
                		}
                	</script> 
            <div class="InputContent">
                <p>备注</p>
               	<textarea rows="3" cols="46"  name="remark" id="remark"></textarea>
            </div>
        
         </form>
        <div class="Button">
            <a href="javascript:check()" class="button button-action button-pill">保存</a>
            <a href="" class="button button-border button-pill button-action">重置</a>
        </div>
    </div>

</body>
</html>