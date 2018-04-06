

$(document).ready(function(){

	$("#btnExpansePlus").click(function(){
		setDateTime();
		$(this).hide();
		$("#overlay").show();
		$("#expense_input_box").fadeIn(100);
	});

	$("#btn_new_expense_add").click(function(){
		var failOrKeep = addExpense();
		if(failOrKeep == "fail"){
			$("#expense_input_box").hide();
			$("#failBox").show();
		}
		else{
			$("#expense_input_box").hide();
			$("#overlay").hide();
			$("#btnExpansePlus").show();
		}
		
		refreshHome();
	});
	
	$("#okIfail").click(function(){
		$("#failBox").hide();
		$("#overlay").hide();
		$("#btnExpansePlus").show();
	});

	$("#btn_new_expense_cancel").click(function(){
		$("#overlay").hide();
		$("#expense_input_box").hide();
		$("#btnExpansePlus").show();
	});

	
});

function addExpense(){
	var amount = $("#new_expense_amount").val();
	var dateTime = $("#new_expense_date_time").val();
	var date = dateTime.substring(0, 10);
	var time = dateTime.substring(11, 16);
	var categoryId = $("#new_expanse_category_select").val();
	var remark = $("#new_expense_remark").val();
	// return string to decide whether just failed or keep going
	return Android.addExpense(amount, date, time, categoryId, remark);
}

function planSelectBoxGenerate(){
	var content = "";
	var data;
	var ps;
	var planCnt = 0;
	try{

		data = Android.getCurrentPlan();
		ps = JSON.parse(data); 
			planCnt += ps.length;
		for(i=0;i<ps.length;i++){
			var id = ps[i].id;
			var name = ps[i].name;
			content += "<option value='"+id+"'>"+name+"</option>";
		}

		data = Android.getOlderPlans();
		ps = JSON.parse(data);
			planCnt += ps.length;
		for(i=0;i<ps.length;i++){
			var id = ps[i].id;
			var name = ps[i].name;
			content += "<option value='"+id+"'>"+name+"</option>";
		}

		if(planCnt==0){
			// hide select boxes
			$("#sltPlan").hide();
			$("#category_plan_select_box").hide();
			$("#view_log_plan_select").hide();
			
		}
		else{
			// display select boxes
			$("#sltPlan").show();
			$("#category_plan_select_box").show();
			$("#view_log_plan_select").show();
			
			// put data into select boxes
			$("#sltPlan").html(content);
			$("#category_plan_select_box").html(content);
			$("#view_log_plan_select").html(content);
		}
		
	}
	catch(e){
		$("#view_log_plan_select").html(e+"<br>"+data);
		$("#sltPlan").html(e+"<br>"+data);
	}
}

function getExpensesByPlanId(planId){
	var content = "";
	var data;
	var es;
	try{
		data = Android.getExpensesByPlanId(planId);
		es = JSON.parse(data);
		if(es.length>0){
			for(i=0;i<es.length;i++){
			var id = es[i].id;
			var amount = es[i].amount;
			var date = es[i].date;
			var time = es[i].time;
			var categoryName = es[i].categoryName;
			var remark = es[i].remark;
			content += "<div class='panel panel-info'>";
			content += 		"<div class='panel-heading'>";
			content += 			"<span class='log_category_name'>"+categoryName+"</span>";
			content += 			"<span class='log_expense_amount'>"+amount+"</span>";
			content += 		"</div>";
			content += 		"<div class='panel-body align-left'>";
			content += 			"<h5 class='log_expense_dateTime'>" + time + "&nbsp;&nbsp;&nbsp;" + date +"</h5>";
			content += 			"<span class='log_expense_remark'>"+remark+"</span>";
			content += 		"</div>";
			content += "</div>";

			}
			$("#logAutoGeneratorTag").html(content);
		}
		else{
			$("#logAutoGeneratorTag").html("<h3 style='text-align:center;color:#ccc;'>NO EXPENSE LOGS</h3>");
		}
		
	}
	catch(e){
		$("#logAutoGeneratorTag").html(e+"<br>"+data);
	}
}

function setDateTime(){
	var currentdate = new Date(); 
    var yyyy = currentdate.getFullYear();
    var mm = (currentdate.getMonth()+1)+"";
    var dd = currentdate.getDate()+"";
    var hr = currentdate.getHours()+"";
    var mi = currentdate.getMinutes()+"";
    if(mm.length == 1) mm = "0"+mm;
    if(dd.length == 1) dd = "0"+dd;
    if(hr.length == 1) hr = "0"+hr;
    if(mi.length == 1) mi = "0"+mi;
    var dt = yyyy+"-"+mm+"-"+dd+"T"+hr+":"+mi;
	$("#new_expense_date_time").val(dt);
}

function categorySelectBoxGenerate(){
	var content = "";
	var data = Android.getAllCategories();
	try{
		var cats = JSON.parse(data);
		content += "<option>Select Category</option>";		
		for(i=0;i<cats.length;i++){
			var id = cats[i].id;
			var name = cats[i].name;

			content += "<option value='"+id+"'>"+name+"</option>";

		}
		$("#new_expanse_category_select").html(content);
	}
	catch(e){
		$("#new_expanse_category_select").html(e+"<br>"+data);
	}
}

function getGraphDataByPlanId(planId){

	var data = Android.getGraphDataByPlanId(planId);
	var graph = JSON.parse(data);
    
    // remove labels
    iChart.data.labels.length = 0;

    // remove datesets
    iChart.data.datasets.splice(0, 1);

	// declare new dataset
	var newDataset = {
		label: 'Usage',
		backgroundColor: "rgba(91, 192, 222, 0.5)",
		data: []
	};

    for(i=0;i<graph.length;i++){
    	iChart.data.labels.push(graph[i].date);
        newDataset.data.push(graph[i].amount);
    }

    iChart.data.datasets.push(newDataset);
    window.iChart.update();
}

function homeCurrentPlan(){
	var content = "";
	var data = Android.getCurrentPlan();
	try{ 
		var ps = JSON.parse(data);
		if(ps.length>0){
			for(i=0;i<ps.length;i++){
				var planId = ps[i].id;
				var name = ps[i].name;
				var startDate = ps[i].startDate;
				var endDate = ps[i].endDate;
				var planAmount = ps[i].amount;
				var usedAmount = parseFloat(Android.getExpenseAmountTotalByPlanId(planId));
				var usedPercent =  parseInt(usedAmount*100 / planAmount);

				content += "<div class='panel panel-info'>";
				content += "<div class='panel-heading'>"+name+"</div>";
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
			$("#homeCurrentPlanGenerator").html(content);
		}
		else{
			$("#homeCurrentPlanGenerator").html("<h3 style='text-align:center;color:#ccc;'>NO CURRENT PLAN</h3>");
		}
	}
	catch(e){
		$("#homeCurrentPlanGenerator").html(e+"<br>"+data);
	}
}

function refreshHome(){
	try{
		categorySelectBoxGenerate();
		planSelectBoxGenerate();
		homeCurrentPlan();
		var planId = $("#sltPlan").val();
		if(planId>0){
			getGraphDataByPlanId(planId);
		}
		else{
			getGraphDataByPlanId(0);
		}
	}
	catch(e){
		alert("expense.js "+e);
	}
}

function refreshExpenseLog(){
	try{
		var planId = $("#view_log_plan_select").val();
		if(planId>0){
			getExpensesByPlanId(planId);
		}
		else{
			getExpensesByPlanId(0);
		}
	}
	catch(e){
		alert("expense.js "+e);
	}
}