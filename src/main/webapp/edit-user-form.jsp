<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit User</title>
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
<h1>Edit User</h1>

<form action="${pageContext.request.contextPath}/users" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="userId" value="<c:out value='${user.userId}' />"/>

    <div><label>Username:</label><input type="text" name="username" value="<c:out value='${user.username}' />" required>
    </div>
    <div>
        <label>New Password (leave blank to keep unchanged):</label>
        <input type="password" name="newPassword">
    </div>
    <div><label>Full Name:</label><input type="text" name="fullName" value="<c:out value='${user.fullName}' />"
                                         required></div>
    <div>
        <label>Role:</label>
        <select name="role">
            <option value="STAFF" ${user.role == 'STAFF' ? 'selected' : ''}>Staff</option>
            <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
            <option value="CUSTOMER" ${user.role == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
        </select>
    </div>

    <%-- We only show customer fields if the user IS a customer --%>
    <c:if test="${user.role == 'CUSTOMER'}">
        <div id="customer-fields">
            <hr>
            <p><strong>Customer Specific Details:</strong></p>
                <%-- We need to fetch and pass the customer object to pre-fill these fields --%>
            <div><label>Account Number:</label><input type="text" name="accountNumber"
                                                      value="<c:out value='${customer.accountNumber}' />"></div>
            <div><label>Address:</label><input type="text" name="address" value="<c:out value='${customer.address}' />">
            </div>
            <div><label>Phone Number:</label><input type="text" name="phoneNumber"
                                                    value="<c:out value='${customer.phoneNumber}' />"></div>
        </div>
    </c:if>

    <button type="submit">Update User</button>
</form>
</body>
</html>