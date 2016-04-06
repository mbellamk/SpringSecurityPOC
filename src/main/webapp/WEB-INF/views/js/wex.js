(function() {
	var app = angular.module('home', ['ngGrid']);

	app.controller('VPayableController', function($scope) {
		 $scope.myData = [{name: "Moroni", age: 50},
		                     {name: "Tiancum", age: 43},
		                     {name: "Jacob", age: 27},
		                     {name: "Nephi", age: 29},
		                     {name: "Enos", age: 34}];
		    $scope.gridOptions = { data: 'myData' };
	});

	var vpayableList = [ {
		amount : 100,
		from : '03/15/2016',
		to : '03/20/2016',
		hide: false
	}, {
		amount : 100,
		from : '03/15/2016',
		to : '03/20/2016',
		hide: false
	}, {
		amount : 100,
		from : '03/15/2016',
		to : '03/20/2016',
		hide: false
	} ];

})();