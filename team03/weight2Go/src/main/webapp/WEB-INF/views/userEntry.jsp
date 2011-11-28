<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page session="false" %>
<html>
<head>
    <title>Weight 2 Go</title>
    <%@include file="js_includes.jsp" %>
</head>
<body>
<div data-role="page" id="login">
    <div data-role="header">
        <h1>Personal Information</h1>
    </div>
    <div data-role="content" style="height:100%;">
        <spring:url var="action" value="/savePersonalInformation"/>
        <forms:form action="${action}" method="POST" data-ajax="false">
            <div data-role="fieldcontain">
                <label for="gender" class="select">Choose shipping method:</label>
                <select name="gender" id="gender">
                    <option value="female">Female</option>
                    <option value="male">Male</option>
                </select>
            </div>
            <div data-role="fieldcontain">
                <label for="dateOfBirth">Date of birth:</label>
                <input type="text" name="dateOfBirth" id="dateOfBirth" value=""/>
            </div>
            <div data-role="fieldcontain">
                <label for="weight">Weight:</label>
                <input type="text" name="weight" id="weight" value=""/>
            </div>

            <div data-role="fieldcontain">
                <label for="height">Height:</label>
                <input type="text" name="height" id="height" value=""/>
            </div>

            <div data-role="fieldcontain">
                <label for="activityLevel" class="select">Activity Level:</label>
                <select name="activityLevel" id="activityLevel">
                    <option value="nonActive">Not Active</option>
                    <option value="lowActive">Low Active</option>
                    <option value="active">Active</option>
                    <option value="highActive">High Active</option>
                </select>
            </div>

            <div data-role="fieldcontain">
                <label for="targetWeight">Target Weight:</label>
                <input type="text" name="targetWeight" id="targetWeight" value=""/>
            </div>
            <div data-role="fieldcontain">
                <label for="targetDate">Target Date:</label>
                <input type="text" name="targetDate" id="targetDate" value=""/>
            </div>
            <div data-role="fieldcontain">
                <label for="targetCalories">Target Daily Caloric Intake:</label>
                <input type="text" name="targetCalories" id="targetCalories" value=""/>
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