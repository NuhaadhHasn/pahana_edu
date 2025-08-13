<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- Import JSTL for loops and logic --%>
<html>
<head>
    <title>Customer List</title>
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

<h1>Pahana Edu - Customer List</h1>

<p><a href="${pageContext.request.contextPath}/customer-form.jsp">Add New Customer</a></p>
<br>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Account Number</th>
        <th>Full Name</th>
        <th>Address</th>
        <th>Phone Number</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%--
        This is the core logic.
        It loops through the 'customerList' attribute that our CustomerController sent.
        'var="customer"' creates a temporary variable for each item in the list.
    --%>
    <c:forEach items="${customerList}" var="customer">
        <tr>
                <%-- We use Expression Language to get the properties of each customer object --%>
            <td><c:out value="${customer.customerId}"/></td>
            <td><c:out value="${customer.accountNumber}"/></td>
            <td><c:out value="${customer.fullName}"/></td>
            <td><c:out value="${customer.address}"/></td>
            <td><c:out value="${customer.phoneNumber}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/customers?action=edit&id=${customer.customerId}">Edit</a>
                &nbsp;|&nbsp; <%-- This adds a little separator --%>
                <a href="${pageContext.request.contextPath}/customers?action=delete&id=${customer.customerId}"
                   onclick="return confirm('Are you sure you want to delete this customer?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>