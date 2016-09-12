'use strict';

(function(window, $) {

	window.aciontGet = function() {
		alert(1);
	}

	window.call = function() {
		var responseObj = $("#txtResponse");
		var url = $("#txtRequestURL").val();
		var action = $("#action").val();
		var param = $("#txtRequestParam").val();
		if (!param) {
			param = "{}";
		}
		var content = JSON.parse(param);
		content._method = action;
		$.ajax({
			url : url,
			data : content,
			method : action,
			crossDomain : true,
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			dataType : "JSON",
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
			},
			success : function(data) {
				responseObj.val(JSON.stringify(data));
			},error:function(data) {
				responseObj.val("Error in Processing-----" + JSON.stringify(data));
			}
		});

	}
})(window, jQuery);