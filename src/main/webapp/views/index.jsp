<%@ page language="java" pageEncoding="UTF-8" contentType="text/html" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comment Dictionary</title>
</head>
<body>
<jsp:useBean id="user" class="com.github.simp7.commentdict.User" scope="session" />
<%
    if (session.getAttribute("") != null) {
%>
<%
    } else {
%>
    <form>
        <label>ID</label>
        <input type="text" name="id" />
        <label>Password</label>
        <input type="password" name="passwd" />
        <input type="submit" value="로그인" />
    </form>
<%}%>
</body>
</html>