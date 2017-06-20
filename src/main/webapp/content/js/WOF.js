/**
 * Created by Administrator on 2017/10/26.
 */
$(document).ready(function () {
    check();
    checkboxSubmit();
    cancelbtn();
    sheet()
})

function check() {
    $('#check').click(function () {
        $('#checkbox').show();
        $('.shade').show();
    })
}

//车检
function checkboxSubmit() {
    $('#checkboxSubmit').click(function () {
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
        $('.shade').hide();
        $('.pop').hide();
    })
}

//所有弹窗关闭
function cancelbtn() {
    $(".cancelbtn").click(function () {
        $('.shade').hide();
        $('.pop').hide();
        return
    })
}
//作废
function sheet() {
    $('.sheet').click(function () {
        $('#ViewDocuments').show();
        $('.shade').show();
    })
}