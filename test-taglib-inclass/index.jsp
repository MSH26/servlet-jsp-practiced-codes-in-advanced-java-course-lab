<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<!DOCTYPE html>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Index</title>
	</head>
	
	<body>
		<h1>INDEX JSP : Hello JSTL</h1>
		<c:set var="Income" scope="session" value="${4000*4}"/>  
		<c:out value="${Income}"/>
		<a href="http://localhost:8080/test/index2.jsp">Be Lost If You Wish!</a>
	</body>
</html>