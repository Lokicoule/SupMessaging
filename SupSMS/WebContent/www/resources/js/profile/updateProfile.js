/**
 * 
 */
$(function() {
	$('#emailProfileButton').on('click', function(e) {
		e.preventDefault();
		var email = document.getElementById("emailProfile").value;
		$("#emailProfileButton").remove();
		$.ajax({
			url : "",
			type : "POST",
			data : "action=updateProfileUser&emailProfile=" + email,
			success:function(data)
			{
				$("#emailIdProfile").append(data);
			}
			});
	});

	$('#phoneProfileButton').on('click', function(e) {
		e.preventDefault();
		var phone = document.getElementById("phoneProfile").value;
		$("#phoneProfileButton").remove();
		$.ajax({
			url : "",
			type : "POST",
			data : "action=updateProfileUser&phoneProfile=" + phone,
			success:function(data)
			{
				$("#phoneIdProfile").append(data);
			}
			});
	});

	$('#addressProfileButton').on('click', function(e) {
		e.preventDefault();
		var address = document.getElementById("addressProfile").value;
		$("#addressProfileButton").remove();
		$.ajax({
			url : "",
			type : "POST",
			data : "action=updateProfileUser&addressProfile=" + address,
			success:function(data)
			{
				$("#addressIdProfile").append(data);
			}
			});
	});
	
	$('#postalCodeProfileButton').on('click', function(e) {
		e.preventDefault();
		var address = document.getElementById("postalCodeProfile").value;
		$("#postalCodeProfileButton").remove();
		$.ajax({
			url : "",
			type : "POST",
			data : "action=updateProfileUser&postalCodeProfile=" + address,
			success:function(data)
			{
				$("#postalCodeIdProfile").append(data);
			}
			});
	});
	
	$('#lastnameProfileButton').on('click', function(e) {
		e.preventDefault();
		var address = document.getElementById("lastnameProfile").value;
		$("#lastnameProfileButton").remove();
		$.ajax({
			url : "",
			type : "POST",
			data : "action=updateProfileUser&lastnameProfile=" + address,
			success:function(data)
			{
				$("#lastnameIdProfile").append(data);
			}
			});
	});
	
	$('#firstnameProfileButton').on('click', function(e) {
		e.preventDefault();
		var address = document.getElementById("firstnameProfile").value;
		$("#firstnameProfileButton").remove();
		$.ajax({
			url : "",
			type : "POST",
			data : "action=updateProfileUser&firstnameProfile=" + address,
			success:function(data)
			{
				$("#firstnameIdProfile").append(data);
			}
			});
	});
});
