<%
	out.println("RAKIB");
	String address = "/test3.jsp";
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
	dispatcher.forward(request, response);
%>