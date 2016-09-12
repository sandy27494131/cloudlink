angular.module('configFilters', ['ngSanitize'])
	.filter('shovelState', ['$sce', function($sce) {
		return function(input,reason) {
			var html = "";
			if (input=='running') {
				html = '<span class="label label-success">'+input+'</span>';
			} else {
				html = '<span class="label label-danger">'+input+'</span>';
			}
			if (reason) {
				html = html + '<br><small>' +reason+ '</small>';
			}
		    return $sce.trustAsHtml(html);
	  	};
	}])
	.filter('shovelSrc', ['$sce', function($sce) {
		return function(input) {
			if (input) {
				var name,type;
				if (input['src-queue']) {
					name = input['src-queue'];
					type = "queue";
				}
				if (input['src-exchange']) {
					name = input['src-exchange'];
					type = "exchange";
				}
				return $sce.trustAsHtml('<small>'+name+'</small><br><small>'+type+'</small>');
			}
			return "";
	  	};
	}])
	.filter('shovelDest', ['$sce', function($sce) {
		return function(input) {
			if (input) {
				var name,type;
				if (input['dest-queue']) {
					name = input['dest-queue'];
					type = "queue";
				}
				if (input['dest-exchange']) {
					name = input['dest-exchange'];
					type = "exchange";
				}
				return $sce.trustAsHtml('<small>'+name+'</small><br><small>'+type+'</small>');
			}
			return "";
	  	};

	}])
	.filter('rate', ['$sce', function($sce) {
		return function(input) {
			if (input || input == 0) {
				return input.toFixed(2)+"/s";
			}
			return "--.--";
	  	};

	}])
	.filter('queueFeatures', ['$sce', function($sce) {
		return function(durable,auto_delete) {
			var html = "";
			if (durable) {
				html = '<span class="label label-success" title="durable:true">D</span>';
			} 
			if (auto_delete) {
				html = html + '&nbsp;<span class="label label-danger" title="auto_delete:true">AD</span>';
			}
		    return $sce.trustAsHtml(html);
	  	};

	}])
	.filter('exchangeFeatures', ['$sce', function($sce) {
		return function(durable,auto_delete,internal) {
			var html = "";
			if (durable) {
				html = '<span class="label label-success" title="durable:true">D</span>';
			} 
			if (auto_delete) {
				html = html + '&nbsp;<span class="label label-danger" title="auto_delete:true">AD</span>';
			} 
			if (internal) {
				html = html + '&nbsp;<span class="label label-danger" title="internal:true">I</span>';
			}
		    return $sce.trustAsHtml(html);
	  	};

	}])
	.filter('channelPrefetch', ['$sce', function($sce) {
		return function(prefetch_count,global_prefetch_count) {
			if (prefetch_count != 0 ) {
				return prefetch_count;
			}
			if (global_prefetch_count != 0 ) {
				return global_prefetch_count;
			}
	  	};

	}])
	.filter('exchangeMode', ['$sce', function($sce) {
		return function(confirm,transactional) {
			var html = "";
			if (confirm) {
				html = '<span class="label label-success" title="confirm:true">C</span>';
			} 
			if (transactional) {
				html = html + '&nbsp;<span class="label label-danger" title="transactional:true">T</span>';
			}
		    return $sce.trustAsHtml(html); 
	  	};

	}])
	.filter('channelState', ['$sce', function($sce) {
		return function(state,idle) {
			var html = "";
			if (state=='running') {
				if (idle) {
					html = '<span class="label label-default" title="idle since:'+idle+'">idle</span>';
				} else {
					html = '<span class="label label-success">'+state+'</span>';
				}
			} else {
				html = '<span class="label label-danger">'+state+'</span>';
			}
		    return $sce.trustAsHtml(html);
	  	};

	}])
	.filter('channelName', ['$sce', function($sce) {
		return function(name) {
			var arr = name.split(" ");
		    return arr[0]+arr[3];
	  	};
	}])
	.filter('connectionName', ['$sce', function($sce) {
		return function(name) {
			if (name) {
				var arr = name.split(" ");
			    return arr[0];
			} else {
				return "";
			}
	  	};
	}])
	.filter('exchangeName', ['$sce', function($sce) {
		return function(name) {
			if (name) {
				return name;
			} else {
				return "(AMQP default)";
			}
	  	};
	}])
	.filter('toMB', [ function() {
		return function(byte) {
			var mb=byte/1024/1024;
			return parseInt(mb);
		};
	}])
	.filter('toGB', [ function() {
		return function(byte) {
			var gb=byte/1024/1024/1024;
			return Math.round(gb*10)/10;;
		};
	}])
	.filter('capacityFormat', [ function() {
		return function(value) {
			var arr=['bit','KB','MB','GB','TB','?B','?B','?B'];
			index=0;
			value=parseFloat(value);
			while (value>=1024){
				 value=value/1024;
				index++;
			}
			return Math.round(value*10)/10 +arr[index];
		};
	}])
	.filter('countPlugins', [ function() {
		return function(node) {
			if (node.applications === undefined) return '';
			var plugins = [];
			for (var i = 0; i < node.applications.length; i++) {
				var application = node.applications[i];
				if ($.inArray(application.name, node.enabled_plugins) != -1 ) {
					plugins.push(application.name);
				}
			}
			return  plugins.length  ;
		};
	}])
	.filter('fmtBoolean', ['$sce', function() {
		return function(b, unknown) {
			if(b){}else{b=false;}
			if (unknown == undefined) unknown = '?';
			if (b == undefined) return unknown;
			return b ? "&#9679;" : "&#9675;";
		};
	}])
	.filter('runningState', ['$sce', function() {
		return function(isRunning) {
			if(isRunning){
				return '<label class="label label-success">Yes</label>';
			}
			else{
				return '<label class="label label-danger">Yes</label>';
			}
		};
	}])
	.filter('fmtUptime', ['$sce', function() {
		return function(u) {
			var uptime = Math.floor(u / 1000);
			var sec = uptime % 60;
			var min = Math.floor(uptime / 60) % 60;
			var hour = Math.floor(uptime / 3600) % 24;
			var day = Math.floor(uptime / 86400);

			if (day > 0)
				return day + 'd ' + hour + 'h';
			else if (hour > 0)
				return hour + 'h ' + min + 'm';
			else
				return min + 'm ' + sec + 's';
		};
	}])
	.filter('yesOrNoCheck', ['$sce', function($sce) {
		return function(state,isOk) {
			var val = "No";
			if (state) {
				val = "Yes";
			}
			var html = "";
			if (isOk) {
				html = '<span class="label label-success">'+val+'</span>';
			} else {
				html = '<span class="label label-danger">'+val+'</span>';
			}
		    return $sce.trustAsHtml(html);
	  	};

	}])
