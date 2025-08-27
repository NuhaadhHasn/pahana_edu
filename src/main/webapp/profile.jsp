<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content fade-in-on-load">
    <div class="row justify-content-center">
        <div class="col-lg-10 col-xl-8">

            <%-- Page Header --%>
            <div class="mb-4">
                <h1 class="mb-0">My Profile</h1>
            </div>

            <%-- Display Success or Error Messages from the session --%>
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    <c:out value="${sessionScope.successMessage}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("successMessage"); %>
            </c:if>
            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <c:out value="${sessionScope.errorMessage}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("errorMessage"); %>
            </c:if>

            <div class="row g-4">
                <%-- Column 1: Update Details --%>
                <div class="col-md-6">
                    <div class="card card-body h-100">
                        <h3 class="card-title">Update Details</h3>
                        <form action="${pageContext.request.contextPath}/profile" method="post">
                            <input type="hidden" name="action" value="updateDetails">
                            <div class="mb-3">
                                <label for="fullName" class="form-label">Full Name:</label>
                                <input type="text" class="form-control" id="fullName" name="fullName"
                                       value="<c:out value='${sessionScope.user.fullName}' />" required>
                            </div>
                            <div class="mb-3">
                                <label for="username" class="form-label">Username:</label>
                                <input type="text" class="form-control" id="username" name="username"
                                       value="<c:out value='${sessionScope.user.username}' />" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Save Changes</button>
                        </form>
                    </div>
                </div>

                <%-- Column 2: Change Password --%>
                <div class="col-md-6">
                    <div class="card card-body h-100">
                        <h3 class="card-title">Change Password</h3>
                        <form action="${pageContext.request.contextPath}/profile" method="post">
                            <input type="hidden" name="action" value="changePassword">
                            <div class="mb-3">
                                <label for="currentPassword" class="form-label">Current Password:</label>
                                <input type="password" class="form-control" id="currentPassword" name="currentPassword"
                                       required>
                            </div>
                            <div class="mb-3">
                                <label for="newPassword" class="form-label">New Password:</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword"
                                       required>
                            </div>
                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Confirm New Password:</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                                       required>
                            </div>
                            <button type="submit" class="btn btn-primary">Update Password</button>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<%@ include file="/WEB-INF/jspf/notification.jspf" %>