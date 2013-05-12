<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
Hello : <%=new java.util.Date() %>
<div class="red"><h1>Python and SAP Connector Test</h1></div>
<div class="body"><a href="testReadSapTable.jsp">Read The Table Meta Info From sap System</a>
<p>测试jython连接SAP，并且通过网页的方式调用</p>
<p>参考：<a href="http://www.jython.org/jythonbook/en/1.0/SimpleWebApps.html#servlets">Chapter 13: Simple Web Applications</a></p>
<p>第一步配置web.xml,把所有对py文件的读取转接到jython.jar处理。</p>
<div class="highlight-python"><pre>&lt;servlet&gt;
    &lt;servlet-name&gt;PyServlet&lt;/servlet-name&gt;
    &lt;servlet-class&gt;org.python.util.PyServlet&lt;/servlet-class&gt;
    &lt;load-on-startup&gt;1&lt;/load-on-startup&gt;
&lt;/servlet&gt;
&lt;servlet-mapping&gt;
    &lt;servlet-name&gt;PyServlet&lt;/servlet-name&gt;
    &lt;url-pattern&gt;*.py&lt;/url-pattern&gt;
&lt;/servlet-mapping&gt;</pre>
</div>
<p>第二步，在py文件中处理get，或post。因为jython继承了类javax.servlet.http.HttpServlet，所以所有的参数的读取与处理可以参考java相关部分。</p>

</div>

</body>

</html>