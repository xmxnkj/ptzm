<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>拨号时间列表</title>
</head>
<body>
	<script src="../../content/scripts/format/format.js"></script>
	<script src="../../content/scripts/common/list.js"></script>
	<script type="text/javascript">
		var listjsonUrl = 'list?clientUserId=${clientUser.id}';
		var editUrl = 'voice/calltime/showEdit';
		var deleteUrl = 'deleteJson';
		var addTitle = "添加计划";
		var editTitle = "修改拨号计划";
		var confirmDeleteTitle = "提示信息";
		var confirmDeleteInfo = "您确认要删除吗？";
		var deleteSuccess = "删除成功！";
		//singleSelect = false;
		var dlgWidth = 400;
		var dlgHeight = 500;
		toolbars = undefined;
		var gridOpt = {};
		gridOpt["onDblClickRow"] = sss;
		function sss(){};
		$(function() {
			$('#start_time').timespinner({
				min : '09:00',
				max : '20:00',
				required : true,
				showSeconds : false,//定义是否显示秒的信息。false不显示
				highlight : 1,//初始高亮的域，0 = 时，1 = 分
				value : '09:00'
			});
			$('#end_time').timespinner({
				min : '09:30',//最小值
				max : '20:00',//最大值
				required : true,
				showSeconds : false,//定义是否显示秒的信息。false不显示
				highlight : 1,//初始高亮的域，0 = 时，1 = 分
				value : '20:00'//默认值
			});
		});
		
		/** 添加 */
		function add(){
			var startTime = $('#start_time').timespinner('getValue');
			var endTime = $('#end_time').timespinner('getValue');
			console.log('开始时间：' + startTime + '，结束时间：' + endTime);
			$.ajax({
				type : "POST", //GET或POST,
				async : true,
				url : "../../voice/calltime/add",
				data : {
					'startTime' : startTime,
					'endTime' : endTime
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

		/** 操作 */
		function operation(val, rows) {
			var v;
			if (val == 'Open') {
				v = "禁用";
			} else {
				v = "启用";
			}
			return "<a href='javascript:opera(\""
					+ rows.id
					+ "\")' class='button button-primary button-rounded button-small'>"
					+ v + "</a> " + "<a href='javascript:del(\"" + rows.id
					+ "\")' class='button button-rounded  button-small'>删除</a>";
		};
		//启用、禁用
		function opera(id) {
			console.log("点击项的id=" + id);
			$.ajax({
				type : "POST", //GET或POST,
				async : true,
				url : "../../voice/calltime/update",
				data : {
					'id' : id,
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
		//删除
		function del(id) {
			console.log("删除的id = " + id);
			$.messager.confirm(confirmDeleteTitle, confirmDeleteInfo, function (data) {
	            if (data) {
	            	$.ajax({
	    				type : "POST", //GET或POST,
	    				async : true,
	    				url : "../../voice/calltime/delete",
	    				data : {
	    					'id' : id,
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
	</script>
	<div class="page-body" style="height: 100%;">
		<div class="content">
			<div class="noteFrame">
				<div class="note">
					<p>友情提示</p>
					<p>1.机器人通话时间统一规定在9：00-20：00</p>
					<p>2.用户可以在上述规定时间内添加多个通话时间段</p>
				</div>
			</div>
			<div class="table">
				<div class="table-function">
					<div class="dialingTime">
						<span>修改企业时间：</span> <input id="start_time" style="width: 120px;">
						<span>--</span> <input id="end_time" style="width: 120px;">
					</div>
					<a id="add" href="javascript:add()"
						class="button button-primary button-rounded button-small">新增拨号时间</a>
				</div>
				<div style="height: -moz-calc(100% - 7%); height: -webkit-calc(100% - 7%); height: calc(100% - 7%);">
					<table id="tbl" width="100%" border="0" cellspacing="0"
						cellpadding="0" class="tb">
						<thead>
							<tr >
								<!-- <th data-options="field:'',checkbox:true"  style="width:30px;"></th> -->
								<th data-options="field:'startTime',width:parseInt($(this).width() * 0.33),align:'center'">开始时间</th>
								<th data-options="field:'endTime',width:parseInt($(this).width() * 0.33),align:'center'">结束时间</th>
								<th data-options="field:'operation',formatter:operation,width:parseInt($(this).width() * 0.34),align:'center'">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>


</body>
</html>