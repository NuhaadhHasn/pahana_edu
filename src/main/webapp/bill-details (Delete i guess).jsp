<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Record Payment</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .form-container {
            width: 500px;
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
            width: 100%;
            box-sizing: border-box;
        }

        button {
            padding: 0.5em 1em;
        }

        .bill-summary {
            border: 1px solid #eee;
            padding: 1em;
            margin-bottom: 2em;
            background-color: #f9f9f9;
        }

        .summary-table {
            width: 100%;
        }

        .summary-table td {
            padding: 4px 0;
        }

        .text-right {
            text-align: right;
        }

        .total-row {
            font-weight: bold;
            border-top: 1px solid #ccc;
        }
    </style>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/bill-history">Back to Bill History</a></p>

<h1>Record Payment for Bill #${bill.billId}</h1>

<div class="bill-summary">
    <p><strong>Customer:</strong> <c:out value="${bill.customer.fullName}"/></p>
    <p><strong>Current Status:</strong> <c:out value="${bill.status}"/></p>

    <h4>Bill Summary:</h4>


    <table class="summary-table">
        <thead>
        <tr>
            <th>Item Description</th>
            <th class="text-right">Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bill.billItems}" var="item">
            <tr>
                <td><c:out value="${item.itemName}"/> (x <c:out value="${item.quantity}"/>)</td>
                <td class="text-right"><fmt:formatNumber value="${item.priceAtPurchase * item.quantity}" type="currency"
                                                         currencySymbol="Rs."/></td>
            </tr>
        </c:forEach>

        <tr style="border-top: 1px solid #ccc;">
            <td>Subtotal</td>
            <td class="text-right"><fmt:formatNumber value="${bill.subTotal}" type="currency"
                                                     currencySymbol="Rs."/></td>
        </tr>

        <c:if test="${bill.discountAmount > 0}">
            <tr>
                <td>Discount</td>
                <td class="text-right">- <fmt:formatNumber value="${bill.discountAmount}" type="currency"
                                                           currencySymbol="Rs."/></td>
            </tr>
        </c:if>

        <c:if test="${bill.taxRateApplied > 0}">
            <tr>
                <td>Tax (<fmt:formatNumber value="${bill.taxRateApplied}" type="percent"/>)</td>
                <td class="text-right">+ <fmt:formatNumber value="${bill.subTotal * bill.taxRateApplied}"
                                                           type="currency" currencySymbol="Rs."/></td>
            </tr>
        </c:if>

        <c:if test="${bill.serviceCharge > 0}">
            <tr>
                <td>Service Charge</td>
                <td class="text-right">+ <fmt:formatNumber value="${bill.serviceCharge}" type="currency"
                                                           currencySymbol="Rs."/></td>
            </tr>
        </c:if>

        <tr class="total-row">
            <td>Total Amount Due</td>
            <td class="text-right"><fmt:formatNumber value="${bill.totalAmount}" type="currency"
                                                     currencySymbol="Rs."/></td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>