<%@ page contentType="text/html; 
  charset=iso-8859-1" 
  language="java" 
  session="true" 
%>

<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.jsp.JspWriter" %>

<html>
<head>
<title>Status</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<!-- <h1>HTTP Info</h1> -->

<%!
  String BR="<br>";
  int hdrBase=1;

  void printRequest(JspWriter ps, HttpServletRequest  req)
    throws IOException
  {
    hprintln(ps,hdrBase+1,"HttpServletRequest Info");
    pprintln(ps,"  ServletPath="+req.getServletPath());
    pprintln(ps,"  ContextPath="+req.getContextPath());

    fprintln(ps,"QueryString",        req.getQueryString());
    //pprintln(ps,"QueryString        "+req.getQueryString());

    pprintln(ps,"  RequestURI="+req.getRequestURI());
    fprintln(ps,"RemoteUser",       req.getRemoteUser());
    fprintln(ps,"PathInfo",         req.getPathInfo());
    fprintln(ps,"PathTranslated",   req.getPathTranslated());
    fprintln(ps,"Method",           req.getMethod());
    fprintln(ps,"Protocol",         req.getProtocol());
    fprintln(ps,"Scheme",           req.getScheme());
    fprintln(ps,"RemoteAddr",       req.getRemoteAddr());
    fprintln(ps,"RemoteHost",       req.getRemoteHost());
    pprintln(ps,"  ServerPort="+req.getServerPort());
    pprintln(ps,"  IsSecure="+req.isSecure());
    pprintln(ps,"  ContentLength="+req.getContentLength());
    pprintln(ps,"  ContentType="+req.getContentType());
    pprintln(ps,"  IsRequestedSessionIdValid="+req.isRequestedSessionIdValid());
    //fprintln(ps,"RequestedSessionId",req.getRequestedSessionId());
  
  // -- Headers, Parameters and Attributes
    hprintln(ps,hdrBase+2,"Header Names");
    Enumeration names = req.getHeaderNames() ;
    while (names.hasMoreElements())
      {
      String name = (String)names.nextElement();
      String value = req.getHeader(name) ;
      pprintln(ps,"    "+name+"="+value);
      }

  // -- Parameters and Attributes
    hprintln(ps,hdrBase+2,"Parameter Names");
    names = req.getParameterNames() ;
    while (names.hasMoreElements())
      {
      String name = (String)names.nextElement();
      printEnumValue(ps, name, req.getParameterValues(name));
      }

  // -- Attributes
    hprintln(ps,hdrBase+2,"Attribute Names");
    names = req.getAttributeNames() ;
    while (names.hasMoreElements())
      {
      String name = (String)names.nextElement();
      Object value = req.getAttribute(name);
      pprintln(ps,"    "+name+"="+value);
      }

  // -- Cookies
    printCookies(ps, req);
   
  // -- Session
    printSession(ps, req.getSession());
  }

  void printEnumValue(JspWriter ps, String name, String [] vals)
    throws IOException
  {
    pprint(ps,"    "+name+"=");
    if (null == vals) 
      pprintln(ps,"");
    else
      {
      for (int j=0 ; j < vals.length ; j++)
        pprint(ps,"'" + vals[j]+ "' ");
      pprintln(ps,"");
      }
  }

  // -- Parameters 
  
  void printSession(JspWriter ps, HttpSession session)
    throws IOException
  {
    if (session==null)
      {
      hprintln(ps,hdrBase+1,"HttpSession: NULL");
      return;
      }
  
    hprintln(ps,hdrBase+1,"HttpSession");
    pprintln(ps,"    CreationTime    ="+session.getCreationTime()
      + " - " + new Date(session.getCreationTime()));
    pprintln(ps,"    LastAccessedTime="+session.getLastAccessedTime()
      + " - " + new Date(session.getLastAccessedTime()));
    pprintln(ps,"    MaxInactiveInterval="+session.getMaxInactiveInterval()+ " seconds");
    pprintln(ps,"    IsNew="+session.isNew());
    pprintln(ps,"    Id="+session.getId());

  // -- Attributes 

    hprintln(ps,hdrBase+2,"Session Attributes");
    for (Enumeration en=session.getAttributeNames(); en.hasMoreElements();)
      {
      String name=(String)en.nextElement();
      pprintln(ps,"      "+name+": "+ session.getAttribute(name));
      }
  }
  
  void printSecurity(JspWriter ps, HttpServletRequest req)
    throws IOException
  {
    hprintln(ps,hdrBase+1,"HttpServletRequest SECURITY INFO");
    pprintln(ps,"  getAuthType (CGI AUTH_TYPE)="+ req.getAuthType());
    pprintln(ps,"  getRemoteUser (CGI REMOTE_USER)="+req.getRemoteUser());
    pprintln(ps,"  getUserPrincipal ="+req.getUserPrincipal());
    //pprintln(ps,"isUserInRole  ",req.isUserInRole());
  }
  
  //*************************************************************************
  
  void printServletConfig(JspWriter ps, ServletConfig servletConfig)
    throws IOException
  {
    hprintln(ps,hdrBase+1,"ServletConfig");
  
  // -- ServletConfig
  
    pprintln(ps,"  ServletName="+servletConfig.getServletName());
  
    hprintln(ps,hdrBase+2,"InitParameters");
    for (Enumeration en=servletConfig.getInitParameterNames(); en.hasMoreElements();)
      {
      String name=(String)en.nextElement();
      pprintln(ps,"    "+name+"="+ (String)servletConfig.getInitParameter(name));
      }
  
  // -- ServletContext
  
    ServletContext servletContext = servletConfig.getServletContext();
    hprintln(ps,hdrBase+2,"ServletContext:");
    pprintln(ps,"  ServerInfo="+servletContext.getServerInfo());
    pprintln(ps,"  Major="+servletContext.getMajorVersion()+" minor="
       +servletContext.getMinorVersion());
    pprintln(ps,"  ServletContextName="+servletContext.getServletContextName());
  
    hprintln(ps,hdrBase+3,"InitParameters:");
    for (Enumeration en=servletContext.getInitParameterNames(); en.hasMoreElements();)
      {
      String name=(String)en.nextElement();
      pprintln(ps,"    "+name+"="+ (String)servletContext.getInitParameter(name));
      }

    hprintln(ps,hdrBase+3,"Attributes:");
    for (Enumeration en=servletContext.getAttributeNames(); en.hasMoreElements();)
      {
      String name=(String)en.nextElement();
      pprintln(ps,"    "+name+"="+ servletContext.getAttribute(name));
      }
  }

  void printCookies(JspWriter ps, HttpServletRequest req)
    throws IOException
  {
    Cookie [] cookies = req.getCookies();
    if (cookies == null) 
      {
      pprintln(ps,"  Cookies: NONE");
      return;
      }
    pprintln(ps,"  #Cookies="+cookies.length+":");
    
    for (int j=0 ; j < cookies.length ; j++)
      {
      Cookie ck = cookies[j];
      pprintln(ps,"    "
       + " name='"+ck.getName()+"'"
       + " value='"+ck.getValue()+"'"
       + " maxAge="+ck.getMaxAge()
       + " domain="+ck.getDomain()
       + " path="+ck.getPath()
       + " secure="+ck.getSecure()
       + " version="+ck.getVersion()
       );
      }
  }

  void fprintln(JspWriter ps, String name, Object val)
    throws IOException
  {
    if (val != null)
      pprintln(ps,"  " + name + "="+ val);
  }

  void hprintln(JspWriter ps, int hnum, Object o) throws IOException {
    ps.println("<h"+hnum+">"+o+"</h"+hnum+">");
  }

  void pprintln(JspWriter ps, Object o) throws IOException {
    ps.println(o+BR);
  }

  void pprint(JspWriter ps, Object o)
      throws IOException
    {ps.print(o);}
%>

<%
  printRequest(out, request);
  out.println("<i>Page updated: "+new Date()+"</i>"+BR);
%>


</body>
</html>
