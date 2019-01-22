<%@ page import = "java.io.*" %>
<%@ page import = "java.util.regex.*" %>
<%@ page import = "java.time.Year" %>
<%@ page contentType="text/html" %>


<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("home.jsp");
		}
	}
%>

<%! 
	private Boolean isNameValid(String name)
	{
		try{
			if(Pattern.matches("([a-z]|[A-Z])([a-z]|[A-Z])([a-z]|[A-Z])((([a-z]|[A-Z])+)?)", name)){
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
	
	private Boolean isDateValid(String formDate)
	{
		try{
			/** regular expression for date validation except** leap year
			((0?(1|3|5|7|8))|(1(0|2)))/(((0?(\\d[^0]))|([1-2]\\d))|(3[0-1]))/\\d\\d\\d\\d  // for 31 days			
			((0?(2|4|6|9))|(11))/(((0?(\\d[^0]))|([1-2]\\d))|(30))/\\d\\d\\d\\d  // for 28/29/30 days
			((((0?(1|3|5|7|8))|(1(0|2)))/(((0?(\\d[^0]))|([1-2]\\d))|(3[0-1])))|(((0?(2|4|6|9))|(11))/(((0?(\\d[^0]))|([1-2]\\d))|(30))))/\\d\\d\\d\\d  // for any days
			*/
			Pattern p = Pattern.compile("((((0?(1|3|5|7|8))|(1(0|2)))/(((0?(\\d[^0]))|([1-2]\\d))|(3[0-1])))|(((0?(2|4|6|9))|(11))/(((0?(\\d[^0]))|([1-2]\\d))|(30))))/\\d\\d\\d\\d"); // date format "mm/dd/yyyy"
			Matcher m = p.matcher(formDate);
			if(m.matches()){
				String[] formDateParts = formDate.split("/");
				
				if ((Year.of(Integer.parseInt(formDateParts[2]) + 18)).isAfter(Year.now()) != true) //Integer.parseInt(dateParts[2]))
                {
					if( Integer.parseInt(formDateParts[0]) == 2)
                    {
						if((Year.of(Integer.parseInt(formDateParts[2]))).isLeap()){
							if(Integer.parseInt(formDateParts[1]) <= 29){
								return true;
							}
							else{
								return false;
							}
						}
						else{
							if(Integer.parseInt(formDateParts[1]) <= 28){
								return true;
							}
							else{
								return false;
							}
						}
					}
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
		/** Another way
		int atpos = email.indexOf("@");
		int dotpos = email.lastIndexOf(".");
		if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length()) {
			return false;
		}
		return true; */
	}
	
	private Boolean isPhoneValid(String phone)
	{
		try{
			Pattern p = Pattern.compile("[\\d]+");
			Matcher m = p.matcher(phone);
			if(m.matches()){
				if(phone.length() == 11){
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
	if(session.getAttribute("registrationStatus") != null && session.getAttribute("registrationStatus").equals("1")){
		/** '1' means basic registration incomplete
		*	'2' means advanced registration incomplete
		*	'3' means advanced registration complete
		*/
		if(session.getAttribute("registrationStatus").equals("2")){ 
			response.sendRedirect("advanced_registration.jsp");
		}
		
		if(request.getMethod().equals("POST") ){
			if(session.getAttribute("registrationStatus").equals("1")){
				Boolean validityStatusFlag = true;
				String errorMessages = "<fieldset>";
				if(!isNameValid(request.getParameter("first_name"))){
					validityStatusFlag = false;
					errorMessages += "Invalid first name or may be length smaller than 3!<br/>";
				}
				if(!isNameValid(request.getParameter("last_name"))){
					validityStatusFlag = false;
					errorMessages += "Invalid last name or may be length smaller than 3!<br/>";
				}
				if(!isDateValid(request.getParameter("date_of_birth"))){
					validityStatusFlag = false;
					errorMessages += "Invalid date of birth<br/>";
				}
				if(!isPhoneValid(request.getParameter("phone"))){
					validityStatusFlag = false;
					errorMessages += "Invalid phone number<br/>";
				}
				if(!isEmailValid(request.getParameter("email"))){
					validityStatusFlag = false;
					errorMessages += "Invalid email address<br/>";
				}
				if(!isPasswordValid(request.getParameter("password"))){
					validityStatusFlag = false;
					errorMessages += "Invalid password<br/>";
				}
				if(isPasswordValid(request.getParameter("password")) && isPasswordValid(request.getParameter("confirm_password")) && !request.getParameter("password").equals(request.getParameter("confirm_password"))){
					validityStatusFlag = false;
					errorMessages += "Password mismatched<br/>";
				}
				if(request.getParameter("gender") == null){
					validityStatusFlag = false;
					errorMessages += "Please choose gender<br/>";
				}
				
				if(validityStatusFlag == true){
					session.setAttribute("registrationStatus", "2");	
%>
				<jsp:useBean id = "user" class = "bean.UserBean" scope = "session" />
				<jsp:setProperty name = 'user' property = 'firstName' value = '<%= request.getParameter("first_name") %>'/>
				<jsp:setProperty name = 'user' property = 'lastName' value = '<%= request.getParameter("last_name") %>'/>
				<jsp:setProperty name = 'user' property = 'dateOfBirth' value = '<%= request.getParameter("date_of_birth") %>'/>
				<jsp:setProperty name = 'user' property = 'gender' value = '<%= request.getParameter("gender") %>'/>
				<jsp:setProperty name = 'user' property = 'email' value = '<%= request.getParameter("email") %>'/>
				<jsp:setProperty name = 'user' property = 'phone' value = '<%= request.getParameter("phone") %>'/>
				<jsp:setProperty name = 'user' property = 'password' value = '<%= request.getParameter("password") %>'/>
<%					
					response.sendRedirect("advanced_registration.jsp");
				}
				out.println(errorMessages + "</fieldset>");
			}
			else{
				session.setAttribute("registrationStatus", "1");
				//session.setMaxInactiveInterval(12*60*60);
				//out.println(session.getMaxInactiveInterval());
			}
		}
		else{
			session.setAttribute("registrationStatus", "1");
			session.setMaxInactiveInterval(12*60*60);
			//out.println(session.getMaxInactiveInterval());
		}
	}
	else{
		if(session.getAttribute("registrationStatus") != null && session.getAttribute("registrationStatus").equals("2")){ 
			response.sendRedirect("advanced_registration.jsp");
		}
		else{
			session.setAttribute("registrationStatus", "1");
		}
	}
%>
<!DOCTYPE html  public "-//w3c//dtd html 4.0 transitional//en">
<html>
	<head>
		<title>REGISTRATION</title>
	</head>
	
	<body>
		<fieldset>
			<legend><h3 align="center">BASIC REGISTRATION </h3></legend>
			<form method="post">
				<b>First Name</b>: <input type='text' name='first_name' placeholder='First name' value='<%= request.getParameter("first_name")==null?"":request.getParameter("first_name") %>'><hr/>
				<b>Last Name</b>: <input type='text' name='last_name' placeholder='Last name' value='<%= request.getParameter("last_name")==null?"":request.getParameter("last_name") %>'><hr/>
				<b>Date Of Birth</b>: <input type='text' name='date_of_birth' placeholder='MM/DD/YYYY' value='<%= request.getParameter("date_of_birth")==null?"":request.getParameter("date_of_birth") %>'> <abbr title='MM/DD/YYYY'><b>i</b></abbr><hr/>
				<b>Gender</b>:
				<%
					if(request.getParameter("gender") != null){
						if(request.getParameter("gender").equals("male")){
							out.print("<input type='radio' name='gender' value='male' checked> Male " + "\n" +
										  "<input type='radio' name='gender' value='female'> Female " + "\n" +
										  "<input type='radio' name='gender' value='other'> Other " + "<hr/>\n");
						}
						else if(request.getParameter("gender").equals("female")){
							out.print("<input type='radio' name='gender' value='male'> Male " + "\n" +
										  "<input type='radio' name='gender' value='female' checked> Female " + "\n" +
										  "<input type='radio' name='gender' value='other'> Other " + "<hr/>\n");
						}
						else if(request.getParameter("gender").equals("other")){
							out.print("<input type='radio' name='gender' value='male'> Male " + "\n" +
						  "<input type='radio' name='gender' value='female'> Female " + "\n" +
						  "<input type='radio' name='gender' value='other' checked> Other " + "<hr/>\n");
						}
					}
					else{
						out.print("<input type='radio' name='gender' value='male'> Male " + "\n" +
						  "<input type='radio' name='gender' value='female'> Female " + "\n" +
						  "<input type='radio' name='gender' value='other'> Other " + "<hr/>\n");
					}
				%>
				<b>Email</b>: <input type='text' name='email' value='<%= request.getParameter("email")==null?"":request.getParameter("email") %>'> <abbr title='hint: sample@example.com'><b>i</b></abbr><hr/>
				<b>Phone</b>: <input type='text' name='phone' value='<%= request.getParameter("phone")==null?"":request.getParameter("phone") %>'><hr/>
				<b>Password</b>: <input type='password' name='password' value='<%= request.getParameter("password")==null?"":request.getParameter("password") %>'><abbr title='must contain one lowercase(a-z),one uppercase(A-Z), one special(@,#,$,&) character. Length at least 8'><b>i</b></abbr><hr/>
				<b>Confirm Password</b>: <input type='password' name='confirm_password' value='<%= request.getParameter("confirm_password")==null?"":request.getParameter("confirm_password") %>'><hr/>
				<input type='submit' value='Submit' />
				<input type='reset' />
			</form>
			<hr/>
			<a align="center" href="login.jsp" >Go Login</a>
		</fieldset>
	</body>
</html>