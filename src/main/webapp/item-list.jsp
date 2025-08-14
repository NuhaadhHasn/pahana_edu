<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Item List</title>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 0.5em; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>

<h1>Pahana Edu - Item Master List</h1>

<p><a href="${pageContext.request.contextPath}/item-form.jsp">Add New Item</a></p>
<br>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Item Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Stock Quantity</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${itemList}" var="item">
        <tr>
            <td><c:out value="${item.itemId}" /></td>
            <td><c:out value="${item.itemName}" /></td>
            <td><c:out value="${item.description}" /></td>
            <td><c:out value="${String.format('%.2f', item.price)}" /></td> <%-- Format price to 2 decimal places --%>
            <td><c:out value="${item.stockQuantity}" /></td>
            <td>
                <a href="${pageContext.request.contextPath}/items?action=edit&id=${item.itemId}">Edit</a>
                &nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/items?action=delete&id=${item.itemId}"
                   onclick="return confirm('Are you sure you want to delete this item?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>