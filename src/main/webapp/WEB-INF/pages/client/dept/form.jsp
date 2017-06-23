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
var saveUrl = "saveDept";
var entityUrl = "entity";
//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';

var saveSucInfo = "保存部门信息";
var nameRequire = true;
var nameMaxLength = 100;
var nameServerValidate = false;
var descriptionLengthValidate = true;
var descriptionMaxLength = 250;
var saveClearForm=true;

function formLoaded(data){
	if(data){
		loadManager(data.clientUserId,eid);
	}else{
		loadManager();
	}
	loadLeaderDept();
}

//默认经理
function loadManager(clientUserId,eid){
	$("#manager").combobox({
		url:"../../client/clientUser/list?isNotLimit=true&"+(eid?("deptId="+eid):("notDept=true")),
		valueField:'id',  
	    textField:'text',
	    multiple:true,
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
        	$("#manager").combobox("setValues",$("#clientUserId").val().split(","));
        },
        onChange: function () {
        	var vals = $(this).combobox('getValues');
        	var temp = "";
        	for(var i=0;i<vals.length;i++){
        		if(vals[i]){
        			temp+=vals[i]+",";
        		}
        	}
       		if(vals.length>0){
       			$("#clientUserId").val(temp.substring(0,temp.length-1));
       		}else{
       			$("#clientUserId").val("");
       		}
        }
	});
}

function loadLeaderDept(){
	$("#leaderDept").combotree({
		url:'../../client/dept/list',
		valueField:'id',  
	    textField:'name',
	    editable: true,   //编辑，支持模糊查询
		loadFilter: function (data) {
          return deptTree(data.rows);
        },
        onSelect: function (node) {
        	$("#dpid").val(node.id);
        	$("#level").val(node.level+1);
        },
        onLoadSuccess: function (node, data) {  
        	$("#leaderDept").combotree("setValue",$("#dpid").val().split(","));
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

function refreshList(){
	top.refreshContent();
}
</script>
	
	<nav>
        <div class="decorate"></div>
        <span>编辑部门</span>
        <img src="../HTML/Images/popup-close.png"/>
    </nav>
    
    <div class="main">
    	 <form method="post" id="frm">
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
            <div class="InputContent">
                <p>部门名称</p>
                <input type="text" name="name" id="name">
            </div>

            <div class="InputContent">
                <p>部门经理</p>
                <input class="easyui-combotree" name="manager" id="manager" />
                <input hidden id="clientUserId" name="clientUserId"  >
            </div>
 			
 			<div class="InputContent">
                <p>私海容量</p>
                <input type="text" name="privateSea" id="privateSea">
            </div>
            
            <div class="InputContent">
                <p>AI数量</p>
                <input type="text" name="aiCount" id="aiCount">
            </div>
            
            <div class="InputContent">
                <p>上级部门</p>
                <input type="text" name="leaderDept" id="leaderDept">
                 <input hidden type="text" name="dpid" id="dpid">
            </div>
            
            <div class="InputContent">
                <p>等级</p>
                <input type="text" name="level" id="level" value="1">
            </div>
            
            <div class="InputContent">
                <p>备注</p>
                <input type="text" name="remarks" id="remarks">
            </div>
            
            <input hidden type="text" name="id" id="id">
         </form>
        <div class="Button">
            <a href="javascript:doSubmit();" class="button button-action button-pill">保存</a>
            <a href="" class="button button-border button-pill button-action">重置</a>
        </div>
    </div>
</body>

</html>