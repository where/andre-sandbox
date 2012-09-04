<%@ page import="java.util.*" %>
<%@ page import="org.springframework.context.support.*" %>
<%@ page import="org.springframework.context.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.beans.factory.NoSuchBeanDefinitionException" %>

<h1>Spring Web Application Context Info</h1>

<%
  String BR="<br>";
  HttpSession hsession = request.getSession(true);
  ServletContext servletContext = hsession.getServletContext();

// -----------------------

  out.println("<table border=1>");

  out.println("<tr>");
  out.println("<td>HttpSession</td><td>"+hsession+"</td>");
  out.println("</tr>");

  out.println("<tr>");
  String aname = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
  out.println("<td>WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE</td><td>"+aname+"</td>");
  out.println("</tr>");

  WebApplicationContext wcontext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
  out.println("<tr>");
  out.println("<td>WebApplicationContext</td><td>"+wcontext+"</td>");
  out.println("</tr>");

  Object aval = servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
  out.println("<tr>");
  out.println("<td>ServletContext.getAttribute(\""+aname+"\")</td><td>"+aval+"</td>");
  out.println("</tr>");

  out.println("</table>");

%>
