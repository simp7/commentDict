<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String id = request.getParameter("id");
    String passwd = request.getParameter("passwd");
    String confirm = request.getParameter("confirm_passwd");
    if (id != null && passwd != null && passwd.equals(confirm)) {
%>
    <jsp:forward page="registering.jsp" />
<%
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
</head>
<body>
    <h2>회원 가입</h2>
    <hr />
    <form >
        <label>ID
            <input type="text" name="id" />
        </label>
        <label>Password
            <input type="password" name="passwd" />
        </label>
        <label>Password(위와 동일하게 입력)
            <input type="password" name="confirm_passwd" />
        </label>
        <input type="submit" value="회원 가입" />
    </form>
</body>
</html>