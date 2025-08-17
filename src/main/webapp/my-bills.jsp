<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>My Bill History</title>
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

<h1>My Bill History</h1>
<p><a href="${pageContext.request.contextPath}/customer-dashboard.jsp">Back to My Dashboard</a></p>
<br>

<c:if test="${empty billList}">
    <p>You have no bills in your history yet.</p>
</c:if>

<c:if test="${not empty billList}">
    <table>
        <thead>
        <tr>
            <th>Bill ID</th>
            <th>Date</th>
            <th>Total Amount</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${billList}" var="bill">
            <tr>
                <td><c:out value="${bill.billId}"/></td>
                <td><fmt:formatDate value="${bill.billDateAsDate}" type="both" dateStyle="medium"
                                    timeStyle="short"/></td>
                <td><fmt:formatNumber value="${bill.totalAmount}" type="currency" currencySymbol="Rs."/></td>
                <td><c:out value="${bill.status}"/></td>
                <td>
                    <a href="#">View Details</a> <%-- Can be implemented later --%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>