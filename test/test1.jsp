
<%@ page contentType="text/html" %>


<%	
	out.println("SAKIB");
	RequestDispatcher dispatcher = request.getRequestDispatcher("test2.jsp");
	dispatcher.include(request, response);
	out.println("KARIMA");
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