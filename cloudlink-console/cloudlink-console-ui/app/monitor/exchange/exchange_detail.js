'use strict';

/* Controllers */
angular.module('app').controller('exchangeDetailMonitorCtrl', ['$scope', '$stateParams','$translate', '$localStorage', 'HttpUtil', '$modal','$timeout','$interval','$rootScope',
    function($scope,$stateParams,$translate,$localStorage,HttpUtil,$modal,$timeout,$interval ,$rootScope) {
	$rootScope.currentArea=$stateParams.area;
	$rootScope.currentNav=$stateParams.exchange;
        $scope.$on("$destroy", function() {
            $interval.cancel($scope.timer);
        })
        //创建 message rate chart
        $scope.chartMsgRate=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartMsgRate'
            },
            title: {
                text: 'Messages Rate',
                x: -20 //center
            },
            subtitle: {
                /*text: 'Source: WorldClimate.com',
                 x: -20*/
            },
            xAxis: {
                type: 'datetime',
                tickPixelInterval: 150
            },
            yAxis: {
                min:0.0,
                allowDecimals: true,
                title: {
                    text: ''
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }],
                labels: {
                    formatter: function() {
                        return  this.value+'/s';
                    }
                }
            },
            tooltip: {
                valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                        this.y+"/s";
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0,
                labelFormat:'{name}'
            },
            series: []
        });
        $scope.seriesMsgRate={
            	deliver_details:{name:'Deliver',allowPointSelect: true,color:'#f0ad4e',data:[ ]},
                ack_details:{name:'Acknowledge',allowPointSelect: true,color:'#337ab7',data:[ ]},
                deliver_no_ack_details:{name:'Deliver (noack)',allowPointSelect: true,color:'#d9534f',data:[]},
                publish_details:{name:'Publish',allowPointSelect: true,color:'#9F4D95',data:[]},
                confirm_details:{name:'Confirm',allowPointSelect: true,color:'#3D7878',data:[]},
                publish_in_details:{name:'Publish (In)',allowPointSelect: true,color:'#707038',data:[]},
                publish_out_details:{name:'Publish (Out)',allowPointSelect: true,color:'#804040',data:[]},
                redeliver_details:{name:'Redelivered',allowPointSelect: true,color:'#F75000',data:[]},
                get_details:{name:'Get',allowPointSelect: true,color:'#EAC100',data:[]},
                get_no_ack_details:{name:'Get (noack)',allowPointSelect: true,color:'#82D900',data:[]},
                return_unroutable_details:{name:'Return',allowPointSelect: true,color:'#009393',data:[]},
                disk_reads_details:{name:'Disk read',allowPointSelect: true,color:'#00A600',data:[]},
                disk_writes_details:{name:'Disk write',allowPointSelect: true,color:'#D200D2',data:[]}
        };
        /**
         * 改变刷新时间
         */
        $scope.changeRefreshTime=function(){
            $interval.cancel($scope.timer);
            if($scope.refreshTime>0){
                $scope.timer=$interval(function(){
                    $scope.refreshInfoByArea($scope.selectedArea);
                },parseInt($scope.refreshTime)*1000);
            }
        };
        /**
         * 根据数据中心刷新整个页面数据
         * @param areaCode
         */
        $scope.refreshInfoByArea=function(areaCode){
            HttpUtil.ajax({
                url : "/api/monitor/exchange/"+areaCode+"/"+$stateParams.exchange+$rootScope.getChartRange(),
                data : null,
                method : "GET",
                success : function(result) {
                    if(result.data==null|| result.data==undefined||result.data==''){
                        return;
                    }
                    $scope.$apply(function(){
                        var exchange=JSON.parse(result.data.exchange);
                        $scope.exchange=exchange;
                        $scope.bindingSource=JSON.parse(result.data.bindingSource);
                        $scope.bindingDestination=JSON.parse(result.data.bindingDestination);
                        //初始化消息消费率图
                        $rootScope.initOrUpdateChart($scope.chartMsgRate,$scope.seriesMsgRate,exchange.message_stats,true,true);
                    });


                }
            });
        };
        /**
         * 根据数据中心初始化页面数据
         * @param areaCode
         */
        $scope.initInfoByArea=function(){
            HttpUtil.ajax({
                url : "/api/monitor/exchange/"+$stateParams.area+"/"+$stateParams.exchange+$rootScope.getChartRange(),
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if(result.data==null|| result.data==undefined||result.data==''){
                            return;
                        }
                        var exchange=JSON.parse(result.data.exchange);
                        $scope.exchange=exchange;
                        $scope.bindingSource=JSON.parse(result.data.bindingSource);
                        $scope.bindingDestination=JSON.parse(result.data.bindingDestination);
                        //初始化消息消费率图
                        $rootScope.initOrUpdateChart($scope.chartMsgRate,$scope.seriesMsgRate,exchange.message_stats,true,false);
                        //删除HighCharts的logo→_→
                        $('text').each(function(i,o){
                            if($(o).html()=='Highcharts.com')o.remove();
                        });
                    });
                }
            });
        };
        
        // 刷新图表方法
        $rootScope.refreshChart = $scope.initInfoByArea;

        /**
         * 展开或者折叠面板
         */
        $scope.toggle=function(panelId){
            $('#'+panelId).collapse('toggle');
        };
        //启动页面
        /**
         * 初始化数据中心单选框
         */
        HttpUtil.ajax({
            url : "/api/common/areas",
            async: false,
            data : null,
            method : "GET",
            success : function(result) {
                $scope.$apply(function(){
                    $scope.areas = result.data;
                    if($scope.areas!=null &&$scope.areas!=undefined)
                    {
                        $timeout(function(){
                            $scope.selectedArea=$stateParams.area;
                        },200);
                        $scope.initInfoByArea();
                        $scope.refreshTime=5;
                        $scope.changeRefreshTime();
                    }
                });
            }
        });
        $scope.toggleExpand=function(btnId){
            if($('#'+btnId+' span').attr('class')=='glyphicon glyphicon-collapse-up') {
                $('#'+btnId+' span').removeClass();
                $('#'+btnId+' span').addClass('glyphicon glyphicon-collapse-down');
                $('.panel-collapse.collapse').collapse('show');
                $('#'+btnId+' font').html('收起所有');
            }else {
                $('#'+btnId+' span').removeClass();
                $('#'+btnId+' span').addClass('glyphicon glyphicon-collapse-up');
                $('.panel-collapse.collapse.in').collapse('hide');
                $('#'+btnId+' font').html('展开所有');
            }
       }
    }]);