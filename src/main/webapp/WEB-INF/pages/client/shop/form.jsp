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
	
	var saveSucInfo = "门店保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
	function fileUploaded(url){
		$("#imgUrl").attr("src", "../../" + url);
		$("#picUrl").val(url);
	}
	
	function formLoaded(data){
		if(data && data.picUrl!=''){
			$("#imgUrl").attr("src", "../../"+data.picUrl);
		}
	}
	
	function clearForm(){
		$("#frm").form("clear");
		$("#imgUrl").attr("src", "");
	}
</script>
<div class="pop" id="PerfectData" style="top:0%; width:400px; margin-left:-193px;">

      <div class="cont" style="margin-top:25px; width:100%;height:340px;">
      <form method="post" id="frm" style="width:85%; ">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="35%">门店名称</th>
                <td width="65%">
                    <input class="inp2 easyui-validatebox" data-options="required:true, missingMessage:'请输入门店！', validType:'length[0,200]'" type="text" name="name" id="name">
                </td>
            </tr>
            <tr>
                <th width="35%">地址</th>
                <td width="65%"><input class="inp2" type="text" name="address" id="address"></td>
            </tr>
            <tr>
                <th width="35%">电话</th>
                <td width="65%"><input class="inp2" type="text" name="tel" id="tel">
                <input type="hidden" id="inUse" name="inUse" value="true"><!-- 默认启用 -->
                </td>
            </tr>
            <tr>
                <th width="35%">经度</th>
                <td width="65%">
                <input type="text" id="lon" name="lon" class="easyui-numberbox" data-options="precision:6">
                
                </td>
            </tr>
            <tr>
                <th width="35%">纬度</th>
                <td width="65%">
                <input type="text" id="lat" name="lat" class="easyui-numberbox" data-options="precision:6">
                </td>
            </tr>
            <tr>
            	<th width="18%">预约通知:</th>
				<td width="32%"><select  id="weChatId" name="weChatId" class="easyui-combobox" 
				data-options="width:160,editable:false,valueField:'openId', textField:'nickName', url:'../../wechat/wxuser/list',loadFilter:function(data){return data.rows}">
				</select></td>
            </tr>
            <!-- <tr>
                <th width="35%">是否启用</th>
                <td width="65%">
                	<label>
                	<input type="checkbox" name="inUse" id="inUse">启用
                	</label>
                </td>
            </tr>
            <tr>
                <th width="35%">是否默认门店</th>
                <td width="65%">
                	<label>
                	<input type="checkbox" name="isDefault" id="isDefault">默认门店 </label>
                </td>
            </tr> -->
            <tr>
                <th width="35%">图片</th>
                <td width="65%">
                	<div>
                	<img alt="" src="" style="width:170px;height:170px" id="imgUrl">
                	</div>
                	<iframe frameborder="0" src="../../common/images/showEdit" style="width:100%; height:100px;"></iframe>
                	<input type="hidden" name="picUrl" id="picUrl">
                </td>
            </tr>
            
            </tbody>
        </table>
           <input type="hidden" name="id" id="id">
        </form>
      </div>
      <div class="btn-box bg-gray">
          <a class="btn2 blue" id="PerfectDataSubmit" onclick="doSubmit();">提交</a>
         
      </div>
</div>      
</body>
</html>