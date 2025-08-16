<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${not empty user ? 'Edit User' : 'Add New User'}</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .form-group {
            margin-bottom: 1em;
        }

        label {
            display: block;
            margin-bottom: 0.25em;
            font-weight: bold;
        }

        input, select {
            padding: 0.5em;
            width: 300px;
            box-sizing: border-box;
        }

        button {
            padding: 0.5em 1em;
        }

        .error-message {
            color: red;
        }

        .note {
            font-size: 0.8em;
            color: #555;
        }
    </style>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/users">Back to User List</a></p>

<h1>${not empty user ? 'Edit User' : 'Add New User'}</h1>

<c:if test="${not empty errorMessage}">
    <p class="error-message"><c:out value="${errorMessage}"/></p>
</c:if>

<form action="${pageContext.request.contextPath}/users" method="post">

    <c:if test="${not empty user}">
        <input type="hidden" name="action" value="update"/>
        <input type="hidden" name="userId" value="<c:out value='${user.userId}' />"/>
    </c:if>

    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<c:out value='${user.username}' />" required>
    </div>

    <%-- Password field is only required when adding a new user --%>
    <c:if test="${empty user}">
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
    </c:if>
    <c:if test="${not empty user}">
        <p class="note">Password cannot be changed from this form.</p>
    </c:if>

    <div class="form-group">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" value="<c:out value='${user.fullName}' />" required>
    </div>
    <div class="form-group">
        <label for="role">Role:</label>
        <select id="role" name="role" required>
            <%-- Use JSTL to select the correct option when editing --%>
            <option value="STAFF" ${user.role == 'STAFF' ? 'selected' : ''}>Staff</option>
            <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
            <option value="CUSTOMER" ${user.role == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
        </select>
    </div>

    <button type="submit">${not empty user ? 'Update User' : 'Add User'}</button>
</form>

</body>
</html>