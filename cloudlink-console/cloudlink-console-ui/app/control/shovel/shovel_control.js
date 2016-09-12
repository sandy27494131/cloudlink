'use strict';

/* Controllers */

angular.module('app').controller('shovelControlCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
    function($scope,$translate,$localStorage,HttpUtil,$modal ) {

	/*排序参数*/
	$scope.orderName='exceptonShovel';
	$scope.ascOrDesc=true;//false 升序  true 降序
	$scope.sort=function(orderName,ascOrDesc){
		$scope.orderName=orderName;
		$scope.ascOrDesc=!$scope.ascOrDesc;
	}
	$scope.checkShovel = function () {
		HttpUtil.ajax({
			url : "/api/check/shovel",
			data : null,
			method : "GET",
			loadingButton:"btnCheck",
			success : function(result) {
				$scope.$apply(function(){
					$scope.datas = result.data;
				});
			}
		});
	}
	
  }]);
