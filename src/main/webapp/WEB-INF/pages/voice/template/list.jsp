<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>语音模板列表</title>
</head>
<body>
	<script src="../../content/scripts/format/format.js"></script>
	<script src="../../content/scripts/common/list.js"></script>
	<script type="text/javascript">
		var listjsonUrl = 'list';
		var editUrl = 'voice/template/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle = "添加模板";
		var editTitle = "修改模板";
		var confirmDeleteTitle = "提示信息";
		var confirmDeleteInfo = "您确认要删除吗？";
		var deleteSuccess = "删除成功！";
		//singleSelect = false;
		var dlgWidth = 800;
		var dlgHeight = 550;
		toolbars = undefined;
		var gridOpt = {};
		gridOpt["onDblClickRow"] = sss;
		function sss(){}; 

		function uploadName(person){
			return person.name;
		}
		function operation(val) {
			return "<a href='javascript:del(\""+ val+ "\")'><img style='width:25px;height:25px;margin:0px 5px;' src='../../content/images/delete.png'></a> " 
			+ "<a href='javascript:update(\"" + val+ "\")'><img style='width:25px;height:25px;margin:0px 5px' src='../../content/images/modification.png'></a>";
		}
		/** 删除 */
		function del(val){
			console.log("要删除的id："+val);
			$.messager.confirm(confirmDeleteTitle, confirmDeleteInfo, function (data) {
	            if (data) {
	            	$.ajax({
	    				type : "POST", //GET或POST,
	    				async : true,
	    				url : "../../voice/template/delete",
	    				data : {
	    					'id' : val,
	    				},
	    				dataType : "json", //xml、html、script、jsonp、text
	    				success : function(data) {
	    					if (data.success) {
	    						console.log(data);
	    						$("#tbl").datagrid("reload")
	    					} else {
	    						alert(data.message)
	    					}
	    				},
	    				error : function() {
	    					alert("网络原因,重试！")
	    				},
	    			});
	            }
	        });
			
		}
		/** 修改 */
		function update(val){		
			console.log("要修改的id："+val);
			edit();
		}
	</script>
	<div class="page-body" style="height: 100%;">
		<div class="content">
			<div class="noteFrame">
				<div class="note">
					<p>友情提示</p>
					<p>1.发给您的文件尽量不要修改文件名，如需修改，请勿修改文件拓展名</p>
					<p>2.发给您的术语模板是一个压缩文件，无需解压等任何操作，直接点击右上角新增模板上传即可</p>
				</div>
			</div>
			<div class="table">
				<div class="table-function">
					<a href="#"
						class="button button-primary button-rounded button-small"
						onclick="add()">新增模板</a>
				</div>
				<div
					style="height: -moz-calc(100% - 7%); height: -webkit-calc(100% - 7%); height: calc(100% - 7%);">
					<table id="tbl" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="tb">
						<thead>
							<tr>
								<!-- <th data-options="field:'',checkbox:true"  style="width:30px;"></th> -->
								<th
									data-options="field:'name',width:parseInt($(this).width() * 0.2),align:'center'">模板名称</th>
								<th
									data-options="field:'description',width:parseInt($(this).width() * 0.2),align:'center'">描述</th>
								<th
									data-options="field:'person',formatter:uploadName,width:parseInt($(this).width() * 0.2),align:'center'">上传人</th>
								<th
									data-options="field:'uploadDate',width:parseInt($(this).width() * 0.2),align:'center'">上传时间</th>
								<th
									data-options="field:'id',formatter:operation,width:parseInt($(this).width() * 0.2),align:'center'">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>

	</div>


</body>
</html>