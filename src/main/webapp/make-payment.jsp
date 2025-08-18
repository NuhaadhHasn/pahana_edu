<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- THE FIX: These two lines are the missing imports --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Make a Payment</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .payment-container {
            max-width: 400px;
            border: 1px solid #ccc;
            padding: 2em;
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
    </style>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/customer-dashboard.jsp">Back to My Dashboard</a></p>

<div class="payment-container">
    <h2>Make an Online Payment</h2>
    <p><em>This is a UI simulation. No real payment will be processed.</em></p>
    <form action="${pageContext.request.contextPath}/customer-make-payment" method="post">
        <div class="form-group">
            <label for="billId">Select Bill to Pay:</label>
            <select id="billId" name="billId" onchange="updatePaymentAmount()" required>
                <option value="">-- Select an Unpaid Bill --</option>
                <c:forEach items="${unpaidBills}" var="bill">
                    <option value="${bill.billId}" data-amount="${bill.totalAmount}">
                        Bill #${bill.billId} - Rs.<fmt:formatNumber value="${bill.totalAmount}" type="number"
                                                                    minFractionDigits="2" maxFractionDigits="2"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="amount">Amount (Rs.):</label>
            <input type="text" id="amount" name="amount" readonly>
        </div>

        <div class="form-group">
            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" onchange="togglePaymentFields()">
                <option value="CREDIT_CARD">Credit Card</option>
                <option value="BANK_TRANSFER">Bank Transfer</option>
                <option value="CASH">Cash (Pay In-Store)</option>
            </select>
        </div>
        <div class="form-group" id="cardRefNumberField">
            <label for="cardNumber">Card/Reference Number:</label>
            <input type="text" id="cardNumber" name="cardNumber">
        </div>

        <button type="submit">Submit Payment</button>
    </form>
</div>

<script>
    function updatePaymentAmount() {
        const selectElement = document.getElementById('billId');
        const selectedOption = selectElement.options[selectElement.selectedIndex];
        const amount = selectedOption.dataset.amount;

        const amountInput = document.getElementById('amount');
        if (amount) {
            amountInput.value = parseFloat(amount).toFixed(2);
        } else {
            amountInput.value = '';
        }
    }

    function togglePaymentFields() {
        const paymentSelect = document.getElementById('paymentMethod');
        const cardRefNumberField = document.getElementById('cardRefNumberField');
        cardRefNumberField.style.display = (paymentSelect.value === 'CASH') ? 'none' : 'block';
    }

    togglePaymentFields()
</script>
</body>
</html>