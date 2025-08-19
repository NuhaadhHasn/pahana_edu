<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">

            <%-- Page Header --%>
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/users" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to User List
                </a>
                <h1 class="mb-0">Edit User</h1>
            </div>

            <%-- The Form --%>
            <form action="${pageContext.request.contextPath}/users" method="post">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="userId" value="<c:out value='${user.userId}' />"/>

                <div class="mb-3">
                    <label for="username" class="form-label">Username:</label>
                    <input type="text" class="form-control" id="username" name="username"
                           value="<c:out value='${user.username}' />" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label">New Password:</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword">
                    <div class="form-text">Leave blank to keep the current password.</div>
                </div>
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name:</label>
                    <input type="text" class="form-control" id="fullName" name="fullName"
                           value="<c:out value='${user.fullName}' />" required>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">Role:</label>
                    <select class="form-select" id="role" name="role" onchange="toggleCustomerFields()">
                        <option value="STAFF" ${user.role == 'STAFF' ? 'selected' : ''}>Staff</option>
                        <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                        <option value="CUSTOMER" ${user.role == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
                    </select>
                </div>

                <div id="customer-fields">
                    <hr>
                    <p><strong>Customer Specific Details:</strong></p>
                    <div class="mb-3">
                        <label for="accountNumber" class="form-label">Account Number:</label>
                        <input type="text" class="form-control" id="accountNumber" name="accountNumber"
                               value="<c:out value='${customer.accountNumber}' />">
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Address:</label>
                        <input type="text" class="form-control" id="address" name="address"
                               value="<c:out value='${customer.address}' />">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number:</label>
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
                               value="<c:out value='${customer.phoneNumber}' />">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-circle-fill me-2"></i>Update User
                </button>
            </form>

            <script>
                function toggleCustomerFields() {
                    const roleSelect = document.getElementById('role');
                    const customerFields = document.getElementById('customer-fields');
                    customerFields.style.display = (roleSelect.value === 'CUSTOMER') ? 'block' : 'none';
                }

                document.getElementById('role').addEventListener('change', toggleCustomerFields);
                
                // Run on page load to set the initial state
                toggleCustomerFields();
            </script>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>