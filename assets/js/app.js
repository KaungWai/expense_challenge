$(document).ready(function(){
	$("#clickHome").click(function(){
		var ret = Android.clickHome();
		if(ret!="")
			alert(ret);
	});
	$("#clickCategory").click(function(){
		var ret = Android.clickCategory();
		if(ret!="")
			alert(ret);
	});
	$("#clickPlan").click(function(){
		var ret = Android.clickPlan();
		if(ret!="")
			alert(ret);
	});
	$("#clickLog").click(function(){
		var ret = Android.clickLog();
		if(ret!="")
			alert(ret);
	});
});