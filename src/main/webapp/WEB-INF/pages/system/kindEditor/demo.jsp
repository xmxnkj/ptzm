<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content") != null ? request.getParameter("content") : "";
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8" />
	<title>KindEditor JSP</title>
	<link rel="stylesheet" href="../../content/kindEditor/themes/default/default.css" />
	<link rel="stylesheet" href="../../content/kindEditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="../../content/kindEditor/kindeditor.js"></script>
	<script charset="utf-8" src="../../content/kindEditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="../../content/kindEditor/plugins/code/prettify.js"></script>
	<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content"]', {
				cssPath : '../../content/kindEditor/plugins/code/prettify.css',
				uploadJson : '../../content/kindEditor/jsp/upload_json.jsp',
				fileManagerJson : '../../content/kindEditor/jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['kindEditor'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['kindEditor'].submit();
					});
				}
			});
			prettyPrint();
		});
		function check(){
			$.ajax({
				
				
			})
		}
	</script>
</head>
<body>
	<%=htmlData%>
	<form name="kindEditor" method="post" action="../../system/kindEditor/subEdtor">
		<textarea name="content" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;"><%=htmlspecialchars(htmlData)%></textarea>
		<br />
		<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
	</form>
</body>
</html>
<%!
private String htmlspecialchars(String str) {
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>