<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML">
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/content/kindEditor/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/content/kindEditor/plugins/code/prettify.css" />
</head>
<body>
<script charset="utf-8" src="${pageContext.request.contextPath}/content/kindEditor/kindeditor.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/content/kindEditor/jwplayer.js"></script>
<%-- <script charset="utf-8" src="${pageContext.request.contextPath}/content/kindEditor/kindeditor-all.js"></script> --%>
	<script charset="utf-8" src="${pageContext.request.contextPath}/content/kindEditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/content/kindEditor/plugins/code/prettify.js"></script>
 <script src="../../content/scripts/common/form.js"></script> 
<script type="text/javascript">
	var saveUrl = "/spma/news/news/saveJson";
	var entityUrl = "/spma/news/news/entity";
	//var serverValidateName = '<s:url action="validatename" namespace="/itinfo/borrow" />';
	
	var saveSucInfo = "数据保存成功！";
	var nameRequire = true;
	var nameMaxLength = 100;
	var nameServerValidate = false;
	var descriptionLengthValidate = true;
	var descriptionMaxLength = 250;
	var saveClearForm=true;
	var area = true;
	var editor;
	function check(){
		editor.sync();
		var message = $("#message").val();
		var title = $("#title").textbox("getValue");
		var id = $("#id").val();
		var image = $("#image").val();
		var paramss = {};
		paramss.message = message;
		paramss.image = image;
		paramss.title = title;
		paramss.belongType = $("#belongType").combobox("getValue");
		paramss.description = $("#description").val();
		paramss.videoUrl = $("#videoUrl").val();
		paramss.id = $("#id").val();
		if (id=="") {
			paramss.creater = top.user;
		}else {
			paramss.modifier = top.user; 
		}
		ajax({
			type:'post',
			url:saveUrl,
			data:paramss,
			success:function(data){
				if (data.success=='true'||data.success==true) {
					top.closeDialog();
					top.refreshContent();
				}else {
					top.showAlert(data.message);
				}
			},
			error:function(){
				top.showAlert("网络延迟，请重试！")
			}
		})
		//doSubmit();
	}
	
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="message"]', {
			cssPath : '../../content/kindEditor/plugins/code/prettify.css',
			uploadJson : '../../content/kindEditor/jsp/upload_json.jsp',
			fileManagerJson : '../../content/kindEditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					check();
					//document.forms['frm'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					check();
					//document.forms['frm'].submit();
				});
			}
		});
		prettyPrint();
		K('#image1').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#image').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#image').val(url);
						$("#showImg").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		});
		K('#media1').click(function() {
			editor.loadPlugin('media', function() {
                   editor.plugin.media.edit({
   					clickFn : function(url, title) {
   						K('#media').val(url);
   						$("#mediaMp4").attr("src",url);
   						$("#mediaOgg").attr("src",url);
   						$("#mediaWebm").attr("src",url);
   						editor.hideDialog();
   					} 
                   });
			});
			});
		/* K('#insertfile').click(function() {
			editor.loadPlugin('insertfile', function() {
				editor.plugin.fileDialog({
					fileUrl : K('#media').val(),
					clickFn : function(url, title) {
						K('#media').val(url);
						$("#mediaMp4").attr("src",url);
						$("#mediaOgg").attr("src",url);
						$("#mediaWebm").attr("src",url);
						editor.hideDialog();
					}
				});
			});
		}); */
	});
	
	function formLoaded(data){
		editor.html(data.message);
		$("#showImg").attr("src",data.image);
		var sources = '<source src="'+data.videoUrl+'" type="video/mp4">'+
	 		 '<source src="'+data.videoUrl+'" type="video/ogg">'+
	 		 '<source src="'+data.videoUrl+'" type="video/WebM">';
		$('#mediaShow').html(sources);
		/* if (data.belongType=="Video") {
			$("#showVideo").show();
		}else {
			$("#showVideo").hide();
			$("#videoUrl").val("");
		} */
	}
	$(function(){
		$("#belongType").combobox({
			onSelect:function(row){
				/* if (row.value=="Video") {
					$("#showVideo").show();
				}else {
					$("#showVideo").hide();
					$("#videoUrl").val("");
				} */
			}
		})
	})
</script>
    <div class="pop" id="PerfectData" style="top:10%; width:100%; margin-left:-500px;">
   <!--  <h2 class="title">新增</h2> -->
      <div class="cont" style="max-height: 600px">
        
        <form id="frm" method="post" >  
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tb tb7">
            <tbody>
            <tr>
                <th width="30%">标题</th>
                <td width="70%"><input class="easyui-textbox" name="title" id="title"></td>
            </tr>
            <tr>
            	 <th>封面图</th>
            	 <td><img alt="封面" src="" width="150px" height="100px" id="showImg">
            	 <input type="hidden" id="image" name="image" value="" />
            	 <input type="button" id="image1" value="选择图片" /></td>
            </tr>
            <tr id="showVideo">
            	 <th>视频</th>
            	 <td>
            	 <div id="video-box">
            	 <video width="150px" height="100px"  controls="controls" id="mediaShow">
            	 	<source id="mediaMp4" src="" type="video/mp4"><!-- /upload/news/media/20170622/20170622150125_976.mp4 -->
            	 	<source id="mediaOgg" src="" type="video/ogg">
            	 	<source id="mediaWebm" src="" type="video/WebM">
            	 </video>
            	 </div>
            	 <input type="hidden" id="videoUrl" name="videoUrl"  />
            	 <input type="button" id="media1" value="选择视频" /></td>
            </tr>
            <tr>
                <th width="30%">首页说明</th>
                <td width="70%"><textarea style="width:700px;height:100px;" name="description" id="description"></textarea></td>
            </tr>
            <tr>
            	 <th>正文</th>
            	 <td><textarea name="message" id="message"  cols="100" rows="8" style="width:700px;height:250px;visibility:hidden;"></textarea></td>
            </tr>
             <tr>
                <th width="30%">所属版面</th>
                <td width="70%"><select class="easyui-combobox"  name="belongType" id="belongType">
                	<option value="General">普通</option>
                	<option value="Top">首页顶部</option>
                	<option value="ImgText">首页图文</option>
                	<option value="Video">首页视频</option>
                </select></td>
            </tr>
           <!--  <tr>
                <th width="30%">首页显示</th>
                <td width="70%"><select class="easyui-combobox"  name="isShowIndex" id="isShowIndex">
                	<option value="0">否</option>
                	<option value="1">是</option>
                </select></td>
            </tr> -->
            </tbody>
        </table>
		 <input type="hidden" name="id" id="id">
        </form>
        <div class="btn-box bg-gray">
          	<a class="btn2 blue" id="PerfectDataSubmit" onclick="check();">提交</a> <!-- doSubmit -->        
    	 </div>
      </div>
    </div>
</body>
</html>