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
        <h1>Menu</h1>
    </div>
    <div data-role="content" style="height:100%;">
        <ul data-role="listview" data-theme="g">
            <li><a href="<c:url value="/displayFoodList"/>">Consumed List</a></li>
            <li><a href="<c:url value="/editFoodEntry"/>">Add Consumed Food</a></li>
        </ul>
    </div>
    <div data-role="footer">
    </div>
</div>
</body>
</html>
