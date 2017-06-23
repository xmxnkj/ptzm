<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
</script>
 
   
   
    <div class="pop" id="PerfectData" style="top:10%; width:400px; margin-left:-280px;">
    <h2 class="title">新增</h2>
      <div class="cont" style="max-height: 500px">
        
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="30%">管理员</th>
                <td width="70%"><input class="inp2" type="text" name="name" id="name"></td>
            </tr>
            <tr>
                <th width="30%">角色</th>
                <td width="70%"><input class="inp2" type="text" name="userRole" id="userRole"></td>
            </tr>
            <tr>
                <th width="30%">登录账号</th>
                <td width="70%"><input class="inp2" type="text" name="loginName" id="loginName"></td>
            </tr>
            <tr>
                <th width="30%">登录密码</th>
                <td width="70%"><input class="inp2" type="text" name="password" id="pasword"></td>
            </tr>
            <tr>
                <th width="30%">确认密码</th>
                <td width="70%"><input class="inp2" type="text" name="password2" id="password2"></td>
            </tr>
            <tr>
                <th width="30%">登录地址</th>  
                <td width="70%"><input class="inp2" type="text" name="loginAddress" id="loginAddress"></td>
            </tr>                                                     
            <tr>
                <th width="30%">描述</th>
                <td width="70%"><input class="inp2" type="text" name="powerContent" id="powerContent"></td>
            </tr>
            </tbody>
        </table>

		 <input type="hidden" name="id" id="id">
          
        </form>
      </div>
    </div>
	<div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="doSubmit();">提交</a>         
    </div>

</body>
</html>