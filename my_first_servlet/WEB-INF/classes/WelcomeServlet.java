// Import required java libraries
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.regex.*;
import java.time.Year; 

// Extend HttpServlet class
public class WelcomeServlet extends HttpServlet {
 
	public void init() throws ServletException
	{
		// Do required initialization
	}

	public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(registrationForm(request, "Get"));
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Boolean validityStatusFlag = true;
		
		out.println("<fieldset>");
		if(!isNameValid(request.getParameter("first_name"))){
			validityStatusFlag = false;
			out.println("Invalid first name or may be length smaller than 3!<br/>");
		}
		if(!isNameValid(request.getParameter("last_name"))){
			validityStatusFlag = false;
			out.println("Invalid last name or may be length smaller than 3!<br/>");
		}
		if(!isDateValid(request.getParameter("date_of_birth"))){
			validityStatusFlag = false;
			out.println("Invalid date of birth<br/>");
		}
		if(!isPhoneValid(request.getParameter("phone"))){
			validityStatusFlag = false;
			out.println("Invalid phone number<br/>");
		}
		if(!isEmailValid(request.getParameter("email"))){
			validityStatusFlag = false;
			out.println("Invalid email address<br/>");
		}
		if(!isPasswordValid(request.getParameter("password"))){
			validityStatusFlag = false;
			out.println("Invalid password<br/>");
		}
		if(!request.getParameter("password").equals(request.getParameter("confirm_password"))){
			validityStatusFlag = false;
			out.println("Password mismatched<br/>");
		}
		if(request.getParameter("gender") == null){
			validityStatusFlag = false;
			out.println("Please choose gender<br/>");
		}
		
		if(validityStatusFlag == true){
			out.println("Successful!!!</fieldset><br/>");
			doGet(request, response);
		}
		else if(validityStatusFlag == false){
			out.println( "</fieldset><br/>");
			out.println(registrationForm(request, "Post"));
		}
	}
	
	private String registrationForm(HttpServletRequest request, String method)
	{
		String title = "Registration Form";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		
		if(method.equals("Get")){
			String htmlForm =   docType +
									"<html>\n" +
										"<head><title>" + title + "</title></head>\n" +
										"<body>\n" +
											"<fieldset>" + "\n" +
												"<legend><h3>" + title + "</h3></legend>" + "\n" +
												"<form method='post'>" + "\n" +
													"<b>First Name</b>: <input type='text' name='first_name' placeholder='First name' value=''><hr/>" + "\n" +
													"<b>Last Name</b>: <input type='text' name='last_name' placeholder='Last name' value=''><hr/>" + "\n" +
													"<b>Date Of Birth</b>: <input type='text' name='date_of_birth' placeholder='MM/DD/YYYY' value=''> <abbr title='MM/DD/YYYY'><b>i</b></abbr><hr/>" + "\n" +
													"<b>Gender</b>: " + 
														"<input type='radio' name='gender' value='male'> Male " + "\n" +
														"<input type='radio' name='gender' value='female'> Female " + "\n" +
														"<input type='radio' name='gender' value='other'> Other " + "<hr/>\n" +
													"<b>Email</b>: <input type='text' name='email' value=''> <abbr title='hint: sample@example.com'><b>i</b></abbr><hr/>" + "\n" +
													"<b>Phone</b>: <input type='text' name='phone' value=''><hr/>" + "\n" +
													"<b>Password</b>: <input type='password' name='password' value=''><abbr title='must contain one lowercase(a-z),one uppercase(A-Z), one special(@,#,$,&) character. Length at least 8'><b>i</b></abbr><hr/>" + "\n" +
													"<b>Confirm Password</b>: <input type='password' name='confirm_password' value=''><hr/>" + "\n" +
													"<input type='submit' value='Submit' />" + "\n" +
													"<input type='reset' />" + "\n" +
												"</form>" + "\n" +
											"</fieldset>" + "\n" +
										"</body>" + "\n" +
									"</html>";
			return htmlForm;
		}
		else if(method.equals("Post")){
			String htmlFormStart =   docType +
										"<html>\n" +
											"<head><title>" + title + "</title></head>\n" +
											"<body>\n" +
												"<fieldset>" + "\n" +
													"<legend><h3>" + title + "</h3></legend>" + "\n" +
													"<form method='post'>" + "\n" +
														"<b>First Name</b>: <input type='text' name='first_name' placeholder='First name' value='" + request.getParameter("first_name") + "'><hr/>" + "\n" +
														"<b>Last Name</b>: <input type='text' name='last_name' placeholder='Last name' value='" + request.getParameter("last_name") + "'><hr/>" + "\n" +
														"<b>Date Of Birth</b>: <input type='text' name='date_of_birth' placeholder='MM/DD/YYYY' value='" + request.getParameter("date_of_birth") + "'> <abbr title='MM/DD/YYYY'><b>i</b></abbr><hr/>" + "\n" +
														"<b>Gender</b>: ";
			String htmlFormMid = "";
			if(request.getParameter("gender") != null){
				if(request.getParameter("gender").equals("male")){
					htmlFormMid = "<input type='radio' name='gender' value='male' checked> Male " + "\n" +
								  "<input type='radio' name='gender' value='female'> Female " + "\n" +
								  "<input type='radio' name='gender' value='other'> Other " + "<hr/>\n";
				}
				else if(request.getParameter("gender").equals("female")){
					htmlFormMid = "<input type='radio' name='gender' value='male'> Male " + "\n" +
								  "<input type='radio' name='gender' value='female' checked> Female " + "\n" +
								  "<input type='radio' name='gender' value='other'> Other " + "<hr/>\n";
				}
				else if(request.getParameter("gender").equals("other")){
					htmlFormMid = "<input type='radio' name='gender' value='male'> Male " + "\n" +
								  "<input type='radio' name='gender' value='female'> Female " + "\n" +
								  "<input type='radio' name='gender' value='other' checked> Other " + "<hr/>\n";
				}
			}
			else{
				htmlFormMid = "<input type='radio' name='gender' value='male'> Male " + "\n" +
							  "<input type='radio' name='gender' value='female'> Female " + "\n" +
							  "<input type='radio' name='gender' value='other'> Other " + "<hr/>\n";
			}
												
			String htmlFormEnd =  	"<b>Email</b>: <input type='text' name='email' value='" + request.getParameter("email") + "'> <abbr title='hint: sample@example.com'><b>i</b></abbr><hr/>" + "\n" +
									"<b>Phone</b>: <input type='text' name='phone' value='" + request.getParameter("phone") + "'><hr/>" + "\n" +
									"<b>Password</b>: <input type='password' name='password' value=''><abbr title='must contain one lowercase(a-z),one uppercase(A-Z), one special(@,#,$,&) character. Length at least 8'><b>i</b></abbr><hr/>" + "\n" +
									"<b>Confirm Password</b>: <input type='password' name='confirm_password' value=''><hr/>" + "\n" +
									"<input type='submit' value='Submit' />" + "\n" +
									"<input type='reset' />" + "\n" +
								"</form>" + "\n" +
							"</fieldset>" + "\n" +
						"</body>" + "\n" +
					"</html>";
			
			return htmlFormStart + htmlFormMid + htmlFormEnd;
		}
		
		return "";
	}
	
	private Boolean isNameValid(String name)
	{
		if(Pattern.matches("([a-z]|[A-Z])([a-z]|[A-Z])([a-z]|[A-Z])((([a-z]|[A-Z])+)?)", name)){
			return true;
		}
		return false;
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

	public void destroy()
	{
		// do nothing.
	}
}