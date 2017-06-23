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
var entityUrl = "getEntityById";
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
			$("#remainOriginal").html("<option value='true'>是</option><option value='false'>否</option>");
		}else{
			$("#remainOriginal").html("<option value='false'>否</option><option value='true'>是</option>");
		}
		if(data.isMainFlow){
			$("#isMainFlow").html("<option value='true'>是</option><option value='false'>否</option>");
		}else{
			$("#isMainFlow").html("<option value='false'>否</option><option value='true'>是</option>");
		}
		
		$("#keywordsString").val(data.keywordsString);
		fillForm(data,typeof(formPrex)!='undefined'?formPrex:undefined);		
	}
}

$(function(){
	/* if(eid){
		$.ajax({
	        url: "../../ivr/flow/getEntityById",
	        data:{"id":eid},
	        type: 'post',
	        success: function (data) {
	          var d = [];
	          for(var key in data){
	        	  if(data[key]){
	        		  d[key] = data[key];
	        	  }
	          }
	          formLoaded(d);
	        }
	    });
	} */
});

$(function(){
	loadFlowTemplate();
});

function saveDate(){
	doSubmit();
}

function successBack(data){
	
}

//模板
function loadFlowTemplate(){
	$("#flowTemplateList").combobox({
		url:'../../ivr/flowTemplate/list',
		valueField:'id',  
	    textField:'name',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return data.rows;
        },
        onSelect: function (node) {
        	$("#flowTemplateId").val(node.id);
        },
        onLoadSuccess:function(data){  
        	var defaultVal = $("#flowTemplateId").val();
        	if(defaultVal){
        		$(this).combobox("setValue",defaultVal);
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
               	<input type="text" id="flowTemplateList" name="flowTemplateList"/>
               	<input hidden type="text" id="flowTemplateId" name="flowTemplateId"/>
            </div>
            
            <div class="InputContent">
                <p>应答</p>
                <input type="text" name="answer" id="answer">
            </div>
            
             <div class="InputContent">
                <p>语音</p>
                <input type="text" name="sound" id="sound" />
            </div>
            
             <div class="InputContent">
                <p>关键字</p>
                <input type="text" name="keywordsString" id="keywordsString" />
            </div>
            
            <div class="InputContent">
                <p>是否为主流程</p>
                <select class="easyui-combobox" id="isMainFlow" name="isMainFlow">
                		<option value="true">是</option>
                		<option value="false">否</option>
                </select>
            </div>
            
            <div class="InputContent">
                <p>是否返回主流程</p>
                <select class="easyui-combobox" id="remainOriginal" name="remainOriginal">
                		<option value="true">是</option>
                		<option value="false">否</option>
                </select>
            </div>
            <div class="InputContent">
                <p>是否打断</p>
                <input type="checkbox" name="blockAsr" id="blockAsr" style="width:20px"><label for="blockAsr">打断</label>
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