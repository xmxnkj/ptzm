/**
 * Created by Administrator on 2017/10/24.
 */
$(document).ready(function () {
    quotedPrice();
    construct();
    settleAccounts();
    cancelbtn();
    quotedPriceSubmit();
    constructBoxSubmit();
    settleAccountsBoxSubmit();
    Perfect();
    dropDownList();
    jquertUI();
    PerfectDataSubmit();
    VehicleConditionInspection();
    VehicleConditionInspectionBoxSubmit();
    AddName();
    AddNameBoxSubmit();
});
//报价弹窗
function quotedPrice() {
    $("#quotedPrice").click(function () {
        $('#quotedPriceBox').show();
        $('#shade').show();
    })
}
//施工弹窗
function construct() {
    $("#construct").click(function () {
        $('#constructBox').show();
        $('#shade').show();
    })
}
//结算弹窗
function settleAccounts() {
    $("#settleAccounts").click(function () {
        $('#settleAccountsBox').show();
        $('#shade').show();

    })
}
//车况检查弹窗
function VehicleConditionInspection() {
    $("#VehicleConditionInspection").click(function () {
        $("#VehicleConditionInspectionBox").show();
        $('#shade').show();
    })
}
//完善资料弹窗
function Perfect() {
    $('#Perfect').click(function () {
        $('#PerfectData').show();
        $('#shade').show();
    })
}
function AddName() {
    $("#AddName").click(function () {
        $("#AddNameBox").show();
        $('#shade').show();

    })
}
//所有弹窗关闭
function cancelbtn() {
    $(".cancelbtn").click(function () {
        $('#shade').hide();
        $('.pop').hide();
        return
    })
}

//施工确认表单提交ajax
function constructBoxSubmit() {
    $("#constructBoxSubmit").click(function () {
        alert('施工确认提交完毕');
        // $.ajax({
        //     type: "GET", //GET或POST,
        //     async:true, //默认设置为true，所有请求均为异步请求。
        //     url: "http://www.idaima.com/xxxxx.php",
        //     data: {
        //         username: $("#username").val(),
        //         content: $("#content").val()
        //     },
        //     dataType: "json", //xml、html、script、jsonp、text
        //     beforeSend:function(){},
        //     complete:function(){},
        //     success: function(data) {
        //         alert(data)
        //     },
        //     error:function(){},
        // });
        $('#shade').hide();
        $('.pop').hide();
    })
}
//结算单表单提交ajax
function quotedPriceSubmit() {
    $("#quotedPriceSubmit").click(function () {
        alert('结算单提交完毕');
        // $.ajax({
        //     type: "GET", //GET或POST,
        //     async:true, //默认设置为true，所有请求均为异步请求。
        //     url: "http://www.idaima.com/xxxxx.php",
        //     data: {
        //         username: $("#username").val(),
        //         content: $("#content").val()
        //     },
        //     dataType: "json", //xml、html、script、jsonp、text
        //     beforeSend:function(){},
        //     complete:function(){},
        //     success: function(data) {
        //         alert(data)
        //     },
        //     error:function(){},
        // });
        $('#shade').hide();
        $('.pop').hide();
    })
}
//报价确认单表单提交ajax
function settleAccountsBoxSubmit() {
    $("#settleAccountsBoxSubmit").click(function () {
        alert('报价确认单提交完毕');
        // $.ajax({
        //     type: "GET", //GET或POST,
        //     async:true, //默认设置为true，所有请求均为异步请求。
        //     url: "http://www.idaima.com/xxxxx.php",
        //     data: {
        //         username: $("#username").val(),
        //         content: $("#content").val()
        //     },
        //     dataType: "json", //xml、html、script、jsonp、text
        //     beforeSend:function(){},
        //     complete:function(){},
        //     success: function(data) {
        //         alert(data)
        //     },
        //     error:function(){},
        // });
        $('#shade').hide();
        $('.pop').hide();
    })
}
//填写个人资料提交ajax
function PerfectDataSubmit() {
    $('#PerfectDataSubmit').click(function () {
        alert('填写个人资料提交完毕');
        // $.ajax({
        //     type: "GET", //GET或POST,
        //     async:true, //默认设置为true，所有请求均为异步请求。
        //     url: "http://www.idaima.com/xxxxx.php",
        //     data: {
        //         username: $("#username").val(),
        //         content: $("#content").val()
        //     },
        //     dataType: "json", //xml、html、script、jsonp、text
        //     beforeSend:function(){},
        //     complete:function(){},
        //     success: function(data) {
        //         alert(data)
        //     },
        //     error:function(){},
        // });
        $('#shade').hide();
        $('.pop').hide();
    })
}
//填写车况检查提交ajax
function VehicleConditionInspectionBoxSubmit() {
    $('#VehicleConditionInspectionBoxSubmit').click(function () {
        alert('填写车况检查提交完毕');
        // $.ajax({
        //     type: "GET", //GET或POST,
        //     async:true, //默认设置为true，所有请求均为异步请求。
        //     url: "http://www.idaima.com/xxxxx.php",
        //     data: {
        //         username: $("#username").val(),
        //         content: $("#content").val()
        //     },
        //     dataType: "json", //xml、html、script、jsonp、text
        //     beforeSend:function(){},
        //     complete:function(){},
        //     success: function(data) {
        //         alert(data)
        //     },
        //     error:function(){},
        // });
        $('#shade').hide();
        $('.pop').hide();
    })
}
//新增商品添加ajax
function AddNameBoxSubmit() {
    $('#AddNameBoxSubmit').click(function () {
        alert('新增商品添加提交完毕');
        // $.ajax({
        //     type: "GET", //GET或POST,
        //     async:true, //默认设置为true，所有请求均为异步请求。
        //     url: "http://www.idaima.com/xxxxx.php",
        //     data: {
        //         username: $("#username").val(),
        //         content: $("#content").val()
        //     },
        //     dataType: "json", //xml、html、script、jsonp、text
        //     beforeSend:function(){},
        //     complete:function(){},
        //     success: function(data) {
        //         alert(data)
        //     },
        //     error:function(){},
        // });
        $('#shade').hide();
        $('.pop').hide();
    })
}

//下拉框初始化
function dropDownList() {
    $('#fade').editableSelect({effects: 'fade'});//微信
    $('#sex').editableSelect({effects: 'fade'});//性别
    $('#starLevel').editableSelect({effects: 'fade'});//星级
    $('#group').editableSelect({effects: 'fade'});//集团
    $('#type').editableSelect({effects: 'fade'});//类型
    $('#namePart').editableSelect({effects: 'fade'});//品名
    $('#specifications').editableSelect({effects: 'fade'});//规格
    $('#encoded').editableSelect({effects: 'fade'});//编码
    $('#Category').editableSelect({effects: 'fade'});//所属分类
    $('#search').editableSelect({effects: 'fade'});//搜索
}
//jqueryUI控件初始化
function jquertUI() {
    $( "#LastMaintenanceTime" ).datepicker();//上次保养时间
    $( "#NextMaintenanceTime" ).datepicker();//下次保养时间
    $( "#AnnualSurveyBecomeDue" ).datepicker();//年审到期时间
    $( "#ExpirationOfInsurance" ).datepicker();//保险到期时间
    $( "#birthday" ).datepicker();//生日时间

}

