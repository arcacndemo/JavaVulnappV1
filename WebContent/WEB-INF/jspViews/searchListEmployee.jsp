<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="e" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<div class="row" width="100px" float="right"><a href="loadIndex.html">Back</a></div>

<form:form action="searchEmployee.html" method="post" modelAttribute="employeeSearchBean">
<table>
        <tr>
        	<td><form:input path="empName"/></td>
        	<td><input type="submit" value="Search"></td>
        </tr> 
</table>

   <div align="center">
        <table>
        <tr>
            <td><caption><h2>List of Employee</h2></caption></td>
           </tr>
            <tr>
                <th>Employee ID</th>
                <th>Employee Name</th>
                <th>Gender</th>
                <th>City</th>
            </tr>
            <c:forEach items="${employeeSearchBean.employeeList}" var="employee" varStatus="status" >
            	 <%--  <tr>
                    <td><c:out value="${employee.empId}" /></td> 
                    <td><c:out value="${employee.empName}" /></td>
                    <td><c:out value="${employee.gender}" /></td>
                    <td><c:out value="${employee.city}" /></td>
                </tr> --%>
               <tr>
	                <td>${employee.empId} </td>
	                <td>${employee.empName} </td>
	                <td>${employee.gender}</td>
	                <td>${employee.city}</td>	  
                </tr>
            </c:forEach>
        </table>
    </div>
    </form:form>
</body>
</html>