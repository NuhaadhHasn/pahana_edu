<%@ include file="/WEB-INF/jspf/header.jspf" %>
<style>
    .invoice-card {
        background: rgba(30, 30, 45, 0.85);
        backdrop-filter: blur(15px);
        -webkit-backdrop-filter: blur(15px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        padding: 2.5rem;
        box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
        margin-bottom: 2rem;
    }

    .summary-table {
        width: 100%;
    }

    .summary-table td {
        padding: 0.5rem 0;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    }

    .summary-table tr:last-child td {
        border-bottom: none;
    }

    .text-right {
        text-align: right;
    }

    .total-row {
        font-size: 1.25rem;
        font-weight: bold;
        border-top: 2px solid rgba(255, 255, 255, 0.2);
    }
</style>

<div class="main-content">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-9">

            <%-- Page Header --%>
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/bill-history" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to Bill History
                </a>
                <h1 class="mb-0">Record Payment for Bill #${bill.billId}</h1>
            </div>

            <%-- Bill Summary Card --%>
            <div class="invoice-card">
                <div class="row">
                    <div class="col-md-6">
                        <p class="mb-1"><strong>Customer:</strong> <c:out value="${bill.customer.fullName}"/></p>
                        <p><strong>Status:</strong> <span class="badge bg-warning text-dark">ISSUED</span></p>
                    </div>
                </div>
                <hr style="border-color: rgba(255, 255, 255, 0.1);">
                <h5 class="card-title mb-3">Bill Summary:</h5>
                <table class="summary-table">
                    <tbody>
                    <c:forEach items="${bill.billItems}" var="item">
                        <tr>
                            <td><c:out value="${item.itemName}"/> (x <c:out value="${item.quantity}"/>)</td>
                            <td class="text-end"><fmt:formatNumber value="${item.priceAtPurchase * item.quantity}"
                                                                   type="currency" currencySymbol="Rs."/></td>
                        </tr>
                    </c:forEach>
                    <tr style="border-top: 1px solid rgba(255,255,255,0.2);">
                        <td>Subtotal</td>
                        <td class="text-end"><fmt:formatNumber value="${bill.subTotal}" type="currency"
                                                               currencySymbol="Rs."/></td>
                    </tr>
                    <c:if test="${bill.discountAmount > 0}">
                        <tr>
                            <td>Discount</td>
                            <td class="text-end">- <fmt:formatNumber value="${bill.discountAmount}" type="currency"
                                                                     currencySymbol="Rs."/></td>
                        </tr>
                    </c:if>
                    <c:if test="${bill.taxRateApplied > 0}">
                        <tr>
                            <td>Tax (<fmt:formatNumber value="${bill.taxRateApplied}" type="percent"/>)</td>
                            <td class="text-end">+ <fmt:formatNumber value="${bill.subTotal * bill.taxRateApplied}"
                                                                     type="currency" currencySymbol="Rs."/></td>
                        </tr>
                    </c:if>
                    <c:if test="${bill.serviceCharge > 0}">
                        <tr>
                            <td>Service Charge</td>
                            <td class="text-end">+ <fmt:formatNumber value="${bill.serviceCharge}" type="currency"
                                                                     currencySymbol="Rs."/></td>
                        </tr>
                    </c:if>
                    <tr class="total-row">
                        <td><strong>Total Amount Due</strong></td>
                        <td class="text-end"><strong><fmt:formatNumber value="${bill.totalAmount}" type="currency"
                                                                       currencySymbol="Rs."/></strong></td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <%-- Payment Form --%>
            <h2 class="h3">Enter Payment Details</h2>
            <form action="${pageContext.request.contextPath}/record-payment" method="post">
                <input type="hidden" name="billId" value="${bill.billId}">
                <div class="mb-3">
                    <label for="amount" class="form-label">Payment Amount:</label>
                    <input type="number" class="form-control" id="amount" name="amount" value="${bill.totalAmount}"
                           step="0.01" min="0.01" required>
                </div>
                <div class="mb-3">
                    <label for="paymentMethod" class="form-label">Payment Method:</label>
                    <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                        <option value="CASH">Cash</option>
                        <option value="CREDIT_CARD">Credit Card</option>
                        <option value="BANK_TRANSFER">Bank Transfer</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary"><i class="bi bi-check2-circle me-2"></i>Confirm Payment
                </button>
            </form>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>