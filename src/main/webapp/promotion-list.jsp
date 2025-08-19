<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
            <h1 class="mb-0">Promotion Management</h1>
        </div>
        <a href="${pageContext.request.contextPath}/promotions?action=new" class="btn btn-primary">
            <i class="bi bi-tag-fill me-2"></i>Add New Promotion
        </a>
    </div>

    <%-- Promotion Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Promo Code</th>
                <th class="text-end">Discount</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Status</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${promotionList}" var="promo">
                <tr>
                    <td><c:out value="${promo.promoId}"/></td>
                    <td><code><c:out value="${promo.promoCode}"/></code></td>
                    <td class="text-end"><fmt:formatNumber value="${promo.discountPercentage / 100}"
                                                           type="percent"/></td>
                    <td><c:out value="${promo.startDate}"/></td>
                    <td><c:out value="${promo.endDate}"/></td>
                    <td>
                        <c:if test="${promo.active}">
                            <span class="badge bg-success">Active</span>
                        </c:if>
                        <c:if test="${!promo.active}">
                            <span class="badge bg-secondary">Inactive</span>
                        </c:if>
                    </td>
                    <td class="text-end">
                        <a href="${pageContext.request.contextPath}/promotions?action=edit&id=${promo.promoId}"
                           class="btn btn-sm btn-outline-primary">Edit</a>
                        <button type="button" class="btn btn-sm btn-outline-danger"
                                onclick="showDeleteConfirmation('${pageContext.request.contextPath}/promotions?action=delete&id=${promo.promoId}')">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%-- Confirmation Modal for Deleting Promotions --%>
<div class="modal fade" id="confirmDeleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Confirm Deletion</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this promotion? This action cannot be undone.
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