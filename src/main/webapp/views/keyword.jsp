<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/general.css">
    <link rel="stylesheet" href="/css/keyword.css">
</head>
<meta charset="UTF-8">
<body>
<jsp:include page="header.jsp" />
<h2>${keyword}</h2>
<hr>
<div id="list-group">
    <c:forEach var="comment" items="${comments}" varStatus="status">
        <c:choose>
            <c:when test="${status.index == 0 && sessionScope.user.uid != null}">
                <c:choose>
                    <c:when test="${comment.isMine}">
                        <div class="comment">
                            ${comment.content}
                            <div class="button-container">
                                추천도: ${comment.popularity}
                                <form action="/keyword/${keyword}/delete/${comment.id}">
                                    <button type="submit">삭제</button>
                                </form>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="comment">
                            <form method="post" style="display: flex; flex-direction: column" action="/keyword/${keyword}/add">
                                <textarea  name="content" rows="5" oninput='this.style.height="auto"; this.style.height=this.scrollHeight + 5 + "px"'></textarea>
                                <div class="button-container">
                                    <button type="submit">등록</button>
                                </div>
                            </form>
                        </div>
                        <hr>
                        <div class="comment">
                            ${comment.content}
                            <div class="button-container">
                                추천도: ${comment.popularity}
                                <form action="/keyword/${keyword}/like/${comment.id}">
                                    <button type="submit">추천</button>
                                </form>
                                <form action="/keyword/${keyword}/dislike/${comment.id}">
                                    <button type="submit">비추천</button>
                                </form>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>

            <c:otherwise>
            <div class="comment">
                ${comment.content}
                <div class="button-container">
                    추천도: ${comment.popularity}
                    <form action="/keyword/${keyword}/like/${comment.id}">
                        <button type="submit">추천</button>
                    </form>
                    <form action="/keyword/${keyword}/dislike/${comment.id}">
                        <button type="submit">비추천</button>
                    </form>
                </div>
            </div>
            </c:otherwise>

        </c:choose>
        <hr>
    </c:forEach>
    <c:if test="${comments.size() == 0}">
        <div class="comment">
            <form method="post" style="display: flex; flex-direction: column" action="/keyword/${keyword}/add">
                <textarea  name="content" rows="5" oninput='this.style.height="auto"; this.style.height=this.scrollHeight + 5 + "px"'></textarea>
                <div class="button-container">
                    <button type="submit">등록</button>
                </div>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
