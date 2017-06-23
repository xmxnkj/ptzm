<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<body >
<script src="../../content/scripts/format/format.js"></script>
<!-- <script src="../../content/scripts/common/form.js"></script> -->
                
<script type="text/javascript">
var saveUrl = "saveJson";
var entityUrl = "entity";
//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
var saveSucInfo = "数据保存成功！";
var nameRequire = true;
var nameMaxLength = 100;
var nameServerValidate = false;
var descriptionLengthValidate = true;
var descriptionMaxLength = 250;
var saveClearForm=true;
$(function(){
	loadForm();
	$("#_name").focus();
});

function doSubmits(){
	$("#frms").form("submit");
}


function loadForm(){
	if(typeof(entityUrl)!='undefined'
		&& typeof(eid)!='undefined'
		&& eid!=''){
		if(typeof(params)=='undefined'){
			params={};
		}
		params["id"]=eid;
		$.ajax({
            url: entityUrl,
            data:params,
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
            	if (data.isAllow == false) {
            		 data.isAllow = "0";
				}
                if (data.isChangeShop == false) {
                	 data.isChangeShop = "0";
				}
                fillForm(data,typeof(formPrex)!='undefined'?formPrex:undefined);
               /*  $("#managerName").combobox("setValue",data.name) */
                $.ajax({
		            url: '../../client/clientAndRole/clientRolelist',
		            data:{'clientUserId':data.id},//
		            type: 'post',
		            timeout: TIMEOUT,
		            success: function (data) {
		            	var roles = [];
		                var rows = data.rows;
		               	for (var i = 0; i < rows.length; i++) {
		               		roles.push(rows[i].userRole.id);
						}
		               	if (roles.length > 0) {
		               		$("#userRole").combobox("setValues",roles);
						}
		            },
		            error: function () {
		                $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
		            }
		        });
                if(typeof(formLoaded)=='function'){
                	formLoaded(data);
                }
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
            }
        });
	}else{
		if(typeof(formLoaded)=='function'){
        	formLoaded();
        }
	}
}

	function check(){
		var rst = true;
		var info = "";
		var params = {};
		var password = $("#passwd").textbox("getValue");
		var comPassword = $("#comPasswd").textbox("getValue");
		var md5Password = $("#md5Passwd").val();
		var account =  $("#account").textbox("getValue").replace(/(^\s*)|(\s*$)/g, "");
		if ($("#id").val() == '') {
			info += "请选择一个用户！<br>";
			rst = false;
		}
		if (account == '') {
	    	info += "请输入登陆账号！<br>";
	    	rst = false;
		}else {
			if ($("#judgeAccount").val()=="0") {
				info += "账号重复，请确认！<br>";
		    	rst = false;
			}
		}
		if ($("#id").val()!='') {
			if (password!='' && comPassword == '') {
				info += "请输入确认密码！<br>";
				rst = false;
			}
		}else {
			if (password == '') {
				info += "请输入登陆密码！<br>";
				rst = false;
			}else {
				if (comPassword == '') {
					info += "请输入确认密码！<br>";
					rst = false;
				}
			}
		}
	    if (password != '' && password!=comPassword) {
			info += "两次密码不同，请重新确认！<br>";
			rst = false;
		}
	    info = info.substring(0,info.length-4);
		if (rst) {
			params.account = account;
			params.passwd = password;
			params.describes = $("#describes").val();
			params.isChangeShop = $("#isChangeShop").combobox("getValue");
			params.isAllow = $("#isAllow").combobox("getValue");
			params.id = $("#id").val();
			params.isManager = $("#isManager").val();
			var roles = $("#userRole").combobox("getValues");
			var reg= /^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$/;
			var sum = 0;
			for (var i = 0; i < roles.length; i++) {
				var r = roles[i].match(reg); 
				if (r) {
					params['items['+sum+'].id'] = roles[i];
				}
				sum++;
			}
			ajax({
				type:'post',
				url:'../../client/clientUser/saveManagerRole',
				data:params,
				success:function(data){
					if (data.success == false) {
						top.showAlert(data.message)
					}else {
						dealJsonResult(data, "保存成功！", null, closeWin);
						//$.messager.alert('Success','保存成功');
					}
				},
				error:function(){
					top.showAlert("网络错误!");
				}
			})
		}else {
			top.showAlert(info);
		}
		
	}
