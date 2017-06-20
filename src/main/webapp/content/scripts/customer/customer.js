function del(){
	var rows = $('#tbl').datagrid('getSelections');
	if(rows != null&&rows.length>0){
		$.messager.confirm(confirmDeleteTitle, confirmDeleteInfo, function (data) {
            if (data) {
            	var ids = "";
            	for (var i = 0; i < rows.length; i++) {
            		if (i==rows.length-1) {
            			ids += rows[i]['id'];
					}else {
						ids += rows[i]['id']+',';
					}
				}
                $.ajax({
                    url: deleteUrl + '?id=' + ids + "&rnd="+ Math.random(),
                    type: 'get',
                    timeout: TIMEOUT,
                    success: function (data) {
                        if(dealJsonResult(data, deleteSuccess)){
                        	$('#tbl').datagrid('reload');	
                        }
                    },
                    error: function () {
                        $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
                    }
                });
            }
        });
	}else {
		top.showAlert("请选择要删除的数据！")
	}
}
function openCustomer(){
	var rows = $('#tbl').datagrid('getSelections');
	if(rows != null&&rows.length>0){
		$.messager.confirm("开放至客户库", "是否确认将所选"+rows.length+"个客户开放至客户库？", function (data) {
            if (data) {
            	var items = {};
            	for (var i = 0; i < rows.length; i++) {
            		items['items['+i+'].id'] = rows[i]['id'];
				}
                ajax({
                    url: "open",
                    type: 'post',
                    data:items,
                    timeout: TIMEOUT,
                    success: function (data) {
                        if(dealJsonResult(data, "开放客户库成功！")){
                        	privateSeaFun(rows.length);
                        	$('#tbl').datagrid('reload');	
                        }
                    },
                    error: function () {
                        $.messager.alert(WINDOW_CAPTION, '网络原因导致删除失败！', 'error');
                    }
                });
            }
        });
	}else {
		top.showAlert("请选择客户！")
	}
}
function csFormat(val){
		switch (val) {
		case "UnContact":
			return "未联系";
			break;
		case "Contacted":
			return "已联系";
			break;
		case "Contacting":
			return "联系中";
			break;
		default:
			break;
		}
	}
	function psFormat(val){
		switch (val) {
		case "UnPlan":
			return "未入计划";
			break;
		case "Planned":
			return "已入计划";
			break;
		case "Contact":
			return "已联系";
			break;
		default:
			break;
		}
	}
	function rsFormat(val){
		switch (val) {
		case "Busy":
			return "线路忙（占线）";
			break;
		case "Empty":
			return "空号";
			break;
		case "IOException":
			return "系统IO异常";
			break;
		case "HangUpAfAns":
			return "接听后挂断";
			break;
		case "NoAnswer":
			return "无人接听";
			break;
		case "NoSpeakAfAns":
			return "接听后无人说话";
			break;
		case "Answer":
			return "正常接听";
			break;
		default:
			break;
		}
	}
	
	
	var gridOpt = {};
	gridOpt["onDblClickRow"] = onDbClick;
	function onDbClick(index,row){
		$("#tbl").datagrid("selectRow",index);
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
		}
	}
	function importCustomer(){
		var importUrl = 'customer/customer/showPage?pageName=import&type='+params['type'];
		top.openDialog(importUrl,"导入"+showName+"客户", 600,600);
	}
	function closeSeat(){
		$("#divSeat ").dialog("close");
	}
	function showSeat(){
		$("#divSeat").dialog({
			title:'分配坐席',
			modal:true
		})
		$("#divSeat").dialog("open");
		/* var importUrl = 'customer/customer/showPage?pageName=distributionSeat&type='+params['type'];
		top.openDialog(importUrl,"分配坐席", 300,300); */
	}
	function closePlan(){
		$("#divPlan").dialog("close");
	}
	function showPlan(){
		$("#divPlan").dialog({
			title:'加入计划',
			modal:true
		})
		$("#divPlan").dialog("open");
	}
	function exportExcel(){
		doExportCommon("tbl",params,"../../customer/customer/exportCommon","customer/customer");
	}
	function listLoaded(){
		$.ajax({
            url: "privateSeaCount",
            type: 'post',
            timeout: TIMEOUT,
            success: function (data) {
            	privateSeaFun(data.entity);
            	//$("#allAmount").prop("innerHTML",data);
            }
        });
	}
	$(function(){
		privateSeaFun(null);
	})
	function privateSeaFun(num){
		if (num!=null) {
			privateSeaCountNum = parseInt(num);
		}
		$("#allAmount").prop("innerHTML",privateSeaCountNum);
		var privateSeaPercentage = privateSeaCountNum / privateSeaNum*100;
		console.log('百分比',privateSeaPercentage);
		if(privateSeaPercentage>=90){
			console.log('红');
			$('#numImg').css('background','#ff3b44');
			$('#numImg').css('width',privateSeaPercentage+'%');
		}else if(privateSeaPercentage>=70){
			console.log('黄');
			$('#numImg').css('background','#ffd956');
			$('#numImg').css('width',privateSeaPercentage+'%');
		}else{
			console.log('绿');
			$('#numImg').css('background','#29cb6c');
			$('#numImg').css('width',privateSeaPercentage+'%');
		}
	}
	//私海容量及当前私海数量
	/*function privateSeaCount(){
		 $.ajax({
             url: "privateSeaCount",
             type: 'post',
             timeout: TIMEOUT,
             success: function (data) {
            	 $("#allAmount").prop("innerHTML",data);
             }
         });
	}
	$(function(){
		privateSeaCount();
	})*/