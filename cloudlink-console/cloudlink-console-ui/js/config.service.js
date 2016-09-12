'use strict';

angular.module('app').service( 'utils', [function() {
	return {
		excludeLoading:['/api/common/areas'],
		contain:function(arr,val){
			if (arr) {
				for (var i = 0 ;i < arr.length; i++) {
					if (arr[i] == val) return true;
				}
			} 
			return false;
		}
	}
}]);

angular.module('app').service( 'HttpUtil', [ '$rootScope','utils', function( $rootScope, utils ) {
	return {
		ajax : function (opt) {
			var showLoading = !utils.contain(utils.excludeLoading,opt.url);
			var options = {
					url : API_BASE_URL + opt.url,
					data : (opt.data) ? JSON.stringify(opt.data) : null,
					method : opt.method,
					crossDomain : true,
					contentType : "application/json; charset=utf-8",
					dataType : "JSON",
					beforeSend:function(req) {
						if (showLoading)$('#loadingShow').show();
						req.setRequestHeader("Authorization", $rootScope.token);
						if (opt && opt.loadingButton){
							$('#'+opt.loadingButton).button("loading");
						}
						if (opt && opt.beforeSend) opt.beforeSend();
					},
					success : function(data) {
						if (opt.method == 'GET' && data.errorCode == -3) {
							window.location="app/login.html";
						} else if (opt.method == 'GET' && data.errorCode == -4) {
							alert(data.errorMsg);
						} else {
							if (opt && opt.success) opt.success(data);
						}
					},
					complete: function (xhr, status) {
						if (opt && opt.complete) opt.complete(xhr, status);
						if (opt && opt.loadingButton){
							$('#'+opt.loadingButton).button("reset");
						}
						if (showLoading)$('#loadingShow').hide();
					}
				};
				
				
			if (opt && opt.error){
				options.error=function(data) {
					opt.error(data);
				}
			}
			$.ajax(options);
		}
	};

}]);