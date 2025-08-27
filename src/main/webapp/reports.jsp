<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="main-content fade-in-on-load">
    <%-- Page Header --%>
    <div class="mb-4">
        <a href="${pageContext.request.contextPath}/dashboard.jsp" class="btn btn-sm btn-outline-info mb-2">
            <i class="bi bi-arrow-left"></i> Back to Dashboard
        </a>
        <h1 class="mb-0">Business Reports</h1>
    </div>

    <div class="row g-4">
        <%-- Total Sales Card --%>
        <div class="col-md-5">
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
        <div class="col-md-7">
            <div class="card stat-card h-100">
                <div class="card-body">
                    <h5 class="card-title text-center">Top 5 Best-Selling Items</h5>
                    <%--                    <table class="table table-sm mt-3">--%>
                    <%--                        <thead>--%>
                    <%--                        <tr>--%>
                    <%--                            <th>Item Name</th>--%>
                    <%--                            <th class="text-end">Quantity Sold</th>--%>
                    <%--                        </tr>--%>
                    <%--                        </thead>--%>
                    <%--                        <tbody>--%>
                    <%--                        <c:forEach items="${topItems}" var="item">--%>
                    <%--                            <tr>--%>
                    <%--                                <td><c:out value="${item.itemName}"/></td>--%>
                    <%--                                <td class="text-end"><c:out value="${item.totalQuantity}"/></td>--%>
                    <%--                            </tr>--%>
                    <%--                        </c:forEach>--%>
                    <%--                        </tbody>--%>
                    <%--                    </table>--%>

                    <canvas id="topItemsChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Fetch the data from our new API endpoint
        fetch('${pageContext.request.contextPath}/api/reports/top-items')
            .then(response => response.json())
            .then(chartData => {
                // Get the canvas element
                const ctx = document.getElementById('topItemsChart').getContext('2d');

                // Create the chart using Chart.js
                new Chart(ctx, {
                    type: 'bar', // Type of chart
                    data: {
                        labels: chartData.labels, // Item names from our API
                        datasets: [{
                            label: 'Quantity Sold',
                            data: chartData.data, // Quantities from our API
                            backgroundColor: 'rgba(54, 162, 235, 0.6)', // Blue bars
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    color: 'rgba(255, 255, 255, 0.7)' // Light text for y-axis
                                }
                            },
                            x: {
                                ticks: {
                                    color: 'rgba(255, 255, 255, 0.7)' // Light text for x-axis
                                }
                            }
                        },
                        plugins: {
                            legend: {
                                display: false // We don't need a legend for a single dataset
                            }
                        }
                    }
                });
            })
            .catch(error => console.error('Error fetching chart data:', error));
    });
</script>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>
<%@ include file="/WEB-INF/jspf/notification.jspf" %>