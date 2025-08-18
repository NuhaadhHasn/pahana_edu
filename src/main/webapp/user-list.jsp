<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Management</title>
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

<h1>User Management</h1>

<p><a href="${pageContext.request.contextPath}/users?action=new">Add New User</a></p>
<br>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Username</th>
        <th>Full Name</th>
        <th>Role</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${userList}" var="user">
        <tr>
            <td><c:out value="${user.userId}"/></td>
            <td><c:out value="${user.username}"/></td>
            <td><c:out value="${user.fullName}"/></td>
            <td><c:out value="${user.role}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.userId}">Edit</a>
                &nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/users?action=delete&id=${user.userId}"
                   onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<p>
    <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
        <a href="${pageContext.request.contextPath}/customer-dashboard.jsp">Back to My Dashboard</a>
    </c:if>
    <c:if test="${sessionScope.user.role != 'CUSTOMER'}">
        <a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a>
    </c:if>
</p>

</body>
</html>