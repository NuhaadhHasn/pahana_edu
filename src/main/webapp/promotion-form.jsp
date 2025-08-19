<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">

            <%-- Page Header --%>
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/promotions" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to Promotion List
                </a>
                <h1 class="mb-0">${not empty promotion ? 'Edit Promotion' : 'Add New Promotion'}</h1>
            </div>

            <%-- The Form --%>
            <form action="${pageContext.request.contextPath}/promotions" method="post">
                <c:if test="${not empty promotion}">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="promoId" value="<c:out value='${promotion.promoId}' />"/>
                </c:if>

                <div class="mb-3">
                    <label for="promoCode" class="form-label">Promo Code:</label>
                    <input type="text" class="form-control" id="promoCode" name="promoCode"
                           value="<c:out value='${promotion.promoCode}' />" required>
                </div>
                <div class="mb-3">
                    <label for="discountPercentage" class="form-label">Discount (%):</label>
                    <input type="number" class="form-control" id="discountPercentage" name="discountPercentage"
                           value="<c:out value='${promotion.discountPercentage}' />" step="0.1" min="0" max="100"
                           required>
                </div>
                <div class="mb-3">
                    <label for="startDate" class="form-label">Start Date (Optional):</label>
                    <input type="date" class="form-control" id="startDate" name="startDate"
                           value="<c:out value='${promotion.startDate}' />">
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">End Date (Optional):</label>
                    <input type="date" class="form-control" id="endDate" name="endDate"
                           value="<c:out value='${promotion.endDate}' />">
                </div>
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="isActive"
                           name="isActive" ${ (empty promotion or promotion.active) ? 'checked' : '' }>
                    <label class="form-check-label" for="isActive">
                        Is Active
                    </label>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-circle-fill me-2"></i> ${not empty promotion ? 'Update Promotion' : 'Add Promotion'}
                </button>
            </form>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>