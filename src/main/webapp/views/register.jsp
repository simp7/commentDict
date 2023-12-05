<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
    <link rel="stylesheet" href="/css/general.css">
    <link rel="stylesheet" href="/css/register.css">
</head>
<body>
    <div id="container">
        <h2>회원 가입</h2>
        <hr />
        <form method="POST" action="/registering">
            <div class="register-item">
                <label>ID</label>
                <input id="register-input-id" type="text" name="id" />
            </div>
            <div class="register-item">
                <label>Password</label>
                <input type="password" name="passwd" />
            </div>
            <div class="register-item">
                <label>Password(위와 동일하게 입력)</label>
                <input type="password" name="confirm_passwd" />
            </div>
            <button type="submit">회원가입</button>
        </form>
    </div>
</body>
</html>