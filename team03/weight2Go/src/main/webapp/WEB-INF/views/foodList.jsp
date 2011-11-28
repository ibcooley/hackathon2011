<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page session="false" %>
<html>
<head>
    <title>Weight 2 Go</title>
    <%@include file="js_includes.jsp" %>
</head>
<body>
<div data-role="page" id="login">
    <div data-role="header">
        <h1>Food List</h1>
        <%--<p><fmt:formatDate pattern="EEE, d MMM yyyy" value="${today}"/></p>--%>
    </div>
    <div data-role="content" style="height:100%;">
        <il data-role="listview" data-theme="g">
            <li data-role="list-divider">Breakfast</li>
            <c:forEach var="userFood" items="${userFoods}">
                <c:if test="${userFood.mealType eq 'breakfast'}">
                    <li><a href="<c:url value="/editFoodEntry?userFoodId=${userFood.id}"/>"
                           data-ajax="false">
                        <h3>${userFood.food.name}</h3>

                        <p>Calories: ${userFood.food.caloriesPerServing}</p></a>
                    </li>
                </c:if>
            </c:forEach>
            <li data-role="list-divider">Lunch</li>
            <c:forEach var="userFood" items="${userFoods}">
                <c:if test="${userFood.mealType eq 'lunch'}">
                    <li><a href="<c:url value="/editUserFood?userFoodId=${userFood.id}"/>"
                           data-ajax="false">
                        <h3>${userFood.food.name}</h3>

                        <p>Calories: ${userFood.food.caloriesPerServing}</p></a>
                    </li>
                </c:if>
            </c:forEach>
            <li data-role="list-divider">Dinner</li>
            <c:forEach var="userFood" items="${userFoods}">
                <c:if test="${userFood.mealType eq 'dinner'}">
                    <li><a href="<c:url value="/editUserFood?userFoodId=${userFood.id}"/>"
                           data-ajax="false">
                        <h3>${userFood.food.name}</h3>

                        <p>Calories: ${userFood.food.caloriesPerServing}</p></a>
                    </li>
                </c:if>
            </c:forEach>
            <li data-role="list-divider">Snacks</li>
            <c:forEach var="userFood" items="${userFoods}">
                <c:if test="${userFood.mealType eq 'snacks'}">
                    <li><a href="<c:url value="/editUserFood?userFoodId=${userFood.id}"/>"
                           data-ajax="false">
                        <h3>${userFood.food.name}</h3>

                        <p>Calories: ${userFood.food.caloriesPerServing}</p></a>
                    </li>
                </c:if>
            </c:forEach>
        </il>
    </div>
    <div data-role="footer">
    </div>
</div>
</body>
</html>