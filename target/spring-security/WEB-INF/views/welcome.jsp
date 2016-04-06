<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome page</title>
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

		<nav class="navbar navbar-default navbar-fixed-top">
			<div class="row">
				<div class="col-xs-9"></div>
				<div class="col-xs-3" align="center">
					<h3 class="text-success">
						Hello,
						<sec:authentication property="principal.username" />
					</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-1"></div>
				<div class="col-xs-1"></div>

				<div class="col-xs-1"></div>
				<div class="col-xs-1"></div>
				<div class="col-xs-1"></div>
				<div class="col-xs-4">
					<h1>Home Page</h1>
				</div>


				<div class="col-xs-1"></div>


				<div class="col-xs-1">
					<h2>
						<a href="<c:url value="/logout" />">Logout</a>
					</h2>
				</div>
			</div>
			<div class="row">
				<div align="center" class="col-xs-12">VPayables</div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>#</th>
						<th><c:if test="${  permissions.contains( '1006' ) }">
								Amount
							</c:if></th>
						<th>from</th>
						<th>to</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th scope="row">1</th>

						<td><c:if test="${  permissions.contains( '1006' ) }">
								<c:choose>
									<c:when test="${  permissions.contains( '1003' ) }">
										<input type="text" value="100" />
									</c:when>
									<c:otherwise>100</c:otherwise>
								</c:choose>
							</c:if></td>
						<td>03/14/2016</td>
						<td>03/17/2016</td>
						<td>
							<%-- <sec:authentication property="principal.authorities"/> --%>
							<c:if test="${  permissions.contains( '1001' ) }">
								<input type="button" value="VIEW VPAYABLE" />
							</c:if> <c:if test="${  permissions.contains( '1002' ) }">
								<input type="button" value="ADD VPAYABLE" />
							</c:if> <c:if test="${  permissions.contains( '1004' ) }">
								<input type="button" value="ADD GROUP" />
							</c:if> <c:if test="${  permissions.contains( '1005' ) }">
								<input type="button" value="MANAGE GROUPS" />
							</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row">2</th>
						<td><c:if test="${  permissions.contains( '1006' ) }">
								<c:choose>
									<c:when test="${  permissions.contains( '1003' ) }">
										<input type="text" value="200" />
									</c:when>
									<c:otherwise>200</c:otherwise>
								</c:choose>
							</c:if></td>
						<td>03/15/2016</td>
						<td>03/18/2016</td>
						<td><c:if test="${  permissions.contains( '1001' ) }">
								<input type="button" value="VIEW VPAYABLE" />
							</c:if> <c:if test="${  permissions.contains( '1002' ) }">
								<input type="button" value="ADD VPAYABLE" />
							</c:if> <c:if test="${  permissions.contains( '1004' ) }">
								<input type="button" value="ADD GROUP" />
							</c:if> <c:if test="${  permissions.contains( '1005' ) }">
								<input type="button" value="MANAGE GROUPS" />
							</c:if></td>
					</tr>
					<tr>
						<th scope="row">3</th>
						<td><c:if test="${  permissions.contains( '1006' ) }">
								<c:choose>
									<c:when test="${  permissions.contains( '1003' ) }">
										<input type="text" value="300" />
									</c:when>
									<c:otherwise>300</c:otherwise>
								</c:choose>
							</c:if></td>
						<td>03/13/2016</td>
						<td>03/19/2016</td>
						<td><c:if test="${  permissions.contains( '1001' ) }">
								<input type="button" value="VIEW VPAYABLE" />
							</c:if> <c:if test="${  permissions.contains( '1002' ) }">
								<input type="button" value="ADD VPAYABLE" />
							</c:if> <c:if test="${  permissions.contains( '1004' ) }">
								<input type="button" value="ADD GROUP" />
							</c:if> <c:if test="${  permissions.contains( '1005' ) }">
								<input type="button" value="MANAGE GROUPS" />
							</c:if></td>
					</tr>

				</tbody>
			</table>

		</nav>


		<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
		<div class="row">
			<h3>Welcome to Home Page.</h3>
		</div>

	</div>
</body>
</html>