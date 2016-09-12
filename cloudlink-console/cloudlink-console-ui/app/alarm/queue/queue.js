'use strict';

/* Controllers */
angular.module('app').controller('queueCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout ) {
        $scope.page={total:0,pageSize:50};
        $scope.obj={};
        $scope.areas=new Array();
        $scope.emails=new Array();
        HttpUtil.ajax({
            url : "/api/common/areas",
            async: false,
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    $scope.areas = result.data;

                    $timeout(function(){
                        $scope.initQueue();
                    },100);

                });
            }
        });
        $scope.checkDefaultSystemConfig=function(){
            HttpUtil.ajax({
                url : "/api/systemConfig",
                async: false,
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        $scope.hasDefaultEmail=false;
                        if(result.data!=null && result.data!=undefined && result.data.alarmEmail!=null && result.data.alarmEmail!=undefined
                            && result.data.alarmEmail.trim()!=''){
                            $scope.hasDefaultEmail=true;
                        }
                    });
                }
            });
        };
        $scope.plusEmail=function(){
            // $scope.$apply(function(){
            $scope.emails.push('');
            // });

        };
        $scope.minusEmail=function(index){
            if($scope.emails.length<2)
            {
                alert('告警邮箱至少要有一个!');
                return;
            }
            $scope.emails.splice(index,1);
        }
        $scope.initQueue=function(){
            HttpUtil.ajax({
                url : '/api/alarm/queue',
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.checkDefaultSystemConfig();
                    $scope.$apply(function(){
                        if (result.errorCode == 0) {
                            $scope.obj=result.data;
                            $scope.obj.alarmSettings.QUEUE_MESSAGE_AMOUNT_THRESHOLD=parseInt( $scope.obj.alarmSettings.QUEUE_MESSAGE_AMOUNT_THRESHOLD);
                            //$scope.obj.alarmSettings.CHANNELS_THRESHOLD=parseInt( $scope.obj.alarmSettings.CHANNELS_THRESHOLD);
                            //切割邮箱地址字符串，使之成为字符串数组
                            if($scope.obj.alarmEmail!=null && $scope.obj.alarmEmail!=undefined){
                                $scope.emails=$scope.obj.alarmEmail.split(';');
                            }else {
                                $scope.emails=new Array();
                            }
                            if($scope.emails.length<1)
                            {
                                $scope.emails.push('');
                            }
                            if($scope.obj.area==null || $scope.obj.area==undefined)
                            {
                                $scope.obj.area=new Array();
                            }
                            //遍历所有的数据中心的checkbox和area数组，area数组存在的在响应的checkbox打钩
                            $('[name=area]').each(function(i,cb){
                                $.each($scope.obj.area,function(j,strArea){
                                    if($(cb).attr('value') ==strArea)
                                    {
                                        $(cb).prop('checked',true);
                                        return;
                                    }
                                });
                            } );


                        } else {
                            alert(result.errorMsg);
                        }

                    });
                },error:function(data) {

                }
            });
        }
        $scope.update=function(valid){
            if (valid) { /*表单验证通过*/
                $scope.obj.alarmEmail=$scope.emails.join(';');
                if($scope.obj.alarmEmail.trim()==''&&  (!$scope.hasDefaultEmail)){
                    alert('未配置告警接受人的邮件地址，可单独在当前告警界面配置，也可在【默认参数配置】中设定全局配置');
                    return;
                }
                //遍历所有的数据中心的checkbox,打钩的放入area数组中
                var arrArea=new Array();
                $('[name=area]').each(function(){

                    if( $(this).is(':checked'))
                    {
                        arrArea.push($(this).attr('value') );
                    }
                });
                $scope.obj.area=arrArea;
                HttpUtil.ajax({
                    url : '/api/alarm/queue',
                    //data : {alarmWay:$scope.obj.alarmWay,alarmEmail:$scope.obj.alarmEmails.join(';')},
                    data :   $scope.obj    ,
                    method : 'PUT',
                    loadingButton:'btnSave',
                    success : function(data) {
                        if (data.errorCode == 0) {
                            alert('操作成功');
                        } else {
                            alert(data.errorMsg);
                        }
                        $scope.initQueue();
                    }
                });
            }
        };

        /**
         * 展开或者折叠面板
         */
        $scope.toggle=function(panelId){
            $('#'+panelId).collapse('toggle');
        };
        /**
         * 列表排序
         * @param orderName
         * @param ascOrDesc
         */
        $scope.sort=function(orderName,ascOrDesc){
            $scope.orderName=orderName;
            $scope.ascOrDesc=!$scope.ascOrDesc;
        };
        /**
         * 查询所有的指令(队列原型)
         */
        $scope.queryAllQueues = function() {
            HttpUtil.ajax({
                url : "/api/queues",
                data : $scope.queryCondition,
                method : "GET",
                loadingButton:"btnQuery",
                success : function(result) {
                    $scope.$apply(function() {
                        $scope.datas = result.data;
                        $scope.page.total=$scope.datas.length;
                    });
                }
            });
        };
        $scope.queryAllQueues();
        /**
         * 展开邮箱
         */
        $scope.showEmails=function($event ){
            var btn= $($event.target)  ;
            if(btn.next().is(":hidden")){
                btn.next().show(300);
            }else btn.next().hide(300);
        }
        /**
         * 打开编辑窗口
         * @param queueName
         * @param isUpdate
         */
        $scope.openEditDialog = function(queueName, isUpdate) {

            /* 弹出新增、修改界面 */
            var modalInstance = $modal.open({
                animation : true,
                templateUrl : 'app/alarm/queue/appQueue_edit.html',
                controller : 'appQueueEditCtrl',
                resolve : {
                    params : function() {
                        return {
                            queueName : queueName
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
    }]);

/**
 * 新增、修改数据中心
 */
angular.module('app').controller('appQueueEditCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil','$modalInstance', 'params',
    function($scope,$translate,$localStorage,HttpUtil,$modalInstance,params ) {
        $scope.queueName = params.queueName;
        $scope.data = {};
        var action = "PUT";

        HttpUtil.ajax({
            url : "/api/queue/" + $scope.queueName,
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    if (result.errorCode == 0) {
                        $scope.data = result.data.queue;
                        if(!$scope.data.maxMsg || $scope.data.maxMsg==null)$scope.data.maxMsg=0;
                        if(!$scope.data.emails || $scope.data.emails==null || $scope.data.emails.length==0){
                            $scope.data.emails=new Array();
                            $scope.data.emails.push('');
                        }
                    } else {
                        alert(result.errorMsg);
                        $modalInstance.close(true);
                    }

                });
            }
        });

        $scope.save=function(valid){
            if (valid) { /*表单验证通过*/
                if($scope.data.emails!=null && $scope.data.emails!=undefined ){
                    for(var i=$scope.data.emails.length-1;i>=0;i--){
                        if($scope.data.emails[i]=='')
                            $scope.data.emails.splice(i,1);
                    }
                }
                HttpUtil.ajax({
                    url : "/api/queue/" + $scope.data.name,
                    data : $scope.data,
                    method : action,
                    loadingButton:"btnSave1",
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

        $scope.returnPage = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.plusEmail=function(){
            // $scope.$apply(function(){
            $scope.data.emails.push('');
            // });

        };
        $scope.minusEmail=function(index){
            if($scope.data.emails.length<2)
            {
                //alert('告警邮箱至少要有一个!');
                return;
            }
            $scope.data.emails.splice(index,1);
        }

    }]);