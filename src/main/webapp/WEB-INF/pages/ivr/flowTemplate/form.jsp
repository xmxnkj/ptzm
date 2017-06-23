<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="../../Css/purge.css">
    <link rel="stylesheet" href="../../Css/Pagestyle.css">
    <link rel="stylesheet" href="../../Css/buttons.css">
    <link rel="stylesheet" href="../../Css/Popup.css">
   
</head>
<body class="wrap">

    <script src="../../content/scripts/common/form.js"></script>
	<script src="../../content/scripts/common/formUtils.js"></script>
<script type="text/javascript">
var saveUrl = "saveJson";
var entityUrl = "entity";
//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';

var saveSucInfo = "保存流程模板信息";
var nameRequire = true;
var nameMaxLength = 100;
var nameServerValidate = false;
var descriptionLengthValidate = true;
var descriptionMaxLength = 250;
var saveClearForm=true;

function formLoaded(data){
	
}

function saveDate(){
	doSubmit();
}

function successBack(data){
	/* if(!data.success){
		$.messager.alert("请选择坐席!");
	} */
}
//校验
</script>
    
    <div class="main">
    	 <form method="post" id="frm" >
        <div class="title">
            <span>基本信息</span>
        </div>
        <div class="Input">
            <div class="InputContent">
                <p>模板名称</p>
                <input type="text" name="name" id="name">
            </div>
            
           <div class="InputContent">
                <p>备注</p>
               	<input  name="remarks" id="remarks" >
            </div>
            
           <!--  <div class="InputContent">
                <p>所属部门</p>
                <input type="text" name="owndept" id="owndept">
                <input hidden type="text" name="deptId" id="deptId">
            </div> -->
            
        </div>
        <input hidden id="id" name="id" >
         </form>
        <div class="Button">
            <a href="javascript:saveDate()" class="button button-action button-pill">保存</a>
            <a href="" class="button button-border button-pill button-action">重置</a>
        </div>
    </div>

</body>

</html>