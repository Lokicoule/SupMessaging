/**
 * 
 */
function doSearch() {
	var search = document.getElementById("searchField").value;
	$.ajax({
		url : "",
		type : "POST",
		data : "action=doSearch&searchField=" + search,
		success : function(data) {
			$("#morphsearch-content").remove();
			$("#morphsearch").append(data);
		}
	});
}

function doCompletion() {
		var search = document.getElementById("searchField").value;
		alert(search);
		if (search.lengh() >= 3)
		{
			$.ajax({
				url : "",
				type : "POST",
				data : "action=doSearch&searchField=" + search,
				success : function(data) {
					$("#morphsearch-content").remove();
					$("#morphsearch").append(data);
				}
			});
		}
}