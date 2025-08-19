<%@ include file="/WEB-INF/jspf/header.jspf" %>

<style>
    .help-card {
        background: rgba(30, 30, 45, 0.85);
        backdrop-filter: blur(15px);
        -webkit-backdrop-filter: blur(15px);
        border: 1px solid rgba(255, 255, 255, 0.1);
        border-radius: 20px;
        padding: 2.5rem;
        box-shadow: 0 8px 32px 0 rgba(0, 0, 0, 0.37);
    }

    .help-card h2 {
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        padding-bottom: 0.5rem;
    }

    .help-card h2:first-child {
        margin-top: 0; /* Fix for the extra space */
    }

    .help-card ul {
        list-style-type: none;
        padding-left: 0;
    }

    .help-card li {
        padding-left: 1.5em;
        text-indent: -1.5em;
        margin-bottom: 0.5em;
    }

</style>

<div class="main-content">
    <div class="row justify-content-center">

        <div class="mb-4">
            <%-- Role-based "Back" Link --%>
            <c:choose>
                <c:when test="${sessionScope.user.role == 'CUSTOMER'}">
                    <a href="${pageContext.request.contextPath}/customer-dashboard.jsp"
                       class="btn btn-sm btn-outline-info mb-2">
                        <i class="bi bi-arrow-left"></i> Back to My Dashboard
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/dashboard.jsp"
                       class="btn btn-sm btn-outline-info mb-2">
                        <i class="bi bi-arrow-left"></i> Back to Main Dashboard
                    </a>
                </c:otherwise>
            </c:choose>
            <h1 class="mb-0">Help & System Guidelines</h1>
        </div>

        <div class="help-card">
            <%-- Admin Help Section (in an Accordion) --%>
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <div class="accordion" id="adminHelpAccordion">
                    <div class="accordion-item bg-transparent">
                        <h2 class="accordion-header" id="headingAdmin">
                            <button class="accordion-button bg-transparent text-white" type="button"
                                    data-bs-toggle="collapse" data-bs-target="#collapseAdmin">
                                Admin Guidelines
                            </button>
                        </h2>
                        <div id="collapseAdmin" class="accordion-collapse collapse show"
                             data-bs-parent="#adminHelpAccordion">
                            <div class="accordion-body">
                                <p>As an Administrator, you have full access to all system functionalities.</p>
                                <ul class="list-unstyled">
                                    <li><i class="bi bi-check-circle me-2 text-info"></i><strong>User
                                        Management:</strong> You can create, view, edit, and delete all user accounts...
                                    </li>
                                    <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Full CRUD
                                        Access:</strong> You have complete control over Customers, Items, and
                                        Promotions.
                                    </li>
                                    <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Reporting:</strong> The
                                        "Business Reports" page provides key insights into sales and product
                                        performance.
                                    </li>
                                    <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Auditing:</strong> The
                                        "Login History" page allows you to review all successful and failed login
                                        attempts...
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <%-- Staff Help Section --%>
            <c:if test="${sessionScope.user.role == 'STAFF'}">
                <div class="role-section mt-4">
                    <h2>Staff Guidelines</h2>
                    <p>As a Staff member, you have access to all day-to-day operational features.</p>
                    <ul class="list-unstyled">
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Billing:</strong> The "Create New
                            Bill" page is your primary workspace...
                        </li>
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Customer Management:</strong> You
                            can view and edit customer details...
                        </li>
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Item Management:</strong> You can
                            manage book inventory...
                        </li>
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Payments:</strong> From the "Bill
                            History" page, you can record a payment...
                        </li>
                    </ul>
                </div>
            </c:if>

            <%-- Customer Help Section --%>
            <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
                <div class="role-section mt-4">
                    <h2>Customer Guidelines</h2>
                    <p>Welcome to your personal customer portal.</p>
                    <ul class="list-unstyled">
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>My Dashboard:</strong> This is your
                            main landing page...
                        </li>
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>My Bill History:</strong> You can
                            view a list of all your past purchases.
                        </li>
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>View Details:</strong> Click "View
                            Details" to see the full, itemized invoice.
                        </li>
                        <li><i class="bi bi-check-circle me-2 text-info"></i><strong>Make a Payment:</strong> Use the
                            link to access a simulated payment page.
                        </li>
                    </ul>
                </div>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>