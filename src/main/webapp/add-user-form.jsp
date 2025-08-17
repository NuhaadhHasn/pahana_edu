<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add New User</title>
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
<h1>Add New User</h1>

<c:if test="${not empty errorMessage}">
    <p style="color:red;"><c:out value="${errorMessage}"/></p>
</c:if>

<form action="${pageContext.request.contextPath}/users" method="post">
    <%-- We explicitly set the action to "add" --%>
    <input type="hidden" name="action" value="add"/>

    <div><label>Username:</label><input type="text" name="username" required></div>
    <div><label>Password:</label><input type="password" name="password" required></div>
    <div><label>Full Name:</label><input type="text" name="fullName" required></div>
    <div>
        <label>Role:</label>
        <select id="role" name="role" onchange="toggleCustomerFields()">
            <option value="STAFF">Staff</option>
            <option value="ADMIN">Admin</option>
            <option value="CUSTOMER">Customer</option>
        </select>
    </div>

    <div id="customer-fields" style="display: none;">
        <hr>
        <p><strong>Customer Specific Details:</strong></p>
        <div><label>Account Number:</label><input type="text" name="accountNumber"></div>
        <div><label>Address:</label><input type="text" name="address"></div>
        <div><label>Phone Number:</label><input type="text" name="phoneNumber"></div>
    </div>

    <button type="submit">Add User</button>
</form>

<script>
    function toggleCustomerFields() {
        const roleSelect = document.getElementById('role');
        const customerFields = document.getElementById('customer-fields');
        customerFields.style.display = (roleSelect.value === 'CUSTOMER') ? 'block' : 'none';
    }

    toggleCustomerFields();
</script>
</body>
</html>