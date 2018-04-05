/* for page preparing */
	var ctx = document.getElementById('chart').getContext('2d');
	var iChart = new Chart(ctx,{
		type : 'bar',
		data : {
			  labels : [],
			datasets : [{
			   label : 'Usage',
				data : []
			}],
		},
		options: {
	        legend: {
	            display : false
	        },
	        scales: {
		        yAxes: [{
		            ticks: {
		                beginAtZero: true
		            }
		        }]
		    }
	    }
	});
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