function setContent(node) {
    var nodeLink = $(node.text);
    $("#ifrContent").attr("src", $(node.text).attr("href"));
}

function setContentUrl(url){
	$("#ifrContent").attr("src", url);
}

function showAlert(info, fn){
	top.$.messager.alert(TITLE, info, 'warning', fn);
}

function showMsg(info, fn){
	top.$.messager.alert(TITLE, info, 'warning', fn);
}

function showConfirm(info, fn){
	top.$.messager.confirm(TITLE, info, fn);
}

function openDialog(url,title, width, height, isMaxed) {
	
	if(url.lastIndexOf("?")>=0)
		url=url+"&rnd=" + Math.random();
	else
		url=url+"?rnd=" + Math.random();
	
    $("#ifrDlg").attr("src", url);

    var options = {};
    if (title) {
        options.title = title;
    }
    if (width) {
        options.width = width;
    }
    if (height) {
        options.height = height;
    }
    if(isMaxed)
    	options.maximized = true;
    else
    	options.maximized = false;
    $("#dlg").dialog(options);
    $("#dlg").dialog("open", options);
    $("#dlg").dialog("center");
    if(typeof($("#ifrDlg")[0].contentWindow)=="object"
		&& typeof($("#ifrDlg")[0].contentWindow.$)=="function"
		&& typeof($("#ifrDlg")[0].contentWindow.$.messager)=="object"
		){
		$("#ifrDlg")[0].contentWindow.$.messager.progress();
}
}

var extData = null;
function openDialog2(url,title, width, height, extDlgData, isMaxed) {
	if(url.lastIndexOf("?")>=0)
		url=url+"&rnd=" + Math.random();
	else
		url=url+"?rnd=" + Math.random();
	//$("#ifrDlg2").attr("src", "");
    $("#ifrDlg2").attr("src", url);

    var options = {};
    if (title) {
        options.title = title;
    }
    if (width) {
        options.width = width;
    }
    if (height) {
        options.height = height;
    }
    if(isMaxed)
    	options.maximized = true;
    else
    	options.maximized = false;
    $("#dlg2").dialog(options);
    $("#dlg2").dialog("open", options);
    $("#dlg2").dialog("center");
    
	if(typeof($("#ifrDlg2")[0].contentWindow) == "object"
		&& typeof($("#ifrDlg2")[0].contentWindow.$)=="function" 
		&& typeof($("#ifrDlg2")[0].contentWindow.$.messager) == "object"){
		$("#ifrDlg2")[0].contentWindow.$.messager.progress();
	}
	extData = extDlgData;
}

function refreshContent(){
	if(typeof($("#ifrContent")[0].contentWindow.refreshData)=="function"){
		$("#ifrContent")[0].contentWindow.refreshData();
	}
}

function refreshContentTree(){
	if(typeof($("#ifrContent")[0].contentWindow.refreshTree)=="function"){
		$("#ifrContent")[0].contentWindow.refreshTree();
	}
}

function getContentWindow(func){
	return $("#ifrContent")[0].contentWindow;
}

function refreshDialogContent(){
	var w = getDialogContentWindow();
	if(typeof(w.refreshData)=="function"){
		w.refreshData();
	}
}

function getDialogContentWindow(){
	return $("#ifrDlg")[0].contentWindow;
}

function getDialogContentWindow2(){
	return $("#ifrDlg2")[0].contentWindow;
}


function closeDialog(){
	$("#dlg").dialog('close');
}

function closeDialog2(){
	$("#dlg2").dialog('close');
}
function trim(str)
{ 
 return str.replace(/(^\s*)|(\s*$)/g, ""); 
}