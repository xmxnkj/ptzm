<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<script src="../../content/scripts/format/format.js"></script>
 <script type="text/javascript">
 	$(function(){
 		//initData();
 	})
 	function initData(){
 		$.ajax({
	    	type:'post',
	    	url:'showSeat',
	    	success:function(data){
	    		if (data.success) {
	    			var rows = data.rows;
	    			if (rows!=null && rows.length > 0) {
	    				var rowLength = rows.length;
	    				var html = "";
						for (var i = 0; i < rowLength; i++) {
							if (i%3==0) {
								html += '<tr><td width="33%"><input type="radio" name="seat" value="'+rows[i].id+'">'+rows[i].name+'</td>';
							}else if (i%3==2) {
								html += '<td width="33%"><input type="radio" name="seat" value="'+rows[i].id+'">'+rows[i].name+'</td></tr>';
							}else{
								html += '<td width="33%"><input type="radio" name="seat" value="'+rows[i].id+'">'+rows[i].name+'</td>';
							}
						}
						$("#append").html(html);
					}
				}else {
					top.showAlert(data.message);
				}
	    	},
	    	error:function(){
	    		alert("网络原因,重试！")
	    	}
	    })
 	}
 	function check(judge){
 		var sel=$('input:radio[name="sel"]:checked').val();
 		var val = $("#seat").combobox("getValue");
 		if (val==''||val==null||typeof val =='undefined') {
			top.showAlert("请选择要分配的坐席！");
		}else {
			var paramsData = paramsSearch;
			/* if ( $('#key').length > 0) {
				paramsData['key'] = $('#key').val();
			} */
			//待加入搜索参数
			if (sel !='all') {
				var rows = $("#tbl").datagrid("getSelections");
				if (rows!=null&&rows.length>0) {
					for (var i = 0; i < rows.length; i++) {
						paramsData['items['+i+'].id']= rows[i]['id'];
					}
				}else {
					top.showAlert("请选择要分配的客户！");
					return;
				}
			}
			paramsData['judge'] = sel;
			paramsData['clientUser.id'] = val;
			ajax({
				type:'post',
				url:'distributionSeat',
				data:paramsData,
				//timeout:10000,
				success:function(data){
					if (data.success) {
						closeSeat();
						$("#tbl").datagrid('reload');
					}else {
						top.showAlert(data.message)
					}
					
				},
				error:function(){
					
				}
			})
		}
 		
 	}
 </script>
<div style="width:600px;height: 40%;display: none;" id="divSeat"> 
    <div class="wrap">
    <div class="main"> 
      <div class="title">
            <span>选择坐席</span>
        </div>
        <div class="Input">
        <div style="width:100%">                <select class="easyui-combobox" id="seat" name="seat"
                	data-options="required:true,missingMessage:'请选择坐席！',width:180,valueField:'id', textField:'name', url:'../../client/clientUser/list?showDept=1',loadFilter:loadFilter">
                </select></div>

               <div style="width:100%">
                <input type="radio" name="sel" value="part" checked="checked">分配所选客户
                <input type="radio" name="sel" value="all">分配搜索客户
                </div>
        	<div class="btn">
            <a href="#" class="button button-action button-pill button-small"  onclick="check()" >确认分配</a>
			<a href="#" class="button button-action button-pill button-small" onclick="closeSeat()">取消</a>
 			</div>
 		</div>
  </div>
</div>
</div>
