<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="mb-4">
        <%-- CORRECTED, ROLE-AWARE "BACK" LINK --%>
        <c:choose>
            <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                <a href="${pageContext.request.contextPath}/customer-dashboard.jsp"
                   class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to My Dashboard
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to Dashboard
                </a>
            </c:otherwise>
        </c:choose>
        <h1 class="mb-0">My Bill History</h1>
    </div>

    <c:if test="${empty billList}">
        <div class="alert alert-info">You have no bills in your history yet.</div>
    </c:if>

    <c:if test="${not empty billList}">
        <div class="table-responsive">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                <tr>
                    <th>Bill ID</th>
                    <th>Date</th>
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
                        <td class="text-end"><fmt:formatNumber value="${bill.totalAmount}" type="currency"
                                                               currencySymbol="Rs."/></td>
                        <td>
                            <c:choose>
                                <c:when test="${bill.status == 'PAID'}"><span
                                        class="badge bg-success">Paid</span></c:when>
                                <c:when test="${bill.status == 'ISSUED'}"><span class="badge bg-warning text-dark">Issued</span></c:when>
                                <c:otherwise><span class="badge bg-secondary">${bill.status}</span></c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-end">
                            <a href="${pageContext.request.contextPath}/view-bill-details?id=${bill.billId}"
                               class="btn btn-sm btn-outline-light">View Details</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>