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
<body>
<script src="../../content/scripts/common/form.js"></script>
<script type="text/javascript">
	var loginLimitTime = '${client.loginLimitTime}';
	 $(function(){
		loadForm();
	})
	function loadForm(){
		debugger
		if (loginLimitTime!=null || loginLimitTime!= "") {
			//loginLimitTime = "00:00:00-00:00:00";
			var time = loginLimitTime.split("-");
			var time_1 = time[0].split(":");
			var time_2 = time[1].split(":");
			$("#time_0").numberbox("setValue",time_1[0]);
			$("#time_1").numberbox("setValue",time_1[1]);
			$("#time_2").numberbox("setValue",time_1[2]);
			$("#time_3").numberbox("setValue",time_2[0]);
			$("#time_4").numberbox("setValue",time_2[1]);
			$("#time_5").numberbox("setValue",time_2[2]);
		}
	} 
	function  check(){
		if (validation()) {
			var params = {};
			params["id"] = top.clientId
			loginLimitTime = "";
			for (var i = 0; i < 6; i++) {
				if (i == 2) {
					loginLimitTime += changeData($("#time_"+i).numberbox("getValue"))+"-";
				}else if(i == 5){
					loginLimitTime += changeData($("#time_"+i).numberbox("getValue"));
				}else {
					loginLimitTime += changeData($("#time_"+i).numberbox("getValue")) + ":";
				}
			}
			params["loginLimitTime"] = loginLimitTime;
			$.ajax({
				type:'post',
				url:"../../client/client/setImgVideo",
				data:params,
				timeout:10000,
				success:function(){
					top.showAlert("保存成功!")
				},
				error:function(){
					top.showAlert("网络错误！")
				}
			})
		}
		
	}
	function changeData (val){
		if (val==""||val==null) {
			return "00"
		}else {
			if (val.length < 2) {
				return "0"+val;
			}
			return val;
		}
	}
	function validation(){
		var time_0 = changeData($("#time_0").numberbox("getValue"));
		var time_1 = changeData($("#time_1").numberbox("getValue"));
		var time_2 = changeData($("#time_2").numberbox("getValue"));
		var time_3 = changeData($("#time_3").numberbox("getValue"));
		var time_4 = changeData($("#time_4").numberbox("getValue"));
		var time_5 = changeData($("#time_5").numberbox("getValue"));
		var rst = true;
		var info =  "前后时间差错误<br>";
		if (time_3 < time_0) {
			info =  "前后小时差错误<br>";
			rst = false;
		}else if(time_0 == time_3){
			if (time_4 < time_1) {
				rst = false;
				info =  "前后分钟差错误<br>";
			}else if (time_1 == time_4) {
				if (time_5 < time_2) {
					info =  "前后秒数差错误<br>";
					rst = false;
				}
			}
		}
		if (!rst) {
			top.showAlert(info);
		}
		return rst;
	}
	var saveUrl = "../client/client/setImgVideo";
	var entityUrl = "getImgVideoLogin";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
</script>
 	<div class="ope-bar">
    		<c:import url="../../header.jsp">
   		 	</c:import>
    </div>
    <div class="pop" id="PerfectData" style="top:10%; width:100%;left:0;text-align:center;">
    <h2 class="title">登录时间限制</h2>
      <div class="cont" style="max-height: 500px">
        <form id="frm" >  
       		 <input id="time_0" class="easyui-numberbox"  data-options="width:40,min:0,max:23" style="text-align: center">:
       		  <input id="time_1" class="easyui-numberbox" data-options="width:40,min:0,max:59">:
       		   <input id="time_2" class="easyui-numberbox" data-options="width:40,min:0,max:59">
       		    ----<input id="time_3" class="easyui-numberbox" data-options="width:40,min:0,max:23">:
       		     <input id="time_4" class="easyui-numberbox" data-options="width:40,min:0,max:59">:
       		      <input id="time_5" class="easyui-numberbox" data-options="width:40,min:0,max:59">
        </form>
    </div>
    
    </div>
	<div class="btn-box bg-gray" style="position:absolute;bottom:0;">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>   
      </div>

</body>
</html>