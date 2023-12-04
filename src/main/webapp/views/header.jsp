<%@ page import="com.github.simp7.commentdict.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("user") != null) {
%>
<form method="POST" action="/logout">
<label>${sessionScope.user.id}</label>
<button type="submit">로그아웃</button>
</form>
<%
} else {
%>
<form method="post" action="/login" style="display: flex; align-items: center">
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
