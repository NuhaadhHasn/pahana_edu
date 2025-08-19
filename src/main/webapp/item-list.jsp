<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
                <i class="bi bi-arrow-left"></i> Back to Dashboard
            </a>
            <h1 class="mb-0">Item Management</h1>
        </div>
        <a href="${pageContext.request.contextPath}/items?action=new" class="btn btn-primary">
            <i class="bi bi-plus-circle-fill me-2"></i>Add New Item
        </a>
    </div>

    <%-- Item Table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Item Name</th>
                <th>Description</th>
                <th class="text-end">Price</th>
                <th class="text-end">Stock</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${itemList}" var="item">
                <tr>
                    <td><c:out value="${item.itemId}"/></td>
                    <td><c:out value="${item.itemName}"/></td>
                    <td><c:out value="${item.description}"/></td>
                    <td class="text-end"><fmt:formatNumber value="${item.price}" type="currency"
                                                           currencySymbol="Rs."/></td>
                    <td class="text-end"><c:out value="${item.stockQuantity}"/></td>
                    <td class="text-end">
                        <a href="${pageContext.request.contextPath}/items?action=edit&id=${item.itemId}"
                           class="btn btn-sm btn-outline-primary">Edit</a>
                        <button type="button" class="btn btn-sm btn-outline-danger"
                                onclick="showDeleteConfirmation('${pageContext.request.contextPath}/items?action=delete&id=${item.itemId}')">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%-- Confirmation Modal for Deleting Items --%>
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">Confirm Deletion</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this item? This action cannot be undone.
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