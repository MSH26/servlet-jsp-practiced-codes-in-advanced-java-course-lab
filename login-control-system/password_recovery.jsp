<%@ page import = "bean.UserDataAccess" %>
<%@ page import = "java.util.regex.*" %>
<%@ page contentType="text/html" %>


<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("home.jsp");
		}
	}
	else{
		if(((String)session.getAttribute("FORGOTPASSWORDUSERNAME")) != null && ((String)session.getAttribute("FORGOTPASSWORDUSERNAME")).equals("")){
			response.sendRedirect("login.jsp");
		}
	}
%>

<%!
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

	<jsp:useBean id = "user" class = "bean.UserBean" scope = "session" />

<%
	if(request.getMethod().equals("POST")){
		out.println(session.getAttribute("FORGOTPASSWORDUSERNAME"));
		Boolean validityStatusFlag = true;
		String errorMessages = "<fieldset>";
		
		if(!isPasswordValid(request.getParameter("current_password"))){
			validityStatusFlag = false;
			errorMessages += "Fill the current password field correctly<br/>";
		}
		if(!isPasswordValid(request.getParameter("password"))){
			validityStatusFlag = false;
			errorMessages += "Fill the password field correctly<br/>";
		}
		if(request.getParameter("password") == null || request.getParameter("confirm_password") == null && !request.getParameter("confirm_password").equals(request.getParameter("password"))){
			validityStatusFlag = false;
			errorMessages += "Fill the confirm password field correctly<br/>";
		}
		errorMessages = errorMessages + "These field cross the word length";

		if(request.getParameter("answer_one") != null && request.getParameter("answer_one").equals("")){
			validityStatusFlag = false;
			errorMessages += "answer one, ";
		}

		if(request.getParameter("answer_two") != null && request.getParameter("answer_two").equals("")){
			validityStatusFlag = false;
			errorMessages += "answer two, ";
		}

		if(request.getParameter("answer_three") != null && request.getParameter("answer_three").equals("")){
			validityStatusFlag = false;
			errorMessages += "answer three.";
		}
		if(validityStatusFlag == true){
			UserDataAccess userDataAccess = new UserDataAccess();
			user = userDataAccess.getByUserName((String)session.getAttribute("FORGOTPASSWORDUSERNAME"));
			if(user != null){
				if(user.getUserName().equals((String)session.getAttribute("FORGOTPASSWORDUSERNAME"))){
					if(user.getPassword().equals(request.getParameter("current_password"))){
						if(user.getAnswerOne().equals(request.getParameter("answer_one")) && user.getAnswerTwo().equals(request.getParameter("answer_two")) && user.getAnswerThree().equals(request.getParameter("answer_three"))){
							if(userDataAccess.update(user, (String)session.getAttribute("FORGOTPASSWORDUSERNAME"))){
								session.setAttribute("FORGOTPASSWORDUSERNAME", "");
								response.sendRedirect("login.jsp");
							}
							errorMessages = "</fieldset>Failled to change password! <br/>";
						}
						else{
							errorMessages = "</fieldset>Anyone of the answer is  wrong! <br/>";
						}
					}
					else{
						errorMessages = "</fieldset> Password don't matched<br/>";
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
		out.println(errorMessages + "</fieldset>");
	}		
	else{
		UserDataAccess userDataAccess = new UserDataAccess();
		user = userDataAccess.getByUserName((String)session.getAttribute("FORGOTPASSWORDUSERNAME"));
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<title>Forgot Password</title>
	</head>
	
	<body>
		<fieldset>
			<legend><h3 align="center">Password Recovery</h3></legend>
			
			<form method="post">
				<b>Current Password</b>: <input type='password' name='current_password' value=''><hr/>
				<b>Password</b>: <input type='password' name='password' value=''><abbr title='must contain one lowercase(a-z),one uppercase(A-Z), one special(@,#,$,&) character. Length at least 8'><b>i</b></abbr><hr/>
				<b>Confirm Password</b>: <input type='password' name='confirm_password' value=''><hr/>
				<b>Security Question One </b>:
				<%= user.getQuestionOne() == null ?  "" : user.getQuestionOne() %> : <input type='text' name='answer_one' value=''><hr/>
				<b>Security Question Two</b>: 
				<%= user.getQuestionTwo()  == null ?  "" : user.getQuestionTwo() %> : <input type='text' name='answer_two' value=''><hr/>
				<b>Security Question Three</b>: 
				<%= user.getQuestionThree() == null ?  "" : user.getQuestionThree() %> : <input type='text' name='answer_three' value=''><hr/>
				<input type='submit' value='Submit' />
			</form>
		</fieldset>
		<a align="center" href="basic_registration.jsp" >Go Registration </a> | <a align="center" href="login.jsp" >Login </a>
	</body>
</html>