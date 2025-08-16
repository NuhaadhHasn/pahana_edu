<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create New Bill</title>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.25em; }
        select, input { padding: 0.5em; }
        table { width: 100%; border-collapse: collapse; margin-top: 1em; }
        th, td { border: 1px solid #ccc; padding: 0.5em; text-align: left; }
    </style>
</head>
<body>
<h1>Create New Bill</h1>

<form action="${pageContext.request.contextPath}/billing_old" method="post">
    <div class="form-group">
        <label for="customerId">Select Customer:</label>
        <select id="customerId" name="customerId" required>
            <c:forEach items="${customers}" var="customer">
                <option value="${customer.customerId}">${customer.fullName} (${customer.accountNumber})</option>
            </c:forEach>
        </select>
    </div>

    <h3>Select Items</h3>
    <table>
        <thead>
        <tr>
            <th>Item Name</th>
            <th>Price</th>
            <th>Available Stock</th>
            <th>Quantity to Purchase</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${items}" var="item">
            <tr>
                <td>
                    <c:out value="${item.itemName}" />
                        <%-- Hidden field to submit the ID of each item --%>
                    <input type="hidden" name="itemIds" value="${item.itemId}" />
                </td>
                <td><c:out value="${String.format('%.2f', item.price)}" /></td>
                <td><c:out value="${item.stockQuantity}" /></td>
                <td>
                        <%-- Input for quantity. Max is the available stock. --%>
                    <input type="number" name="quantities" min="0" max="${item.stockQuantity}" value="0" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <br>
    <button type="submit">Generate Bill</button>
</form>

<br>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>
</body>
</html>