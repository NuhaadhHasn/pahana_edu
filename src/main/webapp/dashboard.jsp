<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard</title>
    <style>
        body {
            font-family: sans-serif;
            margin: 2em;
        }

        .stats-container {
            display: flex;
            gap: 2em;
            margin-bottom: 2em;
        }

        .stat-card {
            border: 1px solid #ccc;
            padding: 1em;
            text-align: center;
            width: 200px;
        }

        .stat-number {
            font-size: 2.5em;
            font-weight: bold;
        }

        .stat-label {
            color: #555;
        }
    </style>
</head>
<body>

<%-- We can greet the user by name using the user object from the session --%>
<h1>Welcome, <c:out value="${sessionScope.user.fullName}"/>!</h1>

<p><a href="${pageContext.request.contextPath}/logout">Logout</a></p>
<hr>

<h2>System Overview</h2>
<div class="stats-container">
    <div class="stat-card">
        <%-- These will be populated by our JavaScript --%>
        <div id="customer-count" class="stat-number">...</div>
        <div class="stat-label">Total Customers</div>
    </div>
    <div class="stat-card">
        <div id="total-stock" class="stat-number">...</div>
        <div class="stat-label">Total Items in Stock</div>
    </div>
</div>

<h3>Available Actions:</h3>
<ul>
    <%-- Customer Management --%>
    <li><a href="${pageContext.request.contextPath}/customers?action=new">Add New Customer</a></li>
    <li><a href="${pageContext.request.contextPath}/customers">View All Customers</a></li>
    <hr>
    <%-- Item Management --%>
    <li><a href="${pageContext.request.contextPath}/items?action=new">Add New Item</a></li>
    <li><a href="${pageContext.request.contextPath}/items">View All Items</a></li>
    <hr>
    <%-- User Management --%>
    <li><a href="${pageContext.request.contextPath}/users?action=new">Add New User</a></li>
    <li><a href="${pageContext.request.contextPath}/users">View All Users</a></li>
    <hr>
    <%-- Billing --%>
    <li><a href="${pageContext.request.contextPath}/billing">Create New Bill</a></li>
    <li><a href="${pageContext.request.contextPath}/bill-history">View Bill History</a></li>
    <hr>
    <%-- Promotions Management --%>
    <li><a href="${pageContext.request.contextPath}/promotion-form.jsp">Add New Promotion</a></li>
    <li><a href="${pageContext.request.contextPath}/promotions">View All Promotions</a></li>
</ul>

<%-- This is the JavaScript that will call our API --%>
<script>
    // This function runs automatically when the page finishes loading
    document.addEventListener('DOMContentLoaded', function () {

        // Use the modern 'fetch' API to call our web service
        fetch('${pageContext.request.contextPath}/api/dashboard-stats')
            .then(response => response.json()) // Tell it to expect a JSON response
            .then(data => {
                // This 'data' variable now holds our JSON object: {customerCount: X, totalStock: Y}

                // Update the HTML elements with the data from the API
                document.getElementById('customer-count').textContent = data.customerCount;
                document.getElementById('total-stock').textContent = data.totalStock;
            })
            .catch(error => {
                // If the API call fails, log an error to the browser console
                console.error('Error fetching dashboard stats:', error);
                document.getElementById('customer-count').textContent = 'Error';
                document.getElementById('total-stock').textContent = 'Error';
            });
    });
</script>

</body>
</html>