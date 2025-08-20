<%@ include file="/WEB-INF/jspf/header.jspf" %>

<style>
    .invoice-card {
        background: rgba(30, 30, 45, 0.85); /* Darker, less transparent glass */
        backdrop-filter: blur(15px);
        -webkit-backdrop-filter: blur(15px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        padding: 2.5rem;
        box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
    }

    .invoice-header {
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        padding-bottom: 1.5rem;
    }

    .invoice-header h2 {
        font-weight: 300; /* Lighter font weight for a modern look */
        letter-spacing: 2px;
    }

    .table > :not(caption) > * > * {
        /* Override Bootstrap's default table padding for a cleaner look */
        padding: 0.75rem 0.5rem;
        border-bottom-width: 1px;
    }

    .totals-table td {
        border: none;
    }

    .totals-table tr:last-child {
        font-size: 1.25rem;
        border-top: 1px solid rgba(255, 255, 255, 0.2);
    }
</style>

<div class="main-content">
    <div class="row justify-content-center">

        <div class="mb-4">
        <%-- Role-based "Back" Links --%>
        <c:choose>
            <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                <a href="${pageContext.request.contextPath}/my-bills" class="btn btn-sm btn-outline-info mb-3">
                    <i class="bi bi-arrow-left"></i> Back to My Bill History
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/bill-history" class="btn btn-sm btn-outline-info mb-3">
                    <i class="bi bi-arrow-left"></i> Back to Bill History
                </a>
            </c:otherwise>
        </c:choose>
        </div>

        <%-- The Bill Itself - styled to look like an invoice on a glass panel --%>
        <div class="invoice-card">
            <div class="invoice-header text-center mb-4">
                <h2 class="mb-0 text-white-50">PAHANA EDU BOOKSHOP</h2>
                <p class="text-muted">Official Bill / Invoice</p>
            </div>

            <div class="row mb-4">
                <div class="col-md-6">
                    <strong>Bill To:</strong> <c:out value="${customer.fullName}"/><br>
                    <strong>Account No:</strong> <c:out value="${customer.accountNumber}"/><br>
                    <strong>Address:</strong> <c:out value="${customer.address}"/>
                </div>
                <div class="col-md-6 text-md-end">
                    <strong>Bill ID:</strong> #${bill.billId}<br>
                    <strong>Date:</strong> <fmt:formatDate value="${bill.billDateAsDate}" type="both"
                                                           dateStyle="long" timeStyle="short"/>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table">
                    <thead class="table-dark">
                    <tr>
                        <th>Item Description</th>
                        <th class="text-center">Quantity</th>
                        <th class="text-end">Unit Price</th>
                        <th class="text-end">Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${bill.billItems}" var="item">
                        <tr>
                            <td><c:out value="${item.itemName}"/></td>
                            <td class="text-center"><c:out value="${item.quantity}"/></td>
                            <td class="text-end"><fmt:formatNumber value="${item.priceAtPurchase}" type="currency"
                                                                   currencySymbol="Rs."/></td>
                            <td class="text-end"><fmt:formatNumber value="${item.priceAtPurchase * item.quantity}"
                                                                   type="currency" currencySymbol="Rs."/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="row justify-content-end">
                <div class="col-md-5">
                    <table class="table totals-table">
                        <tbody>
                        <tr>
                            <td><strong>Subtotal</strong></td>
                            <td class="text-end"><fmt:formatNumber value="${bill.subTotal}" type="currency"
                                                                   currencySymbol="Rs."/></td>
                        </tr>
                        <c:if test="${bill.discountAmount > 0}">
                            <tr>
                                <td>Discount</td>
                                <td class="text-end">- <fmt:formatNumber value="${bill.discountAmount}"
                                                                         type="currency" currencySymbol="Rs."/></td>
                            </tr>
                        </c:if>
                        <c:if test="${bill.taxRateApplied > 0}">
                            <tr>
                                <td>Tax (<fmt:formatNumber value="${bill.taxRateApplied}" type="percent"/>)</td>
                                <td class="text-end">+ <fmt:formatNumber
                                        value="${bill.subTotal * bill.taxRateApplied}" type="currency"
                                        currencySymbol="Rs."/></td>
                            </tr>
                        </c:if>
                        <c:if test="${bill.serviceCharge > 0}">
                            <tr>
                                <td>Service Charge</td>
                                <td class="text-end">+ <fmt:formatNumber value="${bill.serviceCharge}"
                                                                         type="currency" currencySymbol="Rs."/></td>
                            </tr>
                        </c:if>
                        <tr class="fs-5">
                            <td><strong>Total Amount</strong></td>
                            <td class="text-end"><strong><fmt:formatNumber value="${bill.totalAmount}"
                                                                           type="currency"
                                                                           currencySymbol="Rs."/></strong></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="text-center mt-4">
                <p class="text-muted">Thank you for your business!</p>
            </div>
        </div>

    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>