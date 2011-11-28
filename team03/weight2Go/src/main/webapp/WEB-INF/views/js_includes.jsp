<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta name="HandheldFriendly" content="true" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="<c:url value="/resources/jqueryMobile/jquery-1.6.2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/jqueryMobile/jquery.mobile-1.0b2.js"/>" type="text/javascript"></script>
 <style>
      @media screen and (orientation: landscape) {
        html, body {
          width: 100%;
        }

        .content h1.landscape { display: block }
        .content h1.portrait { display: none }
      }
      @media screen and (orientation: portrait) {
        html, body {
          width: 100%;
        }

        .content .landscape { display: none }
        .content .portrait { display: block }
      }
    </style>
<link rel="stylesheet" href="<c:url value="/resources/jqueryMobile/jquery.mobile-1.0b2.min.css"/>"/>
