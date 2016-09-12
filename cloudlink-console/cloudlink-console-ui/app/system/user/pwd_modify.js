'use strict';

/* Controllers */

angular.module('app').controller('pwdModifyCtrl', ['$scope', '$rootScope', '$localStorage', 'HttpUtil',
    function($scope,$rootScope,$localStorage,HttpUtil) {

	$scope.obj={username:$rootScope.loginUser.username,password:null,newPassword:null};
	
	
	$scope.modify=function(){
		HttpUtil.ajax({
			url : "/api/password/modify",
			data : $scope.obj,
			method : "PUT",
			success : function(result) {
				if (result.errorCode == 0) {
					alert('修改成功');
				} else {
					alert(result.errorMsg);
				}
			}
		});
	}
  }]);