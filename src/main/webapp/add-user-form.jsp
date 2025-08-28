<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content fade-in-on-load">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">

            <%-- Page Header --%>
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/users" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to User List
                </a>
                <h1 class="mb-0">Add New User</h1>
            </div>

            <%-- Error Message Display --%>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
            </c:if>

            <%-- The Form --%>
            <form action="${pageContext.request.contextPath}/users" method="post">
                <input type="hidden" name="action" value="add"/>

                <div class="mb-3">
                    <label for="username" class="form-label">Username:</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                    <span id="username-validation-message" class="form-text"></span>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name:</label>
                    <input type="text" class="form-control" id="fullName" name="fullName" required>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">Role:</label>
                    <select class="form-select" id="role" name="role" onchange="toggleCustomerFields()">
                        <option value="STAFF">Staff</option>
                        <option value="ADMIN">Admin</option>
                        <option value="CUSTOMER">Customer</option>
                    </select>
                </div>

                <div id="customer-fields" style="display: none;">
                    <hr>
                    <p><strong>Customer Specific Details:</strong></p>
                    <div class="mb-3">
                        <label for="accountNumber" class="form-label">Account Number:</label>
                        <input type="text" class="form-control" id="accountNumber" name="accountNumber">
                    </div>
                    <div class="mb-3">
                        <label for="address" class="form-label">Address:</label>
                        <input type="text" class="form-control" id="address" name="address">
                    </div>
                    <div class="mb-3">
                        <label for="phoneNumber" class="form-label">Phone Number:</label>
                        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber">
                    </div>
                </div>

                <button type="submit" class="btn btn-primary"><i class="bi bi-person-plus-fill me-2"></i>Add User
                </button>
            </form>

            <script>
                function toggleCustomerFields() {
                    const roleSelect = document.getElementById('role');
                    const customerFields = document.getElementById('customer-fields');
                    customerFields.style.display = (roleSelect.value === 'CUSTOMER') ? 'block' : 'none';
                }

                // Run on page load to set the initial state
                toggleCustomerFields();
            </script>
        </div>
    </div>
</div>
<script>
    console.log("--- SCRIPT BLOCK STARTED ---");

    // JSP evaluates this on the server
    const contextPath = '${pageContext.request.contextPath}' || '';

    // --- START: Real-Time Username Validation ---
    const usernameInput = document.getElementById('username');
    const validationMessage = document.getElementById('username-validation-message');
    let debounceTimer;

    console.log("Username input element found:", usernameInput);

    usernameInput.addEventListener('input', function (event) {
        console.log("--- INPUT EVENT FIRED ---");
        clearTimeout(debounceTimer);

        const currentValue = event.target.value; // capture once

        console.log("Current username value in input event:", currentValue);

        debounceTimer = setTimeout(function () {
            console.log("--- DEBOUNCE TIMER FIRED after 500ms ---");
            if (currentValue.length > 2) {
                console.log("Username is long enough, calling checkUsername...");
                checkUsername(currentValue);
            } else {
                console.log("Username is too short, clearing message.");
                validationMessage.textContent = '';
            }
        }, 500);
    });

    function checkUsername(username) {
        console.log("--- checkUsername called with:", username, "---");
        if (!username) {
            console.warn("Username empty; aborting.");
            return;
        }

        var encodedUsername = encodeURIComponent(username);

        // IMPORTANT: use string concatenation, not template literals
        var apiUrl = contextPath + '/api/users/check-username?username=' + encodedUsername;

        console.log("Fetching API URL:", apiUrl);

        fetch(apiUrl, {headers: {'Accept': 'application/json'}})
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('HTTP ' + response.status + ' – ' + response.statusText);
                }
                return response.json();
            })
            .then(function (data) {
                console.log("API response parsed:", data);
                if (data && data.exists === true) {
                    validationMessage.textContent = 'Username is already taken.';
                    validationMessage.style.color = 'var(--bs-danger)';
                } else {
                    validationMessage.textContent = 'Username is available.';
                    validationMessage.style.color = 'var(--bs-success)';
                }
            })
            .catch(function (error) {
                console.error("Error checking username:", error);
                validationMessage.textContent = 'Could not verify username.';
                validationMessage.style.color = 'var(--bs-warning)';
            });
    }

    // --- START: Role-based Field Toggling ---
    function toggleCustomerFields() {
        var roleSelect = document.getElementById('role');
        var customerFields = document.getElementById('customer-fields');
        customerFields.style.display = (roleSelect.value === 'CUSTOMER') ? 'block' : 'none';
    }

    toggleCustomerFields();
    // --- END: Role-based Field Toggling ---
</script>


<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<%@ include file="/WEB-INF/jspf/notification.jspf" %>