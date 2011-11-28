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
        <h1>Weight2Go Login</h1>
    </div>
    <div data-role="content">
        <spring:url var="action" value="/login"/>
        <forms:form action="${action}" method="POST" data-ajax="false">
            <div data-role="fieldcontain">
                <label for="userName">User ID:</label>
                <input name="userName" type="text" id="userName"/>
            </div>

            <div data-role="fieldcontain">
                <label for="password">Password:</label>
                <input name="password" type="password" id="password"/>
            </div>

            <div class="ui-grid-a">
                <div class="ui-block-a"><a href="#" onclick='$( "form" )[ 0 ].reset();' data-role="button">Clear</a>
                </div>
                <div class="ui-block-b">
                    <button type="submit" data-theme="b">Login</button>
                </div>
            </div>
        </forms:form>
        <a href="<c:url value="/userEntry"/>" data-role="button" data-icon="plus">New User</a>
    </div>
    <div data-role="footer">
    </div>
</div>
</body>
</html>
