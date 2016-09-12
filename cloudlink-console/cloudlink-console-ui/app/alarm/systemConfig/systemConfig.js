'use strict';

/* Controllers */
angular.module('app').controller('systemConfigCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal',
    function($scope,$translate,$localStorage,HttpUtil,$modal ) {
        $scope.obj={};
        $scope.obj.alarmWay='';
        $scope.obj.alarmEmails=new Array();
        $scope.plusEmail=function(){
           // $scope.$apply(function(){
                $scope.obj.alarmEmails.push('');
           // });

        };
        $scope.minusEmail=function(index){
            if($scope.obj.alarmEmails.length<2)
            {
                alert('告警邮箱至少要有一个!');
                return;
            }
            $scope.obj.alarmEmails.splice(index,1);
        }
        $scope.initSystemConfig=function(){
            HttpUtil.ajax({
                url : '/api/systemConfig',
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if (result.errorCode == 0) {
                            $scope.obj.alarmWay=result.data.alarmWay;
                            if(result.data.alarmEmail!=null && result.data.alarmEmail!=undefined){

                                $scope.obj.alarmEmails=result.data.alarmEmail.split(';');
                            }else {
                                $scope.obj.alarmEmails=new Array();
                            }
                            if($scope.obj.alarmEmails.length<1)
                            {
                                $scope.obj.alarmEmails.push('');
                            }
                            //$scope.obj = result.data;
                        } else {
                            alert(result.errorMsg);
                        }

                    });
                },error:function(data) {

                }
            });
        }
        $scope.initSystemConfig();
        $scope.update=function(valid){
            if (valid) { /*表单验证通过*/
                HttpUtil.ajax({
                    url : '/api/systemConfig',
                    data : {alarmWay:$scope.obj.alarmWay,alarmEmail:$scope.obj.alarmEmails.join(';')},
                    method : 'PUT',
                    loadingButton:'btnSave',
                    success : function(data) {
                        if (data.errorCode == 0) {
                            alert('操作成功');
                        } else {
                            alert(data.errorMsg);
                        }
                        $scope.initSystemConfig();
                    }
                });
            }
        }

    }]);


/*

app.animation('.item', function () {
    return {
        enter : function(element, done) {
            console.log("entering...");
            var width = element.width();
            element.css({
                position: 'relative',
                left: -10,
                opacity: 0
            });alert('entering');
            element.animate({
                left: 0,
                opacity: 1
            }, done);
        },
        leave : function(element, done) {
            element.css({
                position: 'relative',
                left: 0,
                opacity: 1
            });alert('leave');
            element.animate({
                left: -10,
                opacity: 0
            }, done);
        },
        move : function(element, done) {
            element.css({
                left: "2px",
                opacity: 0.5
            });
            element.animate({
                left: "0px",
                opacity: 1
            }, done);
        }
    };
});
*/

