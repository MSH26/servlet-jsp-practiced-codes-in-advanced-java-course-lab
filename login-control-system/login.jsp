<%@ page import = "bean.UserDataAccess" %>

<%@ page import = "java.io.*" %>
<%@ page import = "java.util.regex.*" %>

<%@ page contentType="text/html" %>


<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("home.jsp");
		}
	}
%>

<%!
	private int loginFailledFlag = 0;
	private Boolean isEmailValid(String email)
	{
		try{
			Pattern p = Pattern.compile("(.+)@(.+)(.+)\\.(.+)(.+)");
			Matcher m = p.matcher(email);
			if(m.matches()){
					return true;
			}
			return false;
		}
		catch(PatternSyntaxException e){
			return false;
		}
		catch(Exception e){
			return false;
		}
	}
	private Boolean isPasswordValid(String password)
	{
		try{
			if(Pattern.matches("((.+)?)[A-Z]((.+)?)", password) && Pattern.matches("((.+)?)[a-z]((.+)?)", password) && Pattern.matches("((.+)?)[@|#|$|&]((.+)?)", password) && Pattern.matches("((.+)?)[0-9]((.+)?)", password)){
				if(password.length() >=8){
					return true;
				}
			}
			return false;
		}
		catch(PatternSyntaxException e){
			return false;
		}
		catch(Exception e){
			return false;
		}
	}
%>
<%
	if(request.getMethod().equals("POST")){
		loginFailledFlag++;
		Boolean validityStatusFlag = true;
		String errorMessages = "<fieldset>";
		if(!isEmailValid(request.getParameter("user_name"))){
			validityStatusFlag = false;
			errorMessages += "Fill the username field correctly<br/>";
		}
		if(!isPasswordValid(request.getParameter("password"))){
			validityStatusFlag = false;
			errorMessages += "Fill the password field correctly<br/>";
		}
		
		
		if(validityStatusFlag == true){
			if(request.getParameter("user_name") != null && request.getParameter("password") != null){
				UserDataAccess userDataAccess = new UserDataAccess();
%>
				<jsp:useBean id = "user" class = "bean.UserBean" scope = "session" />
<%
				user = userDataAccess.getByUserName(request.getParameter("user_name"));
				if(user != null){
					if(user.getUserName().equals(request.getParameter("user_name"))){
						if(user.getPassword().equals(request.getParameter("password"))){
							session.setAttribute("JSESSIONLOGSTATUS", "LOOGGED_IN");
							session.setAttribute("JSESSIONUSERNAME", user.getUserName());
							response.sendRedirect("home.jsp");
						}
						else{
							errorMessages = "</fieldset> Password missmatched! <br/>";
						}
					}
					else{
						errorMessages = "</fieldset> User name not exist<br/>";
					}
					
				}
				else{
					errorMessages = "</fieldset> User name not exist<br/>";
				}
			}
			
		}
		if(loginFailledFlag >= 4){
			loginFailledFlag = 0;
			errorMessages = errorMessages + "<br/> You may not registered.Please <a href='basic_registration.jsp' >Go Registration </a>";
		}
		out.println(errorMessages + "</fieldset>");
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
	</head>
	
	<body>
		<div align="center">
			<fieldset>
				<legend><h3 align="center">LOGIN PORTAL</h3></legend>
					<form method="post">
						<b>User Name / Email</b>: 
						<input type='text' name='user_name' placeholder='User Name' value=''><abbr title='Email address'><b>i</b></abbr> | 
						<b>Password</b>: 
						<input type='password' name='password' placeholder='Password' value=''><br/><hr/>
						<input type='submit' value='LOGIN' /><br/><hr/>
					</form>
				<a align="center" href="basic_registration.jsp" >Go Registration </a> | <a align="center" href="forgot_password.jsp" >Forgot Password </a>
			</fieldset>
		</div>
	</body>
</html>