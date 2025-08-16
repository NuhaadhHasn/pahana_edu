<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%-- Import formatting library for dates/numbers --%>
<html>
<head>
    <title>View Bill</title>
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .bill-container { border: 1px solid #000; padding: 2em; width: 600px; margin: 0 auto; }
        .bill-header, .bill-footer { text-align: center; margin-bottom: 2em; }
        .customer-info { margin-bottom: 2em; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 2em; }
        th, td { padding: 0.5em; text-align: left; }
        .totals-table td { border-top: 1px solid #000; }
        .text-right { text-align: right; }
    </style>
</head>
<body>

<div class="bill-container">
    <div class="bill-header">
        <h2>Pahana Edu Bookshop</h2>
        <p>Official Bill / Invoice</p>
    </div>

    <div class="customer-info">
        <strong>Bill To:</strong> <c:out value="${customer.fullName}" /><br>
        <strong>Account No:</strong> <c:out value="${customer.accountNumber}" /><br>
        <strong>Address:</strong> <c:out value="${customer.address}" /><br>
        <strong>Date:</strong> <fmt:formatDate value="${bill.billDateAsDate}" type="both" dateStyle="long" timeStyle="short" />
    </div>

    <table>
        <thead>
        <tr>
            <th>Item Description</th>
            <th class="text-right">Quantity</th>
            <th class="text-right">Unit Price</th>
            <th class="text-right">Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bill.billItems}" var="billItem">
            <tr>
                <td><c:out value="${billItem.itemName}" /></td>
                <td class="text-right"><c:out value="${billItem.quantity}" /></td>
                <td class="text-right"><fmt:formatNumber value="${billItem.priceAtPurchase}" type="currency" currencySymbol="Rs." /></td>
                <td class="text-right"><fmt:formatNumber value="${billItem.priceAtPurchase * billItem.quantity}" type="currency" currencySymbol="Rs." /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="totals-table">
        <tr>
            <td>Subtotal</td>
            <td class="text-right"><fmt:formatNumber value="${bill.subTotal}" type="currency" currencySymbol="Rs." /></td>
        </tr>
        <c:if test="${bill.taxRateApplied > 0}">
            <tr>
                <td>Tax (<fmt:formatNumber value="${bill.taxRateApplied}" type="percent" />)</td>
                <td class="text-right"><fmt:formatNumber value="${bill.subTotal * bill.taxRateApplied}" type="currency" currencySymbol="Rs." /></td>
            </tr>
        </c:if>
        <tr>
            <td>Service Charge</td>
            <td class="text-right"><fmt:formatNumber value="100.00" type="currency" currencySymbol="Rs." /></td>
        </tr>
        <tr>
            <td><strong>Total Amount</strong></td>
            <td class="text-right"><strong><fmt:formatNumber value="${bill.totalAmount}" type="currency" currencySymbol="Rs." /></strong></td>
        </tr>
    </table>

    <div class="bill-footer">
        <p>Thank you for your business!</p>
    </div>
</div>

<div style="text-align: center; margin-top: 2em;">
    <p><a href="${pageContext.request.contextPath}/billing">Create Another Bill</a></p>
    <p><a href="${pageContext.request.contextPath}/dashboard.jsp">Back to Dashboard</a></p>
</div>

</body>
</html>