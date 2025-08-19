<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">

            <%-- Page Header --%>
            <div class="mb-4">
                <%-- Role-aware back link --%>
                <c:choose>
                    <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                        <a href="${pageContext.request.contextPath}/customer-dashboard.jsp"
                           class="btn btn-sm btn-outline-info mb-2">
                            <i class="bi bi-arrow-left"></i> Back to My Dashboard
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/dashboard.jsp"
                           class="btn btn-sm btn-outline-info mb-2">
                            <i class="bi bi-arrow-left"></i> Back to Dashboard
                        </a>
                    </c:otherwise>
                </c:choose>
                <h1 class="mb-0">Make a Payment</h1>
                <p class="text-muted">This is a UI simulation. No real payment will be processed.</p>
            </div>

            <div class="card card-body">
                <form action="${pageContext.request.contextPath}/customer-make-payment" method="post">
                    <div class="mb-3">
                        <label for="billId" class="form-label">Select Bill to Pay:</label>
                        <select class="form-select" id="billId" name="billId" onchange="updatePaymentAmount()" required>
                            <option value="">-- Select an Unpaid Bill --</option>
                            <c:forEach items="${unpaidBills}" var="bill">
                                <option value="${bill.billId}" data-amount="${bill.totalAmount}">
                                    Bill #${bill.billId} - <fmt:formatNumber value="${bill.totalAmount}" type="currency"
                                                                             currencySymbol="Rs."/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="amount" class="form-label">Amount (Rs.):</label>
                        <input type="text" class="form-control" id="amount" name="amount" readonly>
                    </div>
                    <div class="mb-3">
                        <label for="paymentMethod" class="form-label">Payment Method:</label>
                        <select class="form-select" id="paymentMethod" name="paymentMethod"
                                onchange="togglePaymentFields()">
                            <option value="CREDIT_CARD">Credit Card</option>
                            <option value="BANK_TRANSFER">Bank Transfer</option>
                            <option value="CASH">Cash (Pay In-Store)</option>
                        </select>
                    </div>
                    <div class="mb-3" id="cardRefNumberField">
                        <label for="cardNumber" class="form-label">Card/Reference Number:</label>
                        <input type="text" class="form-control" id="cardNumber" name="cardNumber">
                    </div>
                    <button type="submit" class="btn btn-primary"><i class="bi bi-credit-card-2-front-fill me-2"></i>Submit
                        Payment
                    </button>
                </form>
            </div>
        </div>
    </div>
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

<%@ include file="/WEB-INF/jspf/footer.jspf" %>