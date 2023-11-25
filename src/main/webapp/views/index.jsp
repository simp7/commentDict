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
    if (user.getId() != null) {
%>
<label>${user.id}</label><button type="button"/>로그아웃</button>
<%
    } else {
%>
    <form action="index.jsp" style="display: flex; align-items: center">
        <div style="display: flex; flex-direction: column; margin-right: 20px">
            <label>ID</label>
            <input type="text" name="id" />
        </div>
        <div style="display: flex; flex-direction: column; margin-right: 20px">
        <label>Password</label>
        <input type="password" name="passwd" />
        </div>
        <div style="margin-right: 10px">
        <input type="submit" value="로그인" />
        </div>
        <div >
            <input type="submit" value="회원가입" />
        </div>
    </form>
<%}%>
<form>

</form>
</body>
</html>