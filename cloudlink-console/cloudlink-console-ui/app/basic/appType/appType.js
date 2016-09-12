'use strict';



/* Controllers */
angular.module('app').controller('appTypeListCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
    function($scope,$translate,$localStorage,HttpUtil,$modal ) {
        /*基本参数*/
        $scope.urlQuery="/api/appTypes";
        $scope.editHtmlUrl='app/basic/appType/appType_add.html';
        $scope.urlPrefix="/api/appType/";
        $scope.editCtrlName='appTypeEditCtrl';
        /*排序参数*/
        $scope.orderName='';
        $scope.ascOrDesc=false;//false 升序  true 降序
        $scope.sort=function(orderName,ascOrDesc){
            $scope.orderName=orderName;
            $scope.ascOrDesc=!$scope.ascOrDesc;
        }
        $scope.queryCode='';
        $scope.queryAll = function () {
            HttpUtil.ajax({
                url :$scope.urlQuery,
                data : {code:$scope.queryCode},
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
 * 新增、修改应用类型
 */
angular.module('app').controller('appTypeEditCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil','$modalInstance', 'params',
    function($scope,$translate,$localStorage,HttpUtil,$modalInstance,params ) {

        $scope.urlPrefix="/api/appType/";
        $scope.editCtrlName='appTypeEditCtrl';

        $scope.isUpdate = params.isUpdate;
        $scope.code = params.code;
        $scope.obj = {};
        var action = "POST";

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
                    HttpUtil.ajax({
                        url : $scope.urlPrefix + $scope.obj.code,
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