<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Security Application</title>
</head>
<body>
<br>
<h1 align="center">Login to Employee Secure App</h1>
<!-- <form action="validate.html" method="post">
Username <input type="text" name="uname"/></br>
password <input type="password" name="pwd"/><br>
<input type="submit" value="Submit"/>
</form> -->
<form action="validate.html" method="post">
<table align="center">
		<tr style="color:red"><center>${msg}</center></tr>
        <tr>
         <td>Username :</td> <td><input type="text" name="uname"></td><br>
        </tr>
        <tr>
         <td>Password :</td> <td><input type="password" name="pwd"></td><br>
        </tr>
        <tr><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /></tr>
        <tr>
         <td><center><input type="submit" value="Submit" name="button" class="btn"></center></td>
        </tr>
 </table>
 </form>
</body>
</html>