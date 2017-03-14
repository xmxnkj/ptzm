/**
 * Created by Administrator on 2017/10/26.
 */
$(document).ready(function () {
    sheet();
    cancelbtn();
    deliverySchedules();
    ScheduleConstructionSubmit();
})

function sheet() {
    $('.sheet').click(function () {
        $('#ViewDocuments').show();
        $('#shade').show();
    })
}

function cancelbtn() {
    $('.cancelbtn').click(function () {
        $('#shade').hide();
        $('.pop').hide();
        return
    })
}

function deliverySchedules() {
    $('#deliverySchedules').click(function () {
        if ($('#checkbox-id').prop('checked')){
            alert('已勾选');
            $('#shade').show();
            $('#ScheduleConstruction').show()
        } else {
            alert('未选中')
        }
    })
}
function ScheduleConstructionSubmit() {
    $('#ScheduleConstructionSubmit').click(function () {
        alert('已提交');
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