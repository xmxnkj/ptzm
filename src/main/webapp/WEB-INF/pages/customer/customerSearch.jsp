<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	
%>
<script src="../../content/scripts/format/format.js"></script>
<c:if test="${!empty params['debet'] }">
	 <script type="text/javascript">
	 	$(function(){
	 		$("#divSearch").height("24%")
	 	})
	 </script>
</c:if>
<div class="hide" style="width:440px;height: 50%" id="divSearch"> 
   <div class="cont" >
       <script type="text/javascript">
       	$(function(){
      		if (typeof $("#key").val() != 'undefined') {
          		$('#key').textbox('textbox').attr('placeholder', '姓名/公司名/联系人/电话号码');
   			}
       	})
       </script>
       	<form id="frmCheck">
	           	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
	               <tbody>
			        <!-- 管理--客户管理--客户列表  customer-->
				        <tr>
	               	 		<th width="35%">关键字</th>
	               	 		<td width="65%"><input class="easyui-textbox" data-options="width:225"  id="key" name="key"></td>
			        	</tr>
			        	<tr>
			                <th width="35%">计划状态</th>
			                <td width="65%">
				                <select class="easyui-combobox" data-options="editable:false,missingMessage:'请选择计划状态！'" style="width:100px" name="planState" id="planState">
				                	<option value=""></option>
				                	<option value="UnPlan">未入计划</option>
				                	<option value="Planned">已入计划</option>
				                	<option value="Contact">已联系</option>
				                </select>
				            </td>
			            </tr>
			             <tr>
			                <th width="35%">客户接听状态</th>
			                <td width="65%">
				                <select class="easyui-combobox" data-options="editable:false" style="width:100px" name="receivingState" id="receivingState">
				                	<option value=""></option>
				                	<option value="Busy">线路忙（占线）</option>
				                	<option value="Empty">空号</option>
				                	<option value="IOException">系统IO异常</option>
				                	<option value="HangUpAfAns">接听后挂断</option>
				                	<option value="NoAnswer">无人接听</option>
				                	<option value="NoSpeakAfAns">接听后无人说话</option>
				                	<option value="Answer">正常接听</option>
				                </select>
				            </td>
			            </tr>
			            <tr>
			                <th width="35%">客户分类</th>
			                <td width="65%">
				                <select class="easyui-combobox" data-options="editable:false" style="width:100px" name="customerType" id="customerType">
				                	<option value=""></option>
				                	<option value="A">A</option>
				                	<option value="B">B</option>
				                	<option value="C">C</option>
				                	<option value="D">D</option>
				                	<option value="E">E</option>
				                	<option value="F">F</option>
				                </select>
				            </td>
			            </tr>
			            <tr>
			                <th width="35%">客户联系状态</th>
			                <td width="65%">
				                <select class="easyui-combobox" data-options="editable:false" style="width:100px" name="contactState" id="contactState">
				                	<option value=""></option>
				                	<option value="UnContact">未联系</option>
				                	<option value="Contacted">已联系</option>
				                	<option value="Contacting">联系中</option>
				                </select>
				            </td>
			            </tr>
			        	
			        	
			        	
			           <!--  <tr>
			                <th>所属集团</th>
			                <td><select class="easyui-combobox" data-options="panelHeight:'auto',width:225,valueField:'id',editable:false, textField:'name',url:'../../customer/customerGroup/list',loadFilter:loadFilter"   id="groupId" name="groupId"></select></td>
		           		 </tr>
		            	 <tr>
			                <th>消费次数</th>  
			                 <td>
			                 	<input class="easyui-numberbox" data-options="width:105,precision:0,min:0"   id="comeInTimesLower" name="comeInTimesLower">—
			                	<input class="easyui-numberbox" data-options="width:105,precision:0,min:0"   id="comeInTimesUpper" name="comeInTimesUpper">
			                 </td>
		            	 </tr>
			            <tr>
			                <th>是否会员</th>
			                <td><select class="easyui-combobox" data-options="width:225,editable:false,panelHeight:'auto'"   id="card" name="card">
			                	<option value="">请选择</option>
			                	<option value="true">是</option>
			                	<option value="false">否</option>
			                </select></td>
			            </tr>
		            	 <tr>
			                <th>保险时间</th>
			                <td>
			                	<input class="easyui-datebox" data-options="width:105,editable:false"  id="insuranceDateLower" name="insuranceDateLower">—
			                	<input class="easyui-datebox" data-options="width:105,editable:false"  id="insuranceDateUpper" name="insuranceDateUpper">
			                </td>
		            	</tr> -->
	           	   </tbody>
	           </table>
         
           </form> 
       </div>
     <div class="btn-box bg-gray"  style="position: absolute;margin-bottom: -25px;border-top-width: 2px">
     	<a class="btn2 red" onclick="reset_s();">重置</a>
     	<a class="btn2 cancelbtn" onclick="closeSearch();">取消</a>
     	<a class="btn2 blue" id="quotedPriceSubmit" onclick="search_s()">确认搜索</a>
     </div>
   <script type="text/javascript">
   var paramsSearch = {};
   function search_s(){
	   paramsSearch = getFormData('frmCheck',params);
	   /* 默认参数 */
	   $("#tbl").datagrid("reload",paramsSearch)
	   closeSearch();
    }
	function reset_s(){
		 setNull('frmCheck');
	}
   </script>
</div>
