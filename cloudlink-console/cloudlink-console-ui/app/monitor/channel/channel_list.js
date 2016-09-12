'use strict';

/* Controllers */
angular.module('app').controller('channelListMonitorCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout','$interval','$stateParams','$rootScope',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout,$interval ,$stateParams,$rootScope) {
		$scope.$on("$destroy", function() {
	        $interval.cancel($scope.timer);
	    })
	    
	    $scope.page={total:0,pageSize:50};
	    
		/*排序参数*/
		$scope.orderName='idle_since';
		$scope.ascOrDesc=true;//false 升序  true 降序
		$scope.sort=function(orderName,ascOrDesc){
			$scope.orderName=orderName;
			$scope.ascOrDesc=!$scope.ascOrDesc;
		}
	
        //初始化数据中心单选框
        HttpUtil.ajax({
            url : "/api/common/areas",
            async: false,
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    $scope.areas = result.data;
                    $timeout(function(){
                        if($scope.areas!=null &&$scope.areas!=undefined)
                        {
                            if($scope.areas.length>0){
                                if(!$rootScope.monitor.selectedArea){
                                    $('[name=area]:eq(0)').prop('checked',true);
                                    $rootScope.monitor.selectedArea=($scope.selectedArea=$scope.areas[0].code);
                                }else {
                                    $('[name=area][value='+$rootScope.monitor.selectedArea+']:eq(0)').prop('checked',true);
                                    $scope.selectedArea=$rootScope.monitor.selectedArea;
                                }
                            }
                            $scope.refresh($scope.selectedArea);
                            $scope.timer=$interval(function(){
                                $scope.refresh($scope.selectedArea);
                            },5000);
                            $scope.refreshTime=5;
                        }
                    },200);

                });
            }
        });
        
        $scope.refresh=function(area) {
        	HttpUtil.ajax({
                url : "/api/monitor/channel/" + area,
                async: false,
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                    	$scope.datas = JSON.parse(result.data);
                    	$scope.page.total = $scope.datas.length;
                    });
                }
            });
        }
        
        $scope.changeRefreshTime=function(value){  
            $interval.cancel($scope.timer);
            if ($scope.refreshTime > 0) {
                $scope.timer=$interval(function(){
                    $scope.refresh($scope.selectedArea);
                },parseInt($scope.refreshTime)*1000);
            }
        };
        $scope.areaClick=function(btn){
            $scope.selectedArea=this.area.code;
            $scope.refresh($scope.selectedArea);
            $scope.changeRefreshTime();
            $rootScope.monitor.selectedArea=$scope.selectedArea;
        };

    }]);