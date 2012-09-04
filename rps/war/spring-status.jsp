<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>

<html>
<head>
<link rel="stylesheet" href="./screen.css" type="text/css" media="screen" />
</head>

<body>
<h2>Spring Web Application Context Status</h2>

<%
  String BR="<br>";
  String SP="&nbsp;";

  WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

  out.println("WAR Startup Time: "+new Date(appContext.getStartupDate())+BR);

  String[] beanDefNames = appContext.getBeanDefinitionNames();
  out.println("<h3>WebApplicationContext</h3>");

  out.println("<table border=1 cellpadding=2 cellspacing=0>");
  out.println("<td><b>Bean Name</b></td><td><b>Type</b></td><td><b>Singleton</b></td>");
  for (int j=0 ; j < beanDefNames.length ; j++) {
    out.println("<tr>");
    String beanDefName = beanDefNames[j];
    String[] aliases = appContext.getAliases(beanDefName);
    String aliasesStr;
    if (aliases.length == 0)
      aliasesStr="";
    else
      aliasesStr = "<br>alias="+aliases[0]+"";
    Class type = appContext.getType(beanDefName);
    //out.println(SP+SP+SP+SP+beanDefName+" type="+type+BR);
    out.println("<td>"+beanDefName+aliasesStr+"</td>");
    out.println("<td>"+type+"</td>");

    //out.println("<td>"+appContext.isSingleton(beanDefName)+"</td>");
    
    try { 
      out.println("<td>"+appContext.isSingleton(beanDefName)+"</td>");
    } catch (Exception ex) { out.println("<td>"+ex.getMessage()+"</td>"); }
    out.println("</tr>");
    }
  out.println("</table>");
%>

<br>
<i>Page refreshed: <%= new Date() %></i>

</body>
</html>
