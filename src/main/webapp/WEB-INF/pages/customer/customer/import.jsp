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
<form method="post" action="importCustomer" enctype="multipart/form-data">
	<div class="main">
		<div class="title">
	          <span>1.<a href="../../importCustomerTemplate">点击下载导入数据模板</a>将要导入的数据导入模板文件中</span>
	      </div>
	      <div class="Input">
	          <p>注意事项</p>
	          <p>模板中的表头不可修改，表头不可删除</p>
	          <p>除必填的列以外，不需要的列可以删除</p>
	          <p>单次导入的数据不超过1,000,000条</p>
	      </div>
	      <div class="title">
	          <span>2.选择要导入的数据文件（只能上传.xlsx文件）</span>
	      </div>
	      <div class="Input">
	       <c:if test="${!empty rst }">
		   <p>导入结果：${rst }</p>
	</c:if>
	<c:if test="${!empty error }">
		<p style="color:red">错误信息：${error }</p>
	</c:if>
	<input type="hidden" id="type" name="type">
	<p>请选择客户资料Excel文件进行导入：</p>
	<input type="file" name="file"/>
	<div class="btn">
		<button class="button button-action button-pill" onclick="importData()">导入</button>
		<button class="button button-action button-pill" onclick="reset()">重置</a>
	</div>

	      </div>
      </div>
     
</form>
<script type="text/javascript">
	$(function(){
		$("#type").val(params['type']);
	})
	function reset(){
		location.href = location.href;
	}
	function importData(){
	   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
	   $("<div class=\"datagrid-mask-msg\"></div>").html("导入中，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	}
</script>
</body>
</html>