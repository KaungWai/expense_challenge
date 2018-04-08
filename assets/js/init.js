/* for page preparing */
$(document).ready(function(){
	var headBarHeight = $(".headBar").outerHeight();
	$("#pd-top").css({
		"height":headBarHeight+"px"
	});

	var naviBarHeight = $("#barNavi").outerHeight() + 10;
	$("#pd-btn").css({
		"height":naviBarHeight+"px"
	});
	
	winPlan();
	var isFirstTime = localStorage.getItem("isFirstTime");
	if(isFirstTime != "yes"){
		$("#splash").show();$(".btnSplash").show();
		Android.createMiscCategory();
	}
	else{
		$("#splash").animate({width:"100%"},2000,function(){
			$("#splash").hide();
		});
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

function winPlan(){
	if(Android.checkCurrentPlan() != "0"){
	    var currentdate = new Date(); 
	    var yyyy = currentdate.getFullYear();
	    var mm = (currentdate.getMonth()+1)+"";
	    var dd = currentdate.getDate()+"";
	    if(mm.length == 1) mm = "0"+mm;
	    if(dd.length == 1) dd = "0"+dd;
	    dNow = new Date(yyyy+"-"+mm+"-"+dd).getTime();

	    var data = Android.getCurrentPlan();
	    var plan = JSON.parse(data);
	    var endDate = plan[0].endDate;
	    dPlanEnd = new Date(endDate).getTime();
	    if(dNow>dPlanEnd){
	        $("#overlay").show();
	        $("#successBox").show();
	        Android.winPlan();
	    }   
	}
}