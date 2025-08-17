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

<div id="notifications-container" style="margin-bottom: 2em;">
    <h3>
        Notifications
        <%-- Add this link --%>
        <a href="${pageContext.request.contextPath}/notifications"
           style="font-size: 0.7em; font-weight: normal; margin-left: 1em;">(View All)</a>
    </h3>
    <ul id="notifications-list">
        <%-- Alerts will be added here by JavaScript --%>
    </ul>
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
    <li><a href="${pageContext.request.contextPath}/login-history">View Login History</a></li>
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
    // This function will be called by the "Dismiss" link
    function dismissNotification(notificationId, listItemElement) {

        // --- THE FIX IS HERE ---
        // We create a URLSearchParams object to safely build the request body.
        // This is the professional way to send form data in a POST request.
        const formData = new URLSearchParams();
        formData.append('id', notificationId); // Add the 'id' parameter and its value

        fetch(`${pageContext.request.contextPath}/api/notifications/mark-as-read`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: formData // Use the new formData object as the body
        })
            .then(response => {
                if (response.ok) {
                    listItemElement.remove();
                } else {
                    alert('Could not dismiss notification. Server returned an error.');
                }
            })
            .catch(error => console.error('Error dismissing notification:', error));
    }

    document.addEventListener('DOMContentLoaded', function () {
        fetch('${pageContext.request.contextPath}/api/dashboard-stats')
            .then(response => response.json())
            .then(data => {
                // Update Statistics
                const stats = data.statistics;
                document.getElementById('customer-count').textContent = stats.customerCount;
                document.getElementById('total-stock').textContent = stats.totalStock;

                // Update Notifications
                const notifications = data.notifications;
                const notificationList = document.getElementById('notifications-list');
                notificationList.innerHTML = '';

                if (notifications && notifications.length > 0) {
                    notifications.forEach(notification => {
                        const li = document.createElement('li');

                        // THE FIX: We create the text part and the link part separately
                        const messageText = document.createTextNode(notification.message + ' '); // Create text node

                        const dismissLink = document.createElement('a');
                        dismissLink.href = "#";
                        dismissLink.textContent = "[Dismiss]";
                        dismissLink.onclick = function () {
                            dismissNotification(notification.notificationId, li);
                            return false; // Prevent page from jumping
                        };

                        // Append both parts to the list item
                        li.appendChild(messageText);
                        li.appendChild(dismissLink);

                        notificationList.appendChild(li);
                    });
                } else {
                    notificationList.innerHTML = '<li>No new notifications.</li>';
                }
            })
            .catch(error => console.error('Error fetching dashboard data:', error));
    });
</script>

</body>
</html>