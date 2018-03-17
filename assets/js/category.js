var selected_cat_id = "";
$(document).ready(function(){
	$("#plusCategory").click(function(){
		$(this).hide();
			$("#overlay").show();
			$("#category-input-box").fadeIn(100);
	});
	
	$("#btnAddCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-input-box").hide();
		$("#plusCategory").show();

		var name = $("#category-name").val();
		addCategory(name);
		$("#category-name").val(""); // reset text box
		getAllCategories(); // refresh cat
	});

	$("#btnCancelCategoryName").click(function(){
		$("#overlay").hide();
		$("#category-input-box").hide();
		$("#plusCategory").show();
	});

	$("#btnCancelCategoryOption").click(function(){
		$("#overlay").hide();
		$("#category-option-box").hide();
		$("#plusCategory").show();
	});

	$("#btnDeleteCategoryName").click(function(){
		$("#category-option-box").hide();
		$("#category-confirm-box").fadeIn(200);
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
			content += "<span class='glyphicon glyphicon-option-vertical cat_item' cat_id="+id+"></span>" + name
			content += "</div>";
		}
			$("#categoryAutoGenWrapperTag").html(content);

		$(".cat_item").click(function(){
			selected_cat_id = $(this).attr("cat_id");
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