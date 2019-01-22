<%@ page import = "bean.UserDataAccess" %>
<%@ page import = "bean.UserBean" %>
<%@ page contentType="text/html" %>



<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("home.jsp");
		}
	}
	
	if(request.getMethod().equals("POST")){
		if(request.getParameter("username") != null){
			UserDataAccess userDataAccess = new UserDataAccess();
			String userName = request.getParameter("username");
			UserBean user = userDataAccess.getByUserName(userName.trim());
			if(user != null){
				session.setAttribute("FORGOTPASSWORDUSERNAME", userName.trim());
				response.sendRedirect("password_recovery.jsp");
			}
			else{
				out.println("Wrong user name");
			}
		}
		else{
			out.println("insert user name");
		}
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Forgot Password</title>
	</head>
	
	<body>
		<fieldset>
			<legend><h3 align="center">Insert Your User Name</h3></legend>
			
			<form method="post">
				<b>User Name</b>: <input type='text' name='username'><hr/>
				<input type='submit' value='Submit' />
			</form>
		</fieldset>
		<a align="center" href="basic_registration.jsp" >Go Registration </a> | <a align="center" href="login.jsp" >Login </a>
	</body>
</html>