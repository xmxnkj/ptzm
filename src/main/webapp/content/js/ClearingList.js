/**
 * Created by Administrator on 2017/10/26.
 */
$(document).ready(function () {
    sheet();
    cancelbtn();
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