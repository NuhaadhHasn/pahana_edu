<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Available Books</title>
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

<h1>Available Books at Pahana Edu</h1>
<p><a href="${pageContext.request.contextPath}/customer-dashboard.jsp">Back to My Dashboard</a></p>
<br>

<table>
    <thead>
    <tr>
        <th>Title</th>
        <th>Description</th>
        <th style="text-align: right;">Price</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${itemList}" var="item">
        <tr>
            <td><c:out value="${item.itemName}"/></td>
            <td><c:out value="${item.description}"/></td>
            <td style="text-align: right;"><fmt:formatNumber value="${item.price}" type="currency"
                                                             currencySymbol="Rs."/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>