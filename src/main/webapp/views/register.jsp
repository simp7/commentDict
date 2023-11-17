<%@ page language="java" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 가입</title>
</head>
<body>
    <h2>회원 가입</h2>
    <hr />
    <form>
        <label>ID</label>
        <input type="text" name="id" />
        <label>Password</label>
        <input type="password" name="passwd" />
        <label>Password(위와 동일하게 입력)</label>
        <input type="password" name="passwd" />
        <input type="submit" value="회원 가입" />
    </form>
</body>
</html>