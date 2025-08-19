<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content">
    <%-- Page Header --%>
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
        <h1 class="mb-0">Business Reports</h1>
    </div>

    <div class="row g-4">
        <%-- Total Sales Card --%>
        <div class="col-md-6">
            <div class="card text-center stat-card h-100">
                <div class="card-body">
                    <div class="text-muted">Total Sales (from Paid Bills)</div>
                    <div class="stat-number mt-2">
                        <fmt:formatNumber value="${totalSales}" type="currency" currencySymbol="Rs."/>
                    </div>
                </div>
            </div>
        </div>

        <%-- Top Selling Items Card --%>
        <div class="col-md-6">
            <div class="card stat-card h-100">
                <div class="card-body">
                    <h5 class="card-title text-center">Top 5 Best-Selling Items</h5>
                    <table class="table table-sm mt-3">
                        <thead>
                        <tr>
                            <th>Item Name</th>
                            <th class="text-end">Quantity Sold</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${topItems}" var="item">
                            <tr>
                                <td><c:out value="${item.itemName}"/></td>
                                <td class="text-end"><c:out value="${item.totalQuantity}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>