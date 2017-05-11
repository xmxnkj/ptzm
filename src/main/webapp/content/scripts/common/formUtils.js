
function genFormData(frmId, form1, varName){
	var fields = $("#" + frmId).serializeArray();
	var data=null;
	if(form1){
		data=form1;
	}else{
		data={};
	}
	if(varName==null || typeof(varName)=='undefined'){
		varName='';
	}else{
		varName+='.';
	}
	
	$.each(fields, function(i, field){
		data[varName+field.name]=field.value;
	});
	
	return data;
}

function genListData(items, itemName, data, varName){
	if(data==null || typeof(data)=='undefined'){
		data={};
	}
	if(varName==null || typeof(varName)=='undefined'){
		varName='';
	}else{
		varName+='.';
	}
	
	for(var i=0; i<items.length; i++){
		for(var key in items[i]){
			data[varName + itemName + "[" + i +"]." + key] = items[i][key];					
		}
	}
	return data;
}

function genGridData(gridId, items, itemName, data, varName){
	if(data==null || typeof(data)=='undefined'){
		data={};
	}
	if(varName==null || typeof(varName)=='undefined'){
		varName='';
	}else{
		varName+='.';
	}
	
	var cols = $("#"+gridId).datagrid("getColumnFields");
	
	for(var i=0; i<cols.length; i++){
		
	}
	
	for(var i=0; i<items.length; i++){
		for(var key in items[i]){
			data[varName + itemName + "[" + i +"]." + key] = items[i][key];					
		}
	}
	return data;
}

function fillForm(data, prefix){
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
    			$(iid).combobox("setValue", data[p]+"");
    		}else{
    			if($(iid).attr("class") && $(iid).attr("class").lastIndexOf('easyui-numberbox')>=0){
    				$(iid).numberbox("setValue", data[p]);
    			}else if($(iid).attr("class") && $(iid).attr("class").lastIndexOf('easyui-datebox')>=0){
    				$(iid).datebox("setValue", data[p]);
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


function fillHTML(data, prefix){
	for(var p in data){
		var iid = "#" + (prefix?prefix+"_":"") + p;
		if(data[p]!=null && typeof(data[p])=='object'){
			fillHTML(data[p], prefix?prefix+"_"+p:p);
		}else{
			if($(iid).length>0){
				$(iid).prop("innerHTML", getValueStr(data[p]));
			}
		}
	}
}
function setHTML(id, content){
	$("#"+id).prop("innerHTML", getValueStr(content));
}

function getValueStr(value){
	if(isFloat(value)){
		return value.toFixed(2);
	}
	return value;
}