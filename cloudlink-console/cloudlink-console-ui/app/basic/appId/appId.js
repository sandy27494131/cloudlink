'use strict';



/* Controllers */
angular.module('app').controller('appIdListCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
    function($scope,$translate,$localStorage,HttpUtil,$modal ) {
        /*基本参数*/
        $scope.urlQuery="/api/appIds";
        $scope.editHtmlUrl='app/basic/appId/appId_add.html';
        $scope.urlPrefix="/api/appId/";
        $scope.editCtrlName='appIdEditCtrl';

        /*排序参数*/
        $scope.orderName='enable';
        $scope.ascOrDesc=true;//false 升序  true 降序
        $scope.sort=function(orderName,ascOrDesc){
            $scope.orderName=orderName;
            $scope.ascOrDesc=!$scope.ascOrDesc;
        }

        $scope.queryStr='';
        $scope.queryKey='appId';
        $scope.queryAll = function () {
            HttpUtil.ajax({
                url :$scope.urlQuery,
                data : {queryStr:$scope.queryStr,queryKey:$scope.queryKey},
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        $scope.datas = result.data;
                    });
                }
            });
        }

        /* 页面加载时查询应用类型列表  */
        $scope.queryAll();

        $scope.openEditDialog = function (code,isUpdate) {

            /* 弹出新增、修改界面 */
            var modalInstance = $modal.open({
                animation: true,
                templateUrl:$scope.editHtmlUrl,
                controller:$scope.editCtrlName,
                resolve: {
                    params:function () { return {code:code,isUpdate:isUpdate}; }
                }
            });

            /* 弹出框关闭时刷新应用类型表 */
            modalInstance.result.then(function (refresh) {
                if (refresh) $scope.queryAll();
            });
        };

        /* 删除应用类型 */
        $scope.delete = function(code) {
            if (confirm("确认删除吗？")) {
                HttpUtil.ajax({
                    url : $scope.urlPrefix + code,
                    data : null,
                    method : "DELETE",
                    success : function(result) {
                        if (result.errorCode == 0) {
                            alert('操作成功');
                            $scope.queryAll();
                        } else {
                            alert(result.errorMsg);
                        }
                    }
                });
            }
        }

    }]);

/**
 * 新增、修改应用实例
 */
angular.module('app').controller('appIdEditCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil','$modalInstance', 'params',
    function($scope,$translate,$localStorage,HttpUtil,$modalInstance,params ) {
        $scope.urlPrefix="/api/appId/";


        $scope.areas=[];
        $scope.appTypes=[];
        $scope.isUpdate = params.isUpdate;
        $scope.code = params.code;

        $scope.obj = {};
        var action = "POST";

        /* 初始化下拉框数据*/
        HttpUtil.ajax({
            url : '/api/areas',
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    if (result.errorCode == 0) {
                        $scope.areas = result.data;
                    } else {
                        alert(result.errorMsg);
                        $modalInstance.close(true);
                    }

                });
            },error:function(data) {

            }
        });
        HttpUtil.ajax({
            url : '/api/appTypes',
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    if (result.errorCode == 0) {
                        $scope.appTypes = result.data;
                    } else {
                        alert(result.errorMsg);
                        $modalInstance.close(true);
                    }

                });
            }
        });

        /* 编辑数据前根据编码查询应用类型*/
        if ($scope.code) {
            HttpUtil.ajax({
                url : $scope.urlPrefix+ $scope.code,
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if (result.errorCode == 0) {
                            $scope.obj = result.data;
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
                    $scope.obj.appId=$scope.obj.uniqueId+'.'+$scope.obj.appType+'.'+$scope.obj.country+'.'+$scope.obj.area;
                    HttpUtil.ajax({
                        url : $scope.urlPrefix + $scope.obj.appId,
                        data : $scope.obj,
                        method : action,
                        loadingButton:'btnSave',
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