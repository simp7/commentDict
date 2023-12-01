
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="user" class="com.github.simp7.commentdict.User" scope="session" />
<%
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
