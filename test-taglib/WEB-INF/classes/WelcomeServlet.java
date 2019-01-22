import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/** Shows all the request headers sent on this
* particular request.
*/
public class WelcomeServlet extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/control");
		dispatcher.forward(request, response, "SAKIB");
	}
	/** Let the same servlet handle both GET and POST. */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		doGet(request, response);
	}
}