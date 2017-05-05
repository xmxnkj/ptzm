if(typeof(toolbars)=='undefined'){
	var toolbars=[];
}
if(typeof(defaultToolbar) == 'undefined'){
	toolbars[0] = {iconCls:'icon-add', text:'添加', handler:add};
	toolbars[1] = {iconCls:'icon-edit', text:'修改', handler:edit};
	toolbars[2] = {iconCls:'icon-remove', text:'删除', handler:del};
}
$(function(){
	if(typeof(listjsonUrl) != 'undefined'){
		var qp={};
		if(typeof(params)!='undefined'){
			qp=params;
		}
		var opt=null;
		if(typeof(gridOpt)!='undefined'){
			opt=gridOpt;
		}else{
			opt={};
		}
		if(typeof(opt["toolbar"])=='undefined'){
			opt["toolbar"]=toolbars;
		}
		if(typeof(opt["fit"])=='undefined'){
			opt["fit"]=true;
		}
		if(typeof(opt["url"])=='undefined'){
			opt["url"]=listjsonUrl;
		}
		//treegird具备
		if(typeof(opt["idField"])=='undefined'){
			opt["idField"]='id';
		}
		if(typeof(opt["treeField"])=='undefined'){
			opt["treeField"]='name';
		}
		if(typeof(opt["animate"])=='undefined'){
			opt["animate"]= true;
		}
		
		if(typeof(opt["onDblClickRow"])=='undefined'){
			opt["onDblClickRow"]=edit;
		}
		if(typeof(opt["singleSelect"])=='undefined'){
			opt["singleSelect"]=true;
		}
		if(typeof(opt["pagination"])=='undefined'){
			opt["pagination"]=true;
		}
		if(typeof(opt["queryParams"])=='undefined'){
			opt["queryParams"]=qp;
		}
		if(typeof(opt["nowrap"])=='undefined'){
			opt["nowrap"]=false;
		}
		if(typeof(opt["onLoadSuccess"])=='undefined'){
			opt["onLoadSuccess"]=function(){
				if(typeof(listLoaded)=='function'){
					listLoaded();
				}
			}
		}
		if(typeof(opt["onBeforeExpand"])=='undefined'){
			opt["onBeforeExpand"]=function(row){
				if(typeof(beforeExpand)=='function'){
					beforeExpand(row);
				}
			}
		}
		if(typeof(opt["onClickRow"])=='undefined'){
			opt["onClickRow"]=function(index,row){
				if(typeof(rowClicked)=='function'){
					rowClicked(index,row);
				}
			}
		}
		$('#tbl').treegrid(opt);
	}
}); 

function beforeExpand(row){
	 if (row) {  
		 $('#tbl').treegrid('options').url = '../../client/dept/getDeptChildren';  
     }  
     return true;
}

function add(){
	if(typeof(isPage)=='undefined' || !isPage){
		var row = $('#tbl').treegrid('getSelected');
		var url = editUrl;
		if (row!=null) {
			var grade = parseInt(row.grade);
			grade = grade+1;
			url = editUrl+"?rowId="+row.id+"&grade="+grade
		}
		if(typeof(formParams)!='undefined'){
			for(var key in formParams){
				if(url.lastIndexOf("?")>=0){
					url+="&";
				}else{
					url+="?";
				}
				url+=key+"="+formParams[key];
				
			}
			
		}
		
		if(isOpenDlg2()){
			top.openDialog2(url,
					addTitle,
					dlgWidth, dlgHeight
					);
		}else{
			top.openDialog(url,
					addTitle,
					dlgWidth, dlgHeight
					);
		}
	}else{
		location.href = editUrl;
		return false;
	}
}

function isOpenDlg2(){
	return (typeof(isDlg2)!='undefined' && isDlg2);
}

function edit(){
	var row = $('#tbl').treegrid('getSelected');
	if(row != null){
		if(editUrl.lastIndexOf('?')>=0){
			url = editUrl + '&id=' + row.id;
		}else{
			url = editUrl + '?id=' + row.id;
		}
		if(typeof(formParams)!='undefined'){
			for(var key in formParams){
				if(url.lastIndexOf("?")>=0){
					url+="&";
				}else{
					url+="?";
				}
				url+=key+"="+formParams[key];
			}
			
		}
		if(typeof(isPage)=='undefined' || !isPage){
			if(isOpenDlg2()){
				top.openDialog2(url,
						editTitle,
						dlgWidth, dlgHeight
						);
			}else{
				top.openDialog(url,
						editTitle,
						dlgWidth, dlgHeight
						);
			}
		}else{
			location.href=url;
		}
	}else{
		$.messager.alert('提示', '请选择一行进行修改！');
	}
}

function del(){
	var row = $('#tbl').treegrid('getSelected');
	if(row != null){
		$.messager.confirm(confirmDeleteTitle, confirmDeleteInfo, function (data) {
            if (data) {
                $.ajax({
                    url: deleteUrl + '?id=' + row.id+ "&rnd="+ Math.random(),
                    type: 'get',
                    timeout: TIMEOUT,
                    success: function (data) {
                        if(dealJsonResult(data, deleteSuccess)){
                        	//$('#tbl').treegrid('reload');	
                        }
                    },
                    error: function () {
                        $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
                    }
                });
            }
        });
	}
}

function refreshData(){
	$("#tbl").treegrid("reload");
}

function yesNoFormatter(val,row,index){
	return val?"是":"否";
}

function getRow(){
	return $("#tbl").treegrid("getSelected");
}