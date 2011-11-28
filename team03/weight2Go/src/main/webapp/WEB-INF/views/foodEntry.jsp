<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>Weight 2 Go</title>
    <%@include file="js_includes.jsp" %>
</head>
<body>
<div data-role="page" id="login">
    <div data-role="header">
        <h1>Food Entry</h1>
    </div>
    <div data-role="content" style="height:100%;">
        <spring:url var="action" value="/saveFoodEntry"/>
        <forms:form modelAttribute="userFood" action="${action}" method="POST" data-ajax="false">
            <forms:hidden path="id" name="userFoodId"/>
            <div data-role="fieldcontain">
                <label for="mealType" class="select">Select Meal:</label>
                <form:select path="mealType" id="mealType">
                    <form:option value="breakfast">Breakfast</form:option>
                    <form:option value="lunch">Lunch</form:option>
                    <form:option value="dinner">Dinner</form:option>
                    <form:option value="snack">Snack</form:option>
                </form:select>
            </div>

            <div data-role="fieldcontain">
                <label for="foodName">Name:</label>
                <form:input type="text" path="food.name" id="foodName"/>
            </div>

            <div data-role="fieldcontain">
                <label for="foodAmount">Amount:</label>
                <form:input type="text" path="amount" id="foodAmount"/>
            </div>

            <div data-role="fieldcontain">
                <label for="calories">Calories:</label>
                <form:input type="text" path="food.caloriesPerServing" id="calories"/>
            </div>

            <div data-role="fieldcontain">
                <label for="fat">Fat grams:</label>
                <form:input type="text" path="food.fat" id="fat"/>
            </div>

            <div data-role="fieldcontain">
                <label for="carbs">Carb grams:</label>
                <form:input type="text" path="food.carbs" id="carbs"/>
            </div>
            <div data-role="fieldcontain">
                <label for="protein">Protein grams:</label>
                <form:input type="text" path="food.protein" id="protein"/>
            </div>
            <div class="ui-grid-a">
                <div class="ui-block-a"><a href="#" onclick='$( "form" )[ 0 ].reset();' data-role="button">Clear</a>
                </div>
                <div class="ui-block-b">
                    <button type="submit" data-theme="b">Save</button>
                </div>
            </div>
        </forms:form>
    </div>
    <div data-role="footer">
    </div>
</div>
</body>
</html>