</script>

    <div class="pop" id="PerfectData" style="top:10%; width:100%; margin-left:-293px;">
     <!--  <h2 class="title">新增用户</h2> -->
      <div class="cont" style="margin-top:25px; width:100%; height:305px;">
            <script type="text/javascript">
	           function closeWin(){
	        	   top.$("#ifrContent")[0].contentWindow.reloadData();
	  	        	if (top.$("#dlg").window('close')) {}
	  	        	if (top.$("#dlg2").window('close')) {}
	  	        }
	            $(function(){
	            	$("#passwd").val("");
	            	$("#comPasswd").val("");
	            	if (typeof eid != 'undefined' ) {
	            		$("#name").combobox({
	            			disabled:true	            			
	            		})
					} 
	            	$("#name").combobox({
	            		onSelect:function(row){
	            			fillForm(row,null);
	            			$("#passwd").val("")
	            			$("#comPasswd").val("")
	        			}
	            	})
	            	if (typeof(eid)=='undefined' || eid=='') {
	            		$("#comPasswd").textbox({
	            			required:true
	            		})
	            		$("#passwd").textbox({
	            			required:true
	            		})
					}
	            	$("#userRole").combobox({
	            		method:'get',
	            		multiple:true,
	            		panelHeight:'auto',
	            		separator:' | '
	            	})
	            	//检测账号是否可用
	            	$("#account").textbox({
	            		onChange:function(newValue){
	            			if (newValue!=null) {
	            				var value = newValue.replace(/(^\s*)|(\s*$)/g, "")
		            			paramsData = {};
		            			paramsData.account = value;
		            			paramsData.notId = $("#id").val();
		            			invokAjax("../../client/clientUser/checkAccount",paramsData,"1")
							}
	            		}
	            	})
	            })
	            function backFun(data,judge){
	            	if (judge=="1") {
	            		if (data.success) {
							$("#checkAccount").html("<font color='green'>账号可用</font>")
							$("#judgeAccount").val(1);
						}else {
							$("#checkAccount").html("<font color='red'>账号重复</font>")
							$("#judgeAccount").val(0);
						}
	            		var account =  $("#account").textbox("getValue").replace(/(^\s*)|(\s*$)/g, "");
	            		if (account == "admin") {
	            			$("#checkAccount").html("<font color='red'>账号重复</font>")
							$("#judgeAccount").val(0);
						}
					}
	            }
            </script>
            <form method="post" id="frms" style="width:85%;">
           <!--  <a onclick="text_1()">text</a>
            <script type="text/javascript">
            	function text_1(){
            		var params = ["3aae902e-cdcb-423c-bff7-b53823c28b91","asdas9usjfaskn","a98sydhasih"];
            		alert($("#userRole").combobox("getValues"))
            		$("#userRole").combobox("setValues",params);
            		
            	}
            </script> -->
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="18%">用户</th>
                <td width="32%"><select class="easyui-combobox" name="name" id="name"  data-options="url:'../../client/clientUser/list?aisUser=true&statu=true&selectShopId=${shop.id }', valueField:'id', textField:'name',width:321,loadFilter:loadFilter" ></select></td>
            </tr>
            <tr>
                <th width="18%">角色</th>
                <td width="32%"><select class="easyui-combobox"  name="userRole" id="userRole" data-options="url:'../../client/userRole/roleShow',editable:true, valueField:'id', textField:'roleName',width:321,loadFilter:loadFilter" ></select></td>
            </tr>
            <tr>
                <th width="18%">登录账号</th>
                <td width="32%"><input class="easyui-textbox" data-options="required:true,width:321,"  type="text" name="account" id="account">
                <span id="checkAccount"></span><input type="hidden" id="judgeAccount" >
                </td>
            </tr>
            <tr>
                <th width="18%">登录密码</th>
                <td width="32%"><input class="easyui-textbox" data-options="width:321,"  type="password"  type="text" name="passwd " id="passwd">
                <input style="display: none;" type="text" name="md5Passwd" id="md5Passwd"></td>
            </tr>
            <tr>
                <th width="18%">确认密码</th>
                <td width="32%"><input class="easyui-textbox" data-options="width:321," type="password"  name="comPasswd" id="comPasswd"  >
                </td>
            </tr>
             <tr>
                <th width="18%">MAC地址</th>
                <td width="32%"><input class="inp2" type="text" name="describes" id="describes"></td>
            </tr>
            <tr>
                <th width="18%">是否允许登陆</th>
                <td width="32%"><select class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false" name="isAllow" id="isAllow">
                	<option value=""></option>
                	<option value="1">是</option>
                	<option value="0">否</option>
                </select></td>
            </tr>
             <tr>
                <th width="18%">允许切换门店</th>
                <td width="32%"><select class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false" name="isChangeShop" id="isChangeShop">
                	<option value=""></option>
                	<option value="1">是</option>
                	<option value="0">否</option>
                </select></td>
            </tr>
            </tbody>
        </table>
          	 <input type="hidden" name="id" id="id">
          	 <input type="hidden" name="isManager" id="isManager">
        </form>
      
    </div>
		<div class="btn-box bg-gray">
          <a class="btn2 blue" id="PerfectDataSubmit"  onclick="check()">提交</a>
         
        </div>
	</div>
</body>

</html>