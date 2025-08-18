<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Business Reports</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .report-container {
            max-width: 800px;
        }

        .stat-card {
            border: 1px solid #ccc;
            padding: 1em;
            margin-bottom: 2em;
            background-color: #f9f9f9;
        }

        .stat-number {
            font-size: 2em;
            font-weight: bold;
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
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>
<h1>Business Reports</h1>

<div class="report-container">
    <div class="stat-card">
        <div>Total Sales (from Paid Bills)</div>
        <div class="stat-number">
            <fmt:formatNumber value="${totalSales}" type="currency" currencySymbol="Rs."/>
        </div>
    </div>

    <h3>Top 5 Best-Selling Items (by Quantity)</h3>
    <table>
        <thead>
        <tr>
            <th>Item Name</th>
            <th>Total Quantity Sold</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${topItems}" var="item">
            <tr>
                <td><c:out value="${item.itemName}"/></td>
                <td><c:out value="${item.totalQuantity}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%-- We can add more reports like Top Customers here in the future --%>
</div>

</body>
</html>