$(document).ready(function(){
	setDateTime();
	$("#btnExpansePlus").click(function(){
		$(this).hide();
		$("#overlay").show();
		$("#expense_input_box").fadeIn(100);
	});

	$("#btn_new_expense_cancel").click(function(){
		$("#overlay").hide();
		$("#expense_input_box").hide();
		$("#btnExpansePlus").show();
	});
});

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
	$("#expane_date_time").val(dt);
}