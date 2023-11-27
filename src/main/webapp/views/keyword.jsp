<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<meta charset="UTF-8">
<body>
<h2>${keyword}</h2>
<hr>
<ul class="list-group">
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <li class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"><a href="/news/${news.aid}" class="text-decoration-none">[${status.count}] ${news.title}, ${news.date}</a>
            <a href="/comment/delete/${comment.id}"><span class="badge bg-secondary">&times;</span></a>
        </li>
    </c:forEach>
</ul>
</body>
</html>
