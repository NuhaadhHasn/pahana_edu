<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content fade-in-on-load">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="mb-0">Admin & Staff Dashboard</h1>
    </div>

    <%-- System Overview Section --%>
    <h2 class="h4">System Overview</h2>
    <div class="row g-4 mb-4">
        <div class="col-md-6">
            <div class="card text-center stat-card h-100">
                <div class="card-body">
                    <div id="customer-count" class="stat-number">...</div>
                    <div class="text-muted">Total Customers</div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card text-center stat-card h-100">
                <div class="card-body">
                    <div id="total-stock" class="stat-number">...</div>
                    <div class="text-muted">Total Items in Stock</div>
                </div>
            </div>
        </div>
    </div>

    <%-- Notifications Section - now styled with Bootstrap Alerts --%>
    <%--    <div id="notifications-container" class="mb-4">--%>
    <%--        <h2 class="h4">--%>
    <%--            Notifications--%>
    <%--            <a href="${pageContext.request.contextPath}/notifications" class="btn btn-sm btn-outline-info ms-2">View--%>
    <%--                All</a>--%>
    <%--        </h2>--%>
    <%--        <div id="notifications-list">--%>
    <%--            &lt;%&ndash; Alerts will be added here by JavaScript &ndash;%&gt;--%>
    <%--        </div>--%>
    <%--    </div>--%>

    <%-- Available Actions Section --%>
    <h2 class="h4">Available Actions</h2>
    <div class="list-group">
        <%-- Management Links --%>
        <a href="${pageContext.request.contextPath}/customers" class="list-group-item list-group-item-action"><i
                class="bi bi-people-fill me-2"></i>Customer Management</a>
        <a href="${pageContext.request.contextPath}/items" class="list-group-item list-group-item-action"><i
                class="bi bi-book-fill me-2"></i>Item Management</a>

        <%-- Admin-Only Management Links --%>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/users" class="list-group-item list-group-item-action"><i
                    class="bi bi-person-badge-fill me-2"></i>User Management</a>
            <a href="${pageContext.request.contextPath}/promotions" class="list-group-item list-group-item-action"><i
                    class="bi bi-tag-fill me-2"></i>Promotion Management</a>
        </c:if>

        <%-- Operational Links --%>
        <a href="${pageContext.request.contextPath}/billing" class="list-group-item list-group-item-action"><i
                class="bi bi-receipt-cutoff me-2"></i>Create New Bill</a>
        <a href="${pageContext.request.contextPath}/bill-history" class="list-group-item list-group-item-action"><i
                class="bi bi-clock-history me-2"></i>View Bill History</a>

        <%-- Admin-Only Operational Links --%>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/login-history" class="list-group-item list-group-item-action"><i
                    class="bi bi-shield-lock-fill me-2"></i>View Login History</a>
            <a href="${pageContext.request.contextPath}/reports" class="list-group-item list-group-item-action"><i
                    class="bi bi-bar-chart-line-fill me-2"></i>View Business Reports</a>
        </c:if>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<%@ include file="/WEB-INF/jspf/notification.jspf" %>