(function() {
	var app = angular.module('store', []);

	//var app = angular.module('store',  ['App.services', 'App.directives']);
	app.service('MyService', function($http, $rootScope) {
		this.data = [];
		var _this = this;

		$http.get('permissions').then(function data(response) {
			_this.data=response.data;
			console.log(response);
			$rootScope.$broadcast('MyServiceReady');
		})
		
		return  {
			permissionData: function(){
				return _this.data;
			}
		}
	})

	
	app.factory('SonService', function ($http, $q) {
        return {
            getWeather: function() {
                // the $http API is based on the deferred/promise APIs exposed by the $q service
                // so it returns a promise for us by default
                return $http.get('permissions')
                    .then(function(response) {
                        if (typeof response.data === 'object') {
                            return response.data;
                        } else {
                            // invalid response
                            return $q.reject(response.data);
                        }

                    }, function(response) {
                        // something went wrong
                        return $q.reject(response.data);
                    });
            }
        };
    });
    
	app.directive('allowed', [ 'MyService', function(MyService) {
		return function(scope, element, attr, MyService) {
			scope.$on('MyServiceReady', function(MyService) {

				$scope = this;
				
				$scope.status = false;
				$scope.permissions=MyService.data;
				console.log(MyService._this);
				console.log("First:" + $scope.status+" permission:"+attr.allowed);
				angular.forEach(permissions, function(item) {

					if (angular.equals(item, attr.allowed)) {

						$scope.status = true;

					}

				});

				
				console.log("last:" + $scope.status);
				console.log(element);
				if (!$scope.status) {
					$(element).hide();
				} else {
					$(element).show();
				}

			});
		};
	} ]);

	var a = app.factory('Permission', function($http) {

		return {
			loadfile : function() {
				return $http({
					method : 'GET',
					url : 'permissions'

				}).success(function(response) {
					this.permissions = response;
					console.log(this.permissions);
					return response;
				});

			}
		};

	});

	app.factory('Auth', [ 'MyService', function(MyService) {

		$parentScope = this;

		$parentScope.permissions = MyService.data;

		console.log("Permissions:" + $parentScope.permissions)

		return {
			allowed : function(permission) {
				$scope = this;

				$scope.status = false;
				console.log("First:" + $scope.status);
				angular.forEach($parentScope.permissions, function(item) {

					if (angular.equals(item, permission)) {

						$scope.status = true;

					}

				});

				console.log("last:" + $scope.status);
				return $scope.status;

			}
		};

	} ]);
	/*
	 * app.directive('allowed', function(Auth) {
	 * 
	 * console.log(Auth.allowed); return {
	 * 
	 * link : function($scope, element, attr) {
	 * 
	 * console.log(element); if (!Auth.allowed(attr.allowed)) {
	 * $(element).hide(); } else { $(element).show(); } } }; });
	 */
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