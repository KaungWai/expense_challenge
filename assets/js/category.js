var selected_cat_id = "";
var selected_cat_name = "";
$(document).ready(function(){
	$("#plusCategory").click(function(){
		$(this).hide();
		$("#category-name").val(""); // reset text box
		$("#overlay").show();
		$("#category-input-title").html("New Category Name");
		$("#btnAddCategoryName").html("Add");
		$("#btnAddCategoryName").attr("disabled", "true");
		// clear input 
		$("#category-name").val("");
		$("#category-input-box").fadeIn(100);
	});

	$("#btnAddCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-input-box").hide();
		$("#plusCategory").show();

		var name = $("#category-name").val();
		if($(this).html()=="Add")
			addCategory(name);
		if($(this).html()=="Update")
			updateCategory(selected_cat_id,name);
		
		refreshCategory();
	});

	$("#btnCancelCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-input-box").hide();
		$("#plusCategory").show();
	});

	$("#btnEditCategoryName").click(function(){
		$("#category-option-box").hide();
		$("#category-input-title").html("Edit Category Name");
		$("#btnAddCategoryName").html("Update");
		$("#category-name").val(selected_cat_name);
		$("#category-input-box").fadeIn(100);
	});

	$("#btnDeleteCategoryName").click(function(){
		$("#category-option-box").hide();
		$("#confirm-notice").html("<strong>"+selected_cat_name+"</strong> will be deleted and expense logs under this category will be moved to <strong>Misc</strong>.");
		$("#category-confirm-box").fadeIn(200);
	});

	$("#btnCancelCategoryOption").click(function(){
		$("#overlay").hide();
		$("#category-option-box").hide();
		$("#plusCategory").show();
	});

	$("#btnYesCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-confirm-box").hide();
		$("#plusCategory").show();
		deleteCategory(selected_cat_id);
		refreshCategory(); // refresh cats
	});

	$("#btnNoCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-confirm-box").hide();
		$("#plusCategory").show();
	});
});

function addCategory(name){
	Android.addCategory(name);
}

function getAllCategories(){
	var content = "";
	var data = Android.getAllCategories();
	try{ 
		var cats = JSON.parse(data);
		
		for(i=0;i<cats.length;i++){
			var id = cats[i].id;
			var name = cats[i].name;

			content += "<div class='panel panel-default'>";
			content += "<div class='panel-heading'>"
			if(i==0)
				content += "<span class='glyphicon glyphicon-chevron-down cat_item' cat_id="+id+" cat_name='"+name+"'></span>&nbsp;&nbsp;" + name
			else
				content += "<span class='glyphicon glyphicon-chevron-down cat_item cat_items' cat_id="+id+" cat_name='"+name+"'></span>&nbsp;&nbsp;" + name
			content += "</div>";
			content += "<div class='panel-body'></div>";
			content += "</div>";
		}
		$("#categoryAutoGenWrapperTag").html(content);

		$(".cat_items").click(function(){
			selected_cat_id = $(this).attr("cat_id");
			selected_cat_name = $(this).attr("cat_name");
			$("#plusCategory").hide();
			$("#overlay").show();
			$("#category-option-box").show();
		});
	}
	catch(e){
		$("#categoryAutoGenWrapperTag").html(e+"<br>"+data);
	}
}

function getDoughnutDataByPlanId(planId){

	var name = new Array();
	var amount = new Array();
	var color = new Array();
	var data = Android.getDoughnutDataByPlanId(planId);
	var graph = JSON.parse(data);
	if (graph.length==0){
		$("#doughnut").hide();
	}
	else{
		$("#doughnut").show();
	}

	for(i=0;i<graph.length;i++){
		name[i] = graph[i].name;
		amount[i] = parseFloat(graph[i].amount).toFixed(2);
		color[i] = getRandomColor();
	}


	 // remove labels
    iDouhnut.data.labels.length = 0;
    // remove datesets
    iDouhnut.data.datasets.splice(0, 1);
    // add new labels
    iDouhnut.data.labels = name;
	// create new dataset
	var newDataset = {
		backgroundColor: color,
		data: amount
	};
	// add dataset
    iDouhnut.data.datasets.push(newDataset);
    // refresh 
    window.iDouhnut.update();
}

function deleteCategory(id){
	Android.deleteCategory(id);
}

function updateCategory(id,name){
	Android.updateCategory(id,name);
}

function categoryInputValidate(name){
	name = name.trim();
	if(name == ""){
		Android.showText("Category name can't be empty!");
		$("#btnAddCategoryName").attr("disabled", "true");
	}
	else{
		//remove diable attribute
		$("#btnAddCategoryName").removeAttr("disabled");
	}
}

function getRandomColor(){
	var r = Math.floor(Math.random()*(255-1+1)+1);
	var g = Math.floor(Math.random()*(255-1+1)+1);
	var b = Math.floor(Math.random()*(255-1+1)+1);
	var a = 0.6;
	return "rgba("+r+","+g+","+b+","+a+")";
}

function refreshCategory(){
	try{
		planSelectBoxGenerate();
		getAllCategories();
		var planId = $("#category_plan_select_box").val();
		if(planId>0){
			getDoughnutDataByPlanId(planId);
		}
		else{
			getDoughnutDataByPlanId(0);
		}
	}
	catch(e){
		alert(e);
	}
}