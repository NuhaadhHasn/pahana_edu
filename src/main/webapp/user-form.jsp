<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add New User</title>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.25em; font-weight: bold; }
        input, select { padding: 0.5em; width: 300px; box-sizing: border-box; }
        button { padding: 0.5em 1em; }
        .error-message { color: red; }
    </style>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>

<h1>Add New User</h1>

<c:if test="${not empty errorMessage}">
    <p class="error-message"><c:out value="${errorMessage}" /></p>
</c:if>

<form action="${pageContext.request.contextPath}/users" method="post">
    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div class="form-group">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required>
    </div>
    <div class="form-group">
        <label for="role">Role:</label>
        <select id="role" name="role" required>
            <option value="STAFF">Staff</option>
            <option value="ADMIN">Admin</option>
            <option value="CUSTOMER">Customer</option>
        </select>
    </div>
    <button type="submit">Add User</button>
</form>

</body>
</html>