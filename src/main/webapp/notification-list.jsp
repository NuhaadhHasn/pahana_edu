<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Notification History</title>
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

        .read-true {
            color: #888;
        }

        /* Style for read messages */
    </style>
</head>
<body>

<h1>Notification History</h1>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>
<br>

<table>
    <thead>
    <tr>
        <th>Date / Time</th>
        <th>Message</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${notificationList}" var="notification">
        <%-- Apply a CSS class if the message has been read --%>
        <tr class="read-${notification.read}">
            <td><fmt:formatDate value="${notification.createdAtAsDate}" type="both" dateStyle="medium"
                                timeStyle="short"/></td>
            <td><c:out value="${notification.message}"/></td>
            <td>${notification.read ? 'Read' : 'Unread'}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>