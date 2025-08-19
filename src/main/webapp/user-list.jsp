<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
            <h1 class="mb-0">User Management</h1>
        </div>
        <a href="${pageContext.request.contextPath}/users?action=new" class="btn btn-primary">
            <i class="bi bi-person-plus-fill me-2"></i>Add New User
        </a>
    </div>

    <%-- User Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Role</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td><c:out value="${user.userId}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td><c:out value="${user.fullName}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${user.role == 'ADMIN'}">
                                <span class="badge bg-danger"><c:out value="${user.role}"/></span>
                            </c:when>
                            <c:when test="${user.role == 'STAFF'}">
                                <span class="badge bg-info text-dark"><c:out value="${user.role}"/></span>
                            </c:when>
                            <c:when test="${user.role == 'CUSTOMER'}">
                                <span class="badge bg-success"><c:out value="${user.role}"/></span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-secondary"><c:out value="${user.role}"/></span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-end">
                        <a href="${pageContext.request.contextPath}/users?action=edit&id=${user.userId}"
                           class="btn btn-sm btn-outline-primary">Edit</a>
                            <%-- Prevent admin from deleting themselves --%>
                        <c:if test="${sessionScope.user.userId != user.userId}">
                            <button type="button" class="btn btn-sm btn-outline-danger"
                                    onclick="showDeleteConfirmation('${pageContext.request.contextPath}/users?action=delete&id=${user.userId}')">
                                Delete
                            </button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%-- Confirmation Modal for Deleting Users --%>
<div class="modal fade" id="confirmDeleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Deletion</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this user? This action cannot be undone. If this user is a customer,
                their customer record will also be deleted.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <a id="confirmDeleteButton" class="btn btn-danger">Yes, Delete</a>
            </div>
        </div>
    </div>
</div>

<script>
    let confirmDeleteModal = null;
    const confirmDeleteModalElement = document.getElementById('confirmDeleteModal');
    const confirmDeleteButton = document.getElementById('confirmDeleteButton');

    function showDeleteConfirmation(deleteUrl) {
        if (confirmDeleteModal === null) {
            confirmDeleteModal = new bootstrap.Modal(confirmDeleteModalElement);
        }
        confirmDeleteButton.href = deleteUrl;
        confirmDeleteModal.show();
    }
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>