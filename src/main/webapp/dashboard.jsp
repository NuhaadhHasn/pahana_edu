<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
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
    <div id="notifications-container" class="mb-4">
        <h2 class="h4">
            Notifications
            <a href="${pageContext.request.contextPath}/notifications" class="btn btn-sm btn-outline-info ms-2">View
                All</a>
        </h2>
        <div id="notifications-list">
            <%-- Alerts will be added here by JavaScript --%>
        </div>
    </div>

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
</div> <%-- Closes the .main-content div --%>
<div class="modal fade" id="confirmDismissModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="confirmModalLabel">Confirm Dismissal</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" id="confirmModalBody">
                <%-- The question will be placed here by JavaScript --%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-danger" id="confirmDismissButton">Yes, Dismiss</button>
            </div>
        </div>
    </div>
</div>
<script>
    let confirmModal = null;
    const confirmModalElement = document.getElementById('confirmDismissModal');
    const confirmModalBody = document.getElementById('confirmModalBody');
    const confirmDismissButton = document.getElementById('confirmDismissButton');

    // This function shows the confirmation modal
    function showDismissConfirmation(notificationId, notificationMessage, alertElement) {
        // Set the question text inside the modal
        if (confirmModal === null) {
            confirmModal = new bootstrap.Modal(confirmModalElement);
        }
        confirmModalBody.textContent = 'Are you sure you want to dismiss the notification: "' + notificationMessage + '"?';

        // This is important: we attach an event listener to the "Confirm" button
        // that will only run ONCE.
        confirmDismissButton.onclick = () => {
            dismissNotification(notificationId, alertElement);
            confirmModal.hide(); // Hide the modal after confirming
        };

        // Show the modal to the user
        confirmModal.show();
    }

    function dismissNotification(notificationId, alertElement) {
        console.log(`Attempting to dismiss notification ID: ${notificationId}`);

        const formData = new URLSearchParams();
        formData.append('id', notificationId);

        fetch(`${pageContext.request.contextPath}/api/notifications/mark-as-read`, {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: formData
        })
            .then(response => {
                console.log('Server response status:', response.status);
                if (response.ok) {
                    console.log('Success! Removing alert element from UI.');
                    alertElement.remove();
                } else {
                    alert('Could not dismiss notification. Server returned an error.');
                }
            })
            .catch(error => {
                console.error('Network error while dismissing notification:', error);
                alert('A network error occurred. Could not dismiss notification.');
            });
    }

    document.addEventListener('DOMContentLoaded', function () {
        fetch('${pageContext.request.contextPath}/api/dashboard-stats')
            .then(response => response.json())
            .then(data => {
                const stats = data.statistics;
                document.getElementById('customer-count').textContent = stats.customerCount;
                document.getElementById('total-stock').textContent = stats.totalStock;

                const notifications = data.notifications;
                const notificationList = document.getElementById('notifications-list');
                notificationList.innerHTML = '';

                if (notifications && notifications.length > 0) {
                    notifications.forEach(notification => {
                        const alertDiv = document.createElement('div');
                        alertDiv.className = 'alert alert-warning alert-dismissible fade show d-flex justify-content-between align-items-center';
                        alertDiv.setAttribute('role', 'alert');

                        const messageSpan = document.createElement('span');

                        // --- THE FINAL FIX IS HERE ---
                        // I am no longer using a template literal to avoid the JSP conflict.
                        // This concatenates the string safely.
                        messageSpan.innerHTML = '<i class="bi bi-exclamation-triangle me-2"></i> ' + notification.message;

                        const dismissButton = document.createElement('button');
                        dismissButton.type = 'button';
                        dismissButton.className = 'btn-close';
                        dismissButton.setAttribute('aria-label', 'Close');
                        // We pass 'alertDiv' so the dismiss function knows which element to remove
                        dismissButton.onclick = () => showDismissConfirmation(notification.notificationId, notification.message, alertDiv);

                        alertDiv.appendChild(messageSpan);
                        alertDiv.appendChild(dismissButton);
                        notificationList.appendChild(alertDiv);
                    });
                } else {
                    notificationList.innerHTML = '<p class="text-muted">No new notifications.</p>';
                }
            })
            .catch(error => console.error('Error fetching dashboard data:', error));
    });
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>