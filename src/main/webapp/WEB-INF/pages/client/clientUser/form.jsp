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
	<script src="../../content/scripts/client/clientForm.js"></script>  
<script type="text/javascript">
var saveUrl = "saveData";
var entityUrl = "entity";
//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';

var saveSucInfo = "保存用户信息";
var nameRequire = true;
var nameMaxLength = 100;
var nameServerValidate = false;
var descriptionLengthValidate = true;
var descriptionMaxLength = 250;
var saveClearForm=true;


function choose_Sex(val){
	$("#sex").val(val);
	loadLeaderDept();
}

function formLoaded(data){

	if(data){
		if(data.sex==1){
			$("#girl").click();
		}else{
			$("#boy").click();
		}
		roleList(data.roleIds);
	}else{
		$("#boy").click();
		roleList();
	}
	
}

function roleList(roleIds){
	$.post('/client/userRole/list',function(data){
		var json = [];
		var son ;
		$.each(data.rows,function(index,obj){
			son = [];
			son["id"] = obj.id;
			son["text"] = obj.roleName;
			json.push(son);
		});
		
		$('#rolerList').combobox({  
		    data:json,  
		    valueField:'id',  
		    textField:'text',
		    multiple:true, 
		    formatter:function(row){  
                var opts=$(this).combobox('options');  
                return row.text;    
            },
            onSelect: function(row){
            	initComtree(this);
            },
            onUnselect: function (row) {
            	initComtree(this);
            }
		});
		if(roleIds){
			$('#rolerList').combobox('setValues',roleIds.split(','));
		}		
	});
}

function initComtree(own){
	$("#roleNames").val($(own).combobox('getText'));
	$("#roleIds").val($(own).combobox('getValues'));
}

function saveForm(){
	var account = $("#account").val();
	if(!account){
		$.messager.alert("操作提示", "登陆账号必填！");  
		return;
	}
	$.ajax({
        //几个参数需要注意一下
            type: "POST",//方法类型
            dataType: "json",//预期服务器返回的数据类型
            url: "../../client/clientUser/saveDate" ,//url
            data: $('#frm').serialize(),
            success: function (result) {
            	if(result.success){
            		$.messager.alert("数据提交成功！");
            		top.closeDialog();
            	}else{
            		$.messager.alert(result.message);
            	}
            	top.refreshContent();
            },
            error : function() {
            	$.messager.alert("异常！");
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
function successBack(data){
	debugger;
	alert(data);
}
</script>

    
   <div class="wrap">
    <div class="main">
    	 <form method="post" id="frm">
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
            <div class="InputContent">
                <p>姓名</p>
                <input type="text" name="name" id="name">
            </div>
            <div class="InputContent">
                <p>性别</p>
                <input type="radio" name="chooseSex" id="boy" checked="checked" onclick="choose_Sex('0')">男 <br>
                <input type="radio" onclick="choose_Sex('1')" name="chooseSex" id="girl">女
                <input hidden value="" id="sex" name="sex">
            </div>
            <div class="InputContent">
                <p>角色</p>
                <input id="rolerList" name="rolerList"  >
                	<input hidden id="roleIds" name="roleIds"  >
            </div>
            
            <div class="InputContent">
                <p>当前职位角色</p>
               	<input id="roleNames" name="roleNames" >
            </div>
            
            <div class="InputContent">
            	<p>生日</p>
            	<input class="easyui-datebox" data-options="editable:false" name="birthday" id="birthday">
            </div>
            
            <div class="InputContent">
            	<p>地址</p>
            	<input type="text" name="address" id="address">
            </div>
            
            <div class="InputContent">
            	<p>邮件</p>
            	<input type="text" name="email" id="email">
            </div>
            
        </div>
        <div class="title">
            <span>帐号信息</span>
        </div>
        <div class="Input">
           
            <div class="InputContent">
                <p>账号</p>
                <input type="text" name="account" id="account">
            </div>
            
            <div class="InputContent">
                <p>密码</p>
                <input type="text" name="passwd" id="passwd">
            </div>
            
             <div class="InputContent">
                <p>身份证</p>
                <input class="inp2 easyui-validatebox" data-options="required:true,missingMessage:'请输入身份证!'" type="text" name="idCard" id="idCard">
            </div>
            <div class="InputContent">
                <p>私海容量</p>
                <input type="text" name="privateSea" id="privateSea">
            </div>
           	
           	<div class="InputContent">
                <p>AI数</p>
                <input type="text" name="aiCount" id="aiCount">
            </div>
            
            <div class="InputContent">
                <p>所属部门</p>
                <input type="text" name="owndept" id="owndept">
                <input hidden type="text" name="deptId" id="deptId">
            </div>
           	
            <div class="InputContent">
                <p>备注</p>
                <input type="text" name="remark" id="remark">
            </div>
			 <input type="text" hidden name="id" id="id">
			 <input type="text" hidden name="deleted" id="deleted" value="0">
        </div>
        
        </form>
        <div class="Button">
            <a href="javascript:saveForm()" class="button button-action button-pill">保存</a>
            <a href="" class="button button-border button-pill button-action">重置</a>
        </div>
    </div>
	</div>
</body>

</html>