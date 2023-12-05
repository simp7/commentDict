<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/general.css">
</head>
<meta charset="UTF-8">
<body>
<jsp:include page="header.jsp" />
<h2>${keyword}</h2>
<hr>
<div class="list-group">
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <c:choose>
            <c:when test="${status.index == 0 && sessionScope.user.uid != null}">
                <c:choose>
                    <c:when test="${comment.isMine}">
                        <div class="comment">
                            ${comment.content}
                            <div>
                                추천도: ${comment.popularity}
                                <a href="/keyword/${keyword}/delete/${comment.id}">삭제</a>
                            </div>
                            <hr>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form method="post" style="display: flex; flex-direction: column" action="/keyword/${keyword}/add">
                            <textarea style="align-self: stretch; resize: none" name="content" rows="4" ></textarea>
                            <div>
                                <button type="submit">등록</button>
                            </div>
                            <hr>
                        </form>
                        <div class="likeButtonContainer">
                                ${comment.content}
                            <div>
                                추천도: ${comment.popularity}
                                <div>
                                    <a href="/keyword/${keyword}/like/${comment.id}">추천</a>
                                    <a href="/keyword/${keyword}/dislike/${comment.id}">비추천</a>
                                </div>
                            </div>
                            <hr>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>

            <c:otherwise>
            <div class="likeButtonContainer">
                ${comment.content}
                <div>
                    추천도: ${comment.popularity}
                    <div>
                        <a href="/keyword/${keyword}/like/${comment.id}">추천</a>
                        <a href="/keyword/${keyword}/dislike/${comment.id}">비추천</a>
                    </div>
                </div>
                <hr>
            </div>
            </c:otherwise>

        </c:choose>
    </c:forEach>
</div>
</body>
</html>
