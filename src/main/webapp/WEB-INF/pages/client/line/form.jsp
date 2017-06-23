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
var saveUrl = "saveDate";
var entityUrl = "entity";
//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';

var saveSucInfo = "保存线路信息";
var nameRequire = true;
var nameMaxLength = 100;
var nameServerValidate = false;
var descriptionLengthValidate = true;
var descriptionMaxLength = 250;
var saveClearForm=true;

function formLoaded(data){
	if(!data){	//新增
		loadLeaderDept();
	}else{
		if(data.deptId){	//编辑
			loadLeaderDept(data.deptId);
			if(data.clientUser){
				loadSeats(data.deptId,data.clientUser.id);
			}else{
				loadSeats(data.deptId);
			}
		}
	}
}

//坐席
function loadSeats(deptId,defaultValue){
	$("#clientUsers").combobox({
		url:"../../client/clientUser/list?isNotLimit=true&deptId="+deptId,
		valueField:'id',  
	    textField:'text',
		loadFilter: function (data) {
			var arr = [];
			$.each(data.rows,function(index,obj){
				var son = [];
				son["id"] = obj.id;
				son["text"] = obj.name;
				arr.push(son);
			});
          return arr;
        },
        onLoadSuccess: function (node, data) {  
        	if(defaultValue){
        		$("#clientUsers").combobox("setValues",defaultValue);
        	}
        },
        onChange: function () {
        	var vals = $(this).combobox('getValues');
       		$("#clientUserId").val(vals);
        }
	});
}

function loadLeaderDept(){
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
        	loadSeats(node.id);
        },
        onLoadSuccess: function (node, data) {  
        	$("#owndept").combotree("setValue",$("#deptId").val());
        }
	});
}

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

function saveDate(){
	var lingTel = $("#lingTel").val();
		
	var deptId = $("#deptId").val();
	
	var clientUserId = $("#clientUserId").val();
	
	if(!lingTel){
		$.messager.alert("线路号码必填!");
		return;
	}
  
	$.ajax({  
        type: "POST",   //提交的方法
        url:"/client/line/saveDate", //提交的地址  
        data:$('#frm').serialize(),// 序列化表单值  
        async: false,  
        error: function(request) {  //失败的话
             alert("Connection error");  
        },  
        success: function(data) {  //成功
        	if(data.success){
        		alert("坐席已提交!");
        		top.closeDialog();
        		top.refreshContent();
    		}else{
    			alert(data.errorMessage);
    		}
        }  
     });
}

function successBack(data){
	/* if(!data.success){
		$.messager.alert("请选择坐席!");
	} */
}
//校验
</script>
	
	<nav>
        <div class="decorate"></div>
        <span>编辑成员</span>
        <img src="../HTML/Images/popup-close.png"/>
    </nav>
    
    <div class="main">
    	 <form method="post" id="frm">
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
        
            <div class="InputContent">
                <p>线路号码</p>
                <input type="text" name="lingTel" id="lingTel">
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