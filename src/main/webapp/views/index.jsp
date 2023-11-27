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

    if (request.getParameter("keyword") != null) {
        String forwardURL = "/keyword/" + request.getParameter("keyword");
        pageContext.forward(forwardURL);
    }
    if (user.getId() != null) {
%>
<label>${user.id}</label>
<button type="button" onclick="location.href=''">로그아웃</button>
<%
    } else {
%>
    <form action="/" style="display: flex; align-items: center">
        <div style="display: flex; flex-direction: column; margin-right: 20px">
            <label>ID<input type="text" name="id" /></label>
        </div>
        <div style="display: flex; flex-direction: column; margin-right: 20px">
        <label>Password<input type="password" name="passwd"></label>
        </div>
        <div style="margin-right: 10px">
        <input type="submit" value="로그인" />
        </div>
        <div>
            <input type="button" value="회원가입" onclick="location.href='views/register.jsp'" />
        </div>
    </form>
<%}%>
<form action="/">
    <input type="text" id="search-input" maxlength="100" name="keyword"/>
    <input type="submit" value="검색" />
</form>
</body>
</html>