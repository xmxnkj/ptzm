/**
 * Created by Administrator on 2017/10/30.
 */

$(document).ready(function () {
    operate();
    operateSubmit();
    cancelbtn();
})

//操作弹窗
function operate() {
    $("#operate").click(function () {
        $('#operateBox').show();
        $('#shade').show();
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

//预约状态提交
function operateSubmit() {
    $("#operateSubmit").click(function () {
        $('.shade').hide();
        $('.pop').hide();
    })
}