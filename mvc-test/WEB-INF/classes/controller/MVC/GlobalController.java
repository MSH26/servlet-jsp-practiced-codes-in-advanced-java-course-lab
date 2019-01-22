package controller.MVC;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class GlobalController extends HttpServlet{
		
		private void doProcess(HttpServletRequest request,HttpServletResponse response)
		throws IOException,ServletException{
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			String requestURL  = request.getPathInfo();

			try {
            	 
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(getClass().getResourceAsStream("mvc.xml"));
	            doc.getDocumentElement().normalize();
	            
	            NodeList ctrlUrlNodeList = doc.getElementsByTagName("ctrl-url");
	            String dispatchURL = null;
	           
	            Node sibling = null;

	            for(int i = 0; i<ctrlUrlNodeList.getLength(); i++){
	               String ctrlUrlContent =  ctrlUrlNodeList.item(i).getTextContent();  

					if(ctrlUrlContent.equals(requestURL)){
	               		sibling = ctrlUrlNodeList.item(i).getNextSibling();
	               		
	               		while (!(sibling instanceof Element) && sibling != null) {
						  sibling = sibling.getNextSibling();
						}

	               		dispatchURL = sibling.getTextContent();
	               		break;
					}else{
	            		dispatchURL = "/WEB-INF/view/error.jsp";   	
					}

	            }
	            
	            RequestDispatcher rs = request.getRequestDispatcher(dispatchURL);
				rs.forward(request,response);

  
        	}catch (Exception e) {
              System.out.println(e);

      		}

			
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	 
	
}

