'use strict';

/* Controllers */
angular.module('app').controller('nodeCtrl', ['$scope', '$translate', '$localStorage', 'HttpUtil', '$modal','$timeout','$interval','$rootScope','$stateParams','$filter',
    function($scope,$translate,$localStorage,HttpUtil,$modal,$timeout,$interval ,$rootScope,$stateParams,$filter) {
        if( !$stateParams.nodeName || !$stateParams.areaCode){
            alert('无效参数');
            return;
        }
        $scope.$on("$destroy", function() {
            $interval.cancel($scope.timer);
        });
        /**
         * 展开或者折叠面板
         */
        $scope.toggle=function(panelId){
            $('#'+panelId).collapse('toggle');
        };
        $scope.initNode=function(){
            HttpUtil.ajax({
                url : "/api/monitor/node/"+$stateParams.areaCode+"/"+$stateParams.nodeName+$rootScope.getChartRange(),
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if(!result.data ){
                            //alert('无法连接RabbitMQ');
                            return;
                        }
                        var data=JSON.parse(result.data);
                        $scope.node=data;
                        $('#nodeName').html($scope.node.name);
                        $('#areaCode').html($stateParams.areaCode);
                        $scope.initPluginsList();
                        $scope.initOrUpdateChartProcessStatistics($scope.chartFileDescriptors,$scope.seriesFileDescriptors,data,false);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartSocketDescriptors,$scope.seriesSocketDescriptors,data,false);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartErlangProcesses,$scope.seriesErlangProcesses,data,false);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartMemory,$scope.seriesMemory,data,false);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartDiskSpace,$scope.seriesDiskSpace,data,false);
                        $rootScope.initOrUpdateChart($scope.chartMnesiaTransactions,$scope.seriesMnesiaTransactions,data,true,false);
                        $rootScope.initOrUpdateChart($scope.chartPersistenceOperationsMsg,$scope.seriesPersistenceOperationsMsg,data,true,false);
                        $rootScope.initOrUpdateChart($scope.chartPersistenceOperationsBulk,$scope.seriesPersistenceOperationsBulk,data,true,false);
                        $rootScope.initOrUpdateChart($scope.chartIOOperations,$scope.seriesIOOperations,data,true,false);
                        $rootScope.initOrUpdateChart($scope.chartIODataRates,$scope.seriesIODataRates,data,true,false);
                        $rootScope.initOrUpdateChart($scope.chartIOAvgTimePerOperation,$scope.seriesIOAvgTimePerOperation,data,true,false);
                    });
                }
            });
        };
        
        // 刷新图表方法
        $rootScope.refreshChart = $scope.initNode;
        
        $scope.refreshNode=function(){
            HttpUtil.ajax({
                url : "/api/monitor/node/"+$stateParams.areaCode+"/"+$stateParams.nodeName+$rootScope.getChartRange(),
                data : null,
                method : "GET",
                success : function(result) {
                    $scope.$apply(function(){
                        if(!result.data ){
                            //alert('无法连接RabbitMQ');
                            return;
                        }
                        var data=JSON.parse(result.data);
                        $scope.node=data;
                        $scope.initPluginsList();
                        $scope.initOrUpdateChartProcessStatistics($scope.chartFileDescriptors,$scope.seriesFileDescriptors,data,true);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartSocketDescriptors,$scope.seriesSocketDescriptors,data,true);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartErlangProcesses,$scope.seriesErlangProcesses,data,true);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartMemory,$scope.seriesMemory,data,true);
                        $scope.initOrUpdateChartProcessStatistics($scope.chartDiskSpace,$scope.seriesDiskSpace,data,true);
                        $rootScope.initOrUpdateChart($scope.chartMnesiaTransactions,$scope.seriesMnesiaTransactions,data,true,true);
                        $rootScope.initOrUpdateChart($scope.chartPersistenceOperationsBulk,$scope.seriesPersistenceOperationsBulk,data,true,true);
                        $rootScope.initOrUpdateChart($scope.chartIOOperations,$scope.seriesIOOperations,data,true,true);
                        $rootScope.initOrUpdateChart($scope.chartIODataRates,$scope.seriesIODataRates,data,true,true);
                        $rootScope.initOrUpdateChart($scope.chartIOAvgTimePerOperation,$scope.seriesIOAvgTimePerOperation,data,true,true);
                    });
                }
            });
        }
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
        };
        /**
         * 改变刷新时间
         */
        $scope.changeRefreshTime=function(){
            $interval.cancel($scope.timer);
            if($scope.refreshTime>0){
                $scope.timer=$interval(function(){
                    $scope.refreshNode();
                },parseInt($scope.refreshTime)*1000);
            }
        };
        /**
         * 获取Plugins
         * @returns {Array}
         */
        $scope.initPluginsList=function() {
            var node=$scope.node;
            var result = [];
            for (var i = 0; i < node.applications.length; i++) {
                var application = node.applications[i];
                if (jQuery.inArray(application.name, node.enabled_plugins) != -1 ) {
                    result.push(application);
                }
            }
            $scope.plugins=result;
        };
        //创建File descriptor chart
        $scope.chartFileDescriptors=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartFileDescriptors'
            },
            title: {
                text: 'File Descriptors',
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
                //valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + parseInt( this.y) ;
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
        $scope.seriesFileDescriptors={
            fd_used_details:{
                name:'used',
                allowPointSelect: true,
                color:'#5cb85c',
                data:[ ]
            },
            fd_total:{
                name:'limit',
                allowPointSelect: true,
                color:'#a94442',
                data:[]
            }
        };
        //创建chart Socket Descriptors
        $scope.chartSocketDescriptors=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartSocketDescriptors'
            },
            title: {
                text: 'Socket Descriptors',
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
                //valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + parseInt( this.y) ;
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
        $scope.seriesSocketDescriptors={
            sockets_used_details:{
                name:'used',
                allowPointSelect: true,
                color:'#5cb85c',
                data:[ ]
            },
            sockets_total:{
                name:'limit',
                allowPointSelect: true,
                color:'#a94442',
                data:[]
            }
        };
        //创建chart Erlang Processes
        $scope.chartErlangProcesses=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartErlangProcesses'
            },
            title: {
                text: 'Erlang Processes',
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
                //valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + parseInt( this.y) ;
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
        $scope.seriesErlangProcesses={
            proc_used_details:{
                name:'used',
                allowPointSelect: true,
                color:'#5cb85c',
                data:[ ]
            },
            proc_total:{
                name:'limit',
                allowPointSelect: true,
                color:'#a94442',
                data:[]
            }
        };


        //创建chart memory
        $scope.chartMemory=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartMemory'
            },
            title: {
                text: 'Memory',
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
                        return $filter('capacityFormat')(this.value);
                        //return this.value / 10000 +'万';
                    }
                }
            },
            tooltip: {
                //valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + $filter('capacityFormat')(this.y) ;
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
        $scope.seriesMemory={
            mem_used_details:{
                type: 'area',
                name:'used',
                allowPointSelect: true,
                color:'#5cb85c',
                data:[ ]
            },
            mem_limit:{
                name:'limit',
                allowPointSelect: true,
                color:'#a94442',
                data:[]
            }
        };
        //创建chart disk space
        $scope.chartDiskSpace=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartDiskSpace'
            },
            title: {
                text: 'Disk Space',
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
                     return $filter('capacityFormat')(this.value);
                     //return this.value / 10000 +'万';
                     }
                }
            },
            tooltip: {
                //valueSuffix: '个',
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' + $filter('capacityFormat')(this.y) ;
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
        $scope.seriesDiskSpace={
            disk_free_details:{
                type: 'area',
                name:'free',
                allowPointSelect: true,
                color:'#5cb85c',
                data:[ ]
            },
            disk_free_limit:{
                name:'limit',
                allowPointSelect: true,
                color:'#a94442',
                data:[]
            }
        };
        //创建 Mnesia transactions chart
        $scope.chartMnesiaTransactions=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartMnesiaTransactions'
            },
            title: {
                text: 'Mnesia Transactions',
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
        $scope.seriesMnesiaTransactions={
            mnesia_ram_tx_count_details:{name:'RAM only',allowPointSelect: true,color:'#edc240',data:[]},
            mnesia_disk_tx_count_details:{name:'Disk',allowPointSelect: true,color:'#337ab7',data:[]}
        };
        //创建 Persistence operations messages chart
        $scope.chartPersistenceOperationsMsg=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartPersistenceOperationsMsg'
            },
            title: {
                text: 'Persistence Operations(Messages)',
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
        $scope.seriesPersistenceOperationsMsg={
            queue_index_journal_write_count_details:{name:'QI Journal',allowPointSelect: true,color:'#edc240',data:[]},
            msg_store_read_count_details:{name:'Store Read',allowPointSelect: true,color:'#5cb85c',data:[]},
            msg_store_write_count_details:{name:'Store Write',allowPointSelect: true,color:'#337ab7',data:[]}
        };
        //创建 Persistence operations bulk chart
        $scope.chartPersistenceOperationsBulk=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartPersistenceOperationsBulk'
            },
            title: {
                text: 'Persistence Operations(bulk)',
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
        $scope.seriesPersistenceOperationsBulk={
            queue_index_read_count_details:{name:'QI Read',allowPointSelect: true,color:'#5cb85c',data:[]},
            queue_index_write_count_details:{name:'QI Write',allowPointSelect: true,color:'#edc240',data:[]}
        };
        //创建 I/O operations chart
        $scope.chartIOOperations=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartIOOperations'
            },
            title: {
                text: 'I/O Operations',
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
        $scope.seriesIOOperations={
            io_read_count_details:{name:'Read',allowPointSelect: true,color:'#5cb85c', data:[]},
            io_write_count_details:{name:'Write',allowPointSelect: true,color:'#edc240', data:[]},
            io_seek_count_details:{name:'Seek',allowPointSelect: true, data:[ ]},
            io_sync_count_details:{name:'Sync',allowPointSelect: true, data:[ ]},
            io_reopen_count_details:{name:'Reopen',allowPointSelect: true, data:[ ]}
        };
        //创建 I/O data rate chart
        $scope.chartIODataRates=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartIODataRates'
            },
            title: {
                text: 'I/O  Data Rates',
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
        $scope.seriesIODataRates={
            io_read_bytes_details:{name:'Read',allowPointSelect: true,color:'#5cb85c', data:[]},
            io_write_bytes_details:{name:'Write',allowPointSelect: true,color:'#edc240', data:[]}
        };
        //创建 I/O average time per operation chart
        $scope.chartIOAvgTimePerOperation=new Highcharts.Chart({
            chart:{
                type: 'spline',
                renderTo: 'chartIOAvgTimePerOperation'
            },
            title: {
                text: 'I/O average time per operation',
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
        $scope.seriesIOAvgTimePerOperation={
            io_read_avg_time_details:{name:'Read',allowPointSelect: true,color:'#5cb85c', data:[ ]},
            io_write_avg_details:{name:'Write',allowPointSelect: true,color:'#edc240', data:[ ]},
            io_seek_avg_time_details:{name:'Seek',allowPointSelect: true,   data:[ ]},
            io_sync_avg_time_details:{name:'Sync',allowPointSelect: true,  data:[ ]}
        };
        /**
         *
         * @param chart chart 对象
         * @param series 包含每条线的配置的对象，格式{json属性名:{options},json属性名:{options}....}
         * @param data   每条线对应的json属性的父节点
         * @param isRefresh 是否刷新chart，false代表初始化chart
         */
        $scope.initOrUpdateChartProcessStatistics=function(chart,series,data, isRefresh){
            var hasData=false;
            for(var pSeries in series ){
                for(var pData in data){
                    if(typeof(data[pData])!='function'){
                        if(pSeries==pData){
                            hasData=true;
                            break;
                        }
                    }
                }
                if(hasData)break;
            }
            var curTitle=chart.options.title.text;
            if(curTitle){
                curTitle=curTitle.replace("(Currently idle)","");
            }

            if(!hasData){
                chart.setTitle({text:curTitle+"(Currently idle)"});
                return;
            }else {
                chart.setTitle({text:curTitle});
            }
            var tmp=0;
            var usedName;
            var limitName;
            for(var seriesName in series){
                if(tmp==0)usedName=seriesName;
                else limitName=seriesName;
                tmp++;
            }
            var points=new Array();
            var limitPoints=new Array();
            //由于points是时间递减的，所以从最后一个开始遍历
            for(var i=data[usedName].samples.length-1;i>=0;i--){
                var point=data[usedName].samples[i];
                if(isRefresh){
                    var curSeries=chart.series[0];
                    if(parseInt(curSeries.data[curSeries.data.length-1].x)<point.timestamp){
                        curSeries.addPoint({x: point.timestamp,y: point.sample },false,true);
                        chart.series[1].addPoint({x:point.timestamp,y:data[limitName]},false,true);
                    }
                }else {
                    points.push({x:point.timestamp,y:point.sample });
                    limitPoints.push({x:point.timestamp,y:data[limitName]});
                }
            }
            if(!isRefresh){
                series[usedName].data=points;
                series[limitName].data=limitPoints;
                //清空queue messages chart
                while(chart.series.length > 0) {
                    chart.series[0].remove(false);
                }
                for(var seriesName in series){
                    if(typeof (series [ seriesName ]) != "function"){
                        if(data && data[seriesName]){
                            chart.addSeries(series[seriesName]);
                        }
                    }
                }
            }
            chart.redraw();
        };
        $scope.initNode();
        $scope.refreshTime=5;
        $scope.changeRefreshTime();
    }]);