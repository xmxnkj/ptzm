function addDays(date,days){ 
	var nd = new Date(date);  
	   nd = nd.valueOf(); 
	   nd = nd + days * 24 * 60 * 60 * 1000; 
	   nd = new Date(nd); 
	   //alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() + "日"); 
	var y = nd.getFullYear(); 
	var m = nd.getMonth()+1; 
	var d = nd.getDate(); 
	if(m <= 9) m = "0"+m; 
	if(d <= 9) d = "0"+d; 
	var cdate = y+"-"+m+"-"+d; 
	return new Date(cdate); 
} 

function  dateDiff(date1,  date2){    //sDate1和sDate2是2006-12-18格式  
    return parseInt(Math.abs(date1  -  date2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数  
}

function initExt(){
	Date.prototype.format = function(fmt){ 
		//author: meizz   
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	}  
}

initExt();