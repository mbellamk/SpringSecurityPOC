(function() {

	var app = angular.module('store', []);

	app.service('MyService', function($http, $rootScope) {
		this.data = [];
		var _this = this;

		$http.get('permissions').then(function data(response) {
			_this.data = response.data;
			console.log(response);
			$rootScope.$broadcast('MyServiceReady');
		})

	})
	app.directive('allowed', function(MyService) {
		return {
			restrict : 'A',
			link : function(scope, element, attrs) {
				scope.$on('MyServiceReady', function() {
					var allowed = false;
					angular.forEach(MyService.data, function(item) {
						if (attrs.allowed === item) {
							allowed = true;
						}
					});
					if (!allowed) {
						element.addClass('hidden');
					}
				});
			}
		}
	});

	app.controller('VPayableController', function() {
		this.vpayables = vpayablesList;
	});

	var vpayablesList = [ {
		amount : 100,
		from : '03/17/2016',
		to : '03/20/2016',
	}, {
		amount : 200,
		from : '03/18/2016',
		to : '03/21/2016',
	}, {
		amount : 300,
		from : '03/19/2016',
		to : '03/22/2016',
	}, ];

})();