<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Customer</title>
    <!-- Simple styling for a cleaner look -->
    <style>
        body { font-family: sans-serif; margin: 2em; }
        .form-group { margin-bottom: 1em; }
        label { display: block; margin-bottom: 0.25em; }
        input { padding: 0.5em; width: 300px; }
        button { padding: 0.5em 1em; }
    </style>
</head>
<body>

<h1>Pahana Edu - Add New Customer</h1>

<%-- This form will send its data to our CustomerController servlet --%>
<form action="customer-add" method="post">
    <div class="form-group">
        <label for="accountNumber">Account Number:</label>
        <input type="text" id="accountNumber" name="accountNumber" required>
    </div>
    <div class="form-group">
        <label for="fullName">Full Name:</label>
        <input type="text" id="fullName" name="fullName" required>
    </div>
    <div class="form-group">
        <label for="address">Address:</label>
        <input type="text" id="address" name="address">
    </div>
    <div class="form-group">
        <label for="phoneNumber">Phone Number:</label>
        <input type="text" id="phoneNumber" name="phoneNumber">
    </div>
    <button type="submit">Add Customer</button>
</form>

</body>
</html>