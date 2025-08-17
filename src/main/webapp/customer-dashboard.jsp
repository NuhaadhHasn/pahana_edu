<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Dashboard</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }
    </style>
</head>
<body>

<h1>Welcome, <c:out value="${sessionScope.user.fullName}"/>!</h1>
<p>This is your personal dashboard.</p>
<p><a href="${pageContext.request.contextPath}/logout">Logout</a></p>
<hr>

<h3>Available Actions:</h3>
<ul>
    <li><a href="${pageContext.request.contextPath}/my-bills">View My Bill History</a></li>
    <li>View Available Items (Coming Soon)</li>
    <li>Make a Payment (Coming Soon)</li>
</ul>

</body>
</html>