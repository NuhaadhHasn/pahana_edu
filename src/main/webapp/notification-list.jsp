<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
        <h1 class="mb-0">Notification History</h1>
    </div>

    <%-- Notification Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>Date / Time</th>
                <th>Message</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${notificationList}" var="notification">
                <tr class="${notification.read ? 'text-muted' : ''}">
                    <td><fmt:formatDate value="${notification.createdAtAsDate}" type="both" dateStyle="medium"
                                        timeStyle="short"/></td>
                    <td><c:out value="${notification.message}"/></td>
                    <td>
                        <c:if test="${notification.read}">
                            <span class="badge bg-secondary">Read</span>
                        </c:if>
                        <c:if test="${!notification.read}">
                            <span class="badge bg-warning text-dark">Unread</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>