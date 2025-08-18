<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login Attempt History</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 0.5em;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>Login Attempt History</h1>
<p>
    <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
        <a href="${pageContext.request.contextPath}/customer-dashboard.jsp">Back to My Dashboard</a>
    </c:if>
    <c:if test="${sessionScope.user.role != 'CUSTOMER'}">
        <a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a>
    </c:if>
</p>
<br>
<table>
    <thead>
    <tr>
        <th>Log ID</th>
        <th>User ID</th>
        <th>Time</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${historyList}" var="log">
        <tr>
            <td><c:out value="${log.logId}"/></td>
            <td><c:out value="${log.userId}"/></td>
            <td><fmt:formatDate value="${log.loginTimeAsDate}" type="both" dateStyle="medium" timeStyle="long"/></td>
            <td><c:out value="${log.status}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>