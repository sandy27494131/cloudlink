var QueryString = {
		data : {},
		Initial : function() {
			var aPairs, aTmp;
			var queryString = new String(window.location.search);
			queryString = queryString.substr(1, queryString.length); 
			aPairs = queryString.split("&");
			for (var i = 0; i < aPairs.length; i++) {
				aTmp = aPairs[i].split("=");
				this.data[aTmp[0]] = aTmp[1];
			}
		},
		GetValue : function(key) {
			return this.data[key];
		}
	}

function logout() {
	$.ajax({
		url : API_BASE_URL+"/api/logout",
		data : {},
		method : "POST",
		crossDomain : true,
		contentType : "application/json; charset=utf-8",
		dataType : "JSON",
		beforeSend:function(req) {
			req.setRequestHeader("Authorization", $.cookie("auth_token"));
		},
		success : function(data) {
			$.cookie("auth_token",null);
			window.location.href = "app/login.html";
		},
		complete: function (xhr, status) {
		}
	});
}

function QueryString_Initial() {
	QueryString.Initial();
	var token = QueryString.GetValue('tk');
	if (token) {
		$.cookie("auth_token", token, { expires: 1 });
		window.location.href = "index.html";
	} else {
		var tk = $.cookie("auth_token");
		if (!tk || tk == 'null') {
			window.location = 'app/login.html';
		}
	}
}
