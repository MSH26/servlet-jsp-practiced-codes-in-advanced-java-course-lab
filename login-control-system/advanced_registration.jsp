<%@ page import = "bean.UserDataAccess" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.regex.*" %>
<%@ page contentType="text/html" %>




<%!
	private Boolean isValidLength(String str, int length){
		try{
			if(str != null && !str.equals("")){
				char[] characters = str.toCharArray();
				if(characters.length > length){
					return false;
				}
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
%>

<%
	if(session.getAttribute("registrationStatus") != null && session.getAttribute("registrationStatus").equals("2") ){
		/** '1' means basic registration incomplete
		*	'2' means advanced registration incomplete
		*	'3' means advanced registration complete
		*/
		if(session.getAttribute("registrationStatus").equals("3")){ 
			session.setAttribute("registrationStatus", "0");
			response.sendRedirect("login.jsp");
		}
		out.println(session.getAttribute("registrationStatus"));
		if(request.getMethod().equals("POST")){
			if(session.getAttribute("registrationStatus") == "2"){
				Boolean validityStatusFlag = true;
				String errorMessages = "<fieldset> These field cross the word length";
				if(!isValidLength(request.getParameter("question_one"), 50)){
					validityStatusFlag = false;
					errorMessages += "Question one, ";
				}
				if(!isValidLength(request.getParameter("answer_one"), 10)){
					validityStatusFlag = false;
					errorMessages += "answer one, ";
				}
				if(!isValidLength(request.getParameter("question_two"), 50)){
					validityStatusFlag = false;
					errorMessages += "Question two, ";
				}
				if(!isValidLength(request.getParameter("answer_two"), 10)){
					validityStatusFlag = false;
					errorMessages += "answer two, ";
				}
				if(!isValidLength(request.getParameter("question_three"), 50)){
					validityStatusFlag = false;
					errorMessages += "question three, ";
				}
				if(!isValidLength(request.getParameter("answer_three"), 10)){
					validityStatusFlag = false;
					errorMessages += "answer three.";
				}
				
				if(validityStatusFlag == true){
%>
					<jsp:useBean id = "user" class = "bean.UserBean" scope = "session" />
					<jsp:setProperty name = 'user' property = 'questionOne' value = '<%= request.getParameter("question_one") %>'/>
					<jsp:setProperty name = 'user' property = 'questionTwo' value = '<%= request.getParameter("question_two") %>'/>
					<jsp:setProperty name = 'user' property = 'questionThree' value = '<%= request.getParameter("question_three") %>'/>
					<jsp:setProperty name = 'user' property = 'answerOne' value = '<%= request.getParameter("answer_one") %>'/>
					<jsp:setProperty name = 'user' property = 'answerTwo' value = '<%= request.getParameter("answer_two") %>'/>
					<jsp:setProperty name = 'user' property = 'answerThree' value = '<%= request.getParameter("answer_three") %>'/>
<%
					UserDataAccess userDataAccess = new UserDataAccess();
					if(userDataAccess.insert(user)){
						session.setAttribute("registrationStatus", "0");
						response.sendRedirect("login.jsp");
					}
					else{
						errorMessages = "</fieldset> Database exception occurs! Please try again.";
					}
				}
				out.println(errorMessages + "</fieldset>");
			}
		}
	}
	else{
		response.sendRedirect("basic_registration.jsp");
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<title>REGISTRATION</title>
	</head>
	
	<body>
		<fieldset>
			<legend><h3 align="center">ADVANCED REGISTRATION </h3></legend>
			<form method="post">
				<h4 align="center">Write three question(not more than 50 character per question) and it's answer(not more than 10 character per answer). This question will be asked for security reason latter</h4>
				<b>Question One</b>: 
				<input type='text' name='question_one' value=''>
				<input type='text' name='answer_one' value=''><hr/>
				<b>Question Two</b>: 
				<input type='text' name='question_two' value=''>
				<input type='text' name='answer_two' value=''><hr/>
				<b>Question Three</b>: 
				<input type='text' name='question_three' value=''>
				<input type='text' name='answer_three' value=''><hr/>
				<input type='submit' value='Submit' />
				<input type='reset' />
			</form>
		</fieldset>
	</body>
</html>