'use strict';

/* Controllers */

angular.module('app').controller('areaListCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
    function($scope,$translate,$localStorage,HttpUtil,$modal ) {

	/*排序参数*/
	$scope.orderName='';
	$scope.ascOrDesc=false;//false 升序  true 降序
	$scope.sort=function(orderName,ascOrDesc){
		$scope.orderName=orderName;
		$scope.ascOrDesc=!$scope.ascOrDesc;
	}
	$scope.queryAllArea = function () {
		HttpUtil.ajax({
			url : "/api/areas",
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
	$scope.queryAllArea();
	
	$scope.openEditDialog = function (code,isUpdate) {
		
		/* 弹出新增、修改界面 */
	    var modalInstance = $modal.open({
	      animation: true,
	      templateUrl: 'app/basic/area/area_add.html',
	      controller: 'areaEditCtrl',
	      resolve: {
	    	  params:function () { return {code:code,isUpdate:isUpdate}; }
	      }
	    });
	    
	    /* 弹出框关闭时刷新数据中心列表 */
	    modalInstance.result.then(function (refresh) {
	        if (refresh) $scope.queryAllArea();
	    });
	 };
	 
	 /* 删除数据中心 */
	 $scope.deleteArea = function(code) {
		 if (confirm("确认删除吗？")) {
			 HttpUtil.ajax({
					url : "/api/area/" + code,
					data : null,
					method : "DELETE",
					success : function(result) {
						if (result.errorCode == 0) {
							alert('操作成功');
							$scope.queryAllArea();
						} else {
							alert(result.errorMsg);
						}
					}
				});
		 }
	 }
	 
	 $scope.initail=function(){
		 if (confirm("确认执行初始化动作吗？只进行增量初始化，不变更原有数据。")) {
			 HttpUtil.ajax({
					url : "/api/data/init",
					data : null,
					method : "POST",
					loadingButton:"btnInitail",
					success : function(result) {
						if (result.errorCode == 0) {
							alert('操作成功');
							$scope.queryAllArea();
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
angular.module('app').controller('areaEditCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil','$modalInstance', 'params',
                                                  function($scope,$translate,$localStorage,HttpUtil,$modalInstance,params ) {
	
	$scope.isUpdate = params.isUpdate;
	$scope.code = params.code;
	$scope.area = {};
	var action = "POST";
	
	/* 编辑数据前根据编码查询数据中心*/
	if ($scope.code) {
		HttpUtil.ajax({
			url : "/api/area/" + $scope.code,
			data : null,
			method : "GET",
			success : function(result) {
				$scope.$apply(function(){
					if (result.errorCode == 0) {
						$scope.area = result.data;
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
	
	$scope.save=function(valid){
		if (valid) { /*表单验证通过*/
			if ($scope.isUpdate) {
				HttpUtil.ajax({
					url : "/api/area/" + $scope.area.code,
					data : $scope.area,
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