<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <title>Edytuj dane profilu - ReadStack</title>
    <%@ include file="../segments/stylesheets.jspf" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/edit-profile-form.css">
</head>
<body>
<div class="container">
    <%@include file="../segments/header.jspf" %>

    <form action="${pageContext.request.contextPath}/profile/edit" method="post" class="edit-form">
        <h2 class="edit-form-title">Edytuj dane profilu</h2>
        <c:if test="${requestScope.incorrectAge != null}">
            <p class="error">${requestScope.incorrectAge}</p>
        </c:if>
        <label class="label-placeholder" for="firstNameInput">Imię:</label>
        <input class="input" id="firstNameInput" name="firstName" placeholder="Imię" value="${requestScope.userDetails.firstName}">
        <label class="label-placeholder" for="lastNameInput">Nazwisko:</label>
        <input class="input" id="lastNameInput" name="lastName" placeholder="Nazwisko" value="${requestScope.userDetails.lastName}">
        <label class="label-placeholder">Płeć:</label>
<%--        <div class="input gender-container">--%>
            <c:forEach var="genderVar" items="${requestScope.genders}">
                <label><input class="gender-item" name="gender" type="radio" value="${genderVar}">${genderVar.name}</label>
            </c:forEach>
<%--        </div>--%>
        <label class="label-placeholder" for="ageInput">Wiek:</label>
        <input class="input" id="ageInput" name="age" placeholder="Wiek" type="number" value="${requestScope.userDetails.age}">
        <label class="label-placeholder" for="description-textarea">Opis:</label>
        <textarea class="input" id="description-textarea" name="description" placeholder="Opis">${requestScope.userDetails.description}</textarea>
        <button type="submit" class="input edit-form-button">Zapisz</button>
    </form>

    <%@include file="../segments/footer.jspf" %>
</div>
</body>
</html>
