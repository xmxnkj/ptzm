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
<script src="../../content/scripts/common/formUtils.js"></script>

<script type="text/javascript">
	var saveUrl = "saveJson";
	var entityUrl = "entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	var saveSucInfo = "客户保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
	var callPlanId = '${callPlanId}';
	
	$(function(){
		
	})
	
	function check(){
		$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: "../../customer/callPlan/save" ,//url
                data: $('#frm').serialize(),
                success: function (data) {
                	if(data.isSuccess){
                		top.closeDialog2();
                    	top.father.$('#numbersList').datagrid("reload");
                	}else{
                		alert(data.message);
                	}
                	
                }
            });
	}
	
	function closeDialog(){
		top.closeDialog();
	}
	
	function formLoaded(data){
		if (data && !data.inUse) {
			$("#inUse").combobox("setValue","0")
		}
	}
</script>
<div class="wrap">
    <div class="main">
        <div class="title">
            <span>新增的客户信息</span>
        </div>
        <form method="post" id="frm" style="width:85%; ">
	        <div class="Input">
	            <div class="InputContent">
	                <p>公司名</p>
	                <input class="easyui-textbox" data-options="missingMessage:'请输入公司名！'"  name="companyName" id="companyName" style="width:90%;">
	            </div>
	            <div class="InputContent">
	                <p>联系人</p>
	               <input class="easyui-textbox"  name="contactUser" id="contactUser" style="width:90%;">
	            </div>
	            <div class="InputContent">
	                <p>电话号码</p>
	                <input class="easyui-textbox" validType='phoneNum' data-options="required:true,missingMessage:'请输入电话号码！'"  name="mobile" id="mobile" style="width:90%;">
	            </div>
	        </div>
	        <input hidden name="callPlanId" value="${callPlanId}"/>
        </form>
        <div class="Button">
            <a href="#" class="button button-action button-pill button-small" onclick="check()">保存</a>
            <a href="#" class="button button-border button-pill button-action button-small"  onclick="closeDialog()">取消</a>
        </div>
    </div>
</div>

</body>
</html>