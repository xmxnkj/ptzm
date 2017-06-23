<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增语音模块弹窗</title>
</head>
<body>
	<script src="../../content/scripts/common/form.js"></script>
	<script src="../../content/scripts/common/formUtils.js"></script>

	<script type="text/javascript">
		var filePath;
		var saveUrl = "saveJson";
		var entityUrl = "entity";
		//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
		var saveSucInfo = "保存成功！";
		var nameRequire = true;
		var nameMaxLength = 100;
		var nameServerValidate = false;
		var descriptionLengthValidate = true;
		var descriptionMaxLength = 250;
		var saveClearForm = true;
		$(function() {
			/* $("#type").val(params['type']) */
		});

		function formLoaded() {

		};
		/** 选择文件，上传文件 */
		function select_file(fileDom) {
			//获取文件
			var file = fileDom.files[0];
			var type = (file.name.substr(file.name.lastIndexOf(".")))
					.toLowerCase();
			if (type != ".zip" && type != ".rar") {
				alert("请上传压缩文件");
				return;
			}
			$("#file_name").text(file.name);
			//上传头像
			var formData = new FormData();
			formData.append("file", file);
			$.ajax({
				type : "POST", //GET或POST,
				url : "../../voice/template/uploadFile",
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				dataType : "json", //xml、html、script、jsonp、text
				success : function(data) {
					if (data.success) {
						console.log(data);
						//上传成功
						filePath = data.entity.filePath;
						console.log("上传的文件路径：" + filePath);
					} else {
						//上传失败
						alert(data.message);
					}
				},
				error : function() {
					alert("网络原因,重试！")
				},
			});
		};
		/** 添加模板 */
		function add() {
			var name = $('#name').val();
			var desc = $('#description').val();
			if (filePath == null) {
				alert("请选择文件");
				return;
			}
			console.log('模板名称：' + name + '，模板描述：' + desc);
			if (name == null || name == "") {
				alert("模板名称不能为空");
				return;
			}
			var formData = new FormData();
			formData.append("name", name);
			formData.append("path", filePath);
			formData.append("description", desc);
			$.ajax({
				type : "POST", //GET或POST,
				url : "../../voice/template/add",
				data : formData,
				cache : false,
				async : false,
				processData : false,
				contentType : false,
				dataType : "json", //xml、html、script、jsonp、text
				success : function(data) {
					if (data.success) {
						console.log(data);
						//$("#tbl").datagrid("reload")
						top.refreshContent();
						top.closeDialog();
					} else {
						alert(data.message)
					}
				},
				error : function() {
					alert("网络原因,重试！")
				},
			});
		};
		function close() {
			top.closeDialog();
		};
	</script>


		<div class="main">
			<div class="title">
				<span>1.填写基本信息</span>
			</div>
			<div class="Input">
				<p>模板名称</p>
				<input class="easyui-textbox" name="name" id="name"
					data-options="required:true,missingMessage:'请输入模板名称！'"
					style="width: 50%;"> <br>
				<p>描述</p>
				<textarea id="description" name="description" clos="50" rows="5"
					warp="virtual" style="border: 1px solid #ddd; width: 50%;"></textarea>

			</div>
			<div class="title">
				<span>2.选择要上传的文件（只能上传压缩文件）</span>
			</div>
			<div class="Input">
				<!-- class="Input" -->
				<label>
					<p href="#" class="button button-primary button-rounded button-small">选取文件</p>
					<input type="file" style="display: none" id="file" onchange="select_file(this)">
				</label>
				<span id="file_name"></span>
			</div>


			<div class="Button" style="margin-top: 20px;">
				<a href='javascript:add()' class="button button-action button-pill">保存</a>
				<a href='javascript:close()'
					class="button button-border button-pill button-action">取消</a>
			</div>
		</div>

</body>
</html>