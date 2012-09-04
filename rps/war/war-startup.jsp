<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<html>
<head>
<link rel="stylesheet" href="./screen.css" type="text/css" media="screen" />
</head>

<body>
<!--
<h2>WAR Startup</h2>
-->

<%
  String BR="<br>";
  String SP="&nbsp;";

  WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

  out.println("WAR Startup Timestamp: "+new Date(appContext.getStartupDate())+BR);
%>

<br>
<i>
Page refreshed: <%= new java.util.Date() %>
</i>
</body>
</html>
