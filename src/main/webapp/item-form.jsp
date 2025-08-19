<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">

            <%-- Page Header --%>
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/items" class="btn btn-sm btn-outline-info mb-2">
                    <i class="bi bi-arrow-left"></i> Back to Item List
                </a>
                <h1 class="mb-0">${not empty item ? 'Edit Item' : 'Add New Item'}</h1>
            </div>

            <%-- Display Error Messages --%>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    <c:out value="${errorMessage}"/>
                </div>
            </c:if>

            <%-- The Form --%>
            <form action="${pageContext.request.contextPath}/items" method="post">
                <c:if test="${not empty item}">
                    <input type="hidden" name="action" value="update"/>
                    <input type="hidden" name="itemId" value="<c:out value='${item.itemId}' />"/>
                </c:if>

                <div class="mb-3">
                    <label for="itemName" class="form-label">Item Name (Book Title):</label>
                    <input type="text" class="form-control" id="itemName" name="itemName"
                           value="<c:out value='${item.itemName}' />" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description:</label>
                    <textarea class="form-control" id="description" name="description" rows="3"><c:out
                            value='${item.description}'/></textarea>
                </div>
                <div class="mb-3">
                    <label for="price" class="form-label">Price:</label>
                    <input type="number" class="form-control" id="price" name="price"
                           value="<c:out value='${item.price}' />" step="0.01" min="0" required>
                </div>
                <div class="mb-3">
                    <label for="stockQuantity" class="form-label">Stock Quantity:</label>
                    <input type="number" class="form-control" id="stockQuantity" name="stockQuantity"
                           value="<c:out value='${item.stockQuantity}' />" min="0" required>
                </div>

                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-circle-fill me-2"></i> ${not empty item ? 'Update Item' : 'Add Item'}
                </button>
            </form>

        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>