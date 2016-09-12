'use strict';
// lazyload config

angular.module('app')
    /**
   * jQuery plugin config use ui-jq directive , config the js and css files that required
   * key: function name of the jQuery plugin
   * value: array of the css js file located
   */
  .constant('JQ_CONFIG', {
	  /*
      easyPieChart:   [   '../libs/jquery/jquery.easy-pie-chart/dist/jquery.easypiechart.fill.js'],
      sparkline:      [   '../libs/jquery/jquery.sparkline/dist/jquery.sparkline.retina.js']
      */
    }
  )
  .constant('MODULE_CONFIG', [
      /*
      {
          name: 'ngGrid',
          files: [
              '../libs/angular/ng-grid/build/ng-grid.min.js',
              '../libs/angular/ng-grid/ng-grid.min.css',
              '../libs/angular/ng-grid/ng-grid.bootstrap.css'
          ]
      }
      */
    ]
  )
  // oclazyload config
  .config(['$ocLazyLoadProvider', 'MODULE_CONFIG', function($ocLazyLoadProvider, MODULE_CONFIG) {
      // We configure ocLazyLoad to use the lib script.js as the async loader
      $ocLazyLoadProvider.config({
          debug:  true,
          events: true,
          modules: MODULE_CONFIG
      });
  }])
;
