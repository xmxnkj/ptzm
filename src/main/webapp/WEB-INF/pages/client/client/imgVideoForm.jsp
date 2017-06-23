<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/content/css/site.css">
    <script src="${pageContext.request.contextPath}/content/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/content/js/common.js"></script>
    <script src="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/icon.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/themes/default/easyui.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/content/scripts/jquery-easyui-1.5.1/locale/easyui-lang-zh_CN.js" rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/content/scripts/main/main.js"></script>



</head>
<body>

<script type="text/javascript">
$(function(){
	loadForm();
	initForm();
})
function doSubmit(){
	$("#frm").form("submit");
}
function validateForm(){};
function initForm(){
	$("#frm").form({
        url: saveUrl,
        onSubmit: function () {
        	var isValid = $(this).form('validate') && validateForm();
            if (isValid) {
                $.messager.progress();
            }
            return isValid;
        },
        success: function (data) {
            $.messager.progress('close');
            if(dealJsonResult(data, saveSucInfo, function(rst){ 
            	if(typeof(saveClearForm)=='undefined'||saveClearForm){
            		/* $.messager.alert("success","保存成功!"); */
            	}
            	$("#_name").focus();
            	},function(rst){
            		if($("#_id").length>0){
            			try{
            				$("#_id").val(rst.id);
            				$("#frm_entity_id").val(rst.id);
            			}catch(e){
            				
            			}
            		}
            		
            	})){
            	if(typeof(saveClearForm)=='undefined'||saveClearForm||refreshContent){
            		top.refreshContent();
            	}
            	if(typeof(refreshList)=='function'){
            		refreshList();
            	}
            	if(typeof(saveCloseDlg)!='undefined'){
            		top.closeDialog();
            	}
            }
        }
    });
}
function loadForm(){
		$.ajax({
            url: entityUrl,
            data:{},
            type: 'post',
            timeout: TIMEOUT,
            success: function (datas) {
            	var data = datas.entity;
            	$("#image_0").attr("src","../../"+data.img_a);
                $("#image_1").attr("src","../../"+data.img_b);
                $("#image_2").attr("src","../../"+data.img_c);
                $("#img_a").val(data.img_a);
                $("#img_b").val(data.img_b);
                $("#img_c").val(data.img_c);
                $("#id").val(data.id);
                if (data.videoUrl != null && data.videoUrl != ' ' && data.videoUrl != '') {
                	$("#videoStatus").html("<font color='green'>视频已上传!</font>");
				}else {
					$("#videoStatus").html("<font color='red'>视频待上传!</font>");
				}
                $("#videoUrl").val(data.videoUrl);
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
            }
        });
	}

	var saveUrl = "setImgVideo";
	var entityUrl = "getImgVideoLogin";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	
</script>
 	<div class="ope-bar">
    		<c:import url="../../header.jsp">
   		 	</c:import>
    </div>
    <div class="pop" id="PerfectData" style="top:10%; width:100%;left:0;text-align:center;">
    <h2 class="title">店面轮播图</h2>
      <div class="cont" style="max-height: 500px">
        
        <form id="frm" method="post" >  
       		 <ul>
		 		<li style="height: 174px;">
		 			<label id="main" >主页图:</label><br>
		 			<img style="margin-left: 15px;border: ridge;border-color: yellow"  name="image_0" id="image_0" width="320px" height="174px" ondblclick="delImage(this)" onclick="choose(this)" onmousemove="moveInt(this)" onmouseout="moveOut(this)" judge="1">
		 			<img style="margin-left: 15px;border: ridge;"  name="image_1" id="image_1" width="320px" height="174px" ondblclick="delImage(this)" onclick="choose(this)" onmousemove="moveInt(this)" onmouseout="moveOut(this)" judge="">
		 			<img style="margin-left: 15px;border: ridge;" name="image_2" id="image_2" width="320px" height="174px" ondblclick="delImage(this)" onclick="choose(this)" onmousemove="moveInt(this)" onmouseout="moveOut(this)" judge="">
		 			
		 			<input type="hidden" id="img_a" name="img_a">
		 			<input type="hidden" id="img_b" name="img_b">
		 			<input type="hidden" id="img_c" name="img_c"><br>
		 			<input type="hidden" id="id" name="id">
		 		</li>
			</ul>
			<br><br>
			<div style="margin-left:-295px;">
			当前店况视频状态：<span id="videoStatus"></span>
			<input type="hidden" id="videoUrl" name="videoUrl" >
			</div>
        </form>
        <form id="uploadForm"  enctype="multipart/form-data">
	      	<div style="margin-top:5px;">
	      		请选择图片：<input type="file" id="file" name="file" onchange="change_photo(this)">
	      		<input type="button" value="上传" onclick="submitFile()">
	      	</div>
	      </form>
	      <form id="uploadVideoForm"  enctype="multipart/form-data">
	      	<div style="margin-left:15px;margin-top:5px;">
	      		视频上传：<input type="file" id="video" name="video" onchange="change_video(this)">
	      		<input type="button" value="上传" onclick="submitVideo()" >
	      	</div>
	      </form>
      </div>
    </div>
	<div class="btn-box bg-gray" style="position:absolute;bottom:0;">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="doSubmit();">提交</a>         
    </div>
