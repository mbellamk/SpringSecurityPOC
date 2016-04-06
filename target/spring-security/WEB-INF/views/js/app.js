(function() {
	var app = angular.module('store', []);

	

	app.factory('Permission', function($http) {

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

	app.factory('Auth', [ 'Permission', function(Permission) {

		$parentScope = this;

		$parentScope.permissions = ['1001','1002','1004'];
		//$parentScope.permissions.then();
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
	app.directive('allowed', function(Auth) {

		console.log(Auth.allowed);
		return {

			link : function($scope, element, attr) {

				console.log(element);
				if (!Auth.allowed(attr.allowed)) {
					$(element).hide();
				} else {
					$(element).show();
				}
			}
		};
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