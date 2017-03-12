$(document).ready(function () {
    ReservationBilling();
    cancelbtn();
    ReservationBillingSubmit()
})

//结算弹窗
function ReservationBilling() {
    $('#ReservationBilling').click(function () {
        $('#ReservationBillingBox').show();
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
function ReservationBillingSubmit() {
    $("#ReservationBillingSubmit").click(function () {
        $('.shade').hide();
        $('.pop').hide();
    })
}