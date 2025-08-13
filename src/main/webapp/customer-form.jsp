<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%-- The title will change depending on whether we are adding or editing --%>
    <title>${not empty customer ? 'Edit Customer' : 'Add New Customer'}</title>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.25em; }
        input { padding: 0.5em; width: 300px; }
        button { padding: 0.5em 1em; }
    </style>
</head>
<body>

<p><a href="${pageContext.request.contextPath}/customers">Back to Customer List</a></p>

<h1>${not empty customer ? 'Edit Customer' : 'Add New Customer'}</h1>

<form action="${pageContext.request.contextPath}/customers" method="post">

    <%-- This is the clever part. If we are editing, we include hidden fields --%>
    <c:if test="${not empty customer}">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="customerId" value="<c:out value='${customer.customerId}' />" />
    </c:if>

    <div class="form-group">
        <label for="accountNumber">Account Number:</label>
        <input type="text" id="accountNumber" name="accountNumber" value="<c:out value='${customer.accountNumber}' />" required>
    </div>
    <div class="form-group">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" value="<c:out value='${customer.fullName}' />" required>
    </div>
    <div class="form-group">
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<c:out value='${customer.address}' />">
    </div>
    <div class="form-group">
        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber" value="<c:out value='${customer.phoneNumber}' />">
    </div>

    <button type="submit">${not empty customer ? 'Update Customer' : 'Add Customer'}</button>
</form>

</body>
</html>