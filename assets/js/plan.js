var selected_p_id = "";
$(document).ready(function(){
	
	$("#btnPlusPlan").click(function(){
		var p = Android.checkCurrentPlan();
		if(p == "0"){
			$(this).hide();
			$("#overlay").show();
			$("#create_new_plan_box").fadeIn(100);
			planInputBoxReady();			
		}
		else{
			Android.showText("Already having a current plan.");
		}

	});

	$("#btnCancelPlan").click(function(){
		$("#overlay").hide();
		$("#create_new_plan_box").hide();
		$("#btnPlusPlan").show();
	});

	$(".btnCancelPlanOption").click(function(){
		$("#overlay").hide();
		$("#cp-option-box,#op-option-box").hide();
		$("#btnPlusPlan").show();
	});

	$("#btnAbortPlan").click(function(){
		$("#overlay").hide();
		$("#cp-option-box,#op-option-box").hide();
		$("#btnPlusPlan").show();
		Android.abortPlan();
		refreshPlan();
	});

	$("#btnPlanRemoveFromHistory").click(function(){
		$("#overlay").hide();
		$("#cp-option-box,#op-option-box").hide();
		$("#btnPlusPlan").show();
		Android.removePlanFromHistory(selected_p_id);
		refreshPlan();
	});

	$("#btnCreatPlan").click(function(){
		validateAndCreatPlan();
	});
});

function getCurrentPlan(){
	var content = "";
	var data = Android.getCurrentPlan();
	try{ 
		var ps = JSON.parse(data);
		if(ps.length>0){
			for(i=0;i<ps.length;i++){
				var id = ps[i].id;
				var name = ps[i].name;
				var startDate = ps[i].startDate;
				var endDate = ps[i].endDate;
				var planAmount = ps[i].amount;
				var usedAmount = parseFloat(Android.getExpenseAmountTotalByPlanId(id));
				var usedPercent =  parseInt(usedAmount*100 / planAmount);

				content += "<div class='panel panel-info'>";
				content += "<div class='panel-heading'>"+"<span class='glyphicon glyphicon-chevron-down cp'></span>&nbsp;&nbsp;"+name+"</div>";
				content += "<div class='panel-body'>";
				content += "<span class='plan_start_date'>"+startDate+"</span>";
				content += "<span class='plan_amount'>"+planAmount+"</span>";
				content += "<span class='plan_end_date'>"+endDate+"</span>";
				content += "<br>";
				content += "<div class='progress'>";
				content += "<div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='70' aria-valuemin='0' aria-valuemax='100' style='width:"+usedPercent+"%'>";
				content += usedPercent+"%";
				content += "</div>";
				content += "</div>";
				content += usedAmount+" Used";
				content += "</div>"
				content += "</div>";
			}
			$("#currentPlanAutoGeneratorTag").html(content);

			$(".cp").click(function(){
				$("#overlay").show();
				$("#cp-option-box").show();
			});
		}
		else{
			$("#currentPlanAutoGeneratorTag").html("<h3 style='text-align:center;color:#ccc;'>NO CURRENT PLAN</h3>");
		}
	}
	catch(e){
		$("#currentPlanAutoGeneratorTag").html(e+"<br>"+data);
	}
}

