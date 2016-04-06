var app = angular.module('App.directives', ['MyService']);
app.directive('allowed', [
		'MyService',
		function(MyService) {
			return function(scope, element, attr, MyService) {
				scope.$on('MyServiceReady', function(MyService) {

					$scope = this;

					$scope.status = false;
					$scope.permissions = MyService.data;
					console.log(MyService._this);
					console.log("First:" + $scope.status + " permission:"
							+ attr.allowed);
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
