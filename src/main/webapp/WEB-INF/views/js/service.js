var app = angular.module('App.services', []);
app.service('MyService', function($http, $rootScope) {
	this.data = [];
	var _this = this;

	$http.get('permissions').then(function data(response) {
		_this.data = response.data;
		console.log(response);
		$rootScope.$broadcast('MyServiceReady');
	})

	/*
	 * return { permissionData: function(){ return _this.data; } }
	 */
})