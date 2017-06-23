<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<script src="../../content/scripts/format/format.js"></script>
 <script type="text/javascript">
 	$(function(){
 		//initPlanData();
 	})
 	function initPlanData(){
 		$.ajax({
	    	type:'post',
	    	url:'../../customer/callPlan/list',
	    	success:function(data){
	    		if (data.success) {
	    			var rows = data.rows;
	    			if (rows!=null && rows.length > 0) {
	    				var rowLength = rows.length;
	    				var html = "";
						for (var i = 0; i < rowLength; i++) {
							if (i%3==0) {
								html += '<tr><td width="33%"><input type="radio" name="callPlan" value="'+rows[i].id+'">'+rows[i].name+'</td>';
							}else if (i%3==2) {
								html += '<td width="33%"><input type="radio" name="callPlan" value="'+rows[i].id+'">'+rows[i].name+'</td></tr>';
							}else{
								html += '<td width="33%"><input type="radio" name="callPlan" value="'+rows[i].id+'">'+rows[i].name+'</td>';
							}
						}
						//$("#planList").html(html);
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
 	function checkPlan(judge){
 		//var val=$('input:radio[name="callPlan"]:checked').val();
 		var val=$("#panId").combobox("getValue");
 		if (val==''||val==null||typeof val =='undefined') {
			top.showAlert("请选择要加入的计划！");
		}else {
			var rows = $("#tbl").datagrid("getSelections");
			$.messager.confirm("加入计划", "确认将选中的"+rows.length+"位客户加入计划？  (<font color='red'>提示：已有的计划将会被覆盖！</font>)", function (j) {
	            if (j) {
	            	var paramsData = {};
	    			if (rows!=null&&rows.length>0) {
	    				for (var i = 0; i < rows.length; i++) {
	    					paramsData['items['+i+'].id']= rows[i]['id'];
	    				}
	    			}else {
	    				top.showAlert("请选择要加入计划的客户！");
	    				return;
	    			}
	    			paramsData['judge'] = judge;
	    			paramsData['callPlan.id'] = val;
	    			ajax({
	    				type:'post',
	    				url:'addCallPlan',
	    				data:paramsData,
	    				//timeout:10000,
	    				success:function(data){
	    					closePlan();
	    					$("#tbl").datagrid('reload');
	    				},
	    				error:function(){
	    					top.showAlert("网络原因，请重试")
	    				}
	    			})
	            }
	        });
			
		}
 		
 	}
 </script>
<div style="width:600px;height: 40%;display: none;" id="divPlan"> 
   <!-- <div class="wrap">
    <nav>
        <div class="decorate"></div>
        <img src="../../content/images/popup-close.png"/>
    </nav>
    <div class="main"> -->
     	<!-- <div class="title">
            <span>基本设置<span style="color: red">（提交后，系统将会在规定的时间内自动拨打）</span></span>
        </div> -->
        <!-- <div class="Input">
            <div class="InputContent"> -->
                <p>选择计划</p>
                <select class="easyui-combobox" id="panId" name="panId"
                data-options="required:true,missingMessage:'请选择计划！',width:180,valueField:'id', textField:'name', url:'../../customer/callPlan/list?inUse=1&clientUserId=${clientUser.id }',loadFilter:loadFilter"></select>
          <!--   </div>
        </div> -->
        <br>
<!--         <div class="Button"> -->
            <a href="#" class="button button-action button-pill button-small" onclick="checkPlan()" >确认</a>
            <a href="#" class="button button-border button-pill button-action button-small" onclick="closePlan()" >取消</a>
<!--         </div> -->
    </div>
<!-- </div>
</div> -->
