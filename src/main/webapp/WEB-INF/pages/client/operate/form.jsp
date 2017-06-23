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
		
		<link rel="stylesheet" href="../../Css/purge.css">
    	<link rel="stylesheet" href="../../Css/Pagestyle.css">
    	<link rel="stylesheet" href="../../Css/buttons.css">
    	<link rel="stylesheet" href="../../Css/Popup.css">
    
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
<body class="wrap">

<script src="../../content/scripts/client/opForm.js"></script>
<script src="../../content/scripts/common/formUtils.js"></script>
<script src="../../content/scripts/common/form.js"></script>
<script type="text/javascript">
	var saveUrl = "saveOperate";
	var entityUrl = "entity";
	// var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
	
	function formLoaded(data){
		if(data){
			if(data.grade>1){
				$("#pid").combotree('setValue',data.pid);
			}
		}
	}
	
</script>
        
    <nav>
        <div class="decorate"></div>
        <span>编辑成员</span>
        <img src="../../HTML/Images/popup-close.png"/>
    </nav>  
        
        
    <div class="main">
    	 <form method="post" id="frm">
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
            <div class="InputContent">
                <p>权限名称</p>
                <input type="text" name="text" id="text">
                    <input style="display: none;" type="text" name="id" id="id">
            </div>
            
            <div class="InputContent">
                <p>所属</p>
               	<input class="easyui-combotree" name="pid" id="pid" data-options="editable:false,width:194,height:26,loadFilter:loadFilter">
            	<script type="text/javascript">
                	  function closeWin(){
                     	//	top.refreshContent();
             	        	if (top.$("#dlg").window('close')) {}
             	        	if (top.$("#dlg2").window('close')) {}
             	        }
                		$(function(){
                			$("#pid").combotree({
                				lines:true, 
                				url:'../../client/operate/operateTreeList',  
                				animate:true,
                				onLoadSuccess:function(node, data){
                					$("#pid").combotree('tree').tree("collapseAll");
                					if (typeof eid != 'undefined') {
                						$.ajax({
                				            url: 'operateListJudge',
                				            data:{'pid':eid},
                				            type: 'post',
                				            timeout: TIMEOUT,
                				            success: function (data) {
                				                if (data.rows.length > 0) {
                				                	//$("#pid").combotree("disable");
                            						return
												}
                				            },
                				            error: function () {
                				                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
                				            }
                				        });
                						var node = $('#pid').combotree("tree").tree('getSelected');
                    					if (node) {
                    						$("#pid").combotree("tree").tree("expandTo",node.target);
                    						$("#pid").combotree("tree").tree("scrollTo",node.target);
                    						var childrens = $("#pid").combotree("tree").tree("getChildren",node.target);
                        					for (var i = 0; i < childrens.length; i++) {
        										if (childrens[i].id == eid) {
        											/* if($("#pid").combotree("tree").tree("getChildren",childrens[i].target)!= ''){
        												$("#pid").combotree("disable");
        											}; */
        											$("#pid").combotree("tree").tree("remove",childrens[i].target);
        										}
        									} 
    									}
                						
									}
                					if (typeof params['eid'] =='undefined') {
                						if (typeof params['rowId']!='undefined') {
                							$("#grade").val(params['grade']);
                    			        	$("#pid").combotree("setValue", params['rowId']);
                    					}
									}
                				},
                				onClick:function(row){
                					$("#grade").val(row.grade+1);
                				},
                				onBeforeExpand:function(node){
                					/* var childrens = node.children
                					for (var i = 0; i < childrens.length; i++) {
										if (childrens[i].id == eid) {
											$("#pid").combotree("tree").tree("remove",childrens[i].target);
										}
									} */
                				},
                				onShowPanel:function(){
                					//$("#pid").combotree("reload");
                				}
                			})
                		})
                	</script> 
            </div>
            
            <div class="InputContent">
            	<p>url</p>
            	<input type="text" name="url" id="url">
            </div>
            
             <div class="InputContent">
            	<p>等级</p>
            	<input type="text" name="grade" id="grade">
            </div>
            
            <div class="InputContent">
            	<p>排序</p>
            	<input  class="easyui-numberbox" data-options="width:194,height:26,min:0" name="displayOrder" id="displayOrder" value="">
            </div>
            <div class="InputContent">
            	<p>脚本/链接</p>
            	 <select  class="easyui-combobox" name="isScript" id="isScript" data-options="width:194,height:26,editable:false">
                    	<option value="1">脚本</option>
                    	<option value="0">链接</option>
                 </select>
            </div>
            
             <div class="InputContent">
            	<p>代码</p>
				<input  class="easyui-textbox"  data-options="width:194,height:26"  name="code" id="code" >
            </div>
			
			<div class="InputContent">
            	<p>css</p>
				<input  class="easyui-textbox"  data-options="width:194,height:26"  name="css" id="css" >
            </div>
            
            <div class="InputContent">
            	<p>是否显示</p>
				<select  class="easyui-combobox" name="isShow" id="isShow" data-options="width:194,height:26,editable:false">
                    	<option value="1">显示</option>
                    	<option value="0">不显示</option>
                    </select>
            </div>
            
        </div>
         </form>
        <div class="Button">
            <a href="javascript:doSubmit()" class="button button-action button-pill">保存</a>
            <a href="" class="button button-border button-pill button-action">重置</a>
        </div>
    </div>
        
        
