angular.module('ui.popover', []).directive('popover', function () {
    return {
        restrict: 'A',
        scope: {
            popoverShow: '=',
            popoverOptions: '@'
        },
        link: function (scope, element) {
        	var options = {
        					"template":'<div class="popover" style="max-width:500px;" role="tooltip"><div class="arrow"></div><div class="popover-content" style="padding:2px 14px"></div></div>',
        					"placement": "right",
        					"trigger": "manual", 
        					"html":true,
        					"content": '<font color="red">'+scope.popoverOptions + '</font>'
        					};
            element.popover(options);
            
            element.on('blur',function(){
            	if (scope.popoverShow) {
                    element.popover('show');
            	}
            });

            element.on('click',function(){
                element.popover('hide');
            });
            scope.$watch('popoverShow', function (show) {
                if (show) {
                    element.popover('show');
                } else {
                    element.popover('hide');
                }
            });
        }
    };
});