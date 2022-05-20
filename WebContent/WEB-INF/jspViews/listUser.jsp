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
<div class="row" width="100px">
 <div class="column" float="right" width="100px"> <a href="loadIndex.html">Back</a></div>
</div>
    <div align="center">
        <table>
           <tr>
            <td><caption><h2>List of users</h2></caption></td>
           </tr>
            <tr>
                <th>Username</th>
                <th>RoleID</th>
            </tr>
            <c:forEach items="${listUsersBean.userAccounts}" var="user" varStatus="status" >

            	<tr>
                    <td><c:out value="${user.userName}" /></td>
                    <td><c:out value="${user.roleID}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>