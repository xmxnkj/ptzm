$(function(){
	fillParamss();
	loadForms();
	initForms();
	$("#_name").focus();
});

function doSubmit(){
	if (checkForm()) {
		$("#frms").form("submit");
	}
	
}

function checkForm(){
	var rst = true;
	var info = "";
	if ($("#name").val()=="") {
		info += "请输入姓名！<br>";
		rst = false;
	}
	var boy = document.getElementById("boy").checked;
	var girl = document.getElementById("girl").checked;
	if (!boy && !girl) {
		info += "请选择性别！<br>";
		rst = false;
	}
	if ($("#phone").val()=="") {
		info += "请输入手机号码！<br>";
		rst = false;
	}
	if ($("#idcard").val()=="") {
		info += "请输入身份证！<br>";
		rst = false;
	}
	if ($("#shop_id").combobox("getValue")=="") {
		info += "请选择所属门店！<br>";
		rst = false;
	}
	if ($("#statu").combobox("getValue")=="") {
		info += "请选择状态！<br>";
		rst = false;
	}
	var AllStore = document.getElementById("AllStore").checked;
	var MyStore = document.getElementById("MyStore").checked;
	var Not = document.getElementById("Not").checked;
	if (!AllStore && !MyStore && !Not) {
		info += "请选择开单选项！<br>";
		rst = false;
	}
	info = info.substring(0,info.length-4)
	if (!rst) {
		top.showAlert(info);
	}
	return rst;
}

function fillParamss(){
	if(typeof(params)!='undefined'){
		fillForms(params);
	}
}

function loadForms(){
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
                fillForms(data,typeof(formPrex)!='undefined'?formPrex:undefined);
                var value = data.billChooseType
                var sex = data.sex;
                if (value != null) {
          			if (value == 'AllStore') {
          				$("#AllStore").attr("checked","checked")
          			}else if (value == 'MyStore') {
          				$("#MyStore").attr("checked","checked")
          			}else if(value == 'Not'){
          				$("#Not").attr("checked","checked")
          			}
          		}else {
          			//choose();
				}
                if (sex == 1) {
                	$("#boy").attr("checked","checked")
				}else if (sex == 0) {
					$("#girl").attr("checked","checked")
				}
                if(typeof(formLoaded)=='function'){
                	formLoaded(data);
                }
            },
            error: function () {
                $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
            }
        });
	}else{
		//choose();choose_1();
		if(typeof(formLoaded)=='function'){
        	formLoaded();
        }
	}
}

function initForms(){
	$("#frms").form({
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
            	 closeWin();
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
            	if(typeof(saveCloseDlg)!='undefined'){
            		top.closeDialog();
            	}
            }
        }
    });	 
}


function fillForms(data, prefix){
	for(var p in data){
		var iid = "#" + (prefix?prefix+"_":"") + p;
		iid = iid.replace(".", "_");
		if(data[p]!=null && typeof(data[p])=='object'){
			fillForm(data[p], prefix?prefix+"_" + p:p);
		}else if($(iid).length>0){
    		if($(iid).attr("type")=='checkbox'){
    			$(iid).prop("checked", data[p]);
    		}else if($(iid).attr("type")=='radio'){
    			iid = iid.replace("_", ".").replace("#","");
    			$("input[type='radio'][name='" + iid + "'][value='" + data[p] + "']").attr("checked", "checked");
    		}else if($(iid).prop("type")=='select-one'){
    			$(iid).combobox("setValue", data[p]);
    		}else{
    			if($(iid).attr("class") && $(iid).attr("class").lastIndexOf('easyui-numberbox')>=0){
    				$(iid).numberbox("setValue", data[p]);
    			}else if($(iid).attr("class") && $(iid).attr("class").lastIndexOf('easyui-datebox')>=0){
    				value = data[p];
    				if (typeof value != 'undefined' && value != '' && value != null) {
    					var unixTimestamp = new Date( value )
    					$(iid).datebox("setValue", unixTimestamp.toLocaleString().substring(0,10).replace("/","-").replace("/","-"));
    				}
    			}else if($(iid).attr("class") && $(iid).attr("class").lastIndexOf('easyui-textbox')>=0){
    				$(iid).textbox("setValue", data[p]);
    			}else if($(iid).attr("class") && $(iid).attr("class").lastIndexOf('easyui-datetimebox')>=0){
    				$(iid).datetimebox("setValue", data[p]);
    			}else{
    				$(iid).val(data[p]);
    			}
    		}
    	}
    }
}


function validateForm(){
	return true;
}

function clearForm(){
	$("#frm").form("clear");
}