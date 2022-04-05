<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>${requestScope.username} - ReadStack</title>
    <%@ include file="../segments/stylesheets.jspf" %>
</head>
<body>
<div class="container">
  <%@ include file="../segments/header.jspf" %>

  <div class="image-info">
    <div class="username ${requestScope.usernameColor.cssClassName}">
      ${requestScope.username}
    </div>
    <div class="image">
      <img src="http://www.gravatar.com/avatar/?d=mp" class="user-image" alt="http://www.gravatar.com/avatar/?d=mp">
    </div>
  </div>

<main>
  <h2 class="header-info">Informacje:</h2>
  <section class="user-info">
    <ul class="user-info-list">
      <li><span class="profile-attribute">Data założenia konta: </span>${requestScope.registrationInfo.registrationDate}</li>
      <li><span class="profile-attribute">Konto aktywne od: </span>${requestScope.registrationInfo.accountAge}
<%--        <c:if test="${not empty requestScope.userDetails.accountAge}">--%>
<%--          ${requestScope.userDetails.accountAge}--%>
<%--        </c:if>--%>
<%--        <c:if test="${empty requestScope.userDetails.accountAge}">--%>
<%--          dzisiaj--%>
<%--        </c:if>--%>
      </li>
      <li><span class="profile-attribute">Imię: </span><span id="firstName">${requestScope.userDetails.firstName}</span></li>
      <li><span class="profile-attribute">Nazwisko: </span><span id="lastName">${requestScope.userDetails.lastName}</span></li>
      <li><span class="profile-attribute">Płeć: </span><span id="gender">${requestScope.userDetails.gender}</span></li>
      <li><span class="profile-attribute">Wiek: </span>
        <span id="age">
          <c:if test="${requestScope.userDetails.age != 0}">
            ${requestScope.userDetails.age}
          </c:if>
        </span>
      </li>
      <li><span class="profile-attribute">Opis: </span><span id="description">${requestScope.userDetails.description}</span></li>
    </ul>
    <c:if test="${requestScope.username eq pageContext.request.userPrincipal.name}">
      <a href="${pageContext.request.contextPath}/profile/edit" id="edit-profile-button">Edytuj</a>
    </c:if>
  </section>
    <h2 class="added-discoveries-header">Dodane znaleziska:</h2>
    <%@ include file="../segments/discovery-list.jspf" %>
</main>

  <%@ include file="../segments/footer.jspf" %>
</div>
  <script src="../../script.js"></script>
</body>
</html>