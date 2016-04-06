<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>

	<%-- <form action="uploadTest" method="post">
		<input type="text" name="parameter" value="${_csrf.parameterName}" />
		<input type="text" name="token" value="${_csrf.token}" /> <input
			type="submit" value="Test" />
	</form> --%>

	<form action="uploadTest" method="post">
		<input type="text" name="parameter" value="test1" /> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="text" name="token" value="test1Value" /> <input
			type="submit" value="Test" />

	</form>

	<form  action="uploadFile" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> File to upload: <input type="file"
			name="file"><br /> Name: <input type="text" name="name"><br />
		<br /> <input type="submit" value="Upload"> Press here to
		upload the file!
	</form>

</body>
</html>