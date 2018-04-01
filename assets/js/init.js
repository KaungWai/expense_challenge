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

	try{
		getAllCategories();
	}
	catch(e){
		alert("init.js Error:"+e);
	}
	
});