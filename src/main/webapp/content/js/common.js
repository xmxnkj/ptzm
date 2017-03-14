// tab
$(document).ready(function(){
	var $tab =$(".tab li");
	var $tab2 =$(".tab2 li");
	$tab.click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		var index = $tab.index(this); 
		$(this).parent().siblings(".tab-cont").eq(index).show().siblings(".tab-cont").hide();
	});	
	$tab2.click(function(){
		$(this).addClass("active").siblings().removeClass("active");
		var index = $tab2.index(this); 
		$(this).parent().siblings(".tab-cont2").eq(index).show().siblings(".tab-cont2").hide();
	});	
});

// height
$(function(){
	var page_h=document.body.clientHeight-80;
	var billing_h=document.body.clientHeight-204;
	$(".page-body").css("min-height",page_h+"px");
	$(".billing").css("min-height",billing_h+"px");
	$(".billing .fr").css("min-height",billing_h+"px");
});	