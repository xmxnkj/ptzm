function ajax(params){
	var success=params["success"];
	var error=params["error"];
	params["success"]=function(data){
		top.hideLoading();
		if(success){
			success(data);
		}
	}
	params["error"]=function(){
		top.hideLoading();
		if(error){
			error();
		}
	}
	top.showLoading();
	$.ajax(params);
}

function dealJsonResult(data, msg, fn, callback,errmsg){
	
	var result = null;
	if(typeof(data)=='string'){
		try{
			result = top.$.parseJSON(data);
		}catch(e){
			
		}
	}else{
		result = data;
	}
	if (result && result.success) {
		if(result.message!=null&&result.message!=""){
			top.$.messager.alert(WINDOW_CAPTION, result.message, 'info');
		}else if(msg!=null && msg != ""){
			top.$.messager.alert(WINDOW_CAPTION, msg, 'info', fn);
		}else {
			top.$.messager.alert(WINDOW_CAPTION, "成功", 'info', fn);
		}
        if(typeof(callback) == "function"){
        	callback(result);
        }
        
        if(result.id){
        	$("#id").val(result.id);
        }
        
        return true;
    } else if (result) {
        top.$.messager.alert(WINDOW_CAPTION, result.message, 'error');
    } else {
    	if(errmsg!=null && errmsg!="")
    		top.$.messager.alert(WINDOW_CAPTION, errmsg, 'error');
    	else
    		top.$.messager.alert(WINDOW_CAPTION, DEFAULT_ERROR, 'error');
    }
    return false;
}

function checkDateTime(str){	  
    var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
    var r = str.match(reg); 
    if(r==null)return false; 
    r[2]=r[2]-1; 
    var d= new Date(r[1], r[2],r[3], r[4],r[5], r[6]); 
    if(d.getFullYear()!=r[1])return false; 
    if(d.getMonth()!=r[2])return false; 
    if(d.getDate()!=r[3])return false; 
    if(d.getHours()!=r[4])return false; 
    if(d.getMinutes()!=r[5])return false; 
    if(d.getSeconds()!=r[6])return false; 
    return true;
}

function checkDate(dateStr){
	var datePat = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/;
    var matchArray = dateStr.match(datePat);
    if (matchArray == null) return false;
    var month = matchArray[3];
    var day = matchArray[5];
    var year = matchArray[1];
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > 31) return false;
    if ((month==4 || month==6 || month==9 || month==11) && day==31) return false;
    if (month == 2){
        var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
        if (day > 29 || (day==29 && !isleap)) return false;
    }
    return true;
}

function loadFilter(data){
	return data.rows;
}

function getFloatValue(value){
	return typeof(value)!='undefined'&&value!=null&&value!=''?parseFloat(value):0;
}

function getNumberBoxValue(id){
	return getFloatValue($("#"+id).numberbox("getValue"));
}

function genderFormatter(value){
	return value==0?'男':'女';
}

function nbnoFormatter(value,row,index){
	return row?(row.nbzone+row.nbno):"";
}

function carModelFormatter(value,row,index){
	return (row&&row.carBrand?row.carBrand.name:"")+(row&&row.carModel?row.carModel.name:"");
}

function gradeFormatter(val,row,index){
	return val?val+'星':'1星';
}

function shopFormatter(val,row,index){
	return row&&row.shop?row.shop.name:'';
}

function nameFormatter(val,row,index){
	return row?row.name:"";
}

function mobileFormatter(val,row,index){
	return row?row.mobile:"";
}

function isCardFormatter(val,row,index){
	return row&&row.cardNumber!=null&&row.cardNumber!=''?'会员':'非会员';
}

function prePayAccountFormatter(val,row,index){
	return row?row.prePayAccount:"";
}

function clientUserFormatter(val,row,index){
	return row&&row.clientUser?row.clientUser.name:'';
}

function opUserFormatter(val,row,index){
	return row&&row.opUser?row.opUser.name:'';
}

function terminalFormatter(value,row,index){
		var ter="电脑";
		switch(value){
		case "Pc":
			break;
		case "MiniApps":
			ter="小程序";
			break;
		case "App":
			ter="App";
			break;
		case "WeChat":
			ter="公号";
			break;
		}
		return ter;
}

function opTypeFormatter(value,row,index){
	var v="消费";
	var at = 'PrePay';
	if(row&&row.accountType){
		at=row.accountType;
	}
	switch(value){
	case "In":
		v=at=='PrePay'?"收入（本店）":"消费";
		break;
	case "OtherShopIn":
		v=at=='PrePay'?"收入（异店）":"消费";
		break;
	case "Out":
		v=at=='PrePay'?"消费":"使用";
		break;
	case "Back":
		v="退款";
		break;
	case "Modify":
		v="修改"
		break;
	case "Sign":
		v="签到";
		break;
	case "StartBack":
		v="期初退款";
		break;
	}
	return v;
}

function goodsCode(value,row,index){
	return row.goods?row.goods.code:'';
}

function goodsName(value,row){
	return row.goods?row.goods.name:'';
}

function goodsSpec(value,row){
	return row.goods?row.goods.spec:'';
}
function goodsPrice(value,row){
	return row.goods&&row.goods.price!=null?row.goods.price.toFixed(2):'';
}

function wareHouseName(value,row){
	return row.wareHouse?row.wareHouse.name:'';
}

function billType(val,billItem){
	var tp='';
	switch(val){
	case 'Sale':
		tp='销售';
		break;
	case 'In':
		tp='采购';
		break;
	case 'Out':
		tp='领料';
		break;
	case 'Check':
		tp='盘点';
		break;
	case 'Cancel':
		tp='报损';
		break;
	case 'StockBack':
		tp='采购退货';
		break;
	case 'Transfer':
		if(top.shopId==billItem.wareHouse.shop.id){
			tp='调出';
		}else{
			tp='调入';
		}
		break;
	}
	return tp;
}

function costNumberFormatter(val){
	if (typeof ViewCost != 'undefined' && ViewCost=='true') {
		try{
			return (typeof(val)!='undefined'&&val!=null)?val.toFixed(2):'0.00';
		}catch(e){
			console.log(typeof(val));
		}
	}else {
		return "***";
	}
}


function numberFormatter(val){
	try{
		return typeof(val)!='undefined'&&val!=null?val.toFixed(2):'0.00';
	}catch(e){
		console.log(typeof(val));
	}
}

function isInt(n){
    return Number(n) === n && n % 1 === 0;
}

function isFloat(n){
    return Number(n) === n && n % 1 !== 0;
}
