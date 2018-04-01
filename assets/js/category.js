var selected_cat_id = "";
var selected_cat_name = "";
$(document).ready(function(){
	$("#plusCategory").click(function(){
		$(this).hide();
		$("#category-name").val(""); // reset text box
		$("#overlay").show();
		$("#category-input-title").html("New Category Name");
		$("#btnAddCategoryName").html("Add");
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

		getAllCategories(); // refresh cat
		categorySelectBoxGenerate();
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
		$("#confirm-notice").html("<strong>"+selected_cat_name+"</strong> will be deleted and expanse logs under this category will be moved to Misc.");
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
		getAllCategories(); // refresh cats
	});

	$("#btnNoCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-confirm-box").hide();
		$("#plusCategory").show();
	});

	// testing codes
	$(".cat_item").click(function(){
		selected_cat_id = $(this).attr("cat_id");
		selected_cat_name = $(this).attr("cat_name");
		$("#plusCategory").hide();
		$("#overlay").show();
		$("#category-option-box").show();
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

			content += "<div class='cat'>";
			content += "<span class='glyphicon glyphicon-option-vertical cat_item' cat_id="+id+" cat_name='"+name+"'></span>" + name
			content += "</div>";
		}
			$("#categoryAutoGenWrapperTag").html(content);

		$(".cat_item").click(function(){
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

function deleteCategory(id){
	Android.deleteCategory(id);
}

function updateCategory(id,name){
	Android.updateCategory(id,name);
}