<script type="text/javascript">
	        function moveInt(obj){
				if ($(obj).attr("judge") == '1') {
					
				}else {
					$(obj).css("border-color","yellow");
				}
	        }
	       
	        function moveOut(obj){
	        	if ($(obj).attr("judge") == '1') {
	        		
				}else {
					$(obj).css("border-color","white")
				}
	        }
	        function choose(obj){
	        	$(obj).css("border-color","yellow");
	        	$(obj).attr("judge","1");
	        	for (var i = 0; i < 3; i++) {
					if ("image_"+i != $(obj).attr("id")) {
						$("#image_"+i).css("border-color","white");
						$("#image_"+i).attr("judge","");
					}
				}
	        }
        	function submitFile(){
        		if ($("#file").val() == '') {
					top.showAlert("请选择要上传的文件！");
        			return;
				}
        		upLoadImg();
        		
        	}
        	
        	function submitVideo(){
        		if ($("#video").val() == '') {
					top.showAlert("请选择要上传的视频！");
        			return;
				}
        		upLoadVideo();
        		
        	}
        	
        function upLoadImg(){
        	var formData = new FormData($( "#uploadForm" )[0]);  
        	debugger
	   	     $.ajax({  
	   	          url: '../../shop/commodity/uploadFile' ,  
	   	          type: 'POST',  
	   	          data: formData,  
	   	          async: false,  
	   	          cache: false,  
	   	          contentType: false,  
	   	          processData: false,  
	   	          success: function (returndata) {  
	   	        	  var url = returndata.entity;
	   	        	  var imageL = $("#liImage").find("img").length;
	   	        	  var src = "../../"+url;
	   	        	  for (var i = 0; i < 5; i++) {
							if ($("#image_"+i).attr("judge") == '1') {
								$("#image_"+i).attr("src",src);
								switch (i) {
								case 0:
									$("#img_a").val(url);
									break;
								case 1:
									$("#img_b").val(url);						
									break;
								case 2:
									$("#img_c").val(url);	
									break;
								default:
									break;
								}
							}
						  }
	   	          },  
	   	          error: function (returndata) {  
	   	              alert("网络错误，请重试！");  
	   	          }  
	   	     });  
        }
        
        function upLoadVideo(){
        	var formData = new FormData($( "#uploadVideoForm" )[0]);
        	debugger
	   	     $.ajax({  
	   	          url: '../../shop/commodity/uploadVideo' ,  
	   	          type: 'POST',  
	   	          data: formData,  
	   	          async: false,  
	   	          cache: false,  
	   	          contentType: false,  
	   	          processData: false,  
	   	          success: function (returndata) {  
	   	        	  if (returndata.success) {
	   	        		$("#videoUrl").val(returndata.entity);
		   	        	$.messager.alert("success","视频上传成功！")
		                $("#videoStatus").html("<font color='green'>视频已上传!</font>");
					 }
	   	        	  
	   	          },  
	   	          error: function (returndata) {  
	   	              alert("网络错误，请重试！");  
	   	          }  
	   	     });  
        }
        
        function delImage(obj){
        	$(obj).attr("src","");
        	var j = $(obj).attr("id").replace("image_","");
        	for (var i = 0; i < 3; i++) {
				if (j == i) {
					switch (i) {
					case 0:
						$("#img_a").val("");
						break;
					case 1:
						$("#img_a").val("");						
						break;
					case 2:
						$("#img_b").val("");	
						break;
					default:
						break;
					}
				}
			}
        }
        	
         function change_photo(obj){
        	 var f=$(obj).val();
             if(f == null || f ==undefined || f == ''){
                 return false;
             }
             if(!/\.(?:png|jpg|bmp|gif|PNG|JPG|BMP|GIF)$/.test(f))
             {
                 top.showAlert("类型必须是图片(.png|jpg|bmp|gif|PNG|JPG|BMP|GIF)");
                 $(obj).val('');
                 return false;
             }
            //PreviewImage($("input[name='file']")[0], 'image', 'Imgdiv');
        } 
         function change_video(obj){
        	 var f=$(obj).val();
             if(f == null || f ==undefined || f == ''){
                 return false;
             }
             if(!/\.(?:rm|rmvb|wmv|avi|mp4|3gp|mkv|qsv)$/.test(f))
             {
                 top.showAlert("类型必须是图片(.rm|rmvb|wmv|avi|mp4|3gp|mkv|qsv)");
                 $(obj).val('');
                 return false;
             }
            //PreviewImage($("input[name='file']")[0], 'image', 'Imgdiv');
        } 
        </script>
</body>
</html>