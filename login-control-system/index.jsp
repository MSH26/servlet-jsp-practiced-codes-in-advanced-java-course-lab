
<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("home.jsp");
		}
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Servlet and JSP</title>
	</head>
	
	<body>
		<div color="yellow">
			<h1 align='center'>Wellcome to simple login control system</h1>
		</div>
		<div align="center">
			<a align="center" href="login.jsp" > Login </a> | 
			<a align="center" href="basic_registration.jsp" > Registration </a>
		</div>

	</body>
</html>