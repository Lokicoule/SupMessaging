$(document).ready(function() {
	var listId = [];
	$("ul.received").load("/SupSMS/Servlet_Received", function(){
		$("li.msgReceived").on("click", function(event) {
			var msgId = $(this).attr('id');
			eventReceived(event, msgId);
		});
	});
	$("li.msgReceived").on("click", function(event) {
		event.preventDefault();
		var msgId = $(this).attr('id');
		eventReceived(event, msgId);
	});
	$("ul.sent").load("/SupSMS/Servlet_Sent", function(){
		$("li.msgSent").on("click", function(event) {
			var msgId = $(this).attr('id');
			eventSent(event, msgId);
		});
	});

	$("#ContactSelect").on("change", function(event) {
		$(".ObjectSelect").empty();
		var contacts = document.getElementById("ContactSelect");
		var contact = contacts.options[contacts.selectedIndex].text;
		$.ajax({
			url : "",
			type : "POST",
			data : "action=showListTopic&contact=" + contact,
			success : function(data) {
				$(".ObjectSelect").append(data);
			}
		});
	});
	
	$("ul.trash").load("/SupSMS/Servlet_Trash", function(){
		$("li.msgTrash").on("click", function(event) {
			var msgId = $(this).attr('id');
			eventTrash(event, msgId);
		});
	});
	
	$("ul.contacted").load("/SupSMS/Servlet_ContactReceived", function(){
		$("li.msgReceived").on("click", function(event) {
			var msgId = $(this).attr('id');
			eventReceived(event, msgId);
		});
	});
	
	function eventReceived(event, msgId)
	{
		event.preventDefault();
		$("li.msgReceived").removeClass('active');
		$("li.msgReceived#" + msgId).addClass('active');
		$(".glyphicon-trash").remove();
		$("#small_"+msgId).append("<font size=\"3\"><span class=\"glyphicon glyphicon-trash\"></span></font>");
		$.ajax({
			url : "",
			type : "POST",
			data : "action=showMsg&msgId=" + msgId,
			success : function(data) {
				$(".new_"+msgId).remove();
				$("#divForAjax").remove();
				$("#bodyMsg").append(data);
				$("a.answerMsg").on("click", function(event) {
					event.preventDefault();
					listId = [];
					listId.push($(this).attr('id'));
					sendNewMsg(event, listId);
				});
			}
		});
		$("#small_"+msgId).on("click", function(event) {
			$.ajax({
				url : "",
				type : "POST",
				data : "action=delMsg&msgId=" + msgId,
				success : function(data) {
					$("#" + msgId).remove();
					$("ul.trash").load("/SupSMS/Servlet_Trash", function(){
						$("li.msgTrash").on("click", function(event) {
							var msgId = $(this).attr('id');
							eventTrash(event, msgId);
						});
					});
				}
			});
		});
	}

	$("li.msgSent").on("click", function(event) {
		event.preventDefault();
		var msgId = $(this).attr('id');
		eventSent(event, msgId);
	});
	
	function eventSent(event, msgId)
	{
		var id = msgId.split('_')[1];
		$("li.msgSent").removeClass('active');
		$("#" + msgId).addClass('active');
		$.ajax({
			url : "",
			type : "POST",
			data : "action=showMsgSent&msgId=" + id,
			success : function(data) {
				$("#divForAjax").remove();
				$("#bodyMsg").append(data);
			}
		});
	}
	
	$("li.msgTrash").on("click", function(event) {
		event.preventDefault();
		var msgId = $(this).attr('id');
		eventTrash(event, msgId);
	});
	
	function eventTrash(event, msgId)
	{
		$("li.msgTrash").removeClass('active');
		$("#" + msgId).addClass('active');
		$.ajax({
			url : "",
			type : "POST",
			data : "action=showMsgDeleted&msgId=" + msgId,
			success : function(data) {
				$("#divForAjax").remove();
				$("#bodyMsg").append(data);
			}
		});
	}

	$("a.composeNew").on("click", function(event) {
		event.preventDefault();
		$("li.msgReceived").removeClass('active');
		$("li.msgSent").removeClass('active');
		$.ajax({
			url : "",
			type : "POST",
			data : "action=composeMsg",
			success : function(data) {
				$("#divForAjax").remove();
				$("#bodyMsg").append(data);
				$("a.addList").on("click", function(event) {
					event.preventDefault();
					listId = addToList();
				});
				$("a.SendMsg").on("click", function(event) {
					event.preventDefault();
					sendNewMsg(event, listId);
				});
			}
		});
	});
	
	$("a.selectFromDatas").on("click", function(event) {
		event.preventDefault();
		var contacts = document.getElementById("ContactSelect");
		var objects = document.getElementById("ObjectSelect");
		var contact = contacts.options[contacts.selectedIndex].text;
		var object = objects.options[objects.selectedIndex].text;
		$.ajax({
			url : "",
			type : "POST",
			data : "action=selectMessages&selectContact="+contact+"&selectObject="+object,
			success : function(data) {
				swap("ul.resultSearch", "ul.sent", "ul.received", "ul.trash", "ul.contacted");
				$("ul.resultSearch").empty();
				$("ul.resultSearch").append(data);
				$("li.msgReceived").on("click", function(event) {
					var msgId = $(this).attr('id');
					eventReceived(event, msgId);
				});
				$("li.msgSent").on("click", function(event) {
					var msgId = $(this).attr('id');
					eventSent(event, msgId);
				});
				$("a.addList").on("click", function(event) {
					event.preventDefault();
					listId = addToList();
				});
				$("a.SendMsg").on("click", function(event) {
					event.preventDefault();
					sendNewMsg(event, listId);
				});
			}
		});
	});
	
	$("a.addList").on("click", function(event) {
		event.preventDefault();
		listId = addToList(event);
	});
	
	$("a.SendMsg").on("click", function(event) {
		event.preventDefault();
		sendNewMsg(event, listId);
	});
	
	function addToList(event)
	{
		var buff = document.getElementById("toSend");
		var listNames = [];
		var listId = [];
		$(".inputFriend:checked").each(function() {
			listNames.push($(this).val());
			listId.push($(this).attr('id'));
		});
		var names, ids;
		names = listNames.join('; ') + ";";
		if(names.length > 1){
			buff.value = names;
			return listId;
		}
	}
	
	function sendNewMsg(event, listId)
	{
		var ids = listId.join(';');
		var body = document.getElementById("bodyNewMsg").value;
		var object = document.getElementById("objetNewMsg").value;
		$.ajax({
			url : "",
			type : "POST",
			data : "action=sendNewMsg&receiversId=" + ids + "&bodyNewMsg="+body+"&objetNewMsg="+object,
			success : function(data) {
				$("a.SendMsg").remove();
				$("a.answerMsg").remove();
				$("ul.sent").load("/SupSMS/Servlet_Sent", function(){
					$("li.msgSent").on("click", function(event) {
						var msgId = $(this).attr('id');
						eventSent(event, msgId);
					});
				});
			}
		});
	}
	
	$("li.sent").on("click", function(event) {
		event.preventDefault();
		swap("ul.sent", "ul.received", "ul.trash", "ul.contacted" , "ul.resultSearch");
		swapState("li.sent", "li.received", "li.trash", "li.contact");
		document.getElementById("showBox").innerHTML = "Outbox";
	});

	$("li.received").on("click", function(event) {
		event.preventDefault();
		swap("ul.received", "ul.sent", "ul.trash", "ul.contacted" , "ul.resultSearch");
		swapState("li.received", "li.sent", "li.trash", "li.contact");
		document.getElementById("showBox").innerHTML = "Inbox";
	});
	
	$("li.trash").on("click", function(event) {
		event.preventDefault();
		swap("ul.trash", "ul.received", "ul.sent", "ul.contacted" , "ul.resultSearch");
		swapState("li.trash", "li.received", "li.sent", "li.contact");
		document.getElementById("showBox").innerHTML = "Trash";
	});
	
	$("li.contact").on("click", function(event) {
		event.preventDefault();
		swap("ul.contacted", "ul.trash", "ul.received", "ul.sent", "ul.resultSearch");
		swapState("li.contact", "li.trash", "li.received", "li.sent");
		document.getElementById("showBox").innerHTML = "Trash";
	});
	
	$("span.refreshMsg").on("click", function(event){
			$("ul.received").load("/SupSMS/Servlet_Received", function(){
				$("li.msgReceived").on("click", function(event) {
					var msgId = $(this).attr('id');
					eventReceived(event, msgId);
				});
			});
	});
	/*setInterval(function(){
		$("ul.contacted").load("/SupSMS/Servlet_ContactReceived", function(){
			$("li.msgReceived").on("click", function(event) {
				var msgId = $(this).attr('id');
				eventReceived(event, msgId);
			});
		});
	},10000);*/
	
	function swapState(active, none1, none2, none3)
	{
		$(none1).removeClass('active');
		$(none2).removeClass('active');
		$(none3).removeClass('active');
		$(active).addClass('active');
	}

	function swap(show, hide1, hide2, hide3, hide4) {
		$(hide1).fadeOut();
		$(hide2).fadeOut();
		$(hide3).fadeOut();
		$(hide4).fadeOut();
		$(show).show();
	}

});