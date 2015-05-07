/**
 * 
 */
$("a.addFriend").on("click", function(event){
	event.preventDefault();
	var friendId = $(this).attr('id');
	$("#" + friendId).fadeOut();
	$.ajax({
		url : "",
		type : "POST",
		data : "action=addFriendship&contactId=" + friendId,
		success : function(data) {
			$("#user_" + friendId).append(data);
		}
	});
});