<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="mb-4">
        <h1 class="mb-0">My Dashboard</h1>
        <p class="text-muted">Welcome to your personal customer portal, <c:out
                value="${sessionScope.user.fullName}"/>!</p>
    </div>

    <%-- Available Actions --%>
    <h2 class="h4">Available Actions</h2>
    <div class="list-group">
        <a href="${pageContext.request.contextPath}/my-bills" class="list-group-item list-group-item-action">
            <i class="bi bi-receipt me-2"></i>View My Bill History
        </a>
        <a href="${pageContext.request.contextPath}/items?action=view" class="list-group-item list-group-item-action">
            <i class="bi bi-book me-2"></i>View Available Items
        </a>
        <a href="${pageContext.request.contextPath}/make-payment" class="list-group-item list-group-item-action">
            <i class="bi bi-credit-card me-2"></i>Make a Payment
        </a>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>