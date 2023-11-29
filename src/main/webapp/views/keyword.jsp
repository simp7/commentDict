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
<div class="list-group">
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <c:choose>

        <c:when test="${status.index == 0} && ${sessionScope.uid != null}">
            <c:choose>
                <c:when test="${comment.isMine}">
                    <div class="comment">
                        ${comment.content}
                        <div>
                            <a href="/comment/delete/${comment.id}">삭제</a>
                        </div>
                        <hr>
                    </div>
                </c:when>
                <c:otherwise>
                    <form style="display: flex; flex-direction: column" action="/comment/add/${keyword}">
                        <textarea style="align-self: stretch; resize: none" name="content" rows="4" ></textarea>
                        <div>
                            <input type="submit" value="등록">
                        </div>
                        <hr>
                    </form>
                </c:otherwise>
            </c:choose>
        </c:when>

        <c:otherwise>
        <div class="comment">
            ${comment.content}
            <div>
                <a href="/comment/like/${comment.id}">추천</a>
                <a href="/comment/dislike/${comment.id}">비추천</a>
            </div>
            <hr>
        </div>
        </c:otherwise>

        </c:choose>
    </c:forEach>
</div>
</body>
</html>
