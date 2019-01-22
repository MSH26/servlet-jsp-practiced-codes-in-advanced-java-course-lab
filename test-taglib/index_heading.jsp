
<%@ taglib uri="/WEB-INF/csajsp-taglib-heading.tld" prefix="csajsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML>
	<HEAD>
		<TITLE>Some Tag-Generated Headings</TITLE>
	</HEAD>
	
	<BODY>
		<csajsp:heading bgColor="#C0C0C0">
		Default Heading
		</csajsp:heading>
		<P>
		<csajsp:heading bgColor="BLACK" color="WHITE">
		White on Black Heading
		</csajsp:heading>
		<P>
		<csajsp:heading bgColor="#EF8429" fontSize="60" border="5">
		Large Bordered Heading
		</csajsp:heading>
		<P>
		<csajsp:heading bgColor="CYAN" width="100%">
		Heading with Full-Width Background
		</csajsp:heading>
		<P>
		<csajsp:heading bgColor="CYAN" fontSize="60"
		fontList="Brush Script MT, Times, serif">
		Heading with Non-Standard Font
		</csajsp:heading>
		<P>
	</BODY>
</HTML>