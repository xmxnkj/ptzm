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
	toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'清空', handler:delTest};

	function reloadData(){
		$("#tbl").datagrid("reload");
	}
	if (typeof params != 'undefined' && params.managerUser == 'true') {
		var listjsonUrl = 'getNumbers?isUser=true';
		var editUrl = 'outBound/autodialerNumber/showPage?pageName=managerForm';
		var deleteUrl = 'deleteManager';
		var addTitle="添加号码信息";
		var editTitle = "修改号码信息";
	}else {
		var listjsonUrl = 'getNumbers?status=1';
		var editUrl = 'outBound/taskNumber/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle="添加号码信息";
		var editTitle = "修改号码 信息";
	}
	
	var confirmDeleteTitle = "提示信息";
	var confirmDeleteInfo = "您确认要删除吗？";
	var deleteSuccess = "删除成功！";
	var dlgWidth = 600;
	var dlgHeight = 520;

$(function(){
	 $("#numbers").val("");
	 
	$("#numberFile").change(function(){
		upload();
	});
});

function upload(){
	var list = $("#numberFile")[0].files;
	var formData = new FormData();
	
	var outBoundTaskId = $("#outBoundTask").combotree("getValue");
	var file = document.getElementById('numberFile');
	if(!outBoundTaskId){
		alert("请选择拨打任务！");
		file.value = '';
		return;
	}
	
	if(list.length>0){
		formData.append("img",list[0]);
	}else{
		alert("请选择文件！");
		file.value = '';
		return;
	}
	
	//判断文件类型
	var type = list[0].name;
	var arr = type.split(".");
	type = arr[arr.length-1];
	if(type!='xlsx' && type!='xls'){
		alert("请选择.xlsx后缀的文件上传！");
		file.value = '';
		return;	
	}
	formData.append("outBoundTaskId",outBoundTaskId);
	
	$.ajax({  
         url: "inputNumbers",  
         type: 'POST',  
         data: formData,  
         async: false,  
         cache: false,  
         contentType: false,  
         processData: false,  
         success: function (data) {
          alert(data.msg);
          reloadData();
         },  
         error: function (returndata) {  
           alert("error");
             console.log(returndata);  
         }  
    });
}


function showOutBoundTask(val,row){
	return row.callPlan.name;
}

function delTest(){
	$.ajax({  
        url: "delTest",  
        type: 'POST',  
        async: false,   
        success: function (data) {
         reloadData();
         $("#numbers").val("");
        }
   });
}
</script>

<div class="content">
        <div class="tab">
          
        </div>
        <div class="table">
            <div class="table-function">
                <div class="num">
                    <div class="ConditionTime">
		                <span>请输入要导入的号码：</span>
		                <textarea type="text" id="numbers" name="numbers" >
		                </textarea>多个号码间用;隔开
		                <a href="#" class="button button-action button-rounded button-small" onclick="searchData()">导入</a>
							 <script type="text/javascript">
									function searchData(){
							         	   var numbers = $("#numbers").val();
							         	  $.ajax({  
							                  url: "testImport",  
							                  type: 'POST',  
							                  data: {"numbers":numbers},  
							                  async: false,   
							                  success: function (data) {
							                   reloadData();
							                   $("#numbers").val("");
							                  }
							             });
						             }
							</script>
		            </div>
                </div>
                <div>
                </div>
            </div>
            <input hidden type="file" name="numberFile" id="numberFile">
            <div class="table-content">
				<table id="tbl" width="100%" border="0" cellspacing="0" cellpadding="0" class="tb">
		            <thead>
			            <tr>
			                 <th data-options="field:'number'" >号码</th>
			            </tr>
		           </thead>
	           </table>
            </div>
        </div>
    </div>
</body>
</html>