<%@ include file="/WEB-INF/jspf/header.jspf" %>

<%-- Page specific title and back link --%>
<div class="main-content">

    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/customers" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to Customer List
                </a>
                <h1 class="mb-0">${not empty customer ? 'Edit Customer' : 'Add New Customer'}</h1>
            </div>

            <form action="${pageContext.request.contextPath}/customers" method="post">

                <%-- Hidden fields for the update action --%>
                <c:if test="${not empty customer}">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="customerId" value="<c:out value='${customer.customerId}' />"/>
                </c:if>

                <%-- Form fields with Bootstrap styling --%>
                <div class="mb-3">
                    <label for="accountNumber" class="form-label">Account Number:</label>
                    <input type="text" class="form-control" id="accountNumber" name="accountNumber"
                           value="<c:out value='${customer.accountNumber}' />" required>
                </div>
                <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name:</label>
                    <input type="text" class="form-control" id="fullName" name="fullName"
                           value="<c:out value='${customer.fullName}' />" required>
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

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-circle-fill me-2"></i> ${not empty customer ? 'Update Customer' : 'Add Customer'}
                </button>
            </form>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>