$(document).ready(function () {
    Settlement();
    cancelbtn();
    SettlementSubmit();
    operate();
})

//结算弹窗
function Settlement() {
    $('#Settlement').click(function () {
        $('#SettlementBox').show();
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

//结算提交
function SettlementSubmit() {
    $("#SettlementSubmit").click(function () {
        $('.shade').hide();
        $('.pop').hide();

    })
}

