	Date.prototype.format = function(fmt) 
	{ //author: meizz 
	  var o = { 
	    "M+" : this.getMonth()+1,                 //月份 
	    "d+" : this.getDate(),                    //日 
	    "h+" : this.getHours(),                   //小时 
	    "m+" : this.getMinutes(),                 //分 
	    "s+" : this.getSeconds(),                 //秒 
	    "q+" : Math.floor((this.getMonth()+3)/3), 
	    "S"  : this.getMilliseconds()             //毫秒 
	  }; 
	  if(/(y+)/.test(fmt)) 
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	  for(var k in o) 
	    if(new RegExp("("+ k +")").test(fmt)) 
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
	  return fmt; 
	}
	//提成设置采用二个提成界面
	//第一个搜索页面
	function showSearch(){
		$("#divSearch").dialog({
			title:'搜索',
			modal:true
		})
		$("#divSearch").dialog("open");
	}
	//第二个搜索页面
	function showSearch_s(){
		$("#divSearch_s").dialog({
			title:'搜索',
			modal:true
		})
		$("#divSearch_s").dialog("open");
	}
	//关闭第一个搜索页面
	function closeSearch(){
		$("#divSearch").dialog("close");
	}
	//关闭第二个搜索页面
	function closeSearch_s(){
		$("#divSearch_s").dialog("close");
	}
	
	function closeDialog(){
		top.closeDialog();
	}
	function closeDialog2(){
		top.closeDialog2();
	}
	 function pagerFilter(data){
	    if (typeof data.length == 'number' && typeof data.splice == 'function'){    // 判断数据是否是数组
	        data = {
	            total: data.length,
	            rows: data
	        }
	    }
	    var dg = $(this);
	    var opts = dg.datagrid('options');
	    var pager = dg.datagrid('getPager');
	    pager.pagination({
	        onSelectPage:function(pageNum, pageSize){
	            opts.pageNumber = pageNum;
	            opts.pageSize = pageSize;
	            pager.pagination('refresh',{
	                pageNumber:pageNum,
	                pageSize:pageSize
	            });
	            dg.datagrid('loadData',data);
	        }
	    });
	    if (!data.originalRows){
	        data.originalRows = (data.rows);
	    }
	    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	    var end = start + parseInt(opts.pageSize);
	    data.rows = (data.originalRows.slice(start, end));
	    return data;
	}
	//保存中
	 function saving(title){
		   $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
    		$("<div class=\"datagrid-mask-msg\"></div>").html(title+"中，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	 }
	 function saved(){
		 $("body").children("div.datagrid-mask-msg").remove();  
		 $("body").children("div.datagrid-mask").remove();  
	 }
	 
	 function getFormData(frmId,params){
		 	var data = {};
			var fields = $("#" + frmId).serializeArray();
			if (params!=null) {
				data = params;
			}
			$.each(fields, function(i, field){
				data[field.name]=field.value;
				if (field.value != '') {
					data[field.name]=field.value;
				}else {
					data[field.name] = undefined;
				}
			});
			return data;
	}
	function setNull(frmId){
		var fields = $("#" + frmId).serializeArray();
		$.each(fields, function(i, field){
			if($("#"+field.name).attr("class") && $("#"+field.name).attr("class").lastIndexOf('easyui-numberbox')>=0){
				$("#"+field.name).numberbox("setValue", '');
			}else if($("#"+field.name).attr("class") && $("#"+field.name).attr("class").lastIndexOf('easyui-datebox')>=0){
				$("#"+field.name).datebox("setValue", '');
			}else if($("#"+field.name).attr("class") && $("#"+field.name).attr("class").lastIndexOf('easyui-textbox')>=0){
				$("#"+field.name).textbox("setValue", '');
			}else if($("#"+field.name).attr("class") && $("#"+field.name).attr("class").lastIndexOf('easyui-datetimebox')>=0){
				$("#"+field.name).datetimebox("setValue", '');
			}else if($("#"+field.name).attr("class") && $("#"+field.name).attr("class").lastIndexOf('easyui-combobox')>=0){
				$("#"+field.name).combobox("setValue", '');
			}else{
				$("#"+field.name).val('');
			}
		});
	}
	/*function exportExcel(){
		top.showAlert("待完善……");
		//doExportCommon("tbl");
	}*/
	
	//导出
	
	function doExportCommon(tableId,params,url,urlPart) {
		var strHead = "";
		var tr= $('#'+tableId).parent().find('tr');
		 for (var i = 0; i < tr.length; i++) {
			var td = tr.eq(i).find('td');
			if (td.length > 0) {
				for (var j = 0; j < td.length; j++) {
					if (typeof td.eq(j).attr("style")=="undefined" && td.eq(j).attr("field").length > 0) {
						var value  = td.eq(j).find('div').eq(0).html().replace(/<br[^>]*>/ig,'\r\n').replace(/(^\s*)|(\s*$)/g, "").replace(/,/g, "");
						if (typeof value !='undefined' && value.indexOf("<span>") > -1) {
							value = td.eq(j).find('span').eq(0).html().replace(/(^\s*)|(\s*$)/g, "").replace(/,/g, "");
						}
						strHead += value+","
					}
				}
				break;
			}
		}
		params['strHead'] = strHead.substring(0,strHead.length-1);
		ajax({
	    	type:'post',
	    	url:url,
	    	data:params,
	    	success:function(data){
	    		if (data.success) {
	    			 window.location.href="../../"+urlPart+"/loadExcel?fileName="+data.entity;
				}else {
					top.showAlert(data.message);
				}
	    	},
	    	error:function(){
	    		alert("网络原因,重试！")
	    	}
	    })
	}
	//公用ajax
	function invokAjax(url,paramsData,judge){
		saving(judge);
		$.ajax({
			type:'post',
			url:url,
			data:paramsData,
			timeout:10000,
			success:function(data){
				if (typeof (backFun) == "function") {
					backFun(data,judge);
				}
				saved();
			},
			error:function(){
				top.showAlert("网络原因，请重试");
				saved();
			}
		})
	}
	//确认提交ajax
	function confirmAjax(confirmTitle,confirmInfo,url,paramsData,judge){
		$.messager.confirm(confirmTitle, confirmInfo, function (data) {
            if (data) {
            	invokAjax(url,paramsData,judge);
            }
        });
	}
	
	//easyui提示框
	function tooltipFun(id,message,color){
		if (color==null) {
			color = "#fff"; 
		}
		$('#'+id).tooltip({
		    position: 'top',
		    content: "<span style=\"color:"+color+"\">"+message+"</span>",
		    hideDelay:3000,
		    onShow: function(){
				$(this).tooltip('tip').css({
					backgroundColor: '#666',
					borderColor: '#666'
				});
		    }
		});
		$('#'+id).tooltip("show")
	}
	function changeNumber(value){
		if (value==null) {
			return 0.00;
		}else {
			return value==''?0.00:parseFloat(value);
		}
	}
	function formatNumber(val){
		try{
			return typeof(val)!='undefined'&&val!=null?val.toFixed(2):'0.00';
		}catch(e){
			console.log(typeof(val));
		}
	}
	function formatString(value){
		if (value == null || typeof value == 'undefined' || value=='null') {
			return '';
		}
		return value;
	}
	function isFloat(n){
	    return Number(n) === n && n % 1 !== 0;
	}
	function isNumber(n){
	    return Number(n) === n;
	}
//	function dynamicTime(timeId,flag){
//		if (flag) {
//			setInterval(() => {
//				$("#"+timeId).prop("innerHTML",new Date().Format("yyyy-MM-dd hh:mm:ss"))
//			}, 1000);
//		}else {
//			$("#"+timeId).prop("innerHTML",new Date().Format("yyyy-MM-dd hh:mm:ss"))
//		}
//		if ($("#title").length > 0) {
//			$("title").eq(0).prop("innerHTML",$("#title").html())
//		}
//	}
	//打印dynamicCommon
	function dynamictTrHtml(widthList,valueList){
		var trHtml ='<tr>';
		for (var i = 0; i < widthList.length; i++) {
			trHtml +='<td ><div class="notx" style="width: '+widthList[i]+'">'+formatString(valueList[i])+'</div></td>';
		}
        trHtml+='</tr>';
		return trHtml;
	}
	function dynamictTableHtml(trId,nameList){
		var tableHtml = '<table  cellpadding="0" cellspacing="0" width="100%" border="0" align="center" class="tb1"><tbody><tr id='+trId+'>';
		for (var i = 0; i < nameList.length; i++) {
			tableHtml+= '<th >'+nameList[i]+'</th>';
		}
		tableHtml += '</tr></tbody></table>';
	    return tableHtml;
	}
	//打印设置
	 function printpage() {
        document.getElementById("noprint").style.display = "none"; //打印整个页面包括标题，在调用打印方法之前隐藏不需要打印的页面标签。无标题将不打印标题
        print();
    }
    function printsetup() {
        try {
            document.all.WebBrowser.ExecWB(8, 1);
        }
        catch (e) {
            //ymPrompt.errorInfo({title:SYS_NAME, message:'页面设置需要ActiveX, 请允许本站点使用ActiveX'});
        }
        printpage();
    }
    /**
     * 比较日期
     */
    function CompareDate(d1,d2){
    	return ((new Date(d1.replace(/-/g,"\/"))) > (new Date(d2.replace(/-/g,"\/"))));
    }
    