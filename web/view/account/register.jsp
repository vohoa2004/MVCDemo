<%-- 
    Document   : register
    Created on : Jan 11, 2025, 4:32:30 PM
    Author     : vothimaihoa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register New Account</title>
</head>
<!-- hien msg -->
<jsp:include page="../layout/header.jsp" flush="true"/>

<%
    String message = (String) request.getAttribute("message");
%>
<%
    if (message != null) {
%>
<p><%=message%></p>
<%
    }
%>

<h1>Register</h1>
<form action="Account" method="POST">
    <label>Username:</label><br>
    <input name="username" type="text" required/><br>

    <label>Password:</label><br>
    <input name="password" type="password" required/><br>

    <label>Confirm Password: </label><br>
    <input name="passwordConfirm" type="password" required/><br>

    <input type="hidden" name="action" value="register"/>
    <input type="submit" value="Register">

</form>


