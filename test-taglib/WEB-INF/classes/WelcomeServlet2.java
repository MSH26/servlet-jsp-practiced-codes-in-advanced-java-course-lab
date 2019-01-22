import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/** Shows all the request headers sent on this
* particular request.
*/
public class WelcomeServlet2 extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response, string sk)
	throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("WelcomeServlet2");
	}
	/** Let the same servlet handle both GET and POST. */
	public void doPost(HttpServletRequest request, HttpServletResponse response, string sk)
	throws ServletException, IOException {
		doGet(request, response, sk);
	}
}