<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page specific title and action button --%>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
        <h1 class="mb-0">Customer Management</h1>
        <a href="${pageContext.request.contextPath}/customers?action=new" class="btn btn-primary">
            <i class="bi bi-plus-circle-fill me-2"></i>Add New Customer
        </a>
    </div>

    <%-- Bootstrap styled table --%>
    <div class="table-responsive">
        <table class="table table-hover align-middle"> <%-- 'table-hover' adds a nice effect on rows --%>
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Account Number</th>
                <th>Full Name</th>
                <th>Address</th>
                <th>Phone Number</th>
                <th class="text-end">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${customerList}" var="customer">
                <tr>
                    <td><c:out value="${customer.customerId}"/></td>
                    <td><c:out value="${customer.accountNumber}"/></td>
                    <td><c:out value="${customer.fullName}"/></td>
                    <td><c:out value="${customer.address}"/></td>
                    <td><c:out value="${customer.phoneNumber}"/></td>
                    <td class="text-end">
                            <%-- Styled action links as small buttons --%>
                        <a href="${pageContext.request.contextPath}/customers?action=edit&id=${customer.customerId}"
                           class="btn btn-sm btn-outline-primary">Edit</a>
                        <button type="button" class="btn btn-sm btn-outline-danger"
                                onclick="showDeleteConfirmation('${pageContext.request.contextPath}/customers?action=delete&id=${customer.customerId}')">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%-- Add this Confirmation Modal before the footer include --%>
<div class="modal fade" id="confirmDeleteModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">Confirm Deletion</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="confirmModalBody">
                Are you sure you want to delete this customer? This action cannot be undone.
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <%-- This button has no initial action; we will add it with JavaScript --%>
                <a id="confirmDeleteButton" class="btn btn-danger">Yes, Delete</a>
            </div>
        </div>
    </div>
</div>
<script>
    // Get a reference to the modal elements
    let confirmDeleteModal = null;
    const confirmDeleteModalElement = document.getElementById('confirmDeleteModal');
    const confirmDeleteButton = document.getElementById('confirmDeleteButton');

    // This function sets the correct URL on the modal's delete button and shows it
    function showDeleteConfirmation(deleteUrl) {
        if (confirmDeleteModal === null) {
            confirmDeleteModal = new bootstrap.Modal(confirmDeleteModalElement);
        }

        // Set the href of the confirm button to the URL we want to go to
        confirmDeleteButton.href = deleteUrl;

        // Show the modal
        confirmDeleteModal.show();
    }
</script>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>