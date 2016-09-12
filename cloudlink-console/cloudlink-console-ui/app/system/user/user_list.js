'use strict';

/* Controllers */

angular.module('app').controller('userListCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
    function($scope,$translate,$localStorage,HttpUtil,$modal ) {

	/*排序参数*/
	$scope.orderName='';
	$scope.ascOrDesc=false;//false 升序  true 降序
	$scope.sort=function(orderName,ascOrDesc){
		$scope.orderName=orderName;
		$scope.ascOrDesc=!$scope.ascOrDesc;
	}
	$scope.queryAllUser = function () {
		HttpUtil.ajax({
			url : "/api/users",
			data : null,
			method : "GET",
			success : function(result) {
				$scope.$apply(function(){
					$scope.datas = result.data;
				});
			}
		});
	}
	
	/* 页面加载时查询数据中心列表  */
	$scope.queryAllUser();
	
	$scope.openEditDialog = function (username,isUpdate) {
		
		/* 弹出新增、修改界面 */
	    var modalInstance = $modal.open({
	      animation: true,
	      templateUrl: 'app/system/user/user_add.html',
	      controller: 'userEditCtrl',
	      resolve: {
	    	  params:function () { return {username:username,isUpdate:isUpdate}; }
	      }
	    });
	    
	    /* 弹出框关闭时刷新数据中心列表 */
	    modalInstance.result.then(function (refresh) {
	        if (refresh) $scope.queryAllUser();
	    });
	 };
	 
	 /* 删除数据中心 */
	 $scope.deleteUser = function(username) {
		 if (confirm("确认删除吗？")) {
			 HttpUtil.ajax({
					url : "/api/user/" + username,
					data : null,
					method : "DELETE",
					success : function(result) {
						if (result.errorCode == 0) {
							alert('操作成功');
							$scope.queryAllUser();
						} else {
							alert(result.errorMsg);
						}
					}
				});
		 }
	 }
	
  }]);

/**
 * 新增、修改数据中心
 */
angular.module('app').controller('userEditCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil','$modalInstance', 'params',
                                                  function($scope,$translate,$localStorage,HttpUtil,$modalInstance,params ) {
	
	$scope.isUpdate = params.isUpdate;
	$scope.username = params.username;
	$scope.data = {};
	$scope.roles=[];
	var action = "POST";
	
	HttpUtil.ajax({
		url : "/api/roles",
		data : null,
		method : "GET",
		success : function(result) {
			$scope.$apply(function(){
				if (result.errorCode == 0) {
					$scope.roles = result.data;
					loadUserInfo();
				} else {
					alert(result.errorMsg);
				}
			});
		}
	});
	
	/* 编辑数据前根据编码查询数据中心*/
	function loadUserInfo() {
		if ($scope.username) {
			HttpUtil.ajax({
				url : "/api/user/" + $scope.username,
				data : null,
				method : "GET",
				success : function(result) {
					$scope.$apply(function(){
						if (result.errorCode == 0) {
							$scope.data = result.data;
							$scope.data.password="$password$";
							$('[name=roleName]').each(function(i,cb){
                                $.each(result.data.roleName,function(j,role){
                                    if($(cb).attr('value') ==role){
                                        $(cb).prop('checked',true);
                                        return;
                                    }
                                });
                            });
						} else {
							alert(result.errorMsg);
							$modalInstance.close(true);
						}
						
					});
				}
			});
			//修改
			action = "PUT";
		} else {
			//新增
			action = "POST";
		}
	}
	
	
	$scope.save=function(valid){
		if (valid) { /*表单验证通过*/
			if ($scope.isUpdate) {
				var chk_value =[]; 
				$('input[name="roleName"]:checked').each(function(){ 
					chk_value.push($(this).val()); 
				}); 
				$scope.data.roleName = chk_value;
				if (action=="PUT" && $.trim($scope.data.password)==""){
					$scope.data.password="$password$";
				}
				HttpUtil.ajax({
					url : "/api/user/" + $scope.data.username,
					data : $scope.data,
					method : action,
					loadingButton:"btnSave",
					success : function(data) {
						if (data.errorCode == 0) {
							alert('操作成功');
							$modalInstance.close(true);
						} else {
							alert(data.errorMsg);
						}
					}
				});
			}
		}
	}

	$scope.returnPage = function() {
		$modalInstance.dismiss('cancel');
	};
	
}]);