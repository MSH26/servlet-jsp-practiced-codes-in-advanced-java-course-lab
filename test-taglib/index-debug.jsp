<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Using the Debug Tag</TITLE>
<LINK REL=STYLESHEET
HREF="JSP-Styles.css"
TYPE="text/css">
</HEAD>
<BODY>
<H1>Using the Debug Tag</H1>
<%@ taglib uri="/WEB-INF/csajsp-taglib-debug.tld" prefix="csajsp" %>
Top of regular page. Blah, blah, blah. Yadda, yadda, yadda.
<P>
<csajsp:debug>
<B>Debug:</B>
<UL>
<LI>Current time: <%= new java.util.Date() %>
<LI>Requesting hostname: <%= request.getRemoteHost() %>
<LI>Session ID: <%= session.getId() %>
</UL>
</csajsp:debug>
<P>
Bottom of regular page. Blah, blah, blah. Yadda, yadda, yadda.
</BODY>
</HTML>