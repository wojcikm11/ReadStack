<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>ReadStack - ${requestScope.discovery.title}</title>
    <%@ include file="../segments/stylesheets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/discovery.css">
</head>
<body>

<div class="container">

    <%@ include file="../segments/header.jspf" %>

    <main>

        <%@ include file="../segments/discovery.jspf" %>

        <article class="comments">
            <h2>Komentarze (${requestScope.comments.size()}):</h2>
            <c:forEach var="comment" items="${requestScope.comments}">
                <div class="comment">
                    <div class="comment-header">
                        <a class="comment-username-link ${comment.usernameColor.cssClassName}" href="${pageContext.request.contextPath}/profile?username=${comment.username}">${comment.username}</a>
                        <p class="comment-date">${comment.dateAdded.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}</p>
                    </div>
                    <p class="comment-content">${comment.content}</p>
                </div>
            </c:forEach>
        </article>
        <form action="${pageContext.request.contextPath}/discovery/add_comment?id=${requestScope.discovery.id}" method="post" class="form-add-comment">
            <h2 class="add-comment-header">Dodaj komentarz:</h2>
            <textarea id="comment-form" class="add-comment-content" name="comment"></textarea>
            <input type="submit" class="add-comment-button" value="Dodaj">
        </form>
    </main>
    <%@ include file="../segments/footer.jspf" %>
</div>
</body>
</html>
