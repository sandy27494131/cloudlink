'use strict';

/* Controllers */
angular.module('app').controller('appidSearchCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout','$interval',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout,$interval ) {
        $scope.page={total:0,pageSize:50};

        /*排序参数*/
        $scope.orderName='areaCode';
        $scope.ascOrDesc=true;//false 升序  true 降序
        $scope.sort=function(orderName,ascOrDesc){
            $scope.orderName=orderName;
            $scope.ascOrDesc=!$scope.ascOrDesc;
        }

        //初始化数据中心多选框
        HttpUtil.ajax({
            url : "/api/common/areas",
            async: false,
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    $scope.areas = result.data;
                });
            }
        });
        $scope.initInfoByAreas=function(){
            HttpUtil.ajax({
                url : "/api/search/appid/"+$scope.selectedAreas.join(','),
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
        $scope.areaClick=function(btn){
            $scope.selectedAreas=new Array();
            $('[name=area]:checked').each(function(i,o){
                $scope.selectedAreas.push(o.value);
            });
            if($scope.selectedAreas.length>0){
                $scope.initInfoByAreas();
            }
            else{
                $scope.datas=[];
            }
        };
        $scope.selectAllAreas=function(){
            $scope.selectedAreas=new Array();
            $scope.isAllSelected=!$scope.isAllSelected;
            if($scope.isAllSelected){
                $('[name=area]').prop('checked',true);
            }
            else{
                $('[name=area]').prop('checked',false);
            }

            $('[name=area]:checked').each(function(i,o){
                $scope.selectedAreas.push(o.value);
            });
            if($scope.selectedAreas.length>0){
                $scope.initInfoByAreas();
            }
            else{
                $scope.datas=[];
            }
        }
    }]);