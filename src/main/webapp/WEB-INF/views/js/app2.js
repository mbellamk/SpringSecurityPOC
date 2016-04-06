(function() {

	var app = angular.module('store', []);

	// var app = angular.module('store', ['App.services', 'App.directives']);
	app.service('MyService', function($http, $rootScope) {
		this.data = [];
		var _this = this;

		$http.get('permissions').then(function data(response) {
			_this.data = response.data;

			console.log(response.data);
			$rootScope.$broadcast('MyServiceReady');
		})

		return {
			permissionData : function() {
				var a = _this.data;
				return a;

			}
		}

		$rootScope.permissionList = function() {
			return _this.data;
		}
	})

	app.directive('allowed', [
			'MyService',
			function(MyService) {
				return function(scope, element, attr) {
					scope.$on('MyServiceReady', function() {

						$Scope = this;

						$Scope.status = false;
						$Scope.permissions = MyService.permissionData();
						console.log(MyService.permissionData());
						console.log("First:" + $Scope.status + " permission:"
								+ attr.allowed);
						angular.forEach(permissions, function(item) {

							if (angular.equals(item, attr.allowed)) {

								$Scope.status = true;

							}

						});

					
						console.log("last:" + $Scope.status);
						var st=$Scope.status;
						if (angular.equals(st,"true")) {
							console.log("Showing the element"+$Scope.status);
							$(element).show();
						} else {
							console.log("hiding the element" +$Scope.status);
							$(element).hide();

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