/* for page preparing */
$(document).ready(function(){
	var naviBarHeight = $("#barNavi").outerHeight() + 10;
	console.log(naviBarHeight);
	$("#pd-btn").css({
		"height":naviBarHeight+"px"
	});

	getAllCategories();
});