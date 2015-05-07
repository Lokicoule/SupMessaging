/**
 * 
 */
function selectProfile(userId) {
	$.ajax({
		url : "",
		type : "POST",
		data : "action=viewProfile&profileUserId=" + userId,
		success : function(data) {
			

		}
	});
}