function getOlderPlans(){
	var content = "";
	var data = Android.getOlderPlans();
	try{ 
		var ps = JSON.parse(data);
		if(ps.length>0){
			for(i=0;i<ps.length;i++){
				var id = ps[i].id;
				var name = ps[i].name;
				var startDate = ps[i].startDate;
				var endDate = ps[i].endDate;
				var planAmount = ps[i].amount;
				var usedAmount = parseFloat(Android.getExpenseAmountTotalByPlanId(id));
				var usedPercent =  parseInt(usedAmount*100 / planAmount);
				var statusCode = ps[i].status;
				var status = "";
				if (statusCode==1){
					status = "<span class='label label-success'>Won</span>";
					
				}
				else if(statusCode==2){
					status = "<span class='label label-danger'>Failed</span>";
				}
				else if(statusCode==3){
					status = "<span class='label label-danger'>Aborted</span>";
				}
				content += "<div class='panel panel-info'>";
				content += "<div class='panel-heading'>"+"<span class='glyphicon glyphicon-chevron-down op' pid='"+id+"'></span>&nbsp;&nbsp;"+name+status+"</div>";
				content += "<div class='panel-body'>";
				content += "<span class='plan_start_date'>"+startDate+"</span>";
				content += "<span class='plan_amount'>"+planAmount+"</span>";
				content += "<span class='plan_end_date'>"+endDate+"</span>";
				content += "<br>";
				content += "<div class='progress'>";
				content += "<div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='70' aria-valuemin='0' aria-valuemax='100' style='width:"+usedPercent+"%'>";
				content += usedPercent+"%";
				content += "</div>";
				content += "</div>";
				content += usedAmount+" Used";
				content += "</div>";
				content += "</div>";
			}
			$("#olderPlanAutoGeneratorTag").html(content);

			$(".op").click(function(){
				selected_p_id = $(this).attr("pid");
				$("#overlay").show();
				$("#op-option-box").show();
			});
		}
		else{
			$("#olderPlanAutoGeneratorTag").html("<h3 style='text-align:center;color:#ccc;'>NO OLDER PLANS</h3>");
		}
	}
	catch(e){
		$("#olderPlanAutoGeneratorTag").html(e+"<br>"+data);
	}
}

function planInputBoxReady(){
    $("#new_plan_name").val("");
    $("#new_plan_end_date").val("");
    $("#new_plan_amount").val("");

    var currentdate = new Date(); 
    var yyyy = currentdate.getFullYear();
    var mm = (currentdate.getMonth()+1)+"";
    var dd = currentdate.getDate()+"";
    if(mm.length == 1) mm = "0"+mm;
    if(dd.length == 1) dd = "0"+dd;
    var dt = yyyy+"-"+mm+"-"+dd;
    $("#new_plan_start_date").val(dt);
    $("#new_plan_end_date").attr("min",dt);

    var nextYear = yyyy + 1;
    var nextMonth = currentdate.getMonth()+1;
    var nextDay = currentdate.getDate();
    if(nextMonth==2 && nextDay>=28 && leapYear(nextYear)){
        nextDay = 29;
    }

    nextMonth = nextMonth + "";
    nextDay = nextDay + "";
    if(nextMonth.length == 1) nextMonth = "0"+nextMonth;
    if(nextDay.length == 1) nextDay = "0"+nextDay;
    dt = nextYear+"-"+nextMonth+"-"+nextDay;

    $("#new_plan_end_date").attr("max",dt);
}

function addPlan(){
	var planName = $("#new_plan_name").val();
	var planStartDate = $("#new_plan_start_date").val();
	var planEndDate = $("#new_plan_end_date").val();
	var planAmount = parseInt($("#new_plan_amount").val());
	Android.addPlan(planName,planStartDate,planEndDate,planAmount);
}

function validateAndCreatPlan(){
    var errMessage = "Error";
    var planName = $("#new_plan_name").val();planName = planName.trim();
    if(planName=="") errMessage += "\nPlan name can't be empty.";
    
    var planStartDate = $("#new_plan_start_date").val();
    var planEndDate = $("#new_plan_end_date").val();
    if(planEndDate=="") errMessage += "\nPlan end date can't be empty.";

    var x = $("#new_plan_amount").val()+"";
    var y = x.split(".");
    var amount;

    if((y.length==1 || y.length==2) && x!=""){
        amount = parseFloat(x).toFixed(2);
    }
    if(!amount || amount<1){
        errMessage += "\nPlease insert valid amount.";
    }

    if(errMessage=="Error"){
        $("#overlay").hide();
        $("#create_new_plan_box").hide();
        $("#btnPlusPlan").show();
        Android.addPlan(planName,planStartDate,planEndDate,amount);
        refreshPlan();
    }
    else{
        Android.showText(errMessage);
    }
}

function refreshPlan(){
	try{
		getCurrentPlan();
		getOlderPlans();
	}
	catch(e){
		alert("Plan.js Error:"+e);
	}
}