<!--         
        
    <div class="pop" id="PerfectData" style="top:0%; width:400px; margin-left:-190px;">
      <div class="cont" style="margin-top:25px; width:100%;height:380px;">
        <form method="post" id="frm" style="width:85%;">
       <a onclick="text_1()">text</a>
        <script type="text/javascript">
         	function text_1(){
        		alert($("#pid").combotree("getValue"))
        	} 
        </script>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="35%">权限名称</th>
                <td width="65%">
                    <input class="inp2" type="text" name="text" id="text">
                    <input style="display: none;" type="text" name="id" id="id">
                </td>
            </tr>
             <tr>
                <th width="35%">所属</th>
                <td width="65%">
                	<input style="display: none;" type="text" name="pid" id="pid">
                	 
                    <input class="easyui-combotree" name="pid" id="pid" data-options="editable:false,width:194,height:26,loadFilter:loadFilter">
                	<script type="text/javascript">
                	  function closeWin(){
                     	//	top.refreshContent();
             	        	if (top.$("#dlg").window('close')) {}
             	        	if (top.$("#dlg2").window('close')) {}
             	        }
                		$(function(){
                			$("#pid").combotree({
                				lines:true, 
                				url:'../../client/operate/operateTreeList',  
                				animate:true,
                				onLoadSuccess:function(node, data){
                					$("#pid").combotree('tree').tree("collapseAll");
                					if (typeof eid != 'undefined') {
                						$.ajax({
                				            url: 'operateListJudge',
                				            data:{'pid':eid},
                				            type: 'post',
                				            timeout: TIMEOUT,
                				            success: function (data) {
                				                if (data.rows.length > 0) {
                				                	//$("#pid").combotree("disable");
                            						return
												}
                				            },
                				            error: function () {
                				                $.messager.alert(WINDOW_CAPTION, '网络原因导致失败！', 'error');
                				            }
                				        });
                						var node = $('#pid').combotree("tree").tree('getSelected');
                    					if (node) {
                    						$("#pid").combotree("tree").tree("expandTo",node.target);
                    						$("#pid").combotree("tree").tree("scrollTo",node.target);
                    						var childrens = $("#pid").combotree("tree").tree("getChildren",node.target);
                        					for (var i = 0; i < childrens.length; i++) {
        										if (childrens[i].id == eid) {
        											/* if($("#pid").combotree("tree").tree("getChildren",childrens[i].target)!= ''){
        												$("#pid").combotree("disable");
        											}; */
        											$("#pid").combotree("tree").tree("remove",childrens[i].target);
        										}
        									} 
    									}
                						
									}
                					if (typeof params['eid'] =='undefined') {
                						if (typeof params['rowId']!='undefined') {
                							$("#grade").val(params['grade']);
                    			        	$("#pid").combotree("setValue", params['rowId']);
                    					}
									}
                				},
                				onClick:function(row){
                					$("#grade").val(row.grade+1);
                				},
                				onBeforeExpand:function(node){
                					/* var childrens = node.children
                					for (var i = 0; i < childrens.length; i++) {
										if (childrens[i].id == eid) {
											$("#pid").combotree("tree").tree("remove",childrens[i].target);
										}
									} */
                				},
                				onShowPanel:function(){
                					//$("#pid").combotree("reload");
                				}
                			})
                		})
                	</script> 
                </td>
            </tr>
            <tr>
                <th width="35%">url</th>
                <td width="65%">
                    <input class="inp2" type="text" name="url" id="url" >
                </td>
            </tr>
            <tr>
                <th width="35%">等级</th>
                <td width="65%">
                    <input readonly="readonly" class="inp2"  type="text" name="grade" id="grade" value ="1">
                </td>
            </tr>
             <tr>
                <th width="35%">排序</th>
                <td width="65%">
                    <input  class="easyui-numberbox" data-options="width:194,height:26,min:0" name="displayOrder" id="displayOrder" value="">
                </td>
            </tr>
             <tr>
                <th width="35%">链接/脚本</th>
                <td width="65%">
                    <select  class="easyui-combobox" name="isScript" id="isScript" data-options="width:194,height:26,editable:false">
                    	<option value="1">脚本</option>
                    	<option value="0">链接</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th width="35%">菜单</th>
                <td width="65%">
                    <select  class="easyui-combobox" name="meauType" id="meauType" data-options="width:194,height:26,editable:false">
                    	<option value="Billing">开单</option>
                    	<option value="Manager">管理</option>
                    	<option value="Finance">财务</option>
                    	<option value="WareHouse">仓库</option>
                    	<option value="Store">商城</option>
                    	<option value="Setting">设置</option>
                    </select>
                </td>
            </tr>
             <tr>
                <th width="35%">代码</th>
                <td width="65%">
                    <input  class="easyui-textbox"  data-options="width:194,height:26"  name="code" id="code" >
                </td>
            </tr>
            <tr>
                <th width="35%">css</th>
                <td width="65%">
                    <input  class="easyui-textbox"  data-options="width:194,height:26"  name="css" id="css" >
                </td>
            </tr>
             <tr>
                <th width="35%">是否显示</th>
                <td width="65%">
                    <select  class="easyui-combobox" name="isShow" id="isShow" data-options="width:194,height:26,editable:false">
                    	<option value="1">显示</option>
                    	<option value="0">不显示</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
          	                                                  
        </form>
      </div>
      <div class="btn-box bg-gray">
          <a class="btn2 blue" id="PerfectDataSubmit"  onclick="doSubmit();">提交  </a>
      </div>
    </div> -->


</body>
</html>