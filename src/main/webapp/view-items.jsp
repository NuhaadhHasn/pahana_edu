<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="mb-4">
        <c:choose>
            <%-- If the user is a CUSTOMER, link to their dashboard --%>
            <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                <a href="${pageContext.request.contextPath}/customer-dashboard.jsp"
                   class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to My Dashboard
                </a>
            </c:when>
            <%-- Otherwise (if Admin/Staff), link to the main dashboard --%>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to Dashboard
                </a>
            </c:otherwise>
        </c:choose>
        <h1 class="mb-0">Available Items</h1>
        <p class="text-muted">Browse our current collection of books.</p>
    </div>

    <%-- Item Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>Item Name</th>
                <th>Description</th>
                <th class="text-end">Price</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${itemList}" var="item">
                <tr>
                    <td><strong><c:out value="${item.itemName}"/></strong></td>
                    <td><c:out value="${item.description}"/></td>
                    <td class="text-end"><fmt:formatNumber value="${item.price}" type="currency"
                                                           currencySymbol="Rs."/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>