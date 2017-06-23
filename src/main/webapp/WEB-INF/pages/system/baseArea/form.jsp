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
		
	})
	function clearForm(){
	};
	function formLoaded(data){
		
		if (data&&data.areaContentId!=null&&data.areaContentId.length>1) {
			var ids = data.areaContentId.split(",");
			$("#areaContentIds").combobox("setValues",ids);
		}		
	}
	function check(){
		var name = $("#name").val();
		var valIds = $("#areaContentIds").combobox("getValues").toString();
		var texts = $("#areaContentIds").combobox("getText"); 
		if (valIds!=''&&valIds.length>1) {
			$("#areaContentId").val(valIds);
			$("#areaContent").val(texts);
		}else {
			top.showAlert("请选择区域内容！");
			return;
		}
		if (name=='') {
			top.showAlert("请填写区域名称！");
			return;
		}
		doSubmit();
	}
</script>
	<!--  <div class="ope-bar clearfix" >
   	    <div class="fl">
           <a class="btn2 active" href="showEdit">提醒设置</a>
           <a class="btn" href="../../system/publishNotice/showEdit">发布通知</a>
           <a class="btn" href="../../system/publishNotice/showList">通知列表</a>
       </div>
    </div> -->
   <%--  <%
     Cookie cookie = new Cookie("test","testValue"); 
    cookie.setMaxAge(10);
    response.addCookie(cookie); 
     Cookie [] c =  request.getCookies();
    %>
     <%=c.length %>
    <%=c[0].getName() %>
    <%=c[0].getValue() %>
     <%=session.getId() %> --%>
    <%-- <div class="ope-bar clearfix">
   		<c:import url="../header.jsp">
   		</c:import>
     </div> --%>
    <div class="pop" id="PerfectData" style="top:10%; width:600px; margin-left:-280px;">
      <div class="cont" style="max-height: 500px">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            	<tr>
	                <th width="30%">区域名称</th>
	                <td width="70%"><input class="inp2" style="width:345px" type="text" name="name" id="name"></td> 
	            </tr>
            	<tr>
	                <th width="30%">区域内容</th>
	                <td width="70%"><select class="easyui-combobox" id="areaContentIds" name="areaContentIds" data-options="multiple:true,panelHeight:'auto',separator:' | ',editable:true,valueField:'id',textField:'name',width:345,url:'../../system/baseArea/list?parentId=0',loadFilter:loadFilter"></select></td> 
	            </tr>
	            <input type="hidden"  id="areaContentId" name="areaContentId">
            	<input type="hidden"  id="areaContent" name="areaContent">
            	<input type="hidden"  id="parentId" name="parentId" value="00">
	            <input type="hidden" id="id" name="id">
            </tbody>
        </table>
        </form>
        <div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a>         
    </div>
      </div>
    </div>
	

</body>
</html>