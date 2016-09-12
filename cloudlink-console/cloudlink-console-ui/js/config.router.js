'use strict';

/**
 * Config for the router
 */
angular.module('app')
  .run(['$rootScope', '$state', '$stateParams','HttpUtil','utils',
      function ($rootScope,   $state,   $stateParams,HttpUtil,utils) {
          $rootScope.$state = $state;
          $rootScope.$stateParams = $stateParams; 
          $rootScope.token = $.cookie("auth_token");
          $rootScope.monitor={
              selectedArea:''
          };
          $rootScope.configs={
        		  cron_expressions_url:"app/common/comments/cron_expressions.html",
        		  rabbitmq:{api:{incr:5}},
        		  monitorRefreshCycle:[{value:5,label:"每5秒刷新"},
			        			  	   {value:10,label:"每10秒刷新"},
			        			  	   {value:30,label:"每30秒刷新"},
			        			  	   {value:60,label:"每60秒刷新"},
			        			  	   {value:-1,label:"从不刷新"}],
			      monitorChartRange:[{age:60,incr:5,label:"最近1分钟数据"},
			        			  	   {age:600,incr:10,label:"最近10分钟数据"},
			        			  	   {age:3600,incr:60,label:"最近1小时数据"},
			        			  	   {age:28800,incr:600,label:"最近8小时数据"},
			        			  	   {age:86400,incr:1800,label:"最近1天数据"}
			                           ],
			      data_age:60,data_incr:5,defaultDataAge:60
          };
          
          $rootScope.getChartRange=function(){
        	  return "?data_age="+$rootScope.configs.data_age+"&data_incr="+$rootScope.configs.data_incr;
          }
          
          $rootScope.chartRangeChange = function(){
        	  angular.forEach($rootScope.configs.monitorChartRange, function(val) {
        		  if (val.age == $rootScope.configs.defaultDataAge) {
        			  $rootScope.configs.data_age = val.age;
        			  $rootScope.configs.data_incr = val.incr;
        			  if ($rootScope.refreshChart){
        				  $rootScope.refreshChart();
        			  };
        			  return;
        		  }
        	  });
          }
          
          function keys(obj) {
        	    var ks = [];
        	    for (var k in obj) {
        	        ks.push(k);
        	    }
        	    return ks;
        	}
          /**
           *
           * @param chart chart 对象
           * @param series 包含每条线的配置的对象，格式{json属性名:{options},json属性名:{options}....}
           * @param data   每条线对应的json属性的父节点
           * @param isRate 是否百分率
           * @param isRefresh 是否刷新chart，false代表初始化chart
           */
          $rootScope.initOrUpdateChart=function(chart,series,data,isRate,isRefresh){
              var index=0; /*chart中的series的下标*/
             /* if (keys(data).length <= 0) {
            	  chart.setTitle({text:"Currently idle"});
            	  return;
              }*/
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
              for(var seriesName in series){
                  if(typeof ( series [ seriesName ]) != "function"){
                      if(!data || !data[seriesName]){
                          continue;
                      }
                      var points=new Array();
                      //由于points是时间递减的，所以从最后一个开始遍历
                      for(var i=data[seriesName].samples.length-1;i>=0;i--){
                          var point=data[seriesName].samples[i];
                          /*如果是百分率*/
                          if(isRate){
                              if(isRefresh){
                                  var curSeries=chart.series[index];
                                  if(parseInt(curSeries.data[curSeries.data.length-1].x)<point.timestamp && (i+1)<data[seriesName].samples.length){
                                      var prePoint=data[seriesName].samples[i+1];
                                      var rate=Highcharts.numberFormat((point.sample-prePoint.sample)/$rootScope.configs.data_incr, 1);
                                      curSeries.addPoint({x: point.timestamp,y: parseFloat(rate)},false,true);
                                  }
                              }
                              else {
                                  if(i==data[seriesName].samples.length-1){
                                      points.push({x:point.timestamp,y:0});
                                  }else{
                                      var prePoint=data[seriesName].samples[i+1];
                                      var rate=Highcharts.numberFormat((point.sample-prePoint.sample)/$rootScope.configs.data_incr, 1);
                                      points.push({x:point.timestamp,y:parseFloat(rate)});
                                  }
                              }

                          } /*如果不是百分率*/
                          else {
                              if(isRefresh){
                                  var curSeries=chart.series[index];
                                  if(parseInt(curSeries.data[curSeries.data.length-1].x)<point.timestamp){
                                      curSeries.addPoint({x: point.timestamp,y: point.sample},false,true);
                                  }
                              }else {
                                  points.push({x:point.timestamp,y:point.sample});
                              }
                          }

                      }
                      if(isRefresh){
                          index++;
                      }else {
                          series[seriesName].data=points;
                      }
                  }
              }
              if(!isRefresh){
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

          $.ajaxSetup({
    	　　　　//timeout: 3000,
    	　　　　/*请求成功后触发*/
    	　　　　success: function (data) {},
    	　　　　/*请求失败遇到异常触发*/
    	　　　　error: function (xhr, status, e) {
    				alert("请求失败，请检查网络连接或API地址配置是否正确。");
    	　　　　},
    	　　　　/*完成请求后触发。即在success或error触发后触发*/
    	　　　　complete: function (xhr, status) {},
    	　　　　/*发送请求前触发*/
    	　　　　beforeSend: function (xhr) {
    	　　　　		//可以设置自定义标头
    	　　　　		//xhr.setRequestHeader('Content-Type', 'application/xml;charset=utf-8');
    	　　　　}
    	　　});
          
          HttpUtil.ajax({
				url : "/api/tk/"+$rootScope.token,
				data : null,
				method : "GET",
				loadingButton:"btnSave",
				success : function(data) {
					if (data.errorCode == 0) {
						$rootScope.$apply(function(){
                            if(data.data) {
                                $rootScope.loginUser=data.data;
                            }
                            else{
                                window.location = 'app/login.html';
                            }
						});
					}
				}
			});
          
          $rootScope.hasRole=function(menuRole) {
        	  if ($rootScope.loginUser) {
        		  var isAdmin = utils.contain($rootScope.loginUser.roleName,"ADMIN");
        		  if (!isAdmin) {
        			  return utils.contain($rootScope.loginUser.roleName,menuRole);
        		  } else {
        			  return true;
        		  }
        	  }
        	  return false;
          }
          
      }
    ]
  )
  .config(['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG', 
      function ($stateProvider,   $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {
          var layout = "../app/common/main.html";
          $urlRouterProvider.otherwise('/app/home');
          
          
          $stateProvider.state('app', {
	              abstract: true,
	              url: '/app',
	              templateUrl: layout,
                  resolve: load(['../plugins/dist/js/frame.js'])
	          }).state('app.home', {
                  url: '/home',
                  templateUrl: '../app/home/home.html',
                  resolve: load(['../app/home/home.js'])
	          }).state('app.user', {
                  url: '/user',
                  templateUrl: 'app/system/user/user_list.html',
                  resolve: load(['../app/system/user/user_list.js'])
	          }).state('app.area', {
                  url: '/area',
                  templateUrl: 'app/basic/area/area_list.html',
                  resolve: load(['../app/basic/area/area_list.js'])
	          }).state('app.apptype', {
	              url: '/appType',
	              templateUrl: 'app/basic/appType/appType_list.html',
	              resolve: load(['../app/basic/appType/appType.js'])
	          }).state('app.appid', {
	              url: '/appId',
	              templateUrl: 'app/basic/appId/appId_list.html',
	              resolve: load(['../app/basic/appId/appId.js'])
	          }).state('app.appqueue', {
	              url: '/appQueue',
	              templateUrl: 'app/basic/appQueue/appQueue_list.html',
	              resolve: load(['../app/basic/appQueue/appQueue.js'])
	          }).state('app.systemconfig', {
              url: '/systemConfig',
              templateUrl: 'app/alarm/systemConfig/systemConfig.html',
              resolve: load(['../app/alarm/systemConfig/systemConfig.js'])
          }).state('app.node_satus_alarm', {
          url: '/alarm/nodeStatus',
              templateUrl: 'app/alarm/nodeStatus/nodeStatus.html',
              resolve: load(['../app/alarm/nodeStatus/nodeStatus.js'])
      }).state('app.node_work_status_alarm', {
          url: '/alarm/nodeWorkStatus',
              templateUrl: 'app/alarm/nodeWorkStatus/nodeWorkStatus.html',
              resolve: load(['../app/alarm/nodeWorkStatus/nodeWorkStatus.js'])
      }).state('app.connection_alarm', {
              url: '/alarm/connections',
          templateUrl: 'app/alarm/connections/connections.html',
              resolve: load(['../app/alarm/connections/connections.js'])
      }).state('app.exchange_alarm', {
              url: '/alarm/exchange',
          templateUrl: 'app/alarm/exchange/exchange.html',
              resolve: load(['../app/alarm/exchange/exchange.js'])
      }).state('app.queue_alarm', {
          url: '/alarm/queue',
              templateUrl: 'app/alarm/queue/queue.html',
              resolve: load(['../app/alarm/queue/queue.js'])
      }).state('app.shovel_alarm', {
          url: '/alarm/shovel',
              templateUrl: 'app/alarm/shovel/shovel.html',
              resolve: load(['../app/alarm/shovel/shovel.js'])
      }).state('app.clusterStatus_monitor', {
          url: '/monitor/clusterStatus',
              templateUrl: 'app/monitor/clusterStatus/clusterStatus.html',
              resolve: load(['../app/monitor/clusterStatus/clusterStatus.js'])
      }).state('app.shovel_monitor', {
          url: '/monitor/shovelStatus',
          templateUrl: 'app/monitor/shovel/shovelStatus.html',
          resolve: load(['../app/monitor/shovel/shovelStatus.js'])
      }).state('app.queues_monitor', {
          url: '/monitor/queues',
          templateUrl: 'app/monitor/queue/queue_list.html',
          resolve: load(['../app/monitor/queue/queue_list.js'])
      }).state('app.queues_detail_monitor', {
          url: '/monitor/queue/{area}/{queue}',
          templateUrl: 'app/monitor/queue/queue_detail.html',
          resolve: load(['../app/monitor/queue/queue_detail.js'])
      }).state('app.exchanges_monitor', {
          url: '/monitor/exchanges',
          templateUrl: 'app/monitor/exchange/exchange_list.html',
          resolve: load(['../app/monitor/exchange/exchange_list.js'])
      }).state('app.exchages_detail_monitor', {
          url: '/monitor/exchange/{area}/{exchange}',
          templateUrl: 'app/monitor/exchange/exchange_detail.html',
          resolve: load(['../app/monitor/exchange/exchange_detail.js'])
      }).state('app.connections_monitor', {
          url: '/monitor/connections',
          templateUrl: 'app/monitor/connection/connection_list.html',
          resolve: load(['../app/monitor/connection/connection_list.js'])
      }).state('app.connections_detail_monitor', {
          url: '/monitor/connection/{area}/{connectionName}',
          templateUrl: 'app/monitor/connection/connection_detail.html',
          resolve: load(['../app/monitor/connection/connection_detail.js'])
      }).state('app.channels_monitor', {
          url: '/monitor/channels',
          templateUrl: 'app/monitor/channel/channel_list.html',
          resolve: load(['../app/monitor/channel/channel_list.js'])
      }).state('app.channels_detail_monitor', {
          url: '/monitor/channel/{area}/{channel}',
          templateUrl: 'app/monitor/channel/channel_detail.html',
          resolve: load(['../app/monitor/channel/channel_detail.js'])
      }).state('app.node_monitor', {
              url: '/monitor/node/{areaCode}/{nodeName}',
              templateUrl: 'app/monitor/node/node.html',
              resolve: load(['../app/monitor/node/node.js'])
      }).state('app.shovel_control', {
          url: '/control/shovel',
          templateUrl: 'app/control/shovel/shovel_control.html',
          resolve: load(['../app/control/shovel/shovel_control.js'])
      }).state('app.queue_control', {
          url: '/control/queue',
          templateUrl: 'app/control/queue/queue_control.html',
          resolve: load(['../app/control/queue/queue_control.js'])
      }).state('app.exchange_control', {
          url: '/control/exchange',
          templateUrl: 'app/control/exchange/exchange_control.html',
          resolve: load(['../app/control/exchange/exchange_control.js'])
      }).state('app.appid_search', {
          url: '/search/appId',
          templateUrl: 'app/search/appid/appid_search.html',
          resolve: load(['../app/search/appid/appid_search.js'])
      }).state('app.command_search', {
          url: '/search/command',
          templateUrl: 'app/search/command/command_search.html',
          resolve: load(['../app/search/command/command_search.js'])
      }).state('app.pwd_modify', {
          url: '/user/password/modify',
          templateUrl: 'app/system/user/pwd_modify.html',
          resolve: load(['../app/system/user/pwd_modify.js'])
      });

          function load(srcs, callback) {
            return {
                deps: ['$ocLazyLoad', '$q',
                  function( $ocLazyLoad, $q ){
                    var deferred = $q.defer();
                    var promise  = false;
                    srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                    if(!promise){
                      promise = deferred.promise;
                    }
                    angular.forEach(srcs, function(src) {
                      console.log(src);
                      promise = promise.then( function(){
                        if(JQ_CONFIG[src]){
                          return $ocLazyLoad.load(JQ_CONFIG[src]);
                        }
                        var name = src;
                        if (MODULE_CONFIG && MODULE_CONFIG.length > 0) {
                        	angular.forEach(MODULE_CONFIG, function(module) {
                                if( module.name == src){
                                  name = module.name;
                                }
                              });
                        }
                        return $ocLazyLoad.load(name);
                      } );
                    });
                    deferred.resolve();
                    return callback ? promise.then(function(){ return callback(); }) : promise;
                }]
            }
          }


      }
    ]
  );
