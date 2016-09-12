'use strict';

/* Controllers */
angular.module('app').controller('overviewCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout','$interval','$rootScope',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout,$interval ,$rootScope) {
        $scope.$on("$destroy", function() {
            $interval.cancel($scope.timer);
        })
        //创建queued message chart
        $scope.chartQueueMsg=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartQueueMsg'
            },
            title: {
                text: 'Queued Messages',
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
                min:0,
                allowDecimals: true,
                title: {
                    text: ''
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                        this.y+" messages";
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
        $scope.seriesQueueMsg={
             messages_ready_details:{
                name:'Ready',
                allowPointSelect: true,
                color:'#5cb85c',
                data:[ ]
             },
             messages_unacknowledged_details:{
                name:'Unacked',
                allowPointSelect: true,
                color:'#a94442',
                data:[]
             },
             messages_details:{name:'Total',
                allowPointSelect: true,
                color:'#337ab7',
                data:[]
             }
        };
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
         * 刷新时间下拉框点击事件
         * @param btn
         */
        $scope.areaClick=function(btn){
            $scope.initInfoByArea(this.area.code);
            $scope.selectedArea=this.area.code;
            $scope.changeRefreshTime();
            $rootScope.monitor.selectedArea=$scope.selectedArea;
        };
        /**
         * 根据数据中心刷新整个页面数据
         * @param areaCode
         */
        $scope.refreshInfoByArea=function(areaCode){
            HttpUtil.ajax({
                url : "/api/monitor/clusterStatus/"+areaCode+$rootScope.getChartRange(),
                data : null,
                method : "GET",
                success : function(result) {
                    if(result.data==null|| result.data==undefined||result.data==''){
                        //alert('无法连接RabbitMQ');
                        return;
                    }
                    $scope.$apply(function(){
                        var data=JSON.parse(result.data);
                        $scope.overview=data;
                        $scope.globalCounts=data['object_totals'];
                        $rootScope.initOrUpdateChart($scope.chartQueueMsg,$scope.seriesQueueMsg,data.queue_totals,false,true);
                        //$scope.initChartQueueMsg(data);
                        //初始化消息消费率图
                        $rootScope.initOrUpdateChart($scope.chartMsgRate,$scope.seriesMsgRate,data.message_stats,true,true);
                        /*$scope.refreshChartQueueMsg(data);
                        $scope.refreshChartMsgRate(data);*/
                    });


                }
            });
            $scope.loadNodesByArea(areaCode);
        };
        /**
         * 根据数据中心初始化页面数据
         * @param areaCode
         */
        $scope.initInfoByArea=function(areaCode){
        	if (!areaCode) areaCode = $scope.selectedArea;
            HttpUtil.ajax({
                url : "/api/monitor/clusterStatus/"+areaCode+$rootScope.getChartRange(),
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if(result.data==null|| result.data==undefined||result.data==''){
                            //alert('无法连接RabbitMQ');
                            return;
                        }
                        var data=JSON.parse(result.data);
                        $scope.overview=data;
                        $scope.globalCounts=data['object_totals'];
                        //初始化队列消息图
                        $rootScope.initOrUpdateChart($scope.chartQueueMsg,$scope.seriesQueueMsg,data.queue_totals,false,false);
                        //初始化消息消费率图
                        $rootScope.initOrUpdateChart($scope.chartMsgRate,$scope.seriesMsgRate,data.message_stats,true,false);
                        //删除HighCharts的logo→_→
                        $('text').each(function(i,o){
                            if($(o).html()=='Highcharts.com')o.remove();
                        });
                    });
                }
            });
            $scope.loadNodesByArea(areaCode);
        };
        
        // 刷新图表方法
        $rootScope.refreshChart = $scope.initInfoByArea;
        
        /**
         * 加载nodes信息
         * @param areaCode
         */
        $scope.loadNodesByArea=function(areaCode){
            HttpUtil.ajax({
                url : "/api/monitor/nodes/"+areaCode,
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if(result.data==null|| result.data==undefined||result.data==''){
                            //alert('无法连接RabbitMQ');
                            return;
                        }
                        var data=JSON.parse(result.data);
                        $scope.nodes=data;
                    });
                }
            });
        }

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
                        //默认选择第一个
                        $timeout(function(){
                            if($scope.areas.length>0){
                                if($rootScope.monitor.selectedArea){
                                    $('[name=area][value='+$rootScope.monitor.selectedArea+']:eq(0)').prop('checked',true);
                                    $scope.selectedArea=$rootScope.monitor.selectedArea;
                                }
                                else {
                                    $('[name=area]:eq(0)').prop('checked',true);
                                    $scope.selectedArea=($rootScope.monitor.selectedArea=$scope.areas[0].code) ;
                                }
                            }
                            $scope.initInfoByArea($scope.selectedArea );
                            $scope.refreshTime=5;
                            $scope.changeRefreshTime();
                        },200);
                    }
                });
            }
        });
        $scope.toggleExpand=function(btnId){
            $scope.isExpanded=!$scope.isExpanded;
            if($scope.isExpanded){
                $('#'+btnId+' span').removeClass();
                $('#'+btnId+' span').addClass('glyphicon glyphicon-collapse-up');
                $('.panel-collapse.collapse.in').collapse('hide');
                $('#'+btnId+' font').html('展开所有');
            }else {
                $('#'+btnId+' span').removeClass();
                $('#'+btnId+' span').addClass('glyphicon glyphicon-collapse-down');
                $('.panel-collapse.collapse').collapse('show');
                $('#'+btnId+' font').html('收起所有');
            }
        }
        $scope.toNodePage=function(nodeName){
            top.location='#/app/monitor/node/'+$scope.selectedArea+'/'+nodeName;
        }
    }]);