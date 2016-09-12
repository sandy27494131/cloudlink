'use strict';

/* Controllers */
angular.module('app').controller(
		'queueListCtrl',
		[ '$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
				function($scope, $translate, $localStorage, HttpUtil, $modal) {
					/*排序参数*/
					$scope.orderName='';
					$scope.ascOrDesc=false;//false 升序  true 降序
					$scope.sort=function(orderName,ascOrDesc){
						$scope.orderName=orderName;
						$scope.ascOrDesc=!$scope.ascOrDesc;
					}
					$scope.queryCondition={};
			
					$scope.queryAllQueues = function() {
						HttpUtil.ajax({
							url : "/api/queues",
							data : $scope.queryCondition,
							method : "GET",
							loadingButton:"btnQuery",
							success : function(result) {
								$scope.$apply(function() {
									$scope.datas = result.data;
								});
							}
						});
					}

					/* 页面加载时查询数据中心列表 */
					$scope.queryAllQueues();

					$scope.openEditDialog = function(queueName, isUpdate) {

						/* 弹出新增、修改界面 */
						var modalInstance = $modal.open({
							animation : true,
							templateUrl : 'app/basic/appQueue/appQueue_add.html',
							controller : 'queueEditCtrl',
							resolve : {
								params : function() {
									return {
										queueName : queueName,
										isUpdate : isUpdate
									};
								}
							}
						});

						/* 弹出框关闭时刷新数据中心列表 */
						modalInstance.result.then(function(refresh) {
							if (refresh)
								$scope.queryAllQueues();
						});
					};

					/* 删除数据中心 */
					$scope.deleteArea = function(name) {
						if (confirm("确认删除吗？")) {
							HttpUtil.ajax({
								url : "/api/queue/" + name,
								data : null,
								method : "DELETE",
								success : function(result) {
									if (result.errorCode == 0) {
										alert('操作成功');
										$scope.queryAllQueues();
									} else {
										alert(result.errorMsg);
									}
								}
							});
						}
					}

				} ]);


/**
 * 新增、修改数据中心
 */
angular.module('app').controller('queueEditCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil','$modalInstance', 'params',
                                                  function($scope,$translate,$localStorage,HttpUtil,$modalInstance,params ) {
	
	$scope.isUpdate = params.isUpdate;
	$scope.queueName = params.queueName;
	$scope.data = {};
	$scope.appTypes = [];
	var action = "POST";
	
	/* 编辑数据前根据编码查询数据中心*/
	if ($scope.queueName) {
		//修改
		action = "PUT";
	} else {
		//新增
		action = "POST";
	}
	
	HttpUtil.ajax({
		url : "/api/queue/" + $scope.queueName,
		data : null,
		method : "GET",
		success : function(result) {
			$scope.$apply(function(){
				if (result.errorCode == 0) {
					$scope.data = result.data.queue;
					$scope.appTypes = result.data.appTypes;
				} else {
					alert(result.errorMsg);
					$modalInstance.close(true);
				}
				
			});
		}
	});
	
	$scope.save=function(valid){
		if (valid) { /*表单验证通过*/
			if ($scope.isUpdate) {
				HttpUtil.ajax({
					url : "/api/queue/" + $scope.data.name,
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