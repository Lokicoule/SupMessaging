/**
 * 
 */
function sendMsg()
{
	$("#contactShowError").remove();

	$.ajax({
		url : "",
		type : "POST",
		data : $("#contactMsg").serialize(),
		success:function(data)
		{
			if (data == "<span class=\"glyphicon glyphicon-ok-sign\">Sent</span>")
				$("#sendMsgContact").fadeOut();
			$("#contactMsg").append(data);
		}
		});
}