$(function(){
	if(typeof(isView)!='undefined' && isView){
		loadView();
	}else{
		fillParams();
		loadForm();
		initForm();
		$("#_name").focus();
	}
	
});

function doSubmit(){
	$("#frm").form("submit");
}

function fillParams(){
	if(typeof(params)!='undefined'){
		fillForm(params);
	}
}

function loadForm(){
	if(typeof(entityUrl)!='undefined'
		&& typeof(eid)!='undefined'
		&& eid!=''){
		if(typeof(params)=='undefined'){
			params={};
		}
		params["id"]=eid;
		$.ajax({
            url: entityUrl,
            data:params,
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
                fillForm(data,typeof(formPrex)!='undefined'?formPrex:undefined);
                if(typeof(formLoaded)=='function'){
                	formLoaded(data);
                }
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致数据载入失败！', 'error');
            }
        });
	}else{
		if(typeof(formLoaded)=='function'){
        	formLoaded();
        }
	}
}

function loadView(){
	if(typeof(entityUrl)!='undefined'
		&& typeof(eid)!='undefined'
		&& eid!=''){
		if(typeof(params)=='undefined'){
			params={};
		}
		params["id"]=eid;
		$.ajax({
            url: entityUrl,
            data:params,
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
                fillHTML(data);
                if(typeof(formLoaded)=='function'){
                	formLoaded(data);
                }
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致数据载入失败！', 'error');
            }
        });
	}else{
		if(typeof(formLoaded)=='function'){
        	formLoaded();
        }
	}
}


function initForm(){
	$("#frm").form({
        url: saveUrl,
        onSubmit: function (data) {
        	var isValid = $(this).form('validate') && validateForm();
            if (isValid) {
                $.messager.progress();
            }
            return isValid;
        },
        success: function (data) {
        	if (typeof(successBack)=='function') {
        		successBack(data)
			}
            $.messager.progress('close');
            if(dealJsonResult(data, saveSucInfo, function(rst){ 
            	if(typeof(saveClearForm)=='undefined'||saveClearForm){
            		clearForm();
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
            	//if(typeof(saveCloseDlg)!='undefined'){
            		top.closeDialog();
            	//}
            }
        }
    });	 
	
	if(nameRequire){
		$("#_name").validatebox({
			required:true,
			validType:'length[0, ' + nameMaxLength + ']',
			missingMessage:'名称必填，请输入！',
			invalidMessage:'名称长度不超过' + nameMaxLength + '个汉字！',
			deltaX:-200
		});
	}
	if(nameServerValidate){
		$("#_name").validatebox({
			validType: "remote['" + serverValidateName + "?id=" + $('#_id').val() + "', 'entity.name']",
			invalidMessage:'名称已存在，请修改名称！',
			deltaX:-200
		});
	}
	
	if(descriptionLengthValidate){
		$("#_description").validatebox({
			validType:'length[0, ' + descriptionMaxLength + ']',
			invalidMessage:'备注说明长度不超过' + descriptionMaxLength + '个汉字！',
			deltaX:-200
		});
	}
}

function validateForm(){
	return true;
}

function clearForm(){
	$("#frm").form("clear");
}