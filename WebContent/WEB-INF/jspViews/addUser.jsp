<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="e" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet"> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Information</title>
</head>
<body>
<c:if test="${not empty fn:trim(userAccountBean.userName)}">
	User ${userAccountBean.userName} account created.
</c:if>

<div class="row" width="100px" float="right"><a href="loadIndex.html">Back</a></div>

<form:form action="addUser.html" method="post" modelAttribute="userAccountBean">

<table align="center">
        <tr>
         <td>User Name:</td> <td><form:input path="userName"/></td><br>
        </tr>
        <tr>
         <td>Password:</td> <td><form:input path="pwd"/></td><br>
        </tr>
        <tr>
         <td>First Name:</td> <td><form:input path="firstName"/></td><br>
        </tr>
        <tr>
         <td>Last Name:</td> <td><form:input path="lastName"/></td><br>
        </tr>        
        <tr></tr>
        <tr>
         <td><center><input type="submit" value="Submit"></center></td>
        </tr>
 </table>

</form:form>
</body>
</html>