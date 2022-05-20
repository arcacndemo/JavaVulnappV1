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
<h1 align="center">Upload/Download File</h1>
</head>
<body>
<br>
<div class="row" width="100px" float="right"><a href="loadIndex.html">Back</a></div>
<br>
<c:if test="${not empty fn:trim(msg)}">
	${msg}
</c:if>
<br>
<br>
<!--alert(${_csrf.token}); alert(${_csrf.parameterName});-->
<form id="fileuploadForm" action="fileupload.html" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <label for="file">File</label>
    <input id="file" type="file" name="file" />
    <p><button type="submit" class="btn">Upload</button></p>        
</form>

<form id="uploademployeeForm" action="uploademployee.html" method="POST" enctype="multipart/form-data">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <label for="file">File</label>
    <input id="file" type="file" name="file" />
    <p><button type="submit" class="btn">Upload Employee Detail</button></p>        
</form>

<form id="downloadForm" action="download.html?param1=file" method="GET">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    <input id="file" type="text" name="file" />
    <p><button type="submit" class="btn">Download</button></p>         
</form>

<form id="downloadPropFileForm" action="downloadPropFile.html" method="POST">
     <p><button type="submit" class="btn">Download prop</button></p>       
</form>

<!--center>
        <h2><a href="download.html">Click here to download file</a></h2>
 </center-->

</body>
</html>