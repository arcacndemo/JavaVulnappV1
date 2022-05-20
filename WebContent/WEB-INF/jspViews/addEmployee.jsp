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
<c:if test="${not empty fn:trim(employeeBean.empName)}">
	Employee ${employeeBean.empName} account created.
</c:if>

<div class="row" width="100px" float="right"><a href="loadIndex.html">Back</a></div>

<form:form action="storeEmployee.html" method="post" modelAttribute="employeeBean">

<table align="center">
        <tr>
         <td>Employee Name:</td> <td><form:input path="empName"/><form:errors path="empName"/></td><br>
        </tr>
        <%-- <tr>
         <td>Gender:</td> <td><form:input path="gender"/><form:errors path="gender"/></td><br>
        </tr> --%>
        <tr>
               <td><form:label path = "gender">Gender</form:label></td>
               <td>
                  <form:radiobutton path = "gender" value = "M" label = "Male" />
                  <form:radiobutton path = "gender" value = "F" label = "Female" />
               </td>
        </tr>
        <tr>
               <td><form:label path = "departmentId">Department</form:label></td>
               <td>
                  <form:select path = "departmentId">
                     <form:option value = "NONE" label = "Select"/>
                     <form:options items = "${departmentList}" />
                  </form:select>     	
               </td>
         </tr>
        <tr>
         <td>City:</td> <td><form:input path="city"/><form:errors path="city"/></td><br>
        </tr>
        <tr>
         <td>Salary:</td> <td><form:input path="salary"/><form:errors path="salary"/></td><br>
        </tr>
        <tr></tr>
        <tr>
         <td><center><input type="submit" value="Submit"></center></td>
        </tr>
 </table>

<e:hasBindErrors name="employeeBean">
	<form:errors path="*"/>
</e:hasBindErrors>
</form:form>
</body>
</html>