$(document).ready(function(){
	$("#clickHome").click(function(){
		hideAllPages();
		$("#home-con").fadeIn(500);
		classUpdate("#clickHome");
	});
	$("#clickCategory").click(function(){
		hideAllPages();
		$("#category-con").fadeIn(500);
		classUpdate("#clickCategory");
	});
	$("#clickPlan").click(function(){
		hideAllPages();
		$("#plan-con").fadeIn(500);
		classUpdate("#clickPlan");
	});
	$("#clickLog").click(function(){
		hideAllPages();
		$("#log-con").fadeIn(500);
		classUpdate("#clickLog");
	});
	$("#btnMenu").click(function(){
		$("#menuScreen").animate({width:'65%'},200);
	});
	$("#btnCloseMenu").click(function(){
		$("#menuScreen").animate({width:'0px'},200);
	});
});

function hideAllPages(){
	$("#home-con, #category-con, #plan-con, #log-con").hide();
}

function classUpdate(ele){
	$("#clickHome, #clickCategory, #clickPlan, #clickLog").removeClass("activeNavi");
	$(ele).addClass("activeNavi");
}