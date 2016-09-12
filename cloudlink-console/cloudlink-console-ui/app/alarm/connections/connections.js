'use strict';

/* Controllers */
angular.module('app').controller('connectionsCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout ) {
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
                    //if(result.data!=null && result.data!=undefined)
                    $scope.areas = result.data;

                    $timeout(function(){
                        $scope.initConnections();
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
        $scope.initConnections=function(){
            HttpUtil.ajax({
                url : '/api/alarm/connections',
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.checkDefaultSystemConfig();
                    $scope.$apply(function(){
                        if (result.errorCode == 0) {
                            $scope.obj=result.data;
                            $scope.obj.alarmSettings.CONNECTIONS_THRESHOLD=parseInt( $scope.obj.alarmSettings.CONNECTIONS_THRESHOLD);
                            $scope.obj.alarmSettings.CHANNELS_THRESHOLD=parseInt( $scope.obj.alarmSettings.CHANNELS_THRESHOLD);
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
                    url : '/api/alarm/connections',
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
                        $scope.initConnections();
                    }
                });
            }
        }
    }]);