<!Doctype html>
<html ng-app="store">

<head>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min.js"></script>
<!-- <script src="js/service.js"></script>
<script src="js/directive.js"></script> -->
<script src="js/app2.js"></script>


</head>
<body class="container" style="height: 98px;">
	
	<form action="uploadFile2">
	<button type="submit" value="Redirect"></button>
	</form>
	<div class="row">
		<div class="col-md-12 col-offset-md-12"
			ng-controller="VPayableController as vpayableCtrl">

			<table border="1">
				<tr>
					<th allowed="1006">Amount</th>
					<th allowed="1003">Amount</th>
					<th>From</th>
					<th>To</th>
					<th>Actions</th>
				</tr>
				<tr ng-repeat="vpayable in vpayableCtrl.vpayables">
					<td allowed="1006">{{ vpayable.amount}}</td>
					<td allowed="1003"><input type="text"
						value='{{ vpayable.amount}}' /></td>
					<td>{{vpayable.from }}</td>
					<td>{{vpayable.to }}</td>
					<td><button class="btn btn-primary" allowed="1001">VIEW
							VPAYABLE</button>
						<button class="btn btn-primary" allowed="1002">ADD
							VPAYABLE</button>
						<button class="btn btn-primary" allowed="1004">ADD GROUP</button>
						<button class="btn btn-primary" allowed="1005">MANAGE
							GROUPS</button></td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>