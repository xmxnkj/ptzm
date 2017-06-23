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
<script src="../../content/scripts/common/form.js"></script>
<script src="../../content/scripts/common/formUtils.js"></script>

<script type="text/javascript">
	var saveUrl = "saveJson";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "客户保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
	$(function(){
		$("#type").val(params['type'])
	})
	
	function check(){
		var f = $("#frm").form("validate");
		var callState = $("#callState").val();
		var inUse = $("#inUse").combobox("getValue",inUse);
		if ((callState=="Planning"||callState=="Pause")&&inUse=='0') {
			top.showAlert("计划执行中！请先取消拨打计划~");
		}else {
			if (f) {
				doSubmit();
			}
		}
		
	}
	function closeDialog(){
		top.closeDialog();
	}
	function formLoaded(data){
		if (data && !data.inUse) {
			$("#inUse").combobox("setValue","0")
		}
		
		showTimes(data);
	}
	function showTimes(obj){
			$("#operate").combotree({
				url:'../../voice/calltime/find?clientUserId=${clientUser.id}',  
				animate:true,
				checkbox:true,
				multiple:true,
				lines:true, 
				cascadeCheck:false,
				onLoadSuccess:function(node, data){
					var val = "";
					for(var i=0;i<obj.callTimeSet.length;i++){
						val+=obj.callTimeSet[i].id+",";
					}
					$("#operate").combotree("setValues", val.substring(0,val.length-1));
					
				},
				loadFilter:function(data){

					varrows=[];

					for(var i=0;i<data.entity.length;i++){
						var arr = [];
						arr["id"] = data.entity[i].id;
						arr["text"] = data.entity[i].startTime + "--" +data.entity[i].endTime;
						varrows.push(arr);
					}
					return varrows;
				},

				onCheck:function(node,checked){
					var value = $("#operate").combotree("getValues");
					$("#callTimeSets").val(value);
				}
			})
	}

</script>
<div class="wrap">
    <div class="main">
        <div class="title">
            <span>基本信息</span>
        </div>
         <form method="post" id="frm" style="width:85%; ">
        <div class="Input">
            <div class="InputContent">
                <p>计划名称</p>
                <input class="easyui-textbox"  name="name" id="name" data-options="required:true,missingMessage:'请输入计划名称！'" style="width:50%;">
            </div>
            <div class="InputContent">
                <p>语音模块</p>
               <select class="easyui-combobox"  name="voiceTemplate.id" id="voiceTemplate_id" style="width:50%;"
            			data-options="panelHeight:'auto',editable:true,required:true, valueField:'id', textField:'name',url:'../../voice/template/list',loadFilter:loadFilter" >
            	</select>
            </div>
            <div class="InputContent">
                <p>电话线路</p>
               <select class="easyui-combobox"  name="lineId" id="line_id" style="width:50%;"
            			data-options="panelHeight:'auto',editable:true,required:true, valueField:'id', textField:'lingTel',url:'../../client/line/list?isDelete=false&clientUserId=${clientUser.id }',loadFilter:loadFilter" >
            	</select>
            </div>
            <div class="InputContent">
                <p>状态</p>
                <select class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'请选择计划状态！'" style="width:50%" name="inUse" id="inUse">
                	<option value=""></option>
                	<option value="1">启用</option>
                	<option value="0">停用</option>
                </select>
            </div>
            
            <div class="InputContent">
                <p>时间设置</p>
                <input class="easyui-combotree" name="operate" id="operate" data-options="editable:false,width:180,loadFilter:loadFilter">
           		<input type="hidden" name="callTimeSets" id="callTimeSets">
            </div>

            <input type="hidden" name="callState" id="callState">
             <input type="hidden" name="id" id="id">
             <input type="hidden" name="clientUserId" id="clientUserId">
        </div>
        </form>
        <div class="Button">
            <a href="#" class="button button-action button-pill button-small" onclick="check()">保存</a>
            <a href="#" class="button button-border button-pill button-action button-small"  onclick="closeDialog()">取消</a>
        </div>
    </div>
</div>
<!-- <div class="pop" id="PerfectData" style="top:0%; width:400px; margin-left:-193px;">

      <div class="cont" style="margin-top:25px; width:100%;height:360px;">
      <form method="post" id="frm" style="width:85%; ">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="35%">计划名称</th>
                <td width="65%"><input class="easyui-textbox"  name="name" id="name" data-options="required:true,missingMessage:'请输入计划名称！'" style="width:90%;"></td>
            </tr>
            <tr>
            	<th width="35%">语音模块</th>
            	<td width="65%">
            	<select class="easyui-combobox"  name="voiceTemplate.id" id="voiceTemplate_id" style="width:90%;"
            			data-options="panelHeight:'auto',editable:true, valueField:'id', textField:'name',url:'../../voice/template/list',loadFilter:loadFilter" >
            	</select>
            	</td>
            </tr>
             <tr>
                <th width="35%">状态</th>
                <td width="65%">
	                <select class="easyui-combobox" data-options="editable:false,required:true,missingMessage:'请选择计划状态！'" style="width:100px" name="inUse" id="inUse">
	                	<option value=""></option>
	                	<option value="1">启用</option>
	                	<option value="0">停用</option>
	                </select>
	            </td>
            </tr>
            </tbody>
        </table>
          <input type="hidden" name="id" id="id">
        </form>
      </div>
      <div class="btn-box bg-gray">
	       <a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>
	  </div>
</div>       -->

</body>
</html>