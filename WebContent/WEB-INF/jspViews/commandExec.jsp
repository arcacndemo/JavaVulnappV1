<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>     
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<br>
<br>
<h1 align="center">Command Execution</h1>
</head>
<body>
<br>
<div class="row" width="100px" float="right"><a href="loadIndex.html">Back</a></div>
<br>
<c:if test="${not empty fn:trim(msg)}">
	${msg}
</c:if>

<form action="deleteFile.html" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<h2 align="center">Deleting file</h2>   
<input id="file" type="text" name="fileName" /> <button type="submit" value="Delete">Delete</button>
</form>

<form action="execCmd.html" method="POST">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<br>
<br>
<h2 align="center">Execute command</h2>
  <!--select name="cmd">
    <option value="dir">dir</option>
    <option value="cd">cd</option>
  </select-->
  <input id="cmd" type="text" name="cmd" />
  <input type="submit" value="Submit">
  <div align="center">
        <table>
            <tr>
                <th>Listing directory</th>
            </tr>
            <c:forEach items="${dirlist}" var="dir" varStatus="status">
            	 <tr>
                    <td><c:out value="${dir}" /></td> 
                </tr> 
            </c:forEach>
        </table>
    </div>
</form>



</body>
</html>