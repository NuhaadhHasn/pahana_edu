<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Promotion Management</title>
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

<h1>Promotion Management</h1>

<p><a href="${pageContext.request.contextPath}/promotions?action=new">Add New Promotion</a></p>
<br>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Promo Code</th>
        <th>Discount</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${promotionList}" var="promo">
        <tr>
            <td><c:out value="${promo.promoId}"/></td>
            <td><c:out value="${promo.promoCode}"/></td>
            <td><fmt:formatNumber value="${promo.discountPercentage / 100}" type="percent"/></td>
            <td><c:out value="${promo.startDate}"/></td>
            <td><c:out value="${promo.endDate}"/></td>
            <td>${promo.active ? 'Active' : 'Inactive'}</td>
            <td>
                <a href="${pageContext.request.contextPath}/promotions?action=edit&id=${promo.promoId}">Edit</a>
                &nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/promotions?action=delete&id=${promo.promoId}"
                   onclick="return confirm('Are you sure you want to delete this promotion?');">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<br>
<p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>

</body>
</html>