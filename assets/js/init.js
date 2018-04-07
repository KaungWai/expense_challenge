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
	
	
	var isFirstTime = localStorage.getItem("isFirstTime");
	if(isFirstTime != "yes"){
		Android.createMiscCategory();
	}
	localStorage.setItem("isFirstTime","yes");
	refreshHome();
});

// override trim to remove first and last spaces
if (typeof String.prototype.trim != 'function') { // detect native implementation
  		String.prototype.trim = function () {
    		return this.replace(/^\s+/, '').replace(/\s+$/, '');
  	};
}