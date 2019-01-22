
<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(!session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("index.jsp");
		}
	}
	else{
		response.sendRedirect("index.jsp");
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Home</title>
	</head>
	
	<body>
		<h1 align='center'>Wellcome to Home</h1>
		<a align="center">User Name : <%= session.getAttribute("JSESSIONUSERNAME") %></a><br/>
		<a align="center" href="logout.jsp" > Logout </a> 
	</body>
</html>