<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
        <h1 class="mb-0">Login Attempt History</h1>
    </div>

    <%-- Login History Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>Log ID</th>
                <th>User ID</th>
                <th>Time</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${historyList}" var="log">
                <tr>
                    <td><c_rt:out value="${log.logId}"/></td>
                    <td>
                        <c:if test="${log.userId > 0}">
                            <c:out value="${log.userId}"/>
                        </c:if>
                        <c:if test="${log.userId == 0}">
                            <span class="text-muted">N/A</span>
                        </c:if>
                    </td>
                    <td><fmt:formatDate value="${log.loginTimeAsDate}" type="both" dateStyle="medium"
                                        timeStyle="long"/></td>
                    <td>
                        <c:if test="${log.status == 'SUCCESS'}">
                            <span class="badge bg-success">Success</span>
                        </c:if>
                        <c:if test="${log.status == 'FAILED'}">
                            <span class="badge bg-danger">Failed</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>