package bean;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.regex.*;
import java.time.Year; 

// Extend HttpServlet class
public class BasicRegistrationServlet extends HttpServlet {
 
	public void init() throws ServletException
	{
		// Do required initialization
	}

	public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		session.setAttribute("registrationStatus", "45");
		out.println("Get");
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws ServletException, IOException
	{
		HttpSession session = request.getSession();  
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Boolean validityStatusFlag = true;
		
		if(!isNameValid(request.getParameter("first_name"))){
			session.setAttribute("first_name", "Invalid first name or may be length smaller than 3!<br/>");
		}
		if(!isNameValid(request.getParameter("last_name"))){
			session.setAttribute("last_name", "Invalid last name or may be length smaller than 3!<br/>");
		}
		if(!isDateValid(request.getParameter("date_of_birth"))){
			session.setAttribute("date_of_birth", "Invalid date of birth");
		}
		if(!isPhoneValid(request.getParameter("phone"))){
			session.setAttribute("phone", "Invalid phone number");
		}
		if(!isEmailValid(request.getParameter("email"))){
			session.setAttribute("email", "Invalid email address");
		}
		if(!isPasswordValid(request.getParameter("password"))){
			session.setAttribute("password", "Invalid password");
		}
		if(!request.getParameter("password").equals(request.getParameter("confirm_password"))){
			session.setAttribute("confirm_password", "Password mismatched");
		}
		if(request.getParameter("gender") == null){
			session.setAttribute("gender", "Please choose gender");
		}
		
		if(validityStatusFlag == true){
			session.setAttribute("registrationStatus", "45");
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("advanced_registration.jsp");
			requestDispatcher.forward(request, response);
		}
		else if(validityStatusFlag == false){
			RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("basic_registration.jsp");
			requestDispatcher.forward(request, response);
		}
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