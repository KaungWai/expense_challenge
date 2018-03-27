$(document).ready(function(){
	$("#btnPlusPlan").click(function(){
		$(this).hide();
			$("#overlay").show();
			$("#create_new_plan_box").fadeIn(100);
	});
	$("#btnCancelPlan").click(function(){
		$("#overlay").hide();
		$("#create_new_plan_box").hide();
		$("#btnPlusPlan").show();
	});
	$("#btnCreatPlan").click(function(){
		$("#overlay").hide();
		$("#create_new_plan_box").hide();
		$("#btnPlusPlan").show();
		addPlan();
		getCurrentPlan();
		getOlderPlans();
	});
	getCurrentPlan();
	getOlderPlans();
});

function getCurrentPlan(){
	var content = "";
	var data = Android.getCurrentPlan();
	try{ 
		var ps = JSON.parse(data);
		
		for(i=0;i<ps.length;i++){
			var id = ps[i].id;
			var name = ps[i].name;
			var startDate = ps[i].startDate;
			var endDate = ps[i].endDate;
			var amount = ps[i].amount;
			content += "<div class='panel panel-default'>";
			content += "<div class='panel-heading'>"+name+"</div>";
			content += "<div class='panel-body'>";
			content += "<span class='plan_start_date'>"+startDate+"</span>";
			content += "<span class='plan_amount'>"+amount+"</span>";
			content += "<span class='plan_end_date'>"+endDate+"</span>";
			content += "<br>";
			content += "<div class='progress'>";
			content += "<div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='70' aria-valuemin='0' aria-valuemax='100' style='width:70%'>";
			content += "10%";
			content += "</div>";
			content += "</div>";
			content += "Current";
			content += "</div>"
			content += "</div>";
		}
		
		$("#currentPlanAutoGeneratorTag").html(content);
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
		
		for(i=0;i<ps.length;i++){
			var id = ps[i].id;
			var name = ps[i].name;
			var startDate = ps[i].startDate;
			var endDate = ps[i].endDate;
			var amount = ps[i].amount;
			var statusCode = ps[i].status;
			var status = "";
			if (statusCode==1){
				status = "Success";
			}
			else if(statusCode==2){
				status = "Failed";
			}
			else if(statusCode==3){
				status = "Failed (Aborted)";
			}

			content += "<div class='panel panel-default'>";
			content += "<div class='panel-heading'>"+name+"</div>";
			content += "<div class='panel-body'>";
			content += "<span class='plan_start_date'>"+startDate+"</span>";
			content += "<span class='plan_amount'>"+amount+"</span>";
			content += "<span class='plan_end_date'>"+endDate+"</span>";
			content += "<br>";
			content += "<div class='progress'>";
			content += "<div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='70' aria-valuemin='0' aria-valuemax='100' style='width:70%'>";
			content += "10%";
			content += "</div>";
			content += "</div>";
			content += status;
			content += "</div>"
			content += "</div>";
		}
		
		$("#olderPlanAutoGeneratorTag").html(content);
	}
	catch(e){
		$("#olderPlanAutoGeneratorTag").html(e+"<br>"+data);
	}
}

function addPlan(){
	var planName = $("#new_plan_name").val();
	var planStartDate = $("#new_plan_start_date").val();
	var planEndDate = $("#new_plan_end_date").val();
	var planAmount = parseInt($("#new_plan_amount").val());
	Android.addPlan(planName,planStartDate,planEndDate,planAmount);
}