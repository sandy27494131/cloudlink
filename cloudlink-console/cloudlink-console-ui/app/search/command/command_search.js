'use strict';

/* Controllers */
angular.module('app').controller('cmdSearchCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout','$interval',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout,$interval ) {
        $scope.page={total:0,pageSize:50};

        /*排序参数*/
        $scope.orderName='callback';
        $scope.ascOrDesc=true;//false 升序  true 降序
        $scope.sort=function(orderName,ascOrDesc){
            $scope.orderName=orderName;
            $scope.ascOrDesc=!$scope.ascOrDesc;
        }
        /**
         * 初始化数据中心列表
         */
        HttpUtil.ajax({
            url : "/api/common/areas",
            async: false,
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    $scope.areas = result.data;
                    if($scope.areas!=null &&$scope.areas!=undefined && $scope.areas.length>0)
                    {
                        //默认选择第一个
                        $timeout(function(){
                            $scope.areaCodeFrom=$scope.areas[0].code;
                            $scope.areaCodeTo=$scope.areas[0].code;
                            $scope.areaSelect();
                        },200);
                    }
                });
            }
        });
        $scope.initInfo=function(){
            HttpUtil.ajax({
                url : "/api/search/cmd/"+$scope.selectedAreas.join(','),
                async: false,
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        $scope.datas = result.data;
                        $scope.page.total = $scope.datas.length;
                    });
                }
            });
        }
        $scope.areaSelect=function(){
            $scope.selectedAreas=new Array();
            $scope.selectedAreas.push($scope.areaCodeFrom);
            $scope.selectedAreas.push($scope.areaCodeTo);
            $scope.initInfo();
        }
        $scope.showAppids=function($event ){
             var btn= $($event.target)  ;
             if(btn.next().is(":hidden")){
                  btn.next().show(300);
             }else btn.next().hide(300);
        }
        $scope.swapAreaCode=function(){
            var tmp=$scope.areaCodeFrom;
            $scope.areaCodeFrom=$scope.areaCodeTo;
            $scope.areaCodeTo=tmp;
            $scope.areaSelect();
        }
    }]);