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
	var saveUrl = "saveMeal";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	function check(){
		var form = {};
		if ($("#id").val()!='') {
			form['id'] = $("#id").val();
		}
		form ['name'] = $("#name").val();
		form ['yearPrice'] = $("#yearPrice").numberbox("getValue");
		form ['mealState'] = true;//$('input[name="mealState"]:checked').val();
		form ['shopAmount'] = $("#shopAmount").numberbox("getValue");
		var nodes = $('#operate').combotree('tree').tree('getChecked', ['checked','indeterminate']);
 		for (var i = 0; i < nodes.length; i++) {
     		 var checkState = nodes[i].checkState;
     		 if (checkState == 'indeterminate') {
     			checkState = 0;
			 }else {
				checkState = 1;
			}
     		 var operateId = nodes[i].id; 
     		 var operateName = nodes[i].text;
     		 form['operateMeals['+i+'].checkState'] = checkState;
     		 form['operateMeals['+i+'].operate.id'] = operateId;
     		 form['operateMeals['+i+'].operateName'] = operateName;
		}
 		ajax({
            url: saveUrl,
            data:form,
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
            	dealJsonResult(data, "保存成功！", null, top.closeDialog());
            	top.refreshContent();
              // $.messager.alert("success","保存成功!")
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
            }
        });
	}
	$(function(){
		if (typeof eid != 'undefined' && eid!=null && eid!='') {
			//载入权限
			$.ajax({
	            url: '../../system/operation/operateRoleList',//../../client/operateRole/operateRoleList
	            data:{'clientMealId':eid},
	            type: 'post',
	            timeout: TIMEOUT,
	            success: function (data) {
	            	var operates = [];
	                var rows = data.rows;
	                var text = "";
	               	for (var i = 0; i < rows.length; i++) {
	               		if (rows[i].checkState == '1') {
	               			operates.push(rows[i].operate.id);
						} 
	               		//operates.push(rows[i].operate.id);
					}
	               	if (operates.length > 0) {
	               		$("#operate").combotree("setValues",operates);
					}
	            },
	            error: function () {
	                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
	            }
	        });
		}
		if (typeof params != 'undefined' && typeof params['look'] != 'undefined') {
			$("#hide").hide();
		}
	})
</script>
    <div class="pop" id="PerfectData" style="top:10%; width:400px; margin-left:-280px;">
      <div class="cont" style="max-height: 500px">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
	            <tr>
	                <th width="30%">套餐名称</th>
	                <td width="70%"><input class="inp2" type="text" name="name" id="name"></td>
	            </tr>
	            <tr>
	                <th width="35%">套餐内容</th>
	                <td width="65%">
	                    <input class="easyui-combotree" name="operate" id="operate" data-options="editable:false,width:180,loadFilter:loadFilter">
	                	<script type="text/javascript">
	                		$(function(){
	                			$("#operate").combotree({
	                				url:'../../client/operate/systemOperateTreeList?only=1',  
	                				animate:true,
	                				checkbox:true,
	                				multiple:true,
	                				lines:true, 
	                				//cascadeCheck:false,
	                				onLoadSuccess:function(node, data){
	                					$("#operate").combotree('tree').tree("collapseAll");
	                				},
	                				onClick:function(row){
	                					
	                				},
	                				onBeforeExpand:function(node){
	                					var childrens = node.children
	                					
	                				},
	                				onCheck:function(node,checked){
	                					/* var nodeChildren = node.children;
	                					isChildren(nodeChildren,checked);
	                					if (checked) {
	                						var parent = $('#operate').combotree('tree').tree('getParent', node.target);
	                						isParent(parent);
										}
	                					var checkedNodes = $('#operate').combotree('tree').tree('getChecked', ['checked']);
	                					var lists = [];
	                					for (var i = 0; i < checkedNodes.length; i++) {
	                						lists.push(checkedNodes[i].id);
										}
	                					$("#operate").combotree("setValues",lists);
	                					$('#operate').combotree('tree').tree('scrollTo', node.target); */
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
	                </td>
	            </tr>
	            <tr>
	                <th width="30%">门店限制</th>
	                <td width="70%"><input class="easyui-numberbox"  name="shopAmount" id="shopAmount" 
	                    data-options="width:180,min:0,precision:0"></td>
	            </tr>
	            <tr>
	                <th width="30%">年费价格</th>
	                <td width="70%"><input class="easyui-numberbox"  name="yearPrice" id="yearPrice" 
	                    data-options="width:180,min:0,precision:2"></td>
	            </tr>
	           <!--  <tr>
	                <th width="30%">套餐状态</th>
	                <td width="70%">启用<input type="radio" name="mealState" id="mealState" value="true">
	               					 停用<input type="radio" name="mealState" id="mealState" value="false"></td>
	            </tr> -->
            </tbody>
        </table>

		 <input type="hidden" name="id" id="id">
          
        </form>
      </div>
    </div>
	<div class="btn-box bg-gray" id="hide">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>         
    </div>

</body>
</html>