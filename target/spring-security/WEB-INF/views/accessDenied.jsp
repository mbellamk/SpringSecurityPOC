<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AccessDenied page</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<nav class="navbar navbar-default navbar-fixed-top">
				<div class="col-xs-1"></div>
				<div class="col-xs-1"></div>

				<div class="col-xs-1"></div>
				<div class="col-xs-1"></div>
				<div class="col-xs-1"></div>
				<div class="col-xs-4"></div>


				<div class="col-xs-1">
					<h2>
						<a href="<c:url value="/home" />">Home</a>
					</h2>
				</div>


				<div class="col-xs-1">
					<h2>
						<a href="<c:url value="/logout" />">Logout</a>
					</h2>
				</div>

			</nav>

		</div>
		<br /> <br /> <br /> <br /> <br />
		<div class="alert alert-danger row">
			<h2>
				Dear <strong>${user}</strong>, You are not authorized to access this
				page.
			</h2>
		</div>

	</div>


</body>
</html>