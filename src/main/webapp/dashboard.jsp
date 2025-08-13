<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<h1>Welcome to the Dashboard!</h1>

<p>You have successfully logged in.</p>

<%-- We will create the logout servlet in the next step --%>
<p><a href="${pageContext.request.contextPath}/logout">Logout</a></p>

<hr>

<h3>Available Actions:</h3>
<ul>
    <li><a href="${pageContext.request.contextPath}/customer-form.jsp">Add New Customer</a></li>
    <li><a href="${pageContext.request.contextPath}/customers">View All Customers</a></li>
    <%-- We will add more links here as we build more features --%>
</ul>

</body>
</html>