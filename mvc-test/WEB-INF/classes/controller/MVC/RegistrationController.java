package controller.MVC;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;

public class RegistrationController extends HttpServlet{
		
		public void doGet(HttpServletRequest request,HttpServletResponse response)
		throws IOException,ServletException{
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("IN-RegistrationController");
			// try {
				// HttpSession session = request.getSession();
	           // /* String view = session.getAttribute("view").toString();*/
				// System.out.println("hello");
				// RequestDispatcher rs = request.getRequestDispatcher("/WEB-INF/view/registration-form.jsp");
				// rs.forward(request,response);	

			// }catch (Exception e) {
              // System.out.println(e);

      		// }		
	}

	// receive data from view

	public void doPost(HttpServletRequest request,HttpServletResponse response)
		throws IOException,ServletException{
			
		doGet(request,response);	
	}
	 	
}

