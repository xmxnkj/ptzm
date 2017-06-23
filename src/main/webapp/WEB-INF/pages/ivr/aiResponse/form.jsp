<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="../../Css/purge.css">
    <link rel="stylesheet" href="../../Css/Pagestyle.css">
    <link rel="stylesheet" href="../../Css/buttons.css">
    <link rel="stylesheet" href="../../Css/Popup.css">
   
</head>
<body class="wrap">

    <script src="../../content/scripts/common/form.js"></script>
	<script src="../../content/scripts/common/formUtils.js"></script>
	
<script type="text/javascript"> 
var saveUrl = "saveJson";
var entityUrl = "entity";
var saveSucInfo = "保存流程信息";
var nameRequire = true;
var nameMaxLength = 100;
var nameServerValidate = false;
var descriptionLengthValidate = true;
var descriptionMaxLength = 250;
var saveClearForm=true;

function formLoaded(data){
	if(data){
		if(data.remainOriginal){
			$("#remainOriginal").html("<option value='true' selected='selected'>是</option><option value='false'>否</option>");
		}else{
			$("#remainOriginal").html("<option value='false' selected='selected'>否</option><option value='true'>是</option>");
		}
		if(data.isMainFlow){
			$("#isMainFlow").html("<option value='true' selected='selected'>是</option><option value='false'>否</option>");
		}else{
			$("#isMainFlow").html("<option value='false' selected='selected'>否</option><option value='true'>是</option>");
		}
		
		$("#keywordsString").val(data.keywordsString);
		fillForm(data,typeof(formPrex)!='undefined'?formPrex:undefined);		
	}
}

$(function(){
	loadFlowTemplate();
	loadTemplate();
});

function saveDate(){
	doSubmit();
}

function successBack(data){
	
}

//模板
function loadFlowTemplate(){
	$("#flowList").combobox({
		url:'../../ivr/flow/list',
		valueField:'id', 
	    textField:'code',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return data.rows;
        },
        onSelect: function (node) {
        	$("#flowId").val(node.id);
        	
        	$("#flowCode").val(node.code);
        	
        	$("#templateId").val(node.flowTemplateId);
        	
        	$("#flowTemplate").combobox("setValue",node.flowTemplateId);
        },
        onLoadSuccess:function(data){  
        	var defaultVal = $("#flowId").val();
        	if(defaultVal){
        		$(this).combobox("setValue",defaultVal);
        	}
        }
	});
}

//模板
function loadTemplate(){
	$("#flowTemplate").combobox({
		url:'../../ivr/flowTemplate/list',
		valueField:'id',  
	    textField:'name',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return data.rows;
        },
        onSelect: function (node) {
        	$("#templateId").val(node.id);
        },
        onLoadSuccess:function(data){  
        	var templateId = $("#templateId").val();
        	if(templateId){
        		$(this).combobox("setValue",templateId);
        	}
        }
	});
}

</script>

    <div class="main">
    	 <form method="post" id="frm" >
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
            <div class="InputContent">
                <p>流程代码</p>
                <input type="text" name="code" id="code">
            </div>
             <div class="InputContent">
                <p>所属流程模板</p>
               	<input type="text" id="flowTemplate" name="flowTemplate"/>
               	<input hidden type="text" id="templateId" name="templateId"/>
            </div>
            
           <div class="InputContent">
                <p>所属流程</p>
               	<input type="text" id="flowList" name="flowList"/>
               	<input hidden type="text" id="flowId" name="flowId"/>
            </div>
			
			<div class="InputContent">
                <p>下一步代码</p>
                <input type="text" name="nextStepCode" id="nextStepCode" />
            </div>
            
             <div class="InputContent">
                <p>流程编号</p>
                <input type="text" name="flowCode" id="flowCode" />
            </div>
            
            <div class="InputContent">
                <p>应答</p>
                <input type="text" name="answer" id="answer">
            </div>
            
            
            <div class="InputContent">
                <p>是否为原</p>
                <select class="easyui-combobox"  id="remainOriginal" name="remainOriginal">
                		<option value="true" >是</option>
                		<option value="false">否</option>
                </select>
            </div>
            
            <div class="InputContent">
                <p>回主流程应答</p>
                <input type="text" name="remainOriginalAnswer" id="remainOriginalAnswer">
            </div>
            
            <div class="InputContent">
                <p>语音</p>
                <input type="text" name="sound" id="sound">
            </div>
            
            <div class="InputContent">
                <p>原语音</p>
                <input type="text" name="remainOriginalSound" id="remainOriginalSound">
            </div>

             <div class="InputContent">
                <p>关键字</p>
                <input type="text" name="keywordsString" id="keywordsString" />
            </div>
            
            <div class="InputContent">
                <p>是否重复</p>
                <select class="easyui-combobox" id="repeat" name="repeat">
                		<option value="true">是</option>
                		<option value="false">否</option>
                </select>
            </div>
        </div>
        <input hidden id="id" name="id" >
         </form>
        <div class="Button">
            <a href="javascript:saveDate()" class="button button-action button-pill">保存</a>
            <a href="" class="button button-border button-pill button-action">重置</a>
        </div>
    </div>

</body>

</html>