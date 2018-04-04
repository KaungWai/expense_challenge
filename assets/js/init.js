/* for page preparing */
$(document).ready(function(){
	var headBarHeight = $(".headBar").outerHeight();
	console.log(headBarHeight);
	$("#pd-top").css({
		"height":headBarHeight+"px"
	});

	var naviBarHeight = $("#barNavi").outerHeight() + 10;
	console.log(naviBarHeight);
	$("#pd-btn").css({
		"height":naviBarHeight+"px"
	});
	
	refreshHome();

	var isFirstTime = localStorage.getItem("isFirstTime");
	if(isFirstTime != "yes"){
		Android.createMiscCategory();
	}
	localStorage.setItem("isFirstTime","yes");
});