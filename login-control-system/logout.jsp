<%
	if(session.getAttribute("JSESSIONLOGSTATUS") != null){
		if(session.getAttribute("JSESSIONLOGSTATUS").equals("LOOGGED_IN")){
			response.sendRedirect("home.jsp");
		}
	}
	else{
		response.sendRedirect("index.jsp");
	}
	
	session.setAttribute("JSESSIONLOGSTATUS", "LOOGGED_OUT");
	session.removeAttribute("JSESSIONUSERNAME");
	session.setMaxInactiveInterval(0);
	response.sendRedirect("index.jsp");
%>