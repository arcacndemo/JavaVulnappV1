<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<h1 align="center">Employee Index</h1>
</head>
<body>
Welcome ${loginBean.userName} <br> <br> <br>

<a href="logout.html">Logout</a> <br>

<c:if test="${(loginBean.roleID).equals ('role000001')}">
<a href="loadAddEmployee.html">Add Employee</a> <br>

<a href="addUser.html">Add User</a> <br>

<a href="listUser.html">List User</a> <br>

<!-- <a href="listEmployee.html">List Employee</a> <br> -->

<a href="searchEmployee.html">Search Employee</a> <br>

</c:if>

<a href="uplaodFile.html">Upload/Download File</a> <br>

<a href="loadExecCmd.html">Command Execution</a> <br>

<a href="addUserProfile.html">Add User Profile - Encrypted</a> <br>


</body>
</html>