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
		$.ajax({
			type:'post',
			url:'../../system/remind/list',
			success:function(data){
				var rows = data.rows;
				if (typeof rows !='undefined'&&rows!=null&&rows.length>0) {
					var row = rows[0];
					fillForm(row);
				}
			}
		})
	})
	function clearForm(){
		location.replace(location.href);
	};
</script>
	<!--  <div class="ope-bar clearfix" >
   	    <div class="fl">
           <a class="btn2 active" href="showEdit">提醒设置</a>
           <a class="btn" href="../../system/publishNotice/showEdit">发布通知</a>
           <a class="btn" href="../../system/publishNotice/showList">通知列表</a>
       </div>
    </div> -->
    <div class="ope-bar clearfix">
   		<c:import url="../header.jsp">
   		</c:import>
     </div>
    <div class="pop" id="PerfectData" style="top:10%; width:600px; margin-left:-280px;">
      <div class="cont" style="max-height: 500px">
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            	<tr>
	                <th width="30%">公众号AppID</th>
	                <td width="70%"><input class="inp2" style="width:345px" type="text" name="appId" id="appId"></td> 
	            </tr>
            	<tr>
	                <th width="30%">支付商户ID</th>
	                <td width="70%"><input class="inp2" style="width:345px" type="text" name="merchantId" id="merchantId"></td> 
	            </tr>
	            <tr>
	                <th width="30%">支付key</th>
	                <td width="70%"><input class="inp2" style="width:345px" type="text" name="payKey" id="payKey"></td> 
	            </tr>
	            <!-- <tr>
	                <th width="30%">账户到期提醒时间</th>
	                <td width="70%"><input class="inp2" style="width:180px" type="text" name="date" id="date">日</td> 
	            </tr>
	            <tr>
	                <th width="30%">账户到期提醒次数</th>
	                <td width="70%"><input class="easyui-numberbox" name="amount" id="amount" 
	                    data-options="height:26,width:180,min:0,precision:0">次</td>
	            </tr>
	            <tr>
	                <th width="30%">账号到期提醒内容</th>
	                <td width="70%"><textarea  style="width:180px;height: 100px" class="inp2"  name="content" id="content" ></textarea></td>
	            </tr> -->
	            <input type="hidden" id="id" name="id">
            </tbody>
        </table>
        </form>
      </div>
    </div>
	<div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="doSubmit();">提交</a>         
    </div>

</body>
</html>