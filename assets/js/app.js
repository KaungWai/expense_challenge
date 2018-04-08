$(document).ready(function(){
	$("#clickHome").click(function(){
		hideAllPages();
		refreshHome();
		$("#home-con").fadeIn(500);
		classUpdate("#clickHome");
	});
	$("#clickCategory").click(function(){
		hideAllPages();
		refreshCategory();
		$("#category-con").fadeIn(500);
		classUpdate("#clickCategory");
	});
	$("#clickPlan").click(function(){
		hideAllPages();
		refreshPlan();
		$("#plan-con").fadeIn(500);
		classUpdate("#clickPlan");
	});
	$("#clickLog").click(function(){
		hideAllPages();
		refreshExpenseLog();
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

function userGuideShow(){
	$("#splash").hide();
	$("#menuScreen").animate({width:'0px'},200);
	$("#userGuide").show();
}

function userGuideHide(){
	$("#userGuide").hide();
}

function splashSkip(){
	$("#splash").hide();
}

function hideAllPages(){
	$("#home-con, #category-con, #plan-con, #log-con").hide();
}

function classUpdate(ele){
	$("#clickHome, #clickCategory, #clickPlan, #clickLog").removeClass("activeNavi");
	$(ele).addClass("activeNavi");
}

function about(){
	$("#menuScreen").animate({width:'0px'},200);
	$("#overlay").show();
	$("#aboutBox").show();
}