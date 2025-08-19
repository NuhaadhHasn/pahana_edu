<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
            <h1 class="mb-0">Bill History</h1>
        </div>
    </div>

    <%-- Bill History Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>Bill ID</th>
                <th>Date</th>
                <th>Customer Name</th>
                <th>Account Number</th>
                <th class="text-end">Total Amount</th>
                <th>Status</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${billList}" var="bill">
                <tr>
                    <td>#<c:out value="${bill.billId}"/></td>
                    <td><fmt:formatDate value="${bill.billDateAsDate}" type="both" dateStyle="medium"
                                        timeStyle="short"/></td>
                    <td><c:out value="${bill.customer.fullName}"/></td>
                    <td><c:out value="${bill.customer.accountNumber}"/></td>
                    <td class="text-end"><fmt:formatNumber value="${bill.totalAmount}" type="currency"
                                                           currencySymbol="Rs."/></td>
                    <td>
                        <c:choose>
                            <c:when test="${bill.status == 'PAID'}"><span class="badge bg-success">Paid</span></c:when>
                            <c:when test="${bill.status == 'ISSUED'}"><span
                                    class="badge bg-warning text-dark">Issued</span></c:when>
                            <c:otherwise><span class="badge bg-secondary">${bill.status}</span></c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-end">
                        <a href="${pageContext.request.contextPath}/view-bill-details?id=${bill.billId}"
                           class="btn btn-sm btn-outline-light">View Details</a>
                        <c:if test="${bill.status != 'PAID'}">
                            <a href="${pageContext.request.contextPath}/bill-history?action=showPaymentForm&id=${bill.billId}"
                               class="btn btn-sm btn-outline-primary">Record Payment</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>