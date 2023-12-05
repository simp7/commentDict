<link rel="stylesheet" href="/css/general.css">
<link rel="stylesheet" href="/css/header.css">
<%@ page import="com.github.simp7.commentdict.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("user") != null) {
%>
<form method="post" action="/logout" class="login-bar">
    <label id="login-user">${sessionScope.user.id}</label>
    <button type="submit">로그아웃</button>
</form>
<%
} else {
%>
<form method="post" action="/login" id="login-bar">
    <div class="login-item">
        <label>ID<input type="text" name="id" /></label>
    </div>
    <div class="login-item">
        <label>Password<input type="password" name="passwd"></label>
    </div>
    <div class="login-item">
        <button type="submit">로그인</button>
    </div>
    <div>
        <button type="button" onclick="location.href='views/register.jsp'">회원가입</button>
    </div>
</form>
<%